
package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.CANDriveSubsystem;
import frc.robot.Subsystems.CANTurretSubsystem;
import frc.robot.Subsystems.Camera;


public final class AprilTagCommands {

    public static boolean CanTargetAprilTag(int id) {
        return (Camera.watcherHasTag() && Camera.watcherTagID() == id);
    }

    // Rotate, then drive forward to the target april tag
    public static Command GoToAprilTag(CANDriveSubsystem drivetrain, Camera camera, int id) {
        // TODO: Figure out direction stuff for int id
            
        return new RunCommand(() -> drivetrain.driveTankII(1, 1), drivetrain)
            .withTimeout(3)
            .andThen(new RunCommand(() -> drivetrain.driveTankII(0, 0)));
    }

    // Rotate the turret toward the target april tag
    public static Command TargetAprilTag(CANTurretSubsystem turretSubsystem, int id) {
        // TODO: filter based on april tag id
        return new RunCommand(() -> turretSubsystem.rotateTurret(turretSubsystem, Camera.watcherHorizontal()), turretSubsystem);
    }

    // This should supply the drivetrain with the input to rotate towards the target april tag
    public static Command RotateTowardsAprilTag() throws Exception {
        throw new Exception();
    }

    // This should drive the robot forward a desired distance away from the target april tag
    public static Command DriveTowardsAprilTag() throws Exception {
        throw new Exception();
    }

    public static Command SwitchShootingMode() {
        return Commands.runOnce(() -> {
            // Switch Target April tag here!!!!!!!!!!!
        }
        );
    }
}