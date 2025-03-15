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

/*
 * TODO:
 *
 * We need to add these methods to the Elevator class.
 * Then we need to use them instead of:
          rClimber.set(0);
 *
 * public void setPosition(TalonFX climberMotor, double rotations) {
 *       armMotor.setControl(positionControl.withPosition(rotations));
 *  }
 *
 *  public double getPosition(TalonFX climberMotor) {
 *       return armMotor.getPosition().getValueAsDouble();
 * }
 */

public class Elevator {
  DigitalInput limitswitchUp = new DigitalInput(0);
  DigitalInput limitswitchDown = new DigitalInput(1);

     
  enum ElevatorDirection {
    ELEVATOR_UP, ELEVATOR_DOWN, ELEVATOR_STOPPED
  }

  ElevatorDirection direction = ElevatorDirection.ELEVATOR_STOPPED;

   public CommandXboxController elevatorOperatorController;
    public Elevator (CommandXboxController rc){
        this.elevatorOperatorController = rc;
    }

   
    public final TalonFX lClimber = new TalonFX(31); // left climber 
    public final TalonFX rClimber = new TalonFX(30); // right climber

    /*
     * TODO:
     * We need a positonControl:
     * We need one of these for the left and right motors
     * private final PositionDutyCycle positionControl = new PositionDutyCycle(0); // Position control object
     */

    /*
     * TODO:
     *
     *  We need create a method to set the configuraion for the motors:
     *
     *  public setMotorConfiguration(TalonFX climberMotor) {
     *
     *  TalonFXConfiguration configs = new TalonFXConfiguration();
     * 
     *   // PID Gains (Tune these for your setup)
     *   configs.Slot0.kP = 0.1;
     *   configs.Slot0.kI = 0.0;
     *   configs.Slot0.kD = 0.0;
     *   configs.Slot0.kV = 0.0; // Optional feedforward
     *
     *   climberMotor.getConfigurator().apply(configs);
     *   climberMotor.setPosition(0); // Reset encoder to zero
     *  }
     *
     */
    
    public void ClimbWithFalcon() {

    elevatorOperatorController.y().whileTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
        if(limitswitchUp.get() == true){
          rClimber.set(0.5);
          lClimber.set(-0.5);
          direction = ElevatorDirection.ELEVATOR_UP;
          System.out.println("up");
        }
      })
    );

    elevatorOperatorController.y().whileFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
        lClimber.set(0);
        direction = ElevatorDirection.ELEVATOR_STOPPED;
      })
    );

    elevatorOperatorController.a().whileTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
        if(limitswitchDown.get() == true){
          rClimber.set(-0.5);
          lClimber.set(0.5);
          direction = ElevatorDirection.ELEVATOR_DOWN;
          System.out.println("down");
        }
      })
    );

    elevatorOperatorController.a().whileFalse( // stop when not in use
      new InstantCommand(() -> {
        rClimber.set(0);
        lClimber.set(0);
        direction = ElevatorDirection.ELEVATOR_STOPPED;
      })
    );

    
    /*
     * TODO: 
     * call setMotorConfiguration for the left climber
     * call setMotorConfiguration for the right climber
     */
  
   

    }

    /*
     * If the elevator is going up and the upper limit switch is 
     * engaged, then turn off the motor.  Set the direction to NONE
     * 
     * If the elevator is going down and the lower limit switch is
     * engaged, then turn off the motor.  Set the direction to NONE
     * 
     *  direction = ElevatorDirection.ELEVATOR_STOPPED;
     *  if (direction == ElevatorDirection.ELEVATOR_DOWN)
     */
    public void elevatorPeriodic() {
      if (direction == ElevatorDirection.ELEVATOR_DOWN) {
        if(limitswitchDown.get() == false){
          direction = ElevatorDirection.ELEVATOR_STOPPED;
          System.out.println("STOP");
          rClimber.set(0);
          lClimber.set(0);
          // if the down switch is false
          //     stop the motors
          //     direction = ElevatorDirection.ELEVATOR_STOPPED;
        }
      }
      if (direction == ElevatorDirection.ELEVATOR_UP) {
        if(limitswitchUp.get() == false){
          direction = ElevatorDirection.ELEVATOR_STOPPED;
          System.out.println("STOP");
          rClimber.set(0);
          lClimber.set(0);
        }
      }
      
    
  }
}
