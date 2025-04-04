// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CoralShooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.CoralShooter.Autos;
import frc.robot.CoralShooter.CANLauncher;
import frc.robot.CoralShooter.CoralShooterConstants.LauncherConstants;
import frc.robot.CoralShooter.CoralShooterConstants.OperatorConstants;
import frc.robot.commands.LaunchCoral;
/*import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.PWMLauncher; */

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class CoralShooterContainer {
  // The robot's subsystems are defined here.
  //private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  //private final PWMLauncher m_launcher = new PWMLauncher();
   private final CANLauncher m_launcher = new CANLauncher();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public CoralShooterContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_operatorController
        .rightTrigger()
        .whileTrue(
            new LaunchCoral(m_launcher)
                .handleInterrupt(() -> m_launcher.stop())
        );
  }

  public void shootCoral() {
    m_launcher.launchCoral(LauncherConstants.kAutoLauncherSpeed);
  }

  public void stop() {
    m_launcher.stop();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

 /* public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain);
  }  */

}