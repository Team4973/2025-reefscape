package frc.robot.Vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.security.AlgorithmConstraints;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Limelight {
    // create a new NetworkTables instance called limelightNT
    NetworkTableInstance limelightNT = NetworkTableInstance.getDefault();
    // x
    DoubleSubscriber txSub = limelightNT.getDoubleTopic("/limelight/tx").subscribe(0.0);
    // y
    DoubleSubscriber tySub = limelightNT.getDoubleTopic("/limelight/ty").subscribe(0.0);
    // area
    DoubleSubscriber taSub = limelightNT.getDoubleTopic("/limelight/ta").subscribe(0.0);
   // get current limelight pipeline type
    DoubleSubscriber pipelineSub = limelightNT.getDoubleTopic("/limelight/getpipe").subscribe(0.0);
    // get the id of april tag currently inview
    DoubleSubscriber idSub = limelightNT.getDoubleTopic("/limelight/tid").subscribe(0.0);

    public double getLimelightTX() {
        double tx = txSub.get();
        return tx;
       
    }
    
    public double getLimelightTY() {
        double ty = tySub.get();
        return ty;
    }

    public double getLimelightTA() {
        double ta = taSub.get();
        return ta;
    }

    public double gteLimelightTID() {
        double tid = idSub.get();
        return tid;
    }

    public Command alignLeft() {


        return alignLeft();
    }
}

