package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HopperConstants;

public class Hopper extends SubsystemBase {

    private final SparkMax spindexerMotor;


    public Hopper() {
        spindexerMotor = new SparkMax(HopperConstants.SPINDEXER_MOTOR_ID, MotorType.kBrushless);
    }
}
