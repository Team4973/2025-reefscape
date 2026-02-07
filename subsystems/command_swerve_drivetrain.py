"""
Command-based swerve drivetrain — wraps phoenix6 SwerveDrivetrain as a commands2 Subsystem.

Handles operator perspective (blue/red alliance), simulation thread,
and exposes `apply_request()` for command-based control.
"""

from typing import Callable

from commands2 import Command, Subsystem
from phoenix6 import utils
from phoenix6.swerve import (
    SwerveDrivetrain,
    SwerveDrivetrainConstants,
    SwerveModuleConstants,
    SwerveRequest,
)
from wpilib import DriverStation, Notifier, RobotController
from wpimath.geometry import Rotation2d


class CommandSwerveDrivetrain(Subsystem):
    """Phoenix 6 SwerveDrivetrain wrapped as a commands2 Subsystem."""

    _SIM_LOOP_PERIOD = 0.005  # 5 ms

    # Alliance perspectives
    _BLUE_PERSPECTIVE = Rotation2d()
    _RED_PERSPECTIVE = Rotation2d.fromDegrees(180)

    def __init__(
        self,
        drivetrain_constants: SwerveDrivetrainConstants,
        *modules: SwerveModuleConstants,
    ):
        super().__init__()

        self._drivetrain = SwerveDrivetrain(drivetrain_constants, *modules)
        self._has_applied_perspective = False
        self._sim_notifier: Notifier | None = None
        self._last_sim_time = 0.0

        if utils.is_simulation():
            self._start_sim_thread()

    # -- Public API -----------------------------------------------------------

    def apply_request(self, request_supplier: Callable[[], SwerveRequest]) -> Command:
        """Return a command that continuously applies the given swerve request."""
        return self.run(lambda: self._drivetrain.set_control(request_supplier()))

    def get_pigeon2(self):
        """Access the Pigeon2 IMU."""
        return self._drivetrain.pigeon2

    def register_telemetry(self, telemetry_fn):
        """Register a telemetry callback (called with SwerveDriveState)."""
        self._drivetrain.register_telemetry(telemetry_fn)

    def get_state(self):
        """Get the current swerve drive state."""
        return self._drivetrain.state

    # -- Subsystem overrides --------------------------------------------------

    def periodic(self):
        """Apply operator perspective based on alliance color."""
        if not self._has_applied_perspective or DriverStation.isDisabled():
            alliance = DriverStation.getAlliance()
            if alliance is not None:
                perspective = (
                    self._RED_PERSPECTIVE
                    if alliance == DriverStation.Alliance.kRed
                    else self._BLUE_PERSPECTIVE
                )
                self._drivetrain.set_operator_perspective_forward(perspective)
                self._has_applied_perspective = True

    # -- Simulation -----------------------------------------------------------

    def _start_sim_thread(self):
        self._last_sim_time = utils.get_current_time_seconds()

        def _sim_periodic():
            current_time = utils.get_current_time_seconds()
            dt = current_time - self._last_sim_time
            self._last_sim_time = current_time
            self._drivetrain.update_sim_state(dt, RobotController.getBatteryVoltage())

        self._sim_notifier = Notifier(_sim_periodic)
        self._sim_notifier.startPeriodic(self._SIM_LOOP_PERIOD)
