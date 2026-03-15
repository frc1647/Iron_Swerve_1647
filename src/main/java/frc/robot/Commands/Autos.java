// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.*;
import frc.robot.AprilTags;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Commands.AprilTagCommands;

public final class Autos {

    public static void AutoOne(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem, CANRollerSubsystem rollerSubsystem) {
        Commands.sequence(
            Commands.run(() -> intakeSubsystem.goDown()).withTimeout(0.5),
            Commands.run(() -> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0),
            Commands.run(() -> shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(3.0),
            Commands.run(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0),
            Commands.run(() -> Commands.parallel(
                new RunCommand(() -> driveTrain.driveTankII(1, 1)),
                new RunCommand(() -> rollerSubsystem.grabBalls()).withTimeout(2))
            ).withTimeout(2.0),
            //Commands.run(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID, 1).until(() -> Camera.sauronSeesTag(AprilTags.homeHubBackLeftID)&&Math.abs(Camera.sauronHorizontal())<=AutoConstants.ANGLE_TOLERANCE))
            Commands.run(() -> shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(3.0),
            Commands.run(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0)
        );
    }

    // go to player station to retrieve balls
    public static Command AutoTwo(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem) {
          return new RunCommand(() -> intakeSubsystem.goDown()).withTimeout(1.0)
        .andThen(new RunCommand(() -> driveTrain.driveTankII(1, 1), driveTrain)).withTimeout(2.0)
        .andThen(new RunCommand(() -> driveTrain.driveTankII(1, -1), driveTrain)).withTimeout(0.2)
        .andThen(new RunCommand(() -> shooterSubsystem.score(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(AutoConstants.AUTO_SHOOT_TIME+2.0))
        //.andThen(new RunCommand(() -> turretSubsystem.resetTurret()))
        .andThen(new AprilTagCommands.DriveToAprilTag(driveTrain, turretSubsystem, AprilTags.homeOutpostLeftID));
    }


    // Go to center pick up balls and pass to human player station
    public static Command AutoThree(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem, CANRollerSubsystem rollerSubsystem) {
        return new AprilTagCommands.DriveToAprilTag(driveTrain, turretSubsystem, AprilTags.awayHubFrontLeftID)
            //.andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeOutpostLeftID, 1))
            .andThen(shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(AutoConstants.AUTO_SHOOT_TIME);
    }
}