package frc.robot.Subsystems;


import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.security.PrivateKey;
import java.util.function.DoubleSupplier;                                                     
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Subsystems.Camera;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import yams.gearing.GearBox;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
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


public class CANShooterSubsystem extends SubsystemBase {
  private final SparkMax shooterMotor;
  private final SparkClosedLoopController closedLoopController;
  private final RelativeEncoder encoder;

  private static final double kP = 0.0001;
  private static final double kI = 0.0;
  private static final double kD = 0.0;
  private static final double kFF = 0.000156;

  public CANShooterSubsystem() {
    shooterMotor = new SparkMax(ShooterConstants.SHOOTER_ID, MotorType.kBrushless);
    closedLoopController = shooterMotor.getClosedLoopController();
    encoder = shooterMotor.getEncoder();


    SparkMaxConfig config = new SparkMaxConfig();

    shooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    /*
    closedLoopController.setP(kP);
    closedLoopController.setI(kI);
    closedLoopController.setD(kD);
    closedLoopController.setFF(kFF);
    closedLoopController.setOutputRange(-1, 1);
    */
  }
  
  public double GetRPMForDistance(double distanceIN) {
    return 320.23162 * Math.pow(distanceIN, 0.469592);    
  }

  public Command runShooter() {
    return Commands.run(() -> runShooterDirect(), this);
  }

  public void runShooterDirect() {
    // If there are no apriltags in sight of the Sauron camera, do not spin at all
    double distance = Camera.sauronDistanceInches();
    if (distance < 0) {
      distance = ShooterConstants.DEFAULT_SHOOTING_DISTANCE;
    }
    double RPM = GetRPMForDistance(distance);
    closedLoopController.setSetpoint(RPM, ControlType.kVelocity);
  }

  public void stopShooter() {
    closedLoopController.setSetpoint(0, ControlType.kVelocity);
  }
}