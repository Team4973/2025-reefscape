"""
Power Distribution Hub monitoring subsystem.

Publishes voltage, temperature, total current, and per-channel current to
SmartDashboard.
"""

import wpilib
from commands2 import Subsystem
from wpilib import SmartDashboard

from constants import PDH_ID


class PowerDistributionHub(Subsystem):
    def __init__(self):
        super().__init__()
        self.pdh = wpilib.PowerDistribution(PDH_ID, wpilib.PowerDistribution.ModuleType.kRev)

    def publish_telemetry(self):
        """Publish PDH data to SmartDashboard."""
        SmartDashboard.putNumber("PDH/Voltage", self.pdh.getVoltage())
        SmartDashboard.putNumber("PDH/Temperature", self.pdh.getTemperature())
        SmartDashboard.putNumber("PDH/TotalCurrent", self.pdh.getTotalCurrent())
        for ch in range(24):
            SmartDashboard.putNumber(f"PDH/CH{ch} Current", self.pdh.getCurrent(ch))
