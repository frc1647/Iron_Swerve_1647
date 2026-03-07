package frc.robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants.AprilTagIDs;
import edu.wpi.first.wpilibj.DriverStation;

public class AprilTags {
    // Home IDs
    public static int homeHubFrontLeftID;
    public static int homeHubFrontRightID;

    public static int homeHubLeftsideLeftID;
    public static int homeHubLeftsideRightID;

    public static int homeHubRightsideLeftID;
    public static int homeHubRightsideRightID;

    public static int homeHubBackLeftID;
    public static int homeHubBackRightID;

    public static int homeOutpostLeftID;
    public static int homeOutpostRightID;

    public static int homeLeftTrenchFrontID;
    public static int homeLeftTrenchBackID;

    public static int homeRightTrenchFrontID;
    public static int homeRightTrenchBackID;

    public static int homeTowerWallLeftID;
    public static int homeTowerWallRightID;

    // Opponent IDs
    public static int awayHubFrontLeftID;
    public static int awayHubFrontRightID;

    public static int awayHubLeftsideLeftID;
    public static int awayHubLeftsideRightID;

    public static int awayHubRightsideLeftID;
    public static int awayHubRightsideRightID;

    public static int awayHubBackLeftID;
    public static int awayHubBackRightID;

    public static int awayOutpostLeftID;
    public static int awayOutpostRightID;

    public static int awayLeftTrenchFrontID;
    public static int awayLeftTrenchBackID;

    public static int awayRightTrenchFrontID;
    public static int awayRightTrenchBackID;

    public static int awayTowerWallLeftID;
    public static int awayTowerWallRightID;

    private static Alliance getAlliance() {
        return DriverStation.getAlliance().orElse(Alliance.Red);
    }

    /**
     * Configures all AprilTags to home or away
     */
    public static void setAprilTagIDs() {
        if (getAlliance() == Alliance.Red) {
            // Home IDs
            homeHubFrontLeftID = AprilTagIDs.RED_HUB_FRONT_LEFT_TAG_ID;
            homeHubFrontRightID = AprilTagIDs.RED_HUB_FRONT_RIGHT_TAG_ID;

            homeHubLeftsideLeftID = AprilTagIDs.RED_HUB_LEFTSIDE_LEFT_TAG_ID;
            homeHubLeftsideRightID = AprilTagIDs.RED_HUB_LEFTSIDE_RIGHT_TAG_ID;

            homeHubRightsideLeftID = AprilTagIDs.RED_HUB_RIGHTSIDE_LEFT_TAG_ID;
            homeHubRightsideRightID = AprilTagIDs.RED_HUB_RIGHTSIDE_RIGHT_TAG_ID;

            homeHubBackLeftID = AprilTagIDs.RED_HUB_BACK_LEFT_TAG_ID;
            homeHubBackRightID = AprilTagIDs.RED_HUB_BACK_RIGHT_TAG_ID;

            homeOutpostLeftID = AprilTagIDs.RED_OUTPOST_LEFT_TAG_ID;
            homeOutpostRightID = AprilTagIDs.RED_OUTPOST_RIGHT_TAG_ID;

            homeLeftTrenchFrontID = AprilTagIDs.RED_LEFTTRENCH_FRONT_TAG_ID;
            homeLeftTrenchBackID = AprilTagIDs.RED_LEFTTRENCH_BACK_TAG_ID;

            homeRightTrenchFrontID = AprilTagIDs.RED_RIGHTTRENCH_FRONT_TAG_ID;
            homeRightTrenchBackID = AprilTagIDs.RED_RIGHTTRENCH_BACK_TAG_ID;

            homeTowerWallLeftID = AprilTagIDs.RED_TOWERWALL_LEFT_TAG_ID;
            homeTowerWallRightID = AprilTagIDs.RED_TOWERWALL_RIGHT_TAG_ID;

            // Opponent IDs
            awayHubFrontLeftID = AprilTagIDs.BLUE_HUB_FRONT_LEFT_TAG_ID;
            awayHubFrontRightID = AprilTagIDs.BLUE_HUB_FRONT_RIGHT_TAG_ID;

            awayHubLeftsideLeftID = AprilTagIDs.BLUE_HUB_LEFTSIDE_LEFT_TAG_ID;
            awayHubLeftsideRightID = AprilTagIDs.BLUE_HUB_LEFTSIDE_RIGHT_TAG_ID;

            awayHubRightsideLeftID = AprilTagIDs.BLUE_HUB_RIGHTSIDE_LEFT_TAG_ID;
            awayHubRightsideRightID = AprilTagIDs.BLUE_HUB_RIGHTSIDE_RIGHT_TAG_ID;

            awayHubBackLeftID = AprilTagIDs.BLUE_HUB_BACK_LEFT_TAG_ID;
            awayHubBackRightID = AprilTagIDs.BLUE_HUB_BACK_RIGHT_TAG_ID;

            awayOutpostLeftID = AprilTagIDs.BLUE_OUTPOST_LEFT_TAG_ID;
            awayOutpostRightID = AprilTagIDs.BLUE_OUTPOST_RIGHT_TAG_ID;

            awayLeftTrenchFrontID = AprilTagIDs.BLUE_LEFTTRENCH_FRONT_TAG_ID;
            awayLeftTrenchBackID = AprilTagIDs.BLUE_LEFTTRENCH_BACK_TAG_ID;

            awayRightTrenchFrontID = AprilTagIDs.BLUE_RIGHTTRENCH_FRONT_TAG_ID;
            awayRightTrenchBackID = AprilTagIDs.BLUE_RIGHTTRENCH_BACK_TAG_ID;

            awayTowerWallLeftID = AprilTagIDs.BLUE_TOWERWALL_LEFT_TAG_ID;
            awayTowerWallRightID = AprilTagIDs.BLUE_TOWERWALL_RIGHT_TAG_ID;

        } else {
            // Home IDs
            homeHubFrontLeftID = AprilTagIDs.BLUE_HUB_FRONT_LEFT_TAG_ID;
            homeHubFrontRightID = AprilTagIDs.BLUE_HUB_FRONT_RIGHT_TAG_ID;

            homeHubLeftsideLeftID = AprilTagIDs.BLUE_HUB_LEFTSIDE_LEFT_TAG_ID;
            homeHubLeftsideRightID = AprilTagIDs.BLUE_HUB_LEFTSIDE_RIGHT_TAG_ID;

            homeHubRightsideLeftID = AprilTagIDs.BLUE_HUB_RIGHTSIDE_LEFT_TAG_ID;
            homeHubRightsideRightID = AprilTagIDs.BLUE_HUB_RIGHTSIDE_RIGHT_TAG_ID;

            homeHubBackLeftID = AprilTagIDs.BLUE_HUB_BACK_LEFT_TAG_ID;
            homeHubBackRightID = AprilTagIDs.BLUE_HUB_BACK_RIGHT_TAG_ID;

            homeOutpostLeftID = AprilTagIDs.BLUE_OUTPOST_LEFT_TAG_ID;
            homeOutpostRightID = AprilTagIDs.BLUE_OUTPOST_RIGHT_TAG_ID;

            homeLeftTrenchFrontID = AprilTagIDs.BLUE_LEFTTRENCH_FRONT_TAG_ID;
            homeLeftTrenchBackID = AprilTagIDs.BLUE_LEFTTRENCH_BACK_TAG_ID;

            homeRightTrenchFrontID = AprilTagIDs.BLUE_RIGHTTRENCH_FRONT_TAG_ID;
            homeRightTrenchBackID = AprilTagIDs.BLUE_RIGHTTRENCH_BACK_TAG_ID;

            homeTowerWallLeftID = AprilTagIDs.BLUE_TOWERWALL_LEFT_TAG_ID;
            homeTowerWallRightID = AprilTagIDs.BLUE_TOWERWALL_RIGHT_TAG_ID;

            // Opponent IDs
            awayHubFrontLeftID = AprilTagIDs.RED_HUB_FRONT_LEFT_TAG_ID;
            awayHubFrontRightID = AprilTagIDs.RED_HUB_FRONT_RIGHT_TAG_ID;

            awayHubLeftsideLeftID = AprilTagIDs.RED_HUB_LEFTSIDE_LEFT_TAG_ID;
            awayHubLeftsideRightID = AprilTagIDs.RED_HUB_LEFTSIDE_RIGHT_TAG_ID;

            awayHubRightsideLeftID = AprilTagIDs.RED_HUB_RIGHTSIDE_LEFT_TAG_ID;
            awayHubRightsideRightID = AprilTagIDs.RED_HUB_RIGHTSIDE_RIGHT_TAG_ID;

            awayHubBackLeftID = AprilTagIDs.RED_HUB_BACK_LEFT_TAG_ID;
            awayHubBackRightID = AprilTagIDs.RED_HUB_BACK_RIGHT_TAG_ID;

            awayOutpostLeftID = AprilTagIDs.RED_OUTPOST_LEFT_TAG_ID;
            awayOutpostRightID = AprilTagIDs.RED_OUTPOST_RIGHT_TAG_ID;

            awayLeftTrenchFrontID = AprilTagIDs.RED_LEFTTRENCH_FRONT_TAG_ID;
            awayLeftTrenchBackID = AprilTagIDs.RED_LEFTTRENCH_BACK_TAG_ID;

            awayRightTrenchFrontID = AprilTagIDs.RED_RIGHTTRENCH_FRONT_TAG_ID;
            awayRightTrenchBackID = AprilTagIDs.RED_RIGHTTRENCH_BACK_TAG_ID;
            
            awayTowerWallLeftID = AprilTagIDs.RED_TOWERWALL_LEFT_TAG_ID;
            awayTowerWallRightID = AprilTagIDs.RED_TOWERWALL_RIGHT_TAG_ID;
        }
    }
}