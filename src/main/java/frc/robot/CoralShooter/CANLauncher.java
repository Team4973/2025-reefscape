// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CoralShooter;

import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kFeedCurrentLimit;
import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kFeederID;
import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kIntakeFeederSpeed;
import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kIntakeLauncherSpeed;
import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kLauncherCurrentLimit;
import static frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants.kLauncherID;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANLauncher extends SubsystemBase {
  public final TalonFX m_leftLaunchWheel; // left wheel
  public final TalonFX m_rightLaunchWheel; // right wheel


  //Limit Switch code

  /** Creates a new Launcher. */
  public CANLauncher() {
    m_leftLaunchWheel = new TalonFX(47); // left climber 
    m_rightLaunchWheel = new TalonFX(48); // right climber
  }

  /**
   * This method is an example of the 'subsystem factory' style of command creation. A method inside
   * the subsytem is created to return an instance of a command. This works for commands that
   * operate on only that subsystem, a similar approach can be done in RobotContainer for commands
   * that need to span subsystems. The Subsystem class has helper methods, such as the startEnd
   * method used here, to create these commands.
   */
  public Command getIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
      // When the command is initialized, set the wheels to the intake speed values
      () -> {
        launchCoral(kIntakeLauncherSpeed);
      },
      // When the command stops, stop the wheels
      () -> {
        stop();
      });
    }
    


  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  public void launchCoral(double speed) {
    m_leftLaunchWheel.set(-speed);
    m_rightLaunchWheel.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_leftLaunchWheel.set(0);
    m_rightLaunchWheel.set(0);
  }
}
