
package frc.robot.Commands;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.CANDriveSubsystem;
import frc.robot.Subsystems.CANTurretSubsystem;
import frc.robot.Subsystems.Camera;
import frc.robot.AprilTags;
import frc.robot.RobotContainer; 
import frc.robot.Constants;

public final class AprilTagCommands {

    // Rotate, then drive forward to the target april tag
    public static Command GoToAprilTag(CANDriveSubsystem drivetrain, int id) {
        // TODO: Figure out direction stuff for int id
        
            
        return new RunCommand(() -> drivetrain.driveTankII(1, 1), drivetrain)
            .withTimeout(3)
            .andThen(new RunCommand(() -> drivetrain.driveTankII(0, 0)));
    }

    public static Command RotateTowardsAprilTag(CANTurretSubsystem turretSubsystem, int id) {
        boolean valid = Camera.sauronSeesTag(id);

        if (valid) {
            return turretSubsystem.rotateTurret(turretSubsystem, Camera.sauronHorizontal());
        }
        else {
            return Constants.DontDoAnythingStayStillCommand();
        }
    }

    // This should drive the robot forward a desired distance away from the target april tag
    public static Command DriveTowardsAprilTag() throws Exception {
        throw new Exception();
    }

    // public static Command ScanForTargets(CANTurretSubsystem turretSubsystem) {
    //     return new Command() {
    //         @Override
    //         public void execute() {
    //             if (Camera.sauronSeesTag(id)) {
    //                 turretSubsystem.rotateTurret(turretSubsystem, Camera.sauronHorizontal());
    //             } else {
    //                 turretSubsystem.rotateTurret(turretSubsystem, 0);
    //             }
    //         }
        
    //         @Override
    //         public boolean isFinished() {
    //             return Camera.sauronSeesTag(id) && Math.abs(Camera.sauronHorizontal()) < 2.0;
    //         }
        
    //         @Override
    //         public void end(boolean interrupted) {
    //             turretSubsystem.rotateTurret(turretSubsystem, 0);
    //         }
    //     }.withTimeout(3.0);
    // }

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
        }
        );
    }                 
} 