package frc.robot.Subsystems;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import static frc.robot.Constants.CameraConstants;
import frc.robot.Constants.AprilTagIDs;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.VideoSource;

public class Camera {
    
    public static NetworkTable watcher =
        NetworkTableInstance.getDefault().getTable("TheWatcher");

    public static NetworkTable sauron =
        NetworkTableInstance.getDefault().getTable("EyeOfSauron");


    //TheWatcher
    public static boolean watcherHasTag() {
        return watcher.getEntry("tv").getDouble(0)>0.5;
    }

    public static int watcherTagID() {
        return (int) watcher.getEntry("tid").getDouble(-1);
    }

    public static double watcherHorizontal() {
        return watcher.getEntry("tx").getDouble(0.0);
    }

    public static double watcherVertical() {
        return watcher.getEntry("ty").getDouble(0.0);
    }

    // Sauron
    public static boolean sauronHasTag() {
        return sauron.getEntry("tv").getDouble(0)==1;
    }

    public static int sauronTagID() {
        return (int) sauron.getEntry("tid").getDouble(-1);
    }

    public static double sauronHorizontal() {
        return sauron.getEntry("tx").getDouble(0.0);
    }

    public static double sauronVertical() {
        return sauron.getEntry("ty").getDouble(0.0);
    }

    
    // MATH

    public static double watcherDistanceInches() {
        return getDistanceToAprilTag(watcherHasTag(), watcherVertical(), watcherTagID());
        
        /* if (!watcherHasTag()) return -1;

        double ty = watcherVertical();
        double angleDeg = CameraConstants.LIMELIGHT_MOUNT_ANGLE_DEG + ty;
        double angleRad = Math.toRadians(angleDeg);

        return (CameraConstants.TAG_HEIGHT_IN - CameraConstants.LIMELIGHT_LENS_HEIGHT_IN) / Math.tan(angleRad); */
    }

    public static double sauronDistanceInches() {
        return getDistanceToAprilTag(sauronHasTag(), sauronVertical(), sauronTagID());
    
        /*if (!sauronHasTag()) return -1;

        double ty = sauronVertical();
        double angleDeg = CameraConstants.LIMELIGHT_MOUNT_ANGLE_DEG + ty;
        double angleRad = Math.toRadians(angleDeg);

        return (CameraConstants.TAG_HEIGHT_IN - CameraConstants.LIMELIGHT_LENS_HEIGHT_IN) / Math.tan(angleRad); */
    }

    // Helper Function 
    private static double getDistanceToAprilTag(boolean camHasTag, double camVertical, int tagID) {
        if (!camHasTag) return -1;

        double angleDeg = CameraConstants.LIMELIGHT_MOUNT_ANGLE_DEG + camVertical;
        double angleRad = Math.toRadians(angleDeg);

        return (AprilTagIDs.getAprilTagHeight(tagID) - CameraConstants.LIMELIGHT_LENS_HEIGHT_IN) / Math.tan(angleRad);
 
   }


    public static void startLimelightStream() {
        // Create an HTTP camera using the Limelight stream URL
        HttpCamera cameraWatcher = new HttpCamera(
            "TheWatcher",
            "http://limelight.local:5800/stream.mjpg"
        );
        cameraWatcher.setResolution(320, 240);
        cameraWatcher.setFPS(30);


        HttpCamera cameraSauron = new HttpCamera(
            "EyeOfSauron",
            "http://limelight-.local:5800/stream.mjpg"
        );
        cameraSauron.setResolution(320, 240);
        cameraSauron.setFPS(30);


        CameraServer.startAutomaticCapture(cameraWatcher);
        CameraServer.startAutomaticCapture(cameraSauron);
  }
}
