"""
Coral launcher subsystem — two TalonFX wheels spinning in opposite directions.

Left wheel spins forward, right wheel spins backward at the same speed.
"""

from commands2 import Subsystem
from phoenix6.hardware import TalonFX

from constants import LAUNCHER_LEFT_ID, LAUNCHER_RIGHT_ID, LAUNCHER_SPEED


class CoralLauncher(Subsystem):
    def __init__(self):
        super().__init__()
        self.left_wheel = TalonFX(LAUNCHER_LEFT_ID)
        self.right_wheel = TalonFX(LAUNCHER_RIGHT_ID)

    def launch(self, speed: float = LAUNCHER_SPEED):
        """Spin both wheels to launch coral."""
        self.left_wheel.set(speed)
        self.right_wheel.set(-speed)

    def stop(self):
        """Stop both wheels."""
        self.left_wheel.set(0)
        self.right_wheel.set(0)
