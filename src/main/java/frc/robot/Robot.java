// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Shooter.ShooterContainer;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  //public final ShooterContainer m_operatorController;

  private SerialPort serial;

  public Robot() {
    
    m_robotContainer = new RobotContainer();
    //m_operatorController = new ShooterContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {

    //serial = new SerialPort(9600, Port.kUSB);

    //SerialPort serial = new SerialPort(9600, SerialPort.Port.kMXP);
    
    //int pattern = 2;

    //serial.writeString(pattern + "\n");

    //serial.writeString(Integer.toString(pattern) + "\n" );

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    int pattern = 2;
    try {
      serial.writeString("2\n");
      System.out.println("Sent" + pattern);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void teleopExit() {
    if (serial != null) {
      serial.close();
  }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
