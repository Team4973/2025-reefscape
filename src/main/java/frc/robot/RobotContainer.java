// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/* Speed  */

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveModule.DriveRequestType;
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
  
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private Command runAuto1 = drivetrain.getAutoPath("demo path");
  private Command runAuto2 = drivetrain.getAutoPath("right path");
  private Command runAuto3 = drivetrain.getAutoPath("left path"); 

  private final LegacySwerveRequest.FieldCentric drive = new LegacySwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.25) // We use a 25% deadband with our Xbox controllers
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final LegacySwerveRequest.SwerveDriveBrake brake = new LegacySwerveRequest.SwerveDriveBrake();
  private final LegacySwerveRequest.PointWheelsAt point = new LegacySwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  public static final double kDSpeedDiv =  1.2; // Value for controlling controller sensitivity
  public static final double kAAngleDiv =  1.0; // Value for controllig how fast robot moves when spining

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(()  ->  {
          //System.out.println("joystick.getRightX=" + -lJoystick.getRightX() );
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
      lJoystick.leftBumper().whileTrue(new InstantCommand(() -> {
        zeroPigeon();
        System.out.println("pigeon rezeroed");
      }));
    

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

  public void zeroPigeon() {
    final Pigeon2 pigeon = drivetrain.getPigeon2();
    pigeon.reset();
  }
 
  public Command getAutonomousCommand() {
    return runAuto1;
    //SwerveRequest driveForward = new SwerveRequest.RobotCentric().withVelocityX(.5);

    //return drivetrain.run(() -> drivetrain.setControl(driveForward));
  }

  public void teleopPeriodic()
  {
  }
}
