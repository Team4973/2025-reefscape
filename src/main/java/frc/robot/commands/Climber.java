package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
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

public class Climber {

   public CommandXboxController elevatorOperatorController;
    public Climber (CommandXboxController rc){
        elevatorOperatorController = rc;
    }

   
    public final TalonFX climber = new TalonFX(32); // left climber 
    //
    
    public void ClimbWithFalcon() {

    elevatorOperatorController.rightTrigger().onTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
        climber.set(0.5);
      })
    );

    elevatorOperatorController.rightTrigger().onFalse( // stop when not in use
      new InstantCommand(() -> {
        climber.set(0);
      })
    );

    elevatorOperatorController.rightBumper().onTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        climber.set(-0.5);
      })
    );

    elevatorOperatorController.rightBumper().onFalse( // stop when not in use
      new InstantCommand(() -> {
        climber.set(0);
      })
    );

    }



}