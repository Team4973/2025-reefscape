"""
Telemetry — publishes swerve drive state to NetworkTables and SignalLogger.

Provides pose, chassis speeds, module states/targets/positions, and
Mechanism2d visualizations for each module.
"""

from ntcore import NetworkTableInstance
from phoenix6 import signal_logger
from phoenix6.swerve import SwerveDrivetrain
from wpilib import Color, Color8Bit, Mechanism2d, SmartDashboard
from wpimath.geometry import Pose2d
from wpimath.kinematics import ChassisSpeeds, SwerveModulePosition, SwerveModuleState


class Telemetry:
    def __init__(self, max_speed: float):
        self._max_speed = max_speed
        signal_logger.start()

        nt = NetworkTableInstance.getDefault()

        # Drive state publishers
        drive_table = nt.getTable("DriveState")
        self._pose_pub = drive_table.getStructTopic("Pose", Pose2d).publish()
        self._speeds_pub = drive_table.getStructTopic("Speeds", ChassisSpeeds).publish()
        self._module_states_pub = drive_table.getStructArrayTopic(
            "ModuleStates", SwerveModuleState
        ).publish()
        self._module_targets_pub = drive_table.getStructArrayTopic(
            "ModuleTargets", SwerveModuleState
        ).publish()
        self._module_positions_pub = drive_table.getStructArrayTopic(
            "ModulePositions", SwerveModulePosition
        ).publish()
        self._timestamp_pub = drive_table.getDoubleTopic("Timestamp").publish()
        self._odom_freq_pub = drive_table.getDoubleTopic("OdometryFrequency").publish()

        # Field2d pose
        pose_table = nt.getTable("Pose")
        self._field_pub = pose_table.getDoubleArrayTopic("robotPose").publish()
        self._field_type_pub = pose_table.getStringTopic(".type").publish()

        # Mechanism2d for each module
        self._mechanisms = [Mechanism2d(1, 1) for _ in range(4)]
        self._speeds = []
        self._directions = []
        for i in range(4):
            root_speed = self._mechanisms[i].getRoot("RootSpeed", 0.5, 0.5)
            self._speeds.append(root_speed.appendLigament("Speed", 0.5, 0))
            root_dir = self._mechanisms[i].getRoot("RootDirection", 0.5, 0.5)
            self._directions.append(
                root_dir.appendLigament("Direction", 0.1, 0, 0, Color8Bit(Color.kWhite))
            )

    def telemeterize(self, state: SwerveDrivetrain.SwerveDriveState):
        """Callback for drivetrain.register_telemetry()."""
        # Publish struct data
        self._pose_pub.set(state.pose)
        self._speeds_pub.set(state.speeds)
        self._module_states_pub.set(state.module_states)
        self._module_targets_pub.set(state.module_targets)
        self._module_positions_pub.set(state.module_positions)
        self._timestamp_pub.set(state.timestamp)
        if state.odometry_period > 0:
            self._odom_freq_pub.set(1.0 / state.odometry_period)

        # Log to SignalLogger
        pose = state.pose
        pose_array = [pose.X(), pose.Y(), pose.rotation().degrees()]
        signal_logger.write_double_array("DriveState/Pose", pose_array)

        states_array = []
        targets_array = []
        for i in range(4):
            ms = state.module_states[i]
            mt = state.module_targets[i]
            states_array.extend([ms.angle.radians(), ms.speed])
            targets_array.extend([mt.angle.radians(), mt.speed])

        signal_logger.write_double_array("DriveState/ModuleStates", states_array)
        signal_logger.write_double_array("DriveState/ModuleTargets", targets_array)
        signal_logger.write_double("DriveState/OdometryPeriod", state.odometry_period)

        # Field2d
        self._field_type_pub.set("Field2d")
        self._field_pub.set(pose_array)

        # Mechanism2d visualization
        for i in range(4):
            ms = state.module_states[i]
            self._speeds[i].setAngle(ms.angle)
            self._directions[i].setAngle(ms.angle)
            self._speeds[i].setLength(ms.speed / (2 * self._max_speed))
            SmartDashboard.putData(f"Module {i}", self._mechanisms[i])
