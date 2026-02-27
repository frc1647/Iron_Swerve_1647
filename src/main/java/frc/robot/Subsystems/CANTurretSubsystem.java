package frc.robot.Subsystems;

import frc.robot.Constants.TurretConstants;
import static edu.wpi.first.units.Units.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import java.util.function.DoubleSupplier;

import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
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

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase.ControlType;

public class CANTurretSubsystem extends SubsystemBase {

    private final SparkMax turretMotor;
    private final RelativeEncoder turretEncoder;
    private final SparkClosedLoopController turretPID;
    private SparkMaxConfig motorConfig;

    public CANTurretSubsystem() {
        turretMotor = new SparkMax(TurretConstants.TURRET_ID, MotorType.kBrushless);
        turretMotor.setCANTimeout(250);

        turretEncoder = turretMotor.getEncoder();
        turretPID = turretMotor.getClosedLoopController();

        motorConfig = new SparkMaxConfig();

        motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.192)
        .i(0.0002)
        .d(0.4)
        .outputRange(-1, 1)
        // Set PID values for velocity control in slot 1
        .p(0.0001, ClosedLoopSlot.kSlot1)
        .i(0, ClosedLoopSlot.kSlot1)
        .d(0, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1)
        .feedForward
          // kV is now in Volts, so we multiply by the nominal voltage (12V)
          .kV(12.0 / 5767, ClosedLoopSlot.kSlot1);
          
        motorConfig.voltageCompensation(12);
        motorConfig.smartCurrentLimit(TurretConstants.TURRET_MOTOR_CURRENT_LIMIT);

        turretMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public double GetTurretAngle() {
        return turretEncoder.getPosition() * 360;
    }

    public void zeroTurret() {
        turretEncoder.setPosition(0);
    }

    /**
     * Rotates the turret by a set number of degrees
     * @param angleChangeDegrees Number of degrees to rotate by
     */
    public void rotateByDegrees(double angleChangeDegrees) {
        double currentRotations = turretEncoder.getPosition();
        double deltaRotations = (angleChangeDegrees / 360.0) * TurretConstants.TURRET_GEAR_RATIO;

        double targetRotations = currentRotations + deltaRotations;

        turretPID.setSetpoint(targetRotations, ControlType.kPosition);
    }

    /**
     * Rotates the turret to a specific point
     * @param turretSubsystem The turret
     * @param angleChange the difference between the end point and current position
     */
    public Command rotateTurret(CANTurretSubsystem turretSubsystem, double angleChange) {
        return Commands.runOnce(() -> {
            double rotation = GetTurretAngle();
            double goalRotation = rotation + angleChange;

            if (goalRotation < TurretConstants.TURRET_AIMING_LIMIT_MAX && goalRotation > TurretConstants.TURRET_AIMING_LIMIT_MIN) { 
                // end Rotation is within bounds, it is valid so let it rotate normally
                turretSubsystem.rotateByDegrees(angleChange);
            } else if (goalRotation >= TurretConstants.TURRET_AIMING_LIMIT_MAX) { 
                // end Rotation is outside of bounds, too large
                turretSubsystem.rotateByDegrees(-360 + goalRotation - rotation);
            } else {  // end Rotation is outside of bounds, too small
                turretSubsystem.rotateByDegrees(360 + goalRotation - rotation);
            }
        }, 
        turretSubsystem
        );
        
    }
}