// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.CANDriveSubsystem;

public final class Autos {

    public static Command exampleAuto(CANDriveSubsystem drivetrain) {
        return new RunCommand(() -> drivetrain.driveTankII(.5, .5), drivetrain)
                // .andThen(() -> drivetrain.driveTankII(.7,.7))
                .withTimeout(3)
                .andThen(new RunCommand(() -> drivetrain.driveTankII(0, 0)));
    }


    
}
