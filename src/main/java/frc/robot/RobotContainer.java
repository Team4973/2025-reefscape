// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
     
    
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

     private final TalonFX lClimber = new TalonFX(31); // left climber 
     private final TalonFX rClimber = new TalonFX(30); // right climber

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private Command runauto1 = drivetrain.getAutoPath("Auto4");

    public double kSpeedDiv = 4.0; // 4.0 is our prefered speed for somewhat fast movements

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed/kSpeedDiv) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed/kSpeedDiv) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        joystick.x().whileTrue(new InstantCommand(() -> {
            zeroPigeon();
            System.out.println("pigeon rezeroed");
        }));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
       // joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
       // joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        //joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        //joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press

        drivetrain.registerTelemetry(logger::telemeterize);

   /*     joystick.x().onTrue(
      new InstantCommand(() -> {
        lClimber.set(0.5);
        rClimber.set(0.5);
      })
    );
    joystick.x().onFalse(
      new InstantCommand(() -> {
        lClimber.set(0);
        rClimber.set(0);
      })
    );
    joystick.y().onTrue(
      new InstantCommand(() -> {
        lClimber.set(-0.5);
        rClimber.set(-0.5);
      })
    );

    joystick.y().onFalse(
      new InstantCommand(() -> {
        lClimber.set(0);
        rClimber.set(0);
      })
    );*/
  }
    
    

    public void zeroPigeon() {
    final Pigeon2 pigeon = drivetrain.getPigeon2();
    pigeon.reset();
  }

    public Command getAutonomousCommand() {
        //return Commands.print("No autonomous command configured");
        return runauto1;
        // SequentialCommandGroup auto_commands = new SequentialCommandGroup(
        //     Commands.print("Got Here!"),
        //     new PathPlannerAuto("Test")
        // );
        // return auto_commands;
        //return new PathPlannerAuto("Test");
    }

    public void putSmartdashboardRobotContainer() {
        SmartDashboard.putNumber("Input Speed Div", kSpeedDiv);
    }
}
