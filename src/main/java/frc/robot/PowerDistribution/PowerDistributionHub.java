package frc.robot.PowerDistribution;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PowerDistributionHub {
    PowerDistribution PowerDistributionCore = new PowerDistribution(1, ModuleType.kRev);

    public double getVoltage() {
        double voltage = PowerDistributionCore.getVoltage(); //PowerDistributionCore.getVoltage();

        return voltage;
    }

    public double getPDHTemp() {
        double PDHTemp = PowerDistributionCore.getTemperature();

        return PDHTemp;
    }

    public double getTotalCurrent() {
        double totalCurrent = PowerDistributionCore.getTotalCurrent();

        return totalCurrent;
    }

    public void putSmartdashboardPower() {
        SmartDashboard.putNumber("voltage", getVoltage());
        SmartDashboard.putNumber("pdh temp", getPDHTemp()); 
        SmartDashboard.putNumber("total current", getTotalCurrent());
    }
    

}


