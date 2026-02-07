# Wiring Map — FRC Team 4973 Reefscape 2025

## CAN Bus Devices

| CAN ID | Device | Type | Python Constant |
|--------|--------|------|-----------------|
| 0 | Pigeon2 IMU | CTRE Pigeon 2 | `PIGEON2_ID` |
| 1 | Power Distribution Hub | REV PDH | `PDH_ID` |
| 2 | Back Right Steer Motor | TalonFX | `BR_STEER_ID` |
| 5 | Front Right Steer Motor | TalonFX | `FR_STEER_ID` |
| 6 | Front Left Steer Motor | TalonFX | `FL_STEER_ID` |
| 7 | Back Left Steer Motor | TalonFX | `BL_STEER_ID` |
| 13 | Front Right CANcoder | CANcoder | `FR_ENCODER_ID` |
| 14 | Front Left CANcoder | CANcoder | `FL_ENCODER_ID` |
| 15 | Back Left CANcoder | CANcoder | `BL_ENCODER_ID` |
| 16 | Back Right CANcoder | CANcoder | `BR_ENCODER_ID` |
| 17 | Front Right Drive Motor | TalonFX | `FR_DRIVE_ID` |
| 18 | Front Left Drive Motor | TalonFX | `FL_DRIVE_ID` |
| 19 | Back Left Drive Motor | TalonFX | `BL_DRIVE_ID` |
| 20 | Back Right Drive Motor | TalonFX | `BR_DRIVE_ID` |
| 25 | Climber Motor | TalonFX | `CLIMBER_ID` |
| 30 | Elevator Right (follower) | TalonFX | `ELEVATOR_RIGHT_ID` |
| 31 | Elevator Left (leader) | TalonFX | `ELEVATOR_LEFT_ID` |
| 47 | Launcher Left Wheel | TalonFX | `LAUNCHER_LEFT_ID` |
| 48 | Launcher Right Wheel | TalonFX | `LAUNCHER_RIGHT_ID` |

## Swerve Module Layout

```
Front
  FL (18/6/14)    FR (17/5/13)
       ┌────────────┐
       │            │
       │   Pigeon   │
       │    (0)     │
       │            │
       └────────────┘
  BL (19/7/15)    BR (20/2/16)
Back
```

Format: `(Drive/Steer/Encoder)`

## Controller (Xbox, Port 0)

| Input | Action |
|-------|--------|
| Left stick | Swerve translate (field-centric) |
| Right stick X | Swerve rotate |
| X | Reset Pigeon2 gyro |
| Y | Elevator up one level |
| A | Elevator down one level |
| B | Reset speed divider to 3.0 |
| Left bumper | Decrease speed divider (faster) |
| Right bumper | Increase speed divider (slower) |
| Right trigger | Fire coral launcher (hold) |
| D-pad up | Climber up (hold) |
| D-pad down | Climber down (hold) |
