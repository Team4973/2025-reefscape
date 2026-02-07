# Reefscape 2025 — FRC Team 4973 (Python)

Python/RobotPy rewrite of Team 4973's 2025 Reefscape season robot code.

## Quick Start

```bash
# Run simulation
uv run python -m robotpy sim

# Run tests
uv run python -m robotpy test --builtin

# Deploy to robot
uv run python -m robotpy sync    # first time only
uv run python -m robotpy deploy

# Lint
uv run ruff check .
uv run ruff format --check .
```

## Project Structure

```
robot.py                          # Entry point (TimedRobot lifecycle)
robot_container.py                # All hardware init + all button bindings
constants.py                      # CAN IDs, PID gains, swerve config
telemetry.py                      # Swerve state → NetworkTables
subsystems/
    command_swerve_drivetrain.py   # CTRE swerve wrapped as Subsystem
    elevator.py                   # Elevator (leader/follower TalonFX)
    climber.py                    # Climber (single TalonFX)
    coral_launcher.py             # Coral launcher (two TalonFX)
    limelight.py                  # Limelight vision (NetworkTables)
    power_distribution_hub.py     # PDH monitoring
```

## Controls

| Input | Action |
|-------|--------|
| Left stick | Swerve translate (field-centric) |
| Right stick X | Swerve rotate |
| X | Reset gyro |
| Y | Elevator up |
| A | Elevator down |
| B | Reset speed to 3.0 |
| Left bumper | Speed up (decrease divider) |
| Right bumper | Slow down (increase divider) |
| Right trigger | Fire launcher (hold) |
| D-pad up/down | Climber up/down (hold) |

## Hardware

See [WIRING.md](WIRING.md) for the full CAN ID table and wiring map.

## Key Improvements Over Java Version

1. **Proper subsystems** — Elevator, Climber, and CoralLauncher all extend `commands2.Subsystem`
2. **Centralized bindings** — All button mappings are in `robot_container.py`, not scattered
3. **Speed divider fix** — Bumper bindings registered once (not re-registered every loop cycle)
4. **Elevator follower** — Right motor set as `Follower` of left motor (was commented out)
5. **PDH simplification** — Loop replaces 24 individual methods
6. **Limelight cleanup** — Dropped LimelightHelpers/LimelightSwerve (only 2 LED methods used)
