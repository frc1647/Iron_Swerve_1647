package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HopperConstants;

public class Hopper extends SubsystemBase {

    private final TalonFX spindexerMotor;


    public Hopper() {
        spindexerMotor = new TalonFX(HopperConstants.SPINDEXER_MOTOR_ID);
    }
}
