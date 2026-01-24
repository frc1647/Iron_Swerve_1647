package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class CANShooterSubsystem extends SubsystemBase {

    private final SparkMax shooterMotor;

    public CANShooterSubsystem() {
        shooterMotor = new SparkMax(ShooterConstants.SHOOTER_ID, MotorType.kBrushless);

        ((SparkMax)shooterMotor).setCANTimeout(250);
        


        SparkMaxConfig shooterConfig = new SparkMaxConfig();
        //shooterConfig.smartCurrentLimit(SHOOTER_MOTOR_CURRENT_LIMIT);

    }

    public Command runShooter(CANShooterSubsystem shooterSubsystem, DoubleSupplier forward, DoubleSupplier reverse) {
        return Commands.run(() -> shooterMotor.set(.60), shooterSubsystem);
    }
//system.out.println
}