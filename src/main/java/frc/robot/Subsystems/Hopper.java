package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HopperConstants;

public class Hopper extends SubsystemBase {

    private final TalonFX spindexerMotor;

    private final SparkMax passageMotorLower;
    private final SparkMax passageMotorUpper;

    public Hopper() {
        spindexerMotor = new TalonFX(HopperConstants.SPINDEXER_MOTOR_ID);


        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 30;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.Feedback.SensorToMechanismRatio = HopperConstants.SPINDEXER_GEAR_RATIO;

        passageMotorLower = new SparkMax(HopperConstants.PASSAGE_MOTOR_LOWER_ID, MotorType.kBrushless);
        passageMotorUpper = new SparkMax(HopperConstants.PASSAGE_MOTOR_UPPER_ID, MotorType.kBrushless);

    }

    public Command runSpindexer() {
        return Commands.run(() -> runSpindexerDirect(), this)
        .finallyDo(() -> stopSpindexer());
    }

    public Command runPassage() {
        return Commands.run(() -> runPassageDirect(), this)
        .finallyDo(() -> stopPassage());
    }

    public void runSpindexerDirect() {
        spindexerMotor.set(HopperConstants.SPINDEXER_SPEED);
    }

    public void stopSpindexer() {
        spindexerMotor.set(0);
    }

    public void runPassageDirect() {
        runLowerPassageDirect();
        runUpperPassageDirect();
    }

    public void runLowerPassageDirect() {
        passageMotorLower.set(HopperConstants.PASSAGE_SPEED);
    }

    public void runUpperPassageDirect() {
        passageMotorUpper.set(HopperConstants.PASSAGE_SPEED);
    }

    public void stopPassage() {
        passageMotorLower.set(0);
        passageMotorUpper.set(0);
    }
}
