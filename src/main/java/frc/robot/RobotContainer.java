// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/* Speed  */

package frc.robot;

/* Load Shooter Code */
import frc.robot.Shooter.ShooterContainer;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.Shooter.ShooterConstants;
import frc.robot.Shooter.ShooterConstants.LauncherConstants;
import static frc.robot.Shooter.ShooterConstants.LauncherConstants.kLaunchFeederSpeed;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants; 
import com.ctre.phoenix6.hardware.TalonFX;

//import frc.robot.Arm.Arm;

public class RobotContainer {
  
  public final CommandXboxController lJoystick = new CommandXboxController(0);
  
 
    
  private final Joystick Rdriver = new Joystick(1);
  private final Joystick Ldriver = new Joystick(0); 

  private final TalonFX lClimber = new TalonFX(31); // left climber 
  private final TalonFX rClimber = new TalonFX(30); // right climber

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.25/kAAngleDiv * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private Command runAuto1 = drivetrain.getAutoPath("middle path");
  private Command runAuto2 = drivetrain.getAutoPath("right path");
  private Command runAuto3 = drivetrain.getAutoPath("left path");

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.25) // We use a 25% deadband with our Xbox controllers
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  public static final double kDSpeedDiv =  1.3; // Value for controlling controller sensitivity
  public static final double kAAngleDiv =  1.0; // Value for controllig how fast robot moves when spining

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(()  ->  {
          System.out.println("joystick.getRightX=" + -lJoystick.getRightX() );
           return drive.withVelocityX(lJoystick.getLeftY() * MaxSpeed/kDSpeedDiv) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(lJoystick.getLeftX() * MaxSpeed/kDSpeedDiv) // Drive left with negative X (left)
            .withRotationalRate(-lJoystick.getRightX()/kAAngleDiv * MaxAngularRate);
            
         } // Drive counterclockwise with negative X (left)
        ));

    //joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
     //lJoystick.b().whileTrue(drivetrain
       // .applyRequest(() -> point.withModuleDirection(new Rotation2d(-lJoystick.getLeftY(), -lJoystick.getLeftX()))));
      lJoystick.x().whileTrue(drivetrain.applyRequest(() -> drive.withRotationalRate(MaxAngularRate)));
      lJoystick.b().whileTrue(drivetrain.applyRequest(() -> drive.withRotationalRate(MaxAngularRate * -1)));

      lJoystick.rightTrigger().onTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
        rClimber.set(0.5);
      })
    );

    lJoystick.rightTrigger().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
      })
    );

    lJoystick.rightBumper().onTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        rClimber.set(-0.5);
      })
    );

    lJoystick.rightBumper().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
      })
    );

    lJoystick.leftTrigger().onTrue( // move left motor clockwise on right trigger
      new InstantCommand(() -> {
        lClimber.set(0.5);
      })
    );

    lJoystick.leftTrigger().onFalse( // stop motor when not in use
      new InstantCommand(() -> {
        lClimber.set(0);
      })
    );

    lJoystick.leftBumper().onTrue( // move left motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        lClimber.set(-0.5);
      })
    );

    lJoystick.leftBumper().onFalse( // stop motor when not in use
      new InstantCommand(() -> {
        lClimber.set(0);
      })
    );
    

    // reset the field-centric heading on left bumper press
    //joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);


    lJoystick.x().onTrue(
      new InstantCommand(() -> {
        lClimber.set(0.5);
        rClimber.set(0.5);
      })
    );
    lJoystick.x().onFalse(
      new InstantCommand(() -> {
        lClimber.set(0);
        rClimber.set(0);
      })
    );
    lJoystick.y().onTrue(
      new InstantCommand(() -> {
        lClimber.set(-0.5);
        rClimber.set(-0.5);
      })
    );

    lJoystick.y().onFalse(
      new InstantCommand(() -> {
        lClimber.set(0);
        rClimber.set(0);
      })
    );
  }


  public RobotContainer() {
    configureBindings();
  }
 
  public Command getAutonomousCommand() {
    // return runAuto1;
    SwerveRequest driveForward = new SwerveRequest.RobotCentric().withVelocityX(.5);

    return drivetrain.run(() -> drivetrain.setControl(driveForward));
  }

  public void teleopPeriodic()
  {
  }
}
