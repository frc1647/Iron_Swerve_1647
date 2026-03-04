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
import frc.robot.Constants.SpecialConstants;
import frc.robot.Commands.AprilTagCommands;

public final class Autos {

    public static Command exampleAuto(CANDriveSubsystem drivetrain) {
        return new RunCommand(() -> drivetrain.driveTankII(.5, .5), drivetrain)
                // .andThen(() -> drivetrain.driveTankII(.7,.7))
                .withTimeout(3)
                .andThen(new RunCommand(() -> drivetrain.driveTankII(0, 0)));
    }

    
    public static Command AutoOne(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper) {
        return new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2)
        .andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID))
        .andThen(new RunCommand(() -> Commands.sequence(Commands.run(() -> shooterSubsystem.runShooterDirect()).withTimeout(SpecialConstants.SHOOTER_TIMEOUT), Commands.run(() -> {shooterSubsystem.runShooterDirect();hopper.runUpperPassageDirect();}).withTimeout(SpecialConstants.SHOOTER_TIMEOUT), Commands.run(() -> {shooterSubsystem.runShooterDirect();hopper.runUpperPassageDirect();hopper.runLowerPassageDirect();}).withTimeout(SpecialConstants.SHOOTER_TIMEOUT), Commands.run(() -> {shooterSubsystem.runShooterDirect();hopper.runUpperPassageDirect();hopper.runLowerPassageDirect();hopper.runSpindexerDirect();})), shooterSubsystem, hopper)).withTimeout(AutoConstants.AUTO_SHOOT_TIME)
        .andThen(new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2));
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
