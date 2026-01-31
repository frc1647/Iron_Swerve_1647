package frc.robot.Subsystems;

import frc.robot.Constants.TurretConstants;
import static edu.wpi.first.units.Units.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import java.util.function.DoubleSupplier;
import com.revrobotics.spark.SparkMax;
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
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;


/*public class CANTurretSubsystem extends SubsystemBase {

    private final SparkMax turretMotor;
    private final RelativeEncoder turretEncoder;
    private final SparkClosedLoopController turretPID;
    public double totalRotation = 0;

    public CANTurretSubsystem() {
        turretMotor = new SparkMax(TurretConstants.TURRET_ID, MotorType.kBrushless);
        turretMotor.setCANTimeout(250);
        
        turretEncoder = turretMotor.getEncoder();
        turretPID = turretMotor.getClosedLoopController();

        // Create and apply SparkMax configuration
        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(TurretConstants.TURRET_MOTOR_CURRENT_LIMIT);
        
        turretMotor.configure(config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

    }

    public void zeroTurret() {
        turretEncoder.setPosition(0);
    }

    public void rotateByDegrees(double angleChangeDegrees) {
        double currentRotations = turretEncoder.getPosition();
        double deltaRotations = (angleChangeDegrees / 360.0) * TurretConstants.TURRET_GEAR_RATIO;

        double targetRotations = currentRotations + deltaRotations;

        turretPID.setSetpoint(targetRotations, ControlType.kPosition);
    }

    public Command rotateTurret(CANTurretSubsystem turretSubsystem, double angleChange) {
        if (totalRotation > 270 || totalRotation < -270) {
            totalRotation += angleChange;
            return Commands.runOnce(() -> turretSubsystem.rotateByDegrees(angleChange), turretSubsystem);
    } else {
        return Commands.runOnce(() -> turretSubsystem.rotateByDegrees(0), turretSubsystem);
    }
    } 
}*/