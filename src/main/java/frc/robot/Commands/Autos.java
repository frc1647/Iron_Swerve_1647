// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.*;
import frc.robot.AprilTags;
import frc.robot.Commands.AprilTagCommands;

public final class Autos {

    public static Command exampleAuto(CANDriveSubsystem drivetrain) {
        return new RunCommand(() -> drivetrain.driveTankII(.5, .5), drivetrain)
                // .andThen(() -> drivetrain.driveTankII(.7,.7))
                .withTimeout(3)
                .andThen(new RunCommand(() -> drivetrain.driveTankII(0, 0)));
    }

    
    public static Command AutoOne(CANTurretSubsystem turretSubsystem, CANDriveSubsystem driveTrain, CANShooterSubsystem shooterSubsystem) {
        return new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2)
        .andThen(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, AprilTags.homeHubBackLeftID))
        .andThen(new RunCommand(() -> shooterSubsystem.runShooter(), shooterSubsystem)).withTimeout(2)
        .andThen(new RunCommand(()-> driveTrain.driveTankII(1, 1), driveTrain).withTimeout(2));
    }

    // 
    public static Command AutoTwo() {
        return new RunCommand(() -> {
            // Do thing!
        });
    }


    public static Command AutoThree() {
        return new RunCommand(() -> {
            // Do thing!
        });
    }
    
}
