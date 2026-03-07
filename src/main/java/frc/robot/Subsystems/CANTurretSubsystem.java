package frc.robot.Subsystems;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.TurretConstants;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase.ControlType;

public class CANTurretSubsystem extends SubsystemBase {

    public double turretRotation;
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
        .p(TurretConstants.TURRET_P)
        .i(TurretConstants.TURRET_I)
        .d(TurretConstants.TURRET_D)
        .outputRange(-1, 1)
        .feedForward
        .kV(0.001, ClosedLoopSlot.kSlot0);
          
        motorConfig.voltageCompensation(12);
        motorConfig.smartCurrentLimit(TurretConstants.TURRET_MOTOR_CURRENT_LIMIT);

        turretMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public double GetTurretAngle() {
        return turretEncoder.getPosition() * 360/TurretConstants.TURRET_GEAR_RATIO;
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

        turretRotation += angleChangeDegrees;
    }

    /**
     * Rotates the turret to a specific point
     * @param turretSubsystem The turret
     * @param angleChange the difference between the end point and current position
     */
    public void rotateTurret(double angleChange) {
    double rotation = GetTurretAngle();
    double goalRotation = rotation + angleChange;

    // Try normalizing into range by wrapping
    while (goalRotation < TurretConstants.TURRET_AIMING_LIMIT_MIN) {
        goalRotation += 360;
    }
    while (goalRotation > TurretConstants.TURRET_AIMING_LIMIT_MAX) {
        goalRotation -= 360;
    }

    // Only move if wrapping found a valid position
    if (goalRotation >= TurretConstants.TURRET_AIMING_LIMIT_MIN && 
        goalRotation <= TurretConstants.TURRET_AIMING_LIMIT_MAX) {
        rotateByDegrees(goalRotation - rotation);
    }
    // else do nothing, position is unreachable
}

    public boolean isAtTarget(double deltaRotation) {
        return Math.abs(deltaRotation) < AutoConstants.ANGLE_TOLERANCE;
    }
}