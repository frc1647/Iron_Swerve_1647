
package frc.robot.Commands;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.CANDriveSubsystem;
import frc.robot.Subsystems.CANTurretSubsystem;
import frc.robot.Subsystems.Camera;
import frc.robot.AprilTags;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.TurretConstants;
import frc.robot.Constants;

public final class AprilTagCommands {

    public static Command AimTowardsAprilTag(CANTurretSubsystem turretSubsystem, int id) {
        return turretSubsystem.run(() -> {
            if (Camera.sauronSeesTag(id)) {
                turretSubsystem.rotateByDegrees(Camera.sauronHorizontal());
            }
        });
    }

    public static Command SwitchShootingMode(RobotContainer robotContainer) {
        return Commands.runOnce(() -> {
            robotContainer.targetingHub = !robotContainer.targetingHub;
            if (robotContainer.targetingHub) {
                robotContainer.targetTagLeft = AprilTags.homeHubBackLeftID;
                robotContainer.targetTagRight = AprilTags.homeHubBackLeftID;
            } else { 
                robotContainer.targetTagLeft = AprilTags.homeOutpostLeftID;
                robotContainer.targetTagRight = AprilTags.homeOutpostRightID;
            }
        });
    }

    public class DriveToAprilTag extends Command {

        // TODO: Rest position of Turret?
        private final CANDriveSubsystem m_drive;
        private final CANTurretSubsystem m_turretSubsystem;
        private final int m_targetTagID;

        // --- Tuning Constants ---
        private static final double kP_Turn  = AutoConstants.TURN_STRENGTH;
        private static final double kP_Drive = AutoConstants.DRIVE_STRENGTH;

        private static final double kTargetDistanceIn = AutoConstants.TARGET_INCHES_FROM_TAG;
        private static final double kDistanceTolIn    = AutoConstants.DISTANCE_TOLERANCE;
        private static final double kAngleTolDeg      = AutoConstants.ANGLE_TOLERANCE;

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

            double turretOffsetAngle = m_turretSubsystem.GetTurretAngle();
            double cameraAngle = Camera.sauronHorizontal();
            double distance = Camera.sauronDistanceInches();

            if (distance < 0) {
                m_drive.driveTankII(0, 0);
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

            double tx = Camera.sauronHorizontal();
            double distance = Camera.sauronDistanceInches();

            return distance > 0 && Math.abs(distance - kTargetDistanceIn) < kDistanceTolIn && Math.abs(tx) < kAngleTolDeg;
        }

        @Override
        public void end(boolean interrupted) {
            m_drive.driveTankII(0, 0);
            Camera.setSauronPriorityTag(-1);
        }
    }
    

    
} 