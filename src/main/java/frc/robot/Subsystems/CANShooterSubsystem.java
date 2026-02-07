package frc.robot.Subsystems;


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


public class CANShooterSubsystem extends SubsystemBase {
  
  private SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
    .withControlMode(ControlMode.CLOSED_LOOP)
    // Feedback Constants (PID Constants)
    .withClosedLoopController(50, 0, 0, DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
    .withSimClosedLoopController(50, 0, 0, DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
    // Feedforward Constants
    .withFeedforward(new SimpleMotorFeedforward(0, 0, 0))
    .withSimFeedforward(new SimpleMotorFeedforward(0, 0, 0))
    // Telemetry name and verbosity level
    .withTelemetry("ShooterMotor", TelemetryVerbosity.HIGH)
    // Gearing from the motor rotor to final shaft.
    // In this example GearBox.fromReductionStages(3,4) is the same as GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your motor.
    // You could also use .withGearing(12) which does the same thing.
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(3, 4)))
    // Motor properties to prevent over currenting.
    .withMotorInverted(false)
    .withIdleMode(MotorMode.COAST)
    .withStatorCurrentLimit(Amps.of(40));

  /*private SparkMax spark = new SparkMax(4, MotorType.kBrushless);
  private SmartMotorController sparkSmartMotorController = new SparkWrapper(spark, DCMotor.getNEO(1), smcConfig);

  private final FlyWheelConfig shooterConfig = new FlyWheelConfig(sparkSmartMotorController)
  // Diameter of the flywheel.
  .withDiameter(Inches.of(4))
  // Mass of the flywheel.
  .withMass(Pounds.of(1))
  // Maximum speed of the shooter.
  .withUpperSoftLimit(RPM.of(1000))
  // Telemetry name and verbosity for the arm.
  .withTelemetry("ShooterMech", TelemetryVerbosity.HIGH);

  // Shooter Mechanism
  private FlyWheel shooter = new FlyWheel(shooterConfig);
*/
private final SparkMax shooterMotor;
  public CANShooterSubsystem() {
    shooterMotor = new SparkMax(ShooterConstants.SHOOTER_ID, MotorType.kBrushless);
  }


  // public AngularVelocity getVelocity() {return shooterMotor.getSpeed();}
  // public Command setVelocity(AngularVelocity speed) {return shooter.setSpeed(speed);}
  // public Command set(double dutyCycle) {return shooterMotor.set(dutyCycle);}
    
    
  /**
  * An example method querying a boolean state of the subsystem (for example, a digital sensor).
  * @return value of some boolean subsystem state, such as a digital sensor.
  */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }
    
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //shooter.updateTelemetry();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    //shooter.simIterate();
  }

  

  public Command runShooter() {
    return Commands.run(() -> shooterMotor.set(ShooterConstants.SHOOTER_SPEED), this);
  }
}