
package frc.robot.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.generated.TunerConstants;
import frc.robot.RobotContainer;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.Utils;

public class Lift {

  public CommandXboxController ArmOperatorController =
      new CommandXboxController(0);


    public final TalonFX rClimber = new TalonFX(30); // right climber
    //
    
    public void ClimbWithFalcon() {

    ArmOperatorController.rightTrigger().onTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
        rClimber.set(-0.5);
      })
    );

    ArmOperatorController.rightTrigger().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
      })
    );

    ArmOperatorController.rightBumper().onTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        rClimber.set(0.5);
      })
    );

    ArmOperatorController.rightBumper().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
      })
    );
    }



}

