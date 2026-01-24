package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Constants.ShooterConstants;

public class CANShooter {

    private final MotorController shooter;

    public CANShooter() {
        shooter = new SparkMax(ShooterConstants.SHOOTER_ID, MotorType.kBrushless);
    }
}
