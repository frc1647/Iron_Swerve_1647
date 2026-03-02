package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;                                                     
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;

public class CANShooterSubsystem extends SubsystemBase {
  private final SparkMax shooterMotor;
  private final SparkClosedLoopController closedLoopController;
  private final RelativeEncoder encoder;

  public CANShooterSubsystem() {
    shooterMotor = new SparkMax(ShooterConstants.SHOOTER_ID, MotorType.kBrushless);
    closedLoopController = shooterMotor.getClosedLoopController();
    encoder = shooterMotor.getEncoder();


    SparkMaxConfig config = new SparkMaxConfig();

    config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(0.001)
        .i(0.0)
        .d(0.004)
        .outputRange(-1, 1)

        .p(0.0001, ClosedLoopSlot.kSlot1)
        .i(0.0, ClosedLoopSlot.kSlot1)
        .d(0.004, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1)

        .feedForward
          .kV(0.000175, ClosedLoopSlot.kSlot0)
          .kV(0.000175, ClosedLoopSlot.kSlot1);

    config.voltageCompensation(12);
    config.smartCurrentLimit(ShooterConstants.SHOOTER_MOTOR_CURRENT_LIMIT);

    shooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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