// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CoralShooter.CoralShooterContainer;
import frc.robot.CoralShooter.CoralShooterConstants;
import frc.robot.commands.Elevator;
import frc.robot.Vision.Limelight;
import frc.robot.Vision.LimelightSwerve;
import frc.robot.PowerDistribution.PowerDistributionHub;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotContainer;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  public final CoralShooterContainer m_operatorController;
  public final Limelight limelightContainer;
  public final LimelightSwerve limelightSwerve; 
  public final PowerDistributionHub powerDistributionHub;
 
  private int previousPOV = -1;

  private final XboxController joystick = new XboxController(0); 
  public final CommandXboxController xboxController = new CommandXboxController(0);

  private  Elevator elevator;
  // initalize serial for Arduino LED subsystem communication 
  private SerialPort serial;

  public Robot() {
    
    m_robotContainer = new RobotContainer();
    m_operatorController = new CoralShooterContainer();
    limelightContainer = new Limelight();
    limelightSwerve = new LimelightSwerve();
     elevator = new Elevator(xboxController);
     elevator.ClimbWithFalcon();
    powerDistributionHub = new PowerDistributionHub();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
    powerDistributionHub.putSmartdashboardPower();
    m_robotContainer.putSmartdashboardRobotContainer();
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

    powerDistributionHub.putSmartdashboardPower();
    m_robotContainer.putSmartdashboardRobotContainer();

    m_robotContainer.kSpeedDiv = 4.0;

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

    powerDistributionHub.putSmartdashboardPower();
    m_robotContainer.putSmartdashboardRobotContainer();
  

    elevator.elevatorPeriodic();


    //boolean fast = 

    // int pov = joystick.getPOV();

    // if (pov == 0 && previousPOV != 0) {
    //   m_robotContainer.kSpeedDiv -= 1.0; // subtract 1 from speed dvider for faster drive
      
    // } else if (pov == 90 && previousPOV != 90) {
    //   // currently unused
    // } else if (pov == 180 && previousPOV != 180) {
    //   m_robotContainer.kSpeedDiv += 1.0; // add 1 to speed divider for slower drive 
      
    // } else if (pov == 270 && previousPOV != 270) {
    //   m_robotContainer.kSpeedDiv = 4.0; // reset to default speed (4.0)
      
    // }
    // previousPOV = pov; - Changed to bumpers, can be optimized (Slavik)

    limelightContainer.configureLimelight();

    limelightContainer.getLimelightTX();
    limelightContainer.getLimelightTY();
    limelightContainer.getLimelightTZ();
    limelightContainer.getLimelightTID();

    /* 
    limelightSwerve.Distance(getLimelightTX, );
    System.out.println("Distance = " + distance);
    */
     //SmartDashboard.putNumber("voltage", getVoltage());
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
