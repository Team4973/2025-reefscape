"""
Climber subsystem — single TalonFX with duty cycle control.

Controlled by D-pad up (extend) and D-pad down (retract), held commands.
"""

from commands2 import Subsystem
from phoenix6.hardware import TalonFX

from constants import CLIMBER_ID, CLIMBER_SPEED


class Climber(Subsystem):
    def __init__(self):
        super().__init__()
        self.motor = TalonFX(CLIMBER_ID)

    def extend(self):
        """Run climber motor forward (up)."""
        self.motor.set(CLIMBER_SPEED)

    def retract(self):
        """Run climber motor backward (down)."""
        self.motor.set(-CLIMBER_SPEED)

    def stop(self):
        """Stop climber motor."""
        self.motor.set(0)
