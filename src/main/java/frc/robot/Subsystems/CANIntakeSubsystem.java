package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.*;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

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

    private final SparkMax leftIntakeMotor;
    private final SparkMax rightIntakeMotor;
    private final RelativeEncoder leftIntakeEncoder;
    private final RelativeEncoder rightIntakeEncoder;
    private final SparkClosedLoopController leftIntakePID;
    private final SparkClosedLoopController rightIntakePID;
    public double totalRotation = 0;

    public CANIntakeSubsystem() {
        leftIntakeMotor = new SparkMax(IntakeConstants.LEFT_INTAKE_ID, MotorType.kBrushless);
        leftIntakeMotor.setCANTimeout(250);
        
        leftIntakeEncoder = leftIntakeMotor.getEncoder();
        leftIntakePID = leftIntakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig leftConfig = new SparkMaxConfig();
        leftConfig.voltageCompensation(12);
        leftConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        leftIntakeMotor.configure(leftConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        rightIntakeMotor = new SparkMax(IntakeConstants.RIGHT_INTAKE_ID, MotorType.kBrushless);
        rightIntakeMotor.setCANTimeout(250);
        
        rightIntakeEncoder = rightIntakeMotor.getEncoder();
        rightIntakePID = rightIntakeMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig rightConfig = new SparkMaxConfig();
        rightConfig.voltageCompensation(12);
        rightConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        
        rightIntakeMotor.configure(rightConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }

    public Command grabBalls() {
        return run(null);
    }
}