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
import edu.wpi.first.wpilibj2.command.RunCommand;
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
        .p(ShooterConstants.SHOOTER_SLOT0_P)
        .i(ShooterConstants.SHOOTER_SLOT0_I)
        .d(ShooterConstants.SHOOTER_SLOT0_D)
        .outputRange(-1, 1)

        .p(ShooterConstants.SHOOTER_SLOT1_P, ClosedLoopSlot.kSlot1)
        .i(ShooterConstants.SHOOTER_SLOT1_I, ClosedLoopSlot.kSlot1)
        .d(ShooterConstants.SHOOTER_SLOT1_D, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1)

        .feedForward
        .kV(0.000175, ClosedLoopSlot.kSlot0)
        .kV(0.000175, ClosedLoopSlot.kSlot1);

    config.voltageCompensation(12);
    config.smartCurrentLimit(ShooterConstants.SHOOTER_MOTOR_CURRENT_LIMIT);

    shooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  
  /**
   * Returns the RPM needed to shoot a ball a specific distance
   * @param distanceIN Distance you want to shoot in inches
   * @return RPM
   */
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
    shooterMotor.stopMotor();
  }

  public void score(Hopper hopper) {
    Commands.sequence(
      Commands.run(() -> 
        runShooterDirect()).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), 

      Commands.run(() -> {
        runShooterDirect();
        hopper.runUpperPassageDirect();
      }).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), 

      Commands.run(() -> {
        runShooterDirect();
        hopper.runUpperPassageDirect();
        hopper.runSpindexerDirect();
      }));
  }

  public void score(double shooterTime, Hopper hopper) {
    Commands.sequence(
      Commands.run(() -> 
        runShooterDirect()).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), 

      Commands.run(() -> {
        runShooterDirect();
        hopper.runUpperPassageDirect();
      }).withTimeout(ShooterConstants.SHOOTER_TIMEOUT), 

      Commands.run(() -> {
        runShooterDirect();
        hopper.runUpperPassageDirect();
        hopper.runSpindexerDirect();
      })).withTimeout(2);
  }

  public Command scoreCommand(Hopper hopper) {
    return new RunCommand(() -> 
      score(hopper)
    );
  }

  public Command scoreCommand(double shootTime, Hopper hopper) {
    return new RunCommand(() -> 
      score(shootTime, hopper)
    );
  }
}