
package frc.robot.Commands;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.CANDriveSubsystem;
import frc.robot.Subsystems.CANTurretSubsystem;
import frc.robot.Subsystems.Camera;
import frc.robot.AprilTags;
import frc.robot.Constants.AutoConstants;

public class AprilTagCommands {
    public static boolean targetingHub = true;
    public static int targetTagLeft = AprilTags.homeHubBackLeftID;
    public static int targetTagRight = AprilTags.homeHubBackRightID;
    public static String target = "Hub";

    /*
    public static Command AimTowardsAprilTag(CANTurretSubsystem turretSubsystem, int id, double factor) {
        return turretSubsystem.runOnce(() -> {
            if (Camera.sauronSeesTag(id)) {
                turretSubsystem.rotateTurret(-Camera.sauronHorizontal()/factor);
            } else {
                turretSubsystem.rotateTurret(20.0/factor);
            }
        });    
    }
    */

    public static void SwitchShootingMode() {
            targetingHub = !targetingHub;
            if (targetingHub) {
                targetTagLeft = AprilTags.homeHubBackLeftID;
                targetTagRight = AprilTags.homeHubBackRightID;
                target = "Hub";
            } else { 
                targetTagLeft = AprilTags.homeOutpostLeftID;
                targetTagRight = AprilTags.homeOutpostRightID;
                target = "Outpost";
            }
        };
    
    public static Command turnToFaceAprilTag(CANDriveSubsystem driveSubsystem) {
        return new RunCommand(() -> {

            double turnAngle = Camera.sauronHorizontal();
            double turnSpeed  = turnAngle * 0.05;

            // Combine drive and turn, then clamp each side independently
            double leftSpeed  = MathUtil.clamp(turnSpeed, -AutoConstants.AUTO_MAX_SPEED, AutoConstants.AUTO_MAX_SPEED);
            double rightSpeed = MathUtil.clamp(-turnSpeed, -AutoConstants.AUTO_MAX_SPEED, AutoConstants.AUTO_MAX_SPEED); 
        
            driveSubsystem.driveTankII(leftSpeed, rightSpeed);
        }, driveSubsystem);
    }


    public static class DriveToAprilTag extends Command {

        private final CANDriveSubsystem m_drive;
        private final CANTurretSubsystem m_turretSubsystem;
        private final int m_targetTagID;

        // --- Tuning Constants ---
        private static final double kP_Turn = AutoConstants.TURN_STRENGTH;
        private static final double kP_Drive = AutoConstants.DRIVE_STRENGTH;

        private static final double kTargetDistanceIn = AutoConstants.TARGET_INCHES_FROM_TAG;
        private static final double kDistanceTolIn = AutoConstants.DISTANCE_TOLERANCE;
        private static final double kAngleTolDeg = AutoConstants.ANGLE_TOLERANCE;

        private static final double kMaxSpeed = AutoConstants.AUTO_MAX_SPEED;

        public DriveToAprilTag(CANDriveSubsystem drive, CANTurretSubsystem turretSubsystem, int tagID) {
            m_drive = drive;
            m_turretSubsystem = turretSubsystem;
            m_targetTagID = tagID;
            addRequirements(drive);
            addRequirements(turretSubsystem);
        }

        @Override
        public void initialize() {
            Camera.setSauronPriorityTag(m_targetTagID);
        }

        @Override
        public void execute() {
            if (!Camera.sauronSeesTag(m_targetTagID)) {
                m_drive.driveTankII(0, 0);
                return;
            }

            double turretOffsetAngle = 0; // m_turretSubsystem.GetTurretAngle();
            double cameraAngle = Camera.sauronHorizontal();
            double distance = Camera.sauronDistanceInches();

            if (distance < 0) {
                m_drive.driveTankII(1, 1);
                return;
            }

            double turnSpeed  = kP_Turn  * (cameraAngle + turretOffsetAngle);
            double driveSpeed = kP_Drive * (distance - kTargetDistanceIn);

            // Combine drive and turn, then clamp each side independently
            double leftSpeed  = MathUtil.clamp(driveSpeed - turnSpeed, -kMaxSpeed, kMaxSpeed);
            double rightSpeed = MathUtil.clamp(driveSpeed + turnSpeed, -kMaxSpeed, kMaxSpeed);

            m_drive.driveTankII(leftSpeed, rightSpeed);
        }

        @Override
        public boolean isFinished() {
            if (!Camera.sauronSeesTag(m_targetTagID)) return false;

            double turretOffsetAngle = 0; // m_turretSubsystem.GetTurretAngle();
            double cameraAngle = Camera.sauronHorizontal();
            double robotAngleToTarget = cameraAngle + turretOffsetAngle;
            double distance = Camera.sauronDistanceInches();

            return distance > 0 && Math.abs(distance - kTargetDistanceIn) < kDistanceTolIn && Math.abs(robotAngleToTarget) < kAngleTolDeg;  
        }

        @Override
        public void end(boolean interrupted) {
            m_drive.driveTankII(0, 0);
            Camera.setSauronPriorityTag(-1);
        }
    }
} 