package frc.robot.PowerDistribution;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class PowerDistributionHub {
    PowerDistribution PowerDistributionCore = new PowerDistribution(1, ModuleType.kRev);

    public double getVoltage() {
        double voltage = 12.32; //PowerDistributionCore.getVoltage();

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
}


