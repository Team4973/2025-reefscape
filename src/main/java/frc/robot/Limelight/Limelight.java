package frc.robot.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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

    public void getLimelightValues() {
        double tx = txSub.get();
        double ty = tySub.get();
        double ta = taSub.get();
        double pipeline = pipelineSub.get();
        double id = idSub.get();
       // System.out.println("ty =" + ty + "tx =" + tx + "ta" + ta +"pipeline type:"+ pipeline + "id:" + id);
    }
}

