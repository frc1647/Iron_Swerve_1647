package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.IntakeConstants;

public class CANIntakeSubsystem extends SubsystemBase {
    private final SparkMax goIntakeMotor;
    
    private final RelativeEncoder goIntakeEncoder;
    
    private final SparkClosedLoopController goIntakePID;
    public double goIntakePosition;

    public CANIntakeSubsystem() {
        
        goIntakeMotor = new SparkMax(IntakeConstants.GO_INTAKE_ID, MotorType.kBrushless);
        goIntakeMotor.setCANTimeout(250);
        
        goIntakeEncoder = goIntakeMotor.getEncoder();
        goIntakePID = goIntakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig goConfig = new SparkMaxConfig();
        goConfig.voltageCompensation(12);
        goConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        goConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(IntakeConstants.GO_INTAKE_P)
            .i(IntakeConstants.GO_INTAKE_I)
            .d(IntakeConstants.GO_INTAKE_D)
            .outputRange(-1, 1);

        goConfig.encoder.positionConversionFactor(IntakeConstants.GO_INTAKE_GEAR_RATIO);
        goConfig.encoder.velocityConversionFactor(IntakeConstants.GO_INTAKE_GEAR_RATIO);

        goIntakeMotor.configure(goConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }

    public Command goIntake(CANIntakeSubsystem intakeSubsystem) {
        return Commands.run(() -> goIntakePID.setSetpoint(getIntakePosition()+IntakeConstants.INTAKE_ANGLE/(360), ControlType.kPosition), intakeSubsystem);
    }

    public Command comeBackIntake(CANIntakeSubsystem intakeSubsystem) {
        return Commands.run(() -> goIntakePID.setSetpoint(getIntakePosition()-IntakeConstants.INTAKE_ANGLE/(360), ControlType.kPosition), intakeSubsystem);
    }

    public void zeroIntake() {
        goIntakeEncoder.setPosition(0);
    }

    public double getIntakePosition() {
        return goIntakeEncoder.getPosition();
    }
}