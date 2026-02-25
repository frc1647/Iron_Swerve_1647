
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final DigitalInput motorTypeSelector = new DigitalInput(9);

  public static boolean isBrushlessMotors() {
    return motorTypeSelector.get(); // true = brushless, false = brushed
  }

  public static final class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  public static final class DriveConstants {
    // public static final int BRUSHED_LEFT_LEADER_ID = 2;
    // public static final int BRUSHED_LEFT_FOLLOWER_ID = 9;
    // public static final int BRUSHED_RIGHT_LEADER_ID = 10;
    // public static final int BRUSHED_RIGHT_FOLLOWER_ID = 7;
    public static final int BRUSHLESS_LEFT_LEADER_ID = 15;
    public static final int BRUSHLESS_LEFT_FOLLOWER_ID = 16;
    public static final int BRUSHLESS_RIGHT_LEADER_ID = 17;
    public static final int BRUSHLESS_RIGHT_FOLLOWER_ID = 18;

    // Drive Motor Speed Limit, Acceptable Range; 30-60
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 45;
    //
    public static final int kPositionConversionFactor = 60;
    public static final int kVelocityConversionFactor = 60;
    public static final int SPEED = 0;

  }

  public static final class ShooterConstants {
    public static final int SHOOTER_ID = 22;
    public static final double SHOOTER_SPEED = 0.6;
    public static final int SHOOTER_MOTOR_CURRENT_LIMIT = 45;
    public static final double DEFAULT_SHOOTING_DISTANCE = 91.055;
  }

  public static final class TurretConstants {
    public static final int TURRET_ID = 25; //TODO set
    public static final double TURRET_GEAR_RATIO = 1;
    public static final int TURRET_MOTOR_CURRENT_LIMIT = 45;

    public static final double TURRET_AIMING_LIMIT_MIN = -270;
    public static final double TURRET_AIMING_LIMIT_MAX = 270;
  }

  public static final class CameraConstants {
    // ★★★★★ TODO: Fill in these values ★★★★★
    public static final double LIMELIGHT_LENS_HEIGHT_IN = 22.5;
    public static final double LIMELIGHT_MOUNT_ANGLE_DEG = 114.125;
  }

  public static final class IntakeConstants {
    public static final int LEFT_INTAKE_ID = 64;
    public static final int RIGHT_INTAKE_ID = 65;
    public static final int INTAKE_MOTOR_CURRENT_LIMIT = 45;
  }

  public static final class HopperConstants {
    // TODO: Verify these CAN IDs match actual hardware!
    public static final int SPINDEXER_MOTOR_ID = 6;
    public static final double SPINDEXER_SPEED = 0.25;
    public static final double SPINDEXER_GEAR_RATIO = 1;


    public static final int PASSAGE_MOTOR_UPPER_ID = 31; // TODO: guess — confirm actual CAN ID
    public static final int PASSAGE_MOTOR_LOWER_ID = 32; // TODO: guess — confirm actual CAN ID
    public static final double PASSAGE_SPEED = 0.05;
  }

  public static final class AutoConstants {
    // TODO
    public static final double TARGET_INCHES_FROM_TAG = 24;
    public static final double TURN_STRENGTH = 0.04; // 0 - 1, how strong the drive-to-apriltag command turns
    public static final double DRIVE_STRENGTH = 0.03;

    public static final double DISTANCE_TOLERANCE = 2.0; // Inches
    public static final double ANGLE_TOLERANCE = 2.0; // Degrees

    public static final double AUTO_MAX_SPEED = 0.4; // 0 - 1
  }

  public static final class LEDConstants {
    public static final int BLINKIN_ID = 0; // TODO: guess — confirm actual PWM port on roboRIO

    public static final double AIM_TOLERANCE = 1;

    public static final class Colors {
      public static final double OFF = 0.99;
      public static final double WHITE = 0.93;

      public static final double PATTERN_SHOT_RED = -0.85;
      public static final double PATTERN_SHOT_BLUE = -0.83;

      public static final double RED = 0.61;
      public static final double GOLD = 0.67;
      public static final double LAWN_GREEN = 0.71;
    }
  }

  public static final class AprilTagIDs {

    // Hub faces are relative to the center of the field

    // ★ Red Constants ★
    public static final int RED_HUB_FRONT_LEFT_TAG_ID = 3;
    public static final int RED_HUB_FRONT_RIGHT_TAG_ID = 4;

    public static final int RED_HUB_LEFTSIDE_LEFT_TAG_ID = 11;
    public static final int RED_HUB_LEFTSIDE_RIGHT_TAG_ID = 2;

    public static final int RED_HUB_RIGHTSIDE_LEFT_TAG_ID = 5;
    public static final int RED_HUB_RIGHTSIDE_RIGHT_TAG_ID = 8;

    public static final int RED_HUB_BACK_LEFT_TAG_ID = 10;
    public static final int RED_HUB_BACK_RIGHT_TAG_ID = 9;

    public static final int RED_OUTPOST_LEFT_TAG_ID = 13;
    public static final int RED_OUTPOST_RIGHT_TAG_ID = 14;

    public static final int RED_LEFTTRENCH_FRONT_TAG_ID = 1;
    public static final int RED_LEFTTRENCH_BACK_TAG_ID = 12;

    public static final int RED_RIGHTTRENCH_FRONT_TAG_ID = 6;
    public static final int RED_RIGHTTRENCH_BACK_TAG_ID = 7;

    public static final int RED_TOWERWALL_LEFT_TAG_ID = 15;
    public static final int RED_TOWERWALL_RIGHT_TAG_ID = 16;

    // ★ Blue Constants ★

    public static final int BLUE_HUB_FRONT_LEFT_TAG_ID = 19;
    public static final int BLUE_HUB_FRONT_RIGHT_TAG_ID = 20;

    public static final int BLUE_HUB_LEFTSIDE_LEFT_TAG_ID = 27;
    public static final int BLUE_HUB_LEFTSIDE_RIGHT_TAG_ID = 18;

    public static final int BLUE_HUB_RIGHTSIDE_LEFT_TAG_ID = 21;
    public static final int BLUE_HUB_RIGHTSIDE_RIGHT_TAG_ID = 24;

    public static final int BLUE_HUB_BACK_LEFT_TAG_ID = 26;
    public static final int BLUE_HUB_BACK_RIGHT_TAG_ID = 25;

    public static final int BLUE_OUTPOST_LEFT_TAG_ID = 29;
    public static final int BLUE_OUTPOST_RIGHT_TAG_ID = 30;

    public static final int BLUE_LEFTTRENCH_FRONT_TAG_ID = 17;
    public static final int BLUE_LEFTTRENCH_BACK_TAG_ID = 28;

    public static final int BLUE_RIGHTTRENCH_FRONT_TAG_ID = 22;
    public static final int BLUE_RIGHTTRENCH_BACK_TAG_ID = 23;

    public static final int BLUE_TOWERWALL_LEFT_TAG_ID = 31;
    public static final int BLUE_TOWERWALL_RIGHT_TAG_ID = 32;


    /**
    * This returns the height of the specified ★ April Tag ★
    * @param id AprilTag ID
    * @return double the height in inches of the tag
    */
    public static double getAprilTagHeight(int id) {
      switch (id) { // TODO: Check if these are right

        // ★ HUB TAGS
        case RED_HUB_FRONT_LEFT_TAG_ID:
        case RED_HUB_FRONT_RIGHT_TAG_ID:
        case RED_HUB_LEFTSIDE_LEFT_TAG_ID:
        case RED_HUB_LEFTSIDE_RIGHT_TAG_ID:
        case RED_HUB_RIGHTSIDE_LEFT_TAG_ID:
        case RED_HUB_RIGHTSIDE_RIGHT_TAG_ID:
        case RED_HUB_BACK_LEFT_TAG_ID:
        case RED_HUB_BACK_RIGHT_TAG_ID:
        case BLUE_HUB_FRONT_LEFT_TAG_ID:
        case BLUE_HUB_FRONT_RIGHT_TAG_ID:
        case BLUE_HUB_LEFTSIDE_LEFT_TAG_ID:
        case BLUE_HUB_LEFTSIDE_RIGHT_TAG_ID:
        case BLUE_HUB_RIGHTSIDE_LEFT_TAG_ID:
        case BLUE_HUB_RIGHTSIDE_RIGHT_TAG_ID:
        case BLUE_HUB_BACK_LEFT_TAG_ID:
        case BLUE_HUB_BACK_RIGHT_TAG_ID:
          return 44.25;

        // ★ OUTPOST
        case RED_OUTPOST_LEFT_TAG_ID:
        case RED_OUTPOST_RIGHT_TAG_ID:
        case BLUE_OUTPOST_LEFT_TAG_ID:
        case BLUE_OUTPOST_RIGHT_TAG_ID:
          return 21.75;

        default:
          return -1;
      }
    }
  }
}