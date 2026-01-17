package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Camera {
    
    private static NetworkTable watcher =
        NetworkTableInstance.getDefault().getTable("TheWatcher");

    private static NetworkTable sauron =
        NetworkTableInstance.getDefault().getTable("EyeOfSauron");


    //TheWatcher

    public static boolean watcherHasTag() {
        return watcher.getEntry("tv").getDouble(0)==1;
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





    
}
