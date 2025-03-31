package frc.robot.commands;

import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.Follower;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import frc.robot.generated.TunerConstants;
import frc.robot.RobotContainer;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.Utils;

public class Elevator {

  double level[] = { 0.0, 10.0, 20.0, 30.0, 40.0 };
  double level[] = { 0.0, 2.6, 10.0, 20.0, 30.0, 40.0 };

  public CommandXboxController elevatorOperatorController;
  double rotations;
  int currentLevel;

  //DigitalInput limitswitchUp = new DigitalInput(0);
  //DigitalInput limitswitchDown = new DigitalInput(1);

  enum ElevatorDirection {
    ELEVATOR_UP, ELEVATOR_DOWN, ELEVATOR_STOPPED
  }

  ElevatorDirection direction = ElevatorDirection.ELEVATOR_STOPPED;

  public final TalonFX lClimber = new TalonFX(31); // left climber 
  public final TalonFX rClimber = new TalonFX(30); // right climber
  public final TalonFX rClimber = new TalonFX(30); // right climber - was  30

  //Follower rightFollower = new Follower(31, false);

  //rClimber.setControl(rightFollower);

  private final PositionDutyCycle leftPositionControl = new PositionDutyCycle(0);
  private final PositionDutyCycle rightPositionControl = new PositionDutyCycle(0);

  public void setPosition(TalonFX climberMotor, PositionDutyCycle positionControl, double rotations) {
    climberMotor.setControl(positionControl.withPosition(rotations));
  }

  public double getPosition(TalonFX climberMotor) {
    return climberMotor.getPosition().getValueAsDouble();
  }

  public Elevator (CommandXboxController rc) {
    this.rotations = 0;
    this.currentLevel = 0;
    this.elevatorOperatorController = rc;
  }

  public void setMotorConfiguration(TalonFX climberMotor) {
     
    TalonFXConfiguration configs = new TalonFXConfiguration();   

    // PID Gains (Tune these for your setup)
    configs.Slot0.kP = 0.1;
    configs.Slot0.kI = 0.0;
    configs.Slot0.kD = 0.01;
    configs.Slot0.kV = 0.0; // Optional feedforward
    configs.Slot0.kS = 0.05;
    configs.Slot0.kA = 0.001;
     
    climberMotor.getConfigurator().apply(configs);
    climberMotor.setPosition(0); // Reset encoder to zero
  }

  public void ClimbWithFalcon() {
    setMotorConfiguration(lClimber);
    setMotorConfiguration(rClimber);

    //we think there are 40.5 rotations for the elevator to reach the top from the bottom 

    elevatorOperatorController.y().whileTrue( // move right motor clockwise on right trigger
      new InstantCommand(() -> {
          if (currentLevel < level.length - 1) {
            currentLevel++;
            rotations = level[currentLevel];
            setPosition(rClimber, rightPositionControl, rotations);
            setPosition(lClimber, leftPositionControl, -rotations);
  
            direction = ElevatorDirection.ELEVATOR_UP;
            System.out.println("up");
          }
          else {
            System.out.println("Heresy!");
          }
      })
    );

    elevatorOperatorController.y().whileFalse( // stop when not in use
      new InstantCommand(() -> {
        direction = ElevatorDirection.ELEVATOR_STOPPED;
      })
    );

    elevatorOperatorController.a().whileTrue( // move right motor counter-clockwise on right bumper
      new InstantCommand(() -> {
          if (currentLevel != 0) {
            currentLevel--;
            rotations = level[currentLevel];
            setPosition(rClimber, rightPositionControl, rotations);
            setPosition(lClimber, leftPositionControl, -rotations);
            direction = ElevatorDirection.ELEVATOR_DOWN;
            System.out.println("down");
          }
          else {
            System.out.println("Heresy!");
          }
      })
    );

    elevatorOperatorController.a().whileFalse( // stop when not in use
      new InstantCommand(() -> {
        direction = ElevatorDirection.ELEVATOR_STOPPED;
      })
    );
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
  }
}