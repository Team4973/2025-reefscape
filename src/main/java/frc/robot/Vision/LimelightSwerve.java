package frc.robot.Vision;

import frc.robot.Vision.Limelight;

import frc.robot.Robot;

public class LimelightSwerve {
    double getLimelightTX;
    double getLimelightTY;
    double getLimelightTZ;

    public double Distance() {
    // compute distance to AprilTag using tx, ty, and tz distance values
    double distance = Math.sqrt(getLimelightTX * 
    getLimelightTX + getLimelightTY * getLimelightTY + getLimelightTZ * 
    getLimelightTZ);

    return distance;
    }
}
