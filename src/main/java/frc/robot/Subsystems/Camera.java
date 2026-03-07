package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.CameraConstants;
import frc.robot.Constants.AprilTagIDs;

public class Camera {
    public static NetworkTable watcher =
        NetworkTableInstance.getDefault().getTable("TheWatcher");

    public static NetworkTable sauron =
        NetworkTableInstance.getDefault().getTable("EyeOfSauron");

    //TheWatcher
    /**
    * This returns whether or not the watcher camera has a valid target
    * @return a true or false if the camera has a valid target
    */
    public static boolean watcherHasTag() {
        return watcher.getEntry("tv").getDouble(0)==1;
    }
    /**
    * Shows the the target id that the camera watcher is looking at
    * @return the target id of watcher
    */
    public static int watcherTagID() {
        return (int) watcher.getEntry("tid").getDouble(-1);
    }
    /**
    * Gets the horizontal angle of the watcher camera
    * @return the horizontal angle in degrees of the watcher camera as a double value
    */
    public static double watcherHorizontal() {
        return watcher.getEntry("tx").getDouble(0.0);
    }
/**
    * Gets the vertical angle of the watcher camera
    * @return the vertical angle in degrees of the watcher camera as a double value
    */
    public static double watcherVertical() {
        return watcher.getEntry("ty").getDouble(0.0);
    }

    // Sauron
    /**
    * This returns whether or not the sauron camera has a valid target
    * @return a true or false if the camera has a valid target
    */
    public static boolean sauronHasTag() {
        return sauron.getEntry("tv").getDouble(0)==1;
    }

/**
    * Shows the the target id that the camera sauron is looking at
    * @return the target id of sauron
    */
    public static int sauronTagID() {
        return (int) sauron.getEntry("tid").getDouble(-1);
    }
/**
    * Gets the horizontal angle of the sauron camera
    * @return the horizontal angle in degrees of the sauron camera as a double value
    */
    public static double sauronHorizontal() {
        return sauron.getEntry("tx").getDouble(0.0);
    }

/**
    * Gets the vertical angle of the sauron camera
    * @return the vertical angle in degrees of the sauron camera as a double value
    */
    public static double sauronVertical() {
        return sauron.getEntry("ty").getDouble(0.0);
    }

    /**
    * Sets Sauron to prioritize a specific AprilTag ID
    * @param tagID the AprilTag ID to prioritize (or -1 for no filtering)
    */
    public static void setSauronPriorityTag(int tagID) {
        sauron.getEntry("priorityid").setNumber(tagID);
    }

    /**
    * Sets TheWatcher to prioritize a specific AprilTag ID
    * @param tagID the AprilTag ID to prioritize (or -1 for no filtering)
    */
    public static void setWatcherPriorityTag(int tagID) {
        watcher.getEntry("priorityid").setNumber(tagID);
    }
    
    // MATH
/**
    * Gets the distance in inches from the watcher camera to the detected AprilTag
    * @return double the distance in inches of the tag
    */
    public static double watcherDistanceInches() {
        return getDistanceToAprilTag(watcherHasTag(), watcherVertical(), watcherTagID());
    }
/**
    * Gets the distance in inches from the sauron camera to the detected tag
    * @return double the distance in inches to the tag
    */
    public static double sauronDistanceInches() {
        return getDistanceToAprilTag(sauronHasTag(), sauronVertical(), sauronTagID());
    }

    /**
    * Checks if TheWatcher is looking at a specific AprilTag
    * @param tagID the ID of the tag to check for
    * @return true if the camera sees that specific tag
    */
    public static boolean watcherSeesTag(int tagID) {
        setWatcherPriorityTag(tagID);
        return watcherHasTag() && watcherTagID() == tagID;
    }

    /**
    * Checks if Sauron is looking at a specific AprilTag, sets it as the highest priority
    * @param tagID the ID of the tag to check for with highest priority
    * @return true if the camera sees that specific tag
    */
    public static boolean sauronSeesTag(int tagID) {
        setSauronPriorityTag(tagID);
        return sauronHasTag() && sauronTagID() == tagID;
    }

    /**
     * Returns the horizontal distance from an april tag
     * @param camHasTag
     * @param camVertical Vertical angle from the camera to april tag
     * @param tagID AprilTag ID
     * @return Horizontal distance in inches to the april tag
     */
    private static double getDistanceToAprilTag(boolean camHasTag, double camVertical, int tagID) {
        if (!camHasTag) return -1;

        double angleDeg = CameraConstants.LIMELIGHT_MOUNT_ANGLE_DEG + camVertical;
        double angleRad = Math.toRadians(angleDeg);
        if (Math.abs(Math.tan(angleRad)) < 1e-6) return -1;

        return (AprilTagIDs.getAprilTagHeight(tagID) - CameraConstants.LIMELIGHT_LENS_HEIGHT_IN) / Math.tan(angleRad);
 
   }

}
//       _ _,---._
//    ,-','       `-.___
//   /-;'              _`._
//  /\/           _   _ 'o \
// ( /\       _,--'\,','"`. )
//  |\      ,'       \'    //\
//  |      \        /   ,--'""`-.
//  :       \_ o  _/ ,-'         `-._
//   \        `--'  /                )
//    `.  \`._    ,'     ________,','
//      .--`     ,'  ,--` __\___,;'
//       \`.,-- ,' ,`_)--'  /`.,'
//        \( ;  | | )      (`-/
//          `--'| |)       |-/
//            | | |        | |
//            | | |,.,-.   | |_
//            | `./ /   )---`  )
//           _|  /    ,',   ,-'
//          ,'|_(    /-<._,' |--,
//          |    `--'---.     \/ \
//          |          / \    /\  \
//        ,-^---._     |  \  /  \  \
//     ,-'        \----'   \/    \--`.
//    /            \              \   \/