"""
Reefscape 2025 — FRC Team 4973
Python/RobotPy rewrite of the 2025 season robot code.

Entry point: TimedRobot lifecycle → RobotContainer holds all hardware + bindings.
"""

import wpilib
from commands2 import CommandScheduler

from robot_container import RobotContainer


class Robot(wpilib.TimedRobot):
    def robotInit(self):
        self.container = RobotContainer()

    def robotPeriodic(self):
        CommandScheduler.getInstance().run()
        self.container.pdh.publish_telemetry()

    def autonomousInit(self):
        pass

    def autonomousPeriodic(self):
        pass

    def teleopInit(self):
        self.container.reset_speed_for_teleop()

    def teleopPeriodic(self):
        pass

    def testInit(self):
        CommandScheduler.getInstance().cancelAll()

    def testPeriodic(self):
        pass

    def simulationPeriodic(self):
        pass


if __name__ == "__main__":
    wpilib.run(Robot)
