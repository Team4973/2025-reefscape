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

    public double getCH1current() {
        double CH1current = PowerDistributionCore.getCurrent(1);

        return CH1current;
    }

    public double getCH2current() {
        double CH2current = PowerDistributionCore.getCurrent(2);

        return CH2current;
    }

    public double getCH3current() {
        double CH3current = PowerDistributionCore.getCurrent(3);

        return CH3current;
    }

    public double getCH4current() {
        double CH4current = PowerDistributionCore.getCurrent(4);

        return CH4current;
    }

    public double getCH5current() {
        double CH5current = PowerDistributionCore.getCurrent(5);

        return CH5current;
    }

    public double getCH6current() {
        double CH6current = PowerDistributionCore.getCurrent(6);

        return CH6current;
    }

    public double getCH7current() {
        double CH7current = PowerDistributionCore.getCurrent(7);

        return CH7current;
    }

    public double getCH8current() {
        double CH8current = PowerDistributionCore.getCurrent(8);

        return CH8current;
    }

    public double getCH9current() {
        double CH9current = PowerDistributionCore.getCurrent(9);

        return CH9current;
    }

    public double getCH10current() {
        double CH10current = PowerDistributionCore.getCurrent(10);

        return CH10current;
    }

    public double getCH11current() {
        double CH11current = PowerDistributionCore.getCurrent(11);

        return CH11current;
    }

    public double getCH12current() {
        double CH12current = PowerDistributionCore.getCurrent(12);

        return CH12current;
    }
    
    public double getCH13current() {
        double CH13current = PowerDistributionCore.getCurrent(13);

        return CH13current;
    }

    public double getCH14current() {
        double CH14current = PowerDistributionCore.getCurrent(14);

        return CH14current;
    }

    public double getCH15current() {
        double CH15current = PowerDistributionCore.getCurrent(15);

        return CH15current;
    }
    
    public double getCH16current() {
        double CH16current = PowerDistributionCore.getCurrent(16);

        return CH16current;
    }

    public double getCH17current() {
        double CH17current = PowerDistributionCore.getCurrent(17);

        return CH17current;
    }

    public double getCH18current() {
        double CH18current = PowerDistributionCore.getCurrent(18);

        return CH18current;
    }
    
    public double getCH19current() {
        double CH19current = PowerDistributionCore.getCurrent(19);

        return CH19current;
    }

    public double getCH20current() {
        double CH20current = PowerDistributionCore.getCurrent(20);

        return CH20current;
    }

    public double getCH21current() {
        double CH21current = PowerDistributionCore.getCurrent(21);

        return CH21current;
    }
    
    public double getCH22current() {
        double CH22current = PowerDistributionCore.getCurrent(22);

        return CH22current;
    }

    public double getCH23current() {
        double CH23current = PowerDistributionCore.getCurrent(23);

        return CH23current;
    }

    

    public void putSmartdashboardPower() {
        // get system-wide statistics
        /*
        SmartDashboard.putNumber("voltage", getVoltage());
        SmartDashboard.putNumber("pdh temp", getPDHTemp()); 
        SmartDashboard.putNumber("total current", getTotalCurrent());
        */

        // get the current draw of indv. PDH channels 
         
        // SmartDashboard.putNumber("CH1 Current", getCH1current());
        // SmartDashboard.putNumber("CH2 Current", getCH2current());
        // SmartDashboard.putNumber("CH3 Current", getCH3current());
        // SmartDashboard.putNumber("CH4 Current", getCH4current());
        // SmartDashboard.putNumber("CH5 Current", getCH5current());
        // SmartDashboard.putNumber("CH6 Current", getCH6current());
        // SmartDashboard.putNumber("CH7 Current", getCH7current()); // Limelight
        // SmartDashboard.putNumber("CH8 Current", getCH8current());
        // SmartDashboard.putNumber("CH9 Current", getCH9current());
        // SmartDashboard.putNumber("CH10 Current", getCH10current());
        // SmartDashboard.putNumber("CH11 Current", getCH11current());
        // SmartDashboard.putNumber("CH12 Current", getCH12current());
        // SmartDashboard.putNumber("CH13 Current", getCH13current());
        // SmartDashboard.putNumber("CH14 Current", getCH14current());
        // SmartDashboard.putNumber("CH15 Current", getCH15current());
        // SmartDashboard.putNumber("CH16 Current", getCH16current());
        // SmartDashboard.putNumber("CH17 Current", getCH17current());
        // SmartDashboard.putNumber("CH18 Current", getCH18current());
        // SmartDashboard.putNumber("CH19 Current", getCH19current());
        // SmartDashboard.putNumber("CH20 Current", getCH20current());
        // SmartDashboard.putNumber("CH21 Current", getCH21current());
        // SmartDashboard.putNumber("CH22 Current", getCH22current());
        // SmartDashboard.putNumber("CH23 Current", getCH23current());
        //SmartDashboard.putNumber("CH24 Current", getCH24current()); // LEDs
        
 
    }
     
    public void setLEDs() {
    PowerDistributionCore.setSwitchableChannel(true);
    }

}


