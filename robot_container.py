"""
RobotContainer — ALL hardware initialization and ALL button bindings.

This centralizes controller mappings that were previously scattered across
Elevator, Climber, and CoralShooterContainer in the Java code.

Controls:
    Left stick:      Swerve translate (field-centric)
    Right stick X:   Swerve rotate
    X:               Reset Pigeon2 gyro
    Y:               Elevator up one level
    A:               Elevator down one level
    B:               Reset speed divider to 3.0
    Left bumper:     Decrease speed divider (faster)
    Right bumper:    Increase speed divider (slower)
    Right trigger:   Fire coral launcher (hold)
    D-pad up:        Climber up (hold)
    D-pad down:      Climber down (hold)
"""

from commands2 import InstantCommand, StartEndCommand
from commands2.button import CommandXboxController
from phoenix6.swerve.requests import FieldCentric

from constants import (
    DEFAULT_SPEED_DIVIDER,
    LAUNCHER_SPEED,
    MAX_ANGULAR_RATE,
    SPEED_AT_12V,
    create_drivetrain,
)
from subsystems.climber import Climber
from subsystems.coral_launcher import CoralLauncher
from subsystems.elevator import Elevator
from subsystems.limelight import Limelight
from subsystems.power_distribution_hub import PowerDistributionHub
from telemetry import Telemetry


class RobotContainer:
    def __init__(self):
        # Swerve drivetrain
        self.drivetrain = create_drivetrain()

        # Subsystems
        self.elevator = Elevator()
        self.climber = Climber()
        self.launcher = CoralLauncher()
        self.limelight = Limelight()
        self.pdh = PowerDistributionHub()

        # Telemetry
        self._max_speed = SPEED_AT_12V
        self._telemetry = Telemetry(self._max_speed)
        self.drivetrain.register_telemetry(self._telemetry.telemeterize)

        # Controller
        self.joystick = CommandXboxController(0)

        # Speed divider (captures by reference via self)
        self.speed_divider = 16.0  # starts at 16 until teleopInit resets to 3

        # Swerve requests
        self._drive = (
            FieldCentric()
            .with_deadband(self._max_speed * 0.1)
            .with_rotational_deadband(MAX_ANGULAR_RATE * 0.1)
        )

        # Wire up ALL bindings
        self._configure_bindings()

    def _configure_bindings(self):
        """Register all controller → command bindings once."""

        # --- Default command: field-centric swerve drive ---
        self.drivetrain.setDefaultCommand(
            self.drivetrain.apply_request(
                lambda: self._drive.with_velocity_x(
                    -self.joystick.getLeftY() * self._max_speed / self.speed_divider
                )
                .with_velocity_y(-self.joystick.getLeftX() * self._max_speed / self.speed_divider)
                .with_rotational_rate(-self.joystick.getRightX() * MAX_ANGULAR_RATE)
            )
        )

        # --- X: reset gyro ---
        self.joystick.x().onTrue(InstantCommand(lambda: self.drivetrain.get_pigeon2().reset()))

        # --- Y: elevator up one level ---
        self.joystick.y().onTrue(InstantCommand(self.elevator.go_up, self.elevator))

        # --- A: elevator down one level ---
        self.joystick.a().onTrue(InstantCommand(self.elevator.go_down, self.elevator))

        # --- D-pad up: climber up (hold) ---
        self.joystick.povUp().onTrue(InstantCommand(self.climber.extend, self.climber))
        self.joystick.povUp().onFalse(InstantCommand(self.climber.stop, self.climber))

        # --- D-pad down: climber down (hold) ---
        self.joystick.povDown().onTrue(InstantCommand(self.climber.retract, self.climber))
        self.joystick.povDown().onFalse(InstantCommand(self.climber.stop, self.climber))

        # --- Right trigger: fire coral launcher (hold) ---
        self.joystick.rightTrigger().whileTrue(
            StartEndCommand(
                lambda: self.launcher.launch(LAUNCHER_SPEED),
                self.launcher.stop,
                self.launcher,
            )
        )

        # --- Speed divider controls (registered once, not every loop) ---
        self.joystick.leftBumper().onTrue(InstantCommand(self._decrease_speed_divider))
        self.joystick.rightBumper().onTrue(InstantCommand(self._increase_speed_divider))
        self.joystick.b().onTrue(InstantCommand(self._reset_speed_divider))

    # -- Speed divider helpers (fix for quickJoystickSpeedChange bug) ---------

    def _decrease_speed_divider(self):
        """Make robot faster (decrease divider), clamped to 1.0."""
        self.speed_divider = max(1.0, self.speed_divider - 0.6)

    def _increase_speed_divider(self):
        """Make robot slower (increase divider), clamped to 12.0."""
        self.speed_divider = min(12.0, self.speed_divider + 0.6)

    def _reset_speed_divider(self):
        """Reset speed divider to default."""
        self.speed_divider = DEFAULT_SPEED_DIVIDER

    def reset_speed_for_teleop(self):
        """Called from Robot.teleopInit() to set teleop default speed."""
        self.speed_divider = DEFAULT_SPEED_DIVIDER
