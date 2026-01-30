
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

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
    //public static final int BRUSHED_LEFT_LEADER_ID = 2;
    //public static final int BRUSHED_LEFT_FOLLOWER_ID = 9;
    //public static final int BRUSHED_RIGHT_LEADER_ID = 10;
    //public static final int BRUSHED_RIGHT_FOLLOWER_ID = 7;
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
    public static final int SHOOTER_ID = 1;

    public static final int SHOOTER_MOTOR_CURRENT_LIMIT = 45;
  }

  public static final class CameraConstants {
    // TODO: Fill in these values
    public static final double LIMELIGHT_LENS_HEIGHT_IN = 0.0;
    public static final double LIMELIGHT_MOUNT_ANGLE_DEG = 0.0;

    public static final double TAG_HEIGHT_IN = 0.0;

  }
}
