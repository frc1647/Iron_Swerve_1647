package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.IntakeConstants;

public class CANIntakeSubsystem extends SubsystemBase {

    private final SparkMax intakeMotor;
    private final SparkFlex goIntakeMotor;
    private final RelativeEncoder intakeEncoder;
    private final RelativeEncoder goIntakeEncoder;
    private final SparkClosedLoopController intakePID;
    private final SparkClosedLoopController goIntakePID;

    public CANIntakeSubsystem() {
        intakeMotor = new SparkMax(IntakeConstants.INTAKE_ID, MotorType.kBrushless);
        intakeMotor.setCANTimeout(250);
        
        intakeEncoder = intakeMotor.getEncoder();
        intakePID = intakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.voltageCompensation(12);
        intakeConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        intakeMotor.configure(intakeConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        goIntakeMotor = new SparkFlex(IntakeConstants.GO_INTAKE_ID, MotorType.kBrushless);
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
        return Commands.run(() -> intakeMotor.set(1), this)
        .finallyDo(() -> intakeMotor.set(0));
    }

    public Command goIntake() {
        return Commands.run(() -> goIntakePID.setSetpoint(IntakeConstants.INTAKE_ANGLE/360, ControlType.kPosition));
    }

    public Command comeBackIntake() {
        return Commands.run(() -> goIntakePID.setSetpoint(0, ControlType.kPosition));
    }
}