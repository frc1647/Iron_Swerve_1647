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

    public static Command AutoOne(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem, CANRollerSubsystem rollerSubsystem) {
        return Commands.sequence(
            Commands.run(() -> intakeSubsystem.goDown(), intakeSubsystem).withTimeout(0.5),
            Commands.run(() -> intakeSubsystem.brakeIntake(), intakeSubsystem).withTimeout(0.1),
            Commands.run(() -> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0),
            Commands.run(() -> shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(3.0),
            Commands.run(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0),
            Commands.run(() -> Commands.parallel(
                new RunCommand(() -> driveTrain.driveTankII(1, 1), driveTrain),
                new RunCommand(() -> rollerSubsystem.grabBalls()))
            ).withTimeout(2.0),
            //Commands.run(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID, 1).until(() -> Camera.sauronSeesTag(AprilTags.homeHubBackLeftID)&&Math.abs(Camera.sauronHorizontal())<=AutoConstants.ANGLE_TOLERANCE))
            Commands.run(() -> shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(3.0),
            Commands.run(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2.0)
        );
    }

    // go to player station to retrieve balls
    public static Command AutoTwo(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem, CANRollerSubsystem rollerSubsystem) {
         return (new RunCommand(() -> intakeSubsystem.goDown(), intakeSubsystem).withTimeout(0.8))
        .andThen(new RunCommand(() -> intakeSubsystem.brakeIntake(), intakeSubsystem).withTimeout(0.1))
        .andThen(new RunCommand(() -> driveTrain.driveTankII(-AutoConstants.AUTO_MAX_SPEED, -AutoConstants.AUTO_MAX_SPEED), driveTrain).withTimeout(1.0))
        //.andThen(new RunCommand(() -> driveTrain.driveTankII(-AutoConstants.AUTO_MAX_SPEED, AutoConstants.AUTO_MAX_SPEED), driveTrain).withTimeout(AutoConstants.AUTO_TURN_TIME))
        .andThen(Commands.sequence(Commands.run(() -> shooterSubsystem.runShooterDirect(1.1)).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), Commands.run(() -> {shooterSubsystem.runShooterDirect();hopper.runUpperPassageDirect();}).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), Commands.run(() -> {shooterSubsystem.runShooterDirect();hopper.runUpperPassageDirect();hopper.runSpindexerDirect();})).withTimeout(10.0))
        //.andThen(new RunCommand(() -> driveTrain.driveTankII(AutoConstants.AUTO_MAX_SPEED, -AutoConstants.AUTO_MAX_SPEED), driveTrain).withTimeout(AutoConstants.AUTO_TURN_TIME))
        .andThen(Commands.parallel(
            new RunCommand(() -> driveTrain.driveTankII(-AutoConstants.AUTO_MAX_SPEED, -AutoConstants.AUTO_MAX_SPEED), driveTrain),
            new RunCommand(() -> rollerSubsystem.grabBalls(), rollerSubsystem)
        ).withTimeout(2.0))
        .andThen(new RunCommand(() -> rollerSubsystem.stopRoller(), rollerSubsystem).withTimeout(0.1))
        .andThen(Commands.run(() -> {shooterSubsystem.stopShooter();hopper.stopPassage();hopper.stopSpindexer();}).withTimeout(0.1));
    }

    // Go to center pick up balls and pass to human player station
    public static Command AutoThree(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem, Hopper hopper, CANIntakeSubsystem intakeSubsystem, CANRollerSubsystem rollerSubsystem) {
        return new AprilTagCommands.DriveToAprilTag(driveTrain, turretSubsystem, AprilTags.awayHubFrontLeftID)
            //.andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeOutpostLeftID, 1))
            .andThen(shooterSubsystem.scoreCommand(AutoConstants.AUTO_SHOOT_TIME, hopper)).withTimeout(AutoConstants.AUTO_SHOOT_TIME);
    }
}