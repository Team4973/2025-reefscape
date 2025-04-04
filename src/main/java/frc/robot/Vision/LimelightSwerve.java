package frc.robot.Vision;

import frc.robot.Vision.Limelight;

import frc.robot.Robot;

import java.lang.Math;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric;


public class LimelightSwerve extends Command{

    boolean isAligned;
    double getLimelightTX;
    double getLimelightTY;
    double getLimelightTZ;
    CommandSwerveDrivetrain drivetrain;
    FieldCentric drive;
    Limelight limelightContainer;

    public LimelightSwerve(
        CommandSwerveDrivetrain drivetrain,
        FieldCentric drive,
        Limelight limelightContainer
    ) {
        this.drivetrain = drivetrain;
        this.drive = drive;
        this.limelightContainer = limelightContainer;
    }

    public double Distance() {
    // compute distance to AprilTag using tx, ty, and tz distance values
        double distance = Math.sqrt(getLimelightTX * 
        getLimelightTX + getLimelightTY * getLimelightTY + getLimelightTZ * 
        getLimelightTZ);

        return distance;
    }

    // move the swerve drive-base to align with the AprilTag
    public void alignToTag(CommandSwerveDrivetrain drivetrain, FieldCentric drive, Limelight limelightContainer) {
        if (limelightContainer.getLimelightTX() > 0) {
            System.out.println("Go right!");
           // drivetrain.
            //drive.withVelocityX(0)
            //.withVelocityY(1)
            //.withRotationalRate(0);
        } 
        else if(limelightContainer.getLimelightTX() < 0){
            System.out.println("Go left!");
            drive.withVelocityX(0)
            .withVelocityY(-1)
            .withRotationalRate(0);
        } 
        else {
            System.out.println("STOP!");
            drive.withVelocityX(0)
            .withVelocityY(0)
            .withRotationalRate(0);
        }
    }

    // Called once when the command is scheduled
  @Override
  public void initialize() {
    // TODO:  figure out if the tag is to the left or right
    // start the robot going the correct direction
    System.out.println("Initialize!");
    alignToTag(drivetrain, drive, limelightContainer);

  }

  // Called repeatedly until the command ends
  @Override
  public void execute() {
    // Process vision data continuously, such as updating target info
    //System.out.println("Execute!");
    
  }

  // Determines when the command should finish
  @Override
  public boolean isFinished() {
    // TODO:
    // For instance, finish when a valid target is acquired
    // Check to see if we are aligned or we can no longer see the tag
    // If we are about to return isFinished true, then stop the robot
    //System.out.println(limelightContainer.getLimelightTX());

    if (Math.abs(limelightContainer.getLimelightTX()) < 10) {
            System.out.println("Stoppping");
            return true;
    }
    else {
        System.out.println("Keep going");
        return false;
    }
  }

  // Called when the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    // Turn off the LED to conserve power or signal end of processing

    // TODO: Stop the robot
    System.out.println("STOP!");
    drive.withVelocityX(0)
            .withVelocityY(0)
            .withRotationalRate(0);
  }
}
