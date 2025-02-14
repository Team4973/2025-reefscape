package frc.robot.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.reflect.Constructor;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    NetworkTableInstance nt = NetworkTableInstance.getDefault();
    DoubleSubscriber txSub = nt.getDoubleTopic("/limelight/tx").subscribe(0.0);
    DoubleSubscriber tySub = nt.getDoubleTopic("/limelight/ty").subscribe(0.0);
    DoubleSubscriber taSub = nt.getDoubleTopic("/limelight/ta").subscribe(0.0);

    public void getLimelightValues() {
        double tx = txSub.get();
        double ty = tySub.get();
        double ta = taSub.get();
        System.out.println("ty =" + ty + "tx =" + tx + "ta" + ta);
    }
}

