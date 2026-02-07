"""
Elevator subsystem — two TalonFX motors (leader/follower) with position control.

The left motor (CAN 31) is the leader, the right motor (CAN 30) follows in
the opposite direction. Five preset height levels controlled by Y (up) and A (down).
"""

from commands2 import Subsystem
from phoenix6.configs import TalonFXConfiguration
from phoenix6.controls import Follower, PositionDutyCycle
from phoenix6.hardware import TalonFX
from phoenix6.signals import MotorAlignmentValue

from constants import (
    ELEVATOR_LEFT_ID,
    ELEVATOR_LEVELS,
    ELEVATOR_PID,
    ELEVATOR_RIGHT_ID,
)


class Elevator(Subsystem):
    def __init__(self):
        super().__init__()

        # Leader (left) and follower (right) motors
        self.leader = TalonFX(ELEVATOR_LEFT_ID)
        self.follower = TalonFX(ELEVATOR_RIGHT_ID)

        # Configure leader PID
        config = TalonFXConfiguration()
        config.slot0 = ELEVATOR_PID
        self.leader.configurator.apply(config)
        self.leader.set_position(0)

        # Set right motor as follower (opposed direction)
        self.follower.set_control(Follower(ELEVATOR_LEFT_ID, MotorAlignmentValue.OPPOSED))

        self._position_control = PositionDutyCycle(0)
        self._current_level = 0
        self._levels = ELEVATOR_LEVELS

    @property
    def current_level(self) -> int:
        return self._current_level

    def go_up(self):
        """Move elevator up one level."""
        if self._current_level < len(self._levels) - 1:
            self._current_level += 1
            self._go_to_level()

    def go_down(self):
        """Move elevator down one level."""
        if self._current_level > 0:
            self._current_level -= 1
            self._go_to_level()

    def _go_to_level(self):
        rotations = self._levels[self._current_level]
        # Negative because the Java code uses -rotations for the left motor
        self.leader.set_control(self._position_control.with_position(-rotations))
