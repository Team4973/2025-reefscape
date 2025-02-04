package frc.robot.PowerDistribution;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class PowerDistributionHub {
    PowerDistribution PowerDistributionCore = new PowerDistribution(1, ModuleType.kRev);

    public void getVoltage() {
        double voltage = 12.32; //PowerDistributionCore.getVoltage();
    }

    public void getPDHTemp() {
        double PDHTemp = PowerDistributionCore.getTemperature();
    }

    public void getTotalCurrent() {
        double totalCurrent = PowerDistributionCore.getTotalCurrent();
    }
}


