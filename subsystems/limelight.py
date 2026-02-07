"""
Limelight vision subsystem — reads AprilTag data from NetworkTables.

Subscribes to the Limelight NT topics for tx, ty, tz, ta, and tid.
LED mode is forced off on init (no need for LimelightHelpers library).
"""

from commands2 import Subsystem
from ntcore import NetworkTableInstance


class Limelight(Subsystem):
    def __init__(self):
        super().__init__()
        nt = NetworkTableInstance.getDefault()

        self._tx_sub = nt.getDoubleTopic("/limelight/tx").subscribe(0.0)
        self._ty_sub = nt.getDoubleTopic("/limelight/ty").subscribe(0.0)
        self._tz_sub = nt.getDoubleTopic("/limelight/tz").subscribe(0.0)
        self._ta_sub = nt.getDoubleTopic("/limelight/ta").subscribe(0.0)
        self._tid_sub = nt.getDoubleTopic("/limelight/tid").subscribe(0.0)

        # Force LEDs off (replaces LimelightHelpers.setLEDMode_ForceOff)
        nt.getTable("limelight").getEntry("ledMode").setNumber(1)

    @property
    def tx(self) -> float:
        return self._tx_sub.get()

    @property
    def ty(self) -> float:
        return self._ty_sub.get()

    @property
    def tz(self) -> float:
        return self._tz_sub.get()

    @property
    def ta(self) -> float:
        return self._ta_sub.get()

    @property
    def tid(self) -> float:
        return self._tid_sub.get()
