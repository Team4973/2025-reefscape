package frc.robot.DistributionHub;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class PowerDistributionHub {

    public int SelectedChannelNumber = 0; // number of slot on PDH for monitoring current through individaul channel
    
    PowerDistribution MainPowerDistributionHub = new PowerDistribution(1, ModuleType.kRev); // CAN ID must be 1 for REV PDH. If using CTRE PDP, use 0.

    public void getVoltage() {// get the voltage coming from the battery of the robot to the PDH
        double Voltage = MainPowerDistributionHub.getVoltage();
        SmartDashboard.putNumber("System Voltage", Voltage);
    }

    public void getTemperatureInC() { // get temperature of power distribution hub in celsius
        double TemperatureInC = MainPowerDistributionHub.getTemperature();
        SmartDashboard.putNumber("PDH Temp", TemperatureInC);
    }

    public void getTotalCurrent() { // get current coming from battery
        double TotalCurrent = MainPowerDistributionHub.getTotalCurrent();
        SmartDashboard.putNumber("System Current", TotalCurrent);
    }

    public void getIndividualCurrent() { // get current of each individual channel
        double selectedPortCurrent = MainPowerDistributionHub.getCurrent(SelectedChannelNumber); 
    }

    public void LEDs() { 
        MainPowerDistributionHub.setSwitchableChannel(true);
    }
}
    
    
