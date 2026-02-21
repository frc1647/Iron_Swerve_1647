package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.*;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkFlex;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.IntakeConstants;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import yams.gearing.GearBox;
import edu.wpi.first.units.measure.AngularVelocity;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.mechanisms.SmartMechanism;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper;
import yams.motorcontrollers.SmartMotorController;
import edu.wpi.first.math.system.plant.DCMotor;

public class CANIntakeSubsystem extends SubsystemBase {

    private final SparkMax intakeMotor;
    private final SparkMax goIntakeMotor;
    private final RelativeEncoder intakeEncoder;
    private final RelativeEncoder goIntakeEncoder;
    private final SparkClosedLoopController intakePID;
    private final SparkClosedLoopController goIntakePID;

    public CANIntakeSubsystem() {
        intakeMotor = new SparkMax(IntakeConstants.LEFT_INTAKE_ID, MotorType.kBrushless);
        intakeMotor.setCANTimeout(250);
        
        intakeEncoder = intakeMotor.getEncoder();
        intakePID = intakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.voltageCompensation(12);
        intakeConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        intakeMotor.configure(intakeConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        goIntakeMotor = new SparkMax(IntakeConstants.RIGHT_INTAKE_ID, MotorType.kBrushless);
        goIntakeMotor.setCANTimeout(250);
        
        goIntakeEncoder = goIntakeMotor.getEncoder();
        goIntakePID = goIntakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig goConfig = new SparkMaxConfig();
        goConfig.voltageCompensation(12);
        goConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        goIntakeMotor.configure(goConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }

    public Command grabBalls() {
        return Commands.run(() -> {
            intakeMotor.set(1);
            goIntakeMotor.set(1);
        }, this)
        .finallyDo(() -> {
            intakeMotor.set(0);
            goIntakeMotor.set(0);
        });
    }
}