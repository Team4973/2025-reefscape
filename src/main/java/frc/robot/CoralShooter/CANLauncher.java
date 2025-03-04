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

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANLauncher extends SubsystemBase {
  SparkMax m_launchWheel;
  SparkMax m_feedWheel;
  SparkMaxConfig m_launchWheelConfig;
  SparkMaxConfig m_feedWheelConfig;

  //Limit Switch code

  /** Creates a new Launcher. */
  public CANLauncher() {
    m_launchWheel = new SparkMax(kLauncherID, MotorType.kBrushless);
    m_feedWheel = new SparkMax(kFeederID, MotorType.kBrushless);
    m_launchWheelConfig = new SparkMaxConfig();
    m_feedWheelConfig = new SparkMaxConfig();

    m_launchWheelConfig.smartCurrentLimit(kLauncherCurrentLimit);
    m_feedWheelConfig.smartCurrentLimit(kFeedCurrentLimit);

    m_launchWheel.configure(m_launchWheelConfig, null, null);
    m_feedWheel.configure(m_feedWheelConfig, null, null);
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
        setFeedWheel(kIntakeFeederSpeed);
        //negative for crescendo bot, positive for reefscape bot.
        setLaunchWheel(-kIntakeLauncherSpeed);
      },
      // When the command stops, stop the wheels
      () -> {
        stop();
      });
    }
    


  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  public void setLaunchWheel(double speed) {
    m_launchWheel.set(-speed);
  }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    m_feedWheel.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_launchWheel.set(0);
    m_feedWheel.set(0);
  }
}
