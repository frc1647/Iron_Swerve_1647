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

    public static Command AutoOne(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper) {
        return new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2)
        .andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID))
        .andThen(shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).andThen(new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2));
    }

    // go to player station to retrieve balls
    public static Command AutoTwo(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper) {
        return AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID)
        .andThen(shooterSubsystem.runShooter()).withTimeout(AutoConstants.AUTO_SHOOT_TIME)
        .andThen(new AprilTagCommands.DriveToAprilTag(driveTrain, turretSubsystem, AprilTags.homeOutpostLeftID));
    }

    // Go to center pick up balls and pass to human player station
    public static Command AutoThree(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper) {
        return new AprilTagCommands.DriveToAprilTag(driveTrain, turretSubsystem, AprilTags.awayHubFrontLeftID)
        .andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeOutpostLeftID))
        .andThen(shooterSubsystem.runShooter()).withTimeout(AutoConstants.AUTO_SHOOT_TIME);
    }
}