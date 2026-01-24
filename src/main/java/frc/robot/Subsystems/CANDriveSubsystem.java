// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

//import com.revro?botics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
//import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
//import static frc.robot.Constants.DriveConstants.*;

import edu.wpi.first.wpilibj.RobotController;
//import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
//import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import static frc.robot.Constants.isBrushlessMotors;

//Brushed Motor Controller Imports
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
// import frc.robot.Constants;
//import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class CANDriveSubsystem extends SubsystemBase {
private final MotorController leftLeader;
private final MotorController leftFollower;
private final MotorController rightLeader;
private final MotorController rightFollower;
double rightPositionMeters, leftPositionMeters, leftVelocityMPS, rightVelocityMPS, batteryVolts;

  public RelativeEncoder m_rightEncoder, m_leftEncoder;

  public SparkMaxConfig motorConfig;

  DifferentialDrive drive;
  //TalonFXConfiguration configs = new TalonFXConfiguration();

  public CANDriveSubsystem() {
    //if (isBrushlessMotors()) {
        // Brushless configuration (NEO motors with SparkMax)
        leftLeader = new SparkMax(DriveConstants.BRUSHLESS_LEFT_LEADER_ID, MotorType.kBrushless);
        leftFollower = new SparkMax(DriveConstants.BRUSHLESS_LEFT_FOLLOWER_ID, MotorType.kBrushless);
        rightLeader = new SparkMax(DriveConstants.BRUSHLESS_RIGHT_LEADER_ID, MotorType.kBrushless);
        rightFollower = new SparkMax(DriveConstants.BRUSHLESS_RIGHT_FOLLOWER_ID, MotorType.kBrushless);

        // Get encoders (only available on SparkMax)
        m_leftEncoder = ((SparkMax)leftLeader).getEncoder();
        m_rightEncoder = ((SparkMax)rightLeader).getEncoder();

        // Set can timeout for SparkMax controllers
        ((SparkMax)leftLeader).setCANTimeout(250);
        ((SparkMax)rightLeader).setCANTimeout(250);
        ((SparkMax)leftFollower).setCANTimeout(250);
        ((SparkMax)rightFollower).setCANTimeout(250);

        // Create and apply SparkMax configuration
        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

        // Configure followers
        config.follow((SparkMax)leftLeader);
        ((SparkMax)leftFollower).configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        config.follow((SparkMax)rightLeader);
        ((SparkMax)rightFollower).configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Configure leaders
        config.disableFollowerMode();
        ((SparkMax)rightLeader).configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        config.inverted(true);
        ((SparkMax)leftLeader).configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // } else {
    //     // Brushed configuration (CIM motors with VictorSPX)
    //     leftLeader = new WPI_VictorSPX(DriveConstants.BRUSHED_LEFT_LEADER_ID);
    //     leftFollower = new WPI_VictorSPX(DriveConstants.BRUSHED_LEFT_FOLLOWER_ID);
    //     rightLeader = new WPI_VictorSPX(DriveConstants.BRUSHED_RIGHT_LEADER_ID);
    //     rightFollower = new WPI_VictorSPX(DriveConstants.BRUSHED_RIGHT_FOLLOWER_ID);

    //     // Configure VictorSPX controllers
    //     ((WPI_VictorSPX)leftLeader).configFactoryDefault();
    //     ((WPI_VictorSPX)rightLeader).configFactoryDefault();
    //     ((WPI_VictorSPX)leftFollower).configFactoryDefault();
    //     ((WPI_VictorSPX)rightFollower).configFactoryDefault();

    //     // Set up voltage compensation
    //     ((WPI_VictorSPX)leftLeader).configVoltageCompSaturation(12);
    //     ((WPI_VictorSPX)rightLeader).configVoltageCompSaturation(12);
    //     ((WPI_VictorSPX)leftFollower).configVoltageCompSaturation(12);
    //     ((WPI_VictorSPX)rightFollower).configVoltageCompSaturation(12);

    //     ((WPI_VictorSPX)leftLeader).enableVoltageCompensation(true);
    //     ((WPI_VictorSPX)rightLeader).enableVoltageCompensation(true);
    //     ((WPI_VictorSPX)leftFollower).enableVoltageCompensation(true);
    //     ((WPI_VictorSPX)rightFollower).enableVoltageCompensation(true);

    //     // Configure followers
    //     ((WPI_VictorSPX)leftFollower).follow((WPI_VictorSPX)leftLeader);
    //     ((WPI_VictorSPX)rightFollower).follow((WPI_VictorSPX)rightLeader);

    //     // Set left side inverted
    //     ((WPI_VictorSPX)leftLeader).setInverted(true);
    //     ((WPI_VictorSPX)leftFollower).setInverted(true);

    //     // No encoders for VictorSPX
    //     m_leftEncoder = null;
    //     m_rightEncoder = null;
    // }

    SmartDashboard.putBoolean("Using Brushless Motors", isBrushlessMotors());

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);

    // Initialize dashboard values
    SmartDashboard.putNumber("Right motor Position", 0);
    SmartDashboard.putNumber("Right motor Velocity", 0);
    SmartDashboard.putNumber("Left motor Position", 0);
    SmartDashboard.putNumber("Right motor Position", 0);
    SmartDashboard.setDefaultBoolean("Control Mode", false);
    SmartDashboard.setDefaultBoolean("Reset Encoder", false);
}
public void zeroEncoders(){
  m_leftEncoder.setPosition(0);
  m_rightEncoder.setPosition(0);
}
  //public int position(){
    //return m_rightEncoder.getPosition();
  //}

  //
  //public void outputMax(SparkMax motor){
  //    motor.setMaxOutput(SPEED);
  //  }
    
  @Override
  public void periodic() {
    // Only update encoder values if we have encoders (brushless motors)
    if (m_rightEncoder != null && m_leftEncoder != null) {
      SmartDashboard.putNumber("Right motor Position", m_rightEncoder.getPosition());
      SmartDashboard.putNumber("Right motor Velocity", m_rightEncoder.getVelocity());
      SmartDashboard.putNumber("Left motor Position", m_leftEncoder.getPosition());
      SmartDashboard.putNumber("Left motor Velocity", m_leftEncoder.getVelocity());

      if (SmartDashboard.getBoolean("Reset Encoder", false)) {
        SmartDashboard.putBoolean("Reset Encoder", false);
        m_rightEncoder.setPosition(0);
        m_leftEncoder.setPosition(0);
      }
    leftPositionMeters = m_leftEncoder.getPosition();
    rightPositionMeters = m_rightEncoder.getPosition();

    leftVelocityMPS = m_leftEncoder.getVelocity();
    rightVelocityMPS = m_rightEncoder.getVelocity();

    batteryVolts = RobotController.getBatteryVoltage();   //only needed for SysID Testing

    SmartDashboard.putNumber("left Encoder", leftPositionMeters);
    SmartDashboard.putNumber("right Encoder", rightPositionMeters);
    SmartDashboard.putNumber("left Velo MPS", leftVelocityMPS);
    SmartDashboard.putNumber("right Velo MPS", rightVelocityMPS);

    }
  }
  /*public double[] getPos(){
    double[] Pos_list = new double[m_rightEncoder.getPosition(), m_leftEncoder.getPosition()];
    return Pos_list;
  }*/


  // Command to drive the robot with joystick inputs
   public Command driveTank(double leftSpeed, double rightSpeed) {
        return new RunCommand(
             () -> drive.tankDrive(
                 0.533 * Math.pow(leftSpeed, 1) + 0.467 * leftSpeed,
                 0.533 * Math.pow(rightSpeed, 1) + 0.467 * rightSpeed
             ),
             this
        );
 }

  public void driveTankII(double leftSpeed, double rightSpeed){
    drive.tankDrive(
      0.533 * Math.pow(leftSpeed, 1) + 0.467 * leftSpeed,
      0.533 * Math.pow(rightSpeed, 1) + 0.467 * rightSpeed
    );
  }

public double getLeftPositionMeters() {
  return leftPositionMeters;
}


    public Command slowCommand(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        double conversionFactor = 0.5;
        return new RunCommand(
            () -> drive.tankDrive(
                0.533 * Math.pow(leftSpeed.getAsDouble() * conversionFactor, 1) + 0.467 * leftSpeed.getAsDouble() * conversionFactor,
                0.533 * Math.pow(rightSpeed.getAsDouble() * conversionFactor, 1) + 0.467 * rightSpeed.getAsDouble() * conversionFactor
            ),
            this
        );
    }

   // public double getLeftPositionMeters() {
       // throw new UnsupportedOperationException("Unimplemented method 'getLeftPositionMeters'");
    //}
}
  