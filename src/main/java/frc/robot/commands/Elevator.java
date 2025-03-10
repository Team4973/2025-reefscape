package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
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

public class Elevator {
  DigitalInput limitswitchUp = new DigitalInput(0);
  DigitalInput limitswitchDown = new DigitalInput(1);
     
  enum ElevatorDirection {
    ELEVATOR_UP, ELEVATOR_DOWN, ELEVATOR_STOPPED
  }

  ElevatorDirection direction = ElevatorDirection.ELEVATOR_STOPPED;

   public CommandXboxController elevatorOperatorController;
    public Elevator (CommandXboxController rc){
        elevatorOperatorController = rc;
    }

   
    public final TalonFX lClimber = new TalonFX(31); // left climber 
    public final TalonFX rClimber = new TalonFX(30); // right climber
    //
    
    public void ClimbWithFalcon() {

    elevatorOperatorController.y().onTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
        rClimber.set(0.5);
        lClimber.set(-0.5);
        /*
         * TODO: set the direction to UP
         */
      })
    );

    elevatorOperatorController.y().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
        lClimber.set(0);
        /*
         * TODO: st the direction to STOPPED
         */
      })
    );

    elevatorOperatorController.a().onTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        rClimber.set(-0.5);
        lClimber.set(0.5);
        /*
         * TODO: Set the direction to DOWN
         */
      })
    );

    elevatorOperatorController.a().onFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
        lClimber.set(0);
        /*
         * TODO: Set the direction to STOPPED
         */
      })
    );

    /*
     * TODO:
     * We need an elevatorPeriodic() method.  This method will decide
     * what to do based on the values of the limit switches.
     * 
     * General algorithm:
     * 
     * If the elevator is going up and the upper limit switch is 
     * engaged, then turn off the motor.  Set the direction to NONE
     * 
     * If the elevator is going down and the lower limit switch is
     * engaged, then turn off the motor.  Set the direction to NONE
     * 
     *  direction = ElevatorDirection.ELEVATOR_STOPPED;
     *  if (direction == ElevatorDirection.ELEVATOR_DOWN)
     */
   
    //  if(limitswitch.get() == false){
    //   System.out.println("STOP");
    // }
    // else{
    //   System.out.println("Peace");
    // }

    }



}