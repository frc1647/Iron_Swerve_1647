package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.IntakeConstants;

public class CANRollerSubsystem extends SubsystemBase {
    private final SparkFlex intakeMotor;
    //private final RelativeEncoder intakeEncoder;
    //private final SparkClosedLoopController intakePID;

    public CANRollerSubsystem() {
        intakeMotor = new SparkFlex(IntakeConstants.INTAKE_ID, MotorType.kBrushless);
        intakeMotor.setCANTimeout(250);

        //intakeEncoder = intakeMotor.getEncoder();
        //intakePID = intakeMotor.getClosedLoopController();

        SparkFlexConfig intakeConfig = new SparkFlexConfig();
        intakeConfig.voltageCompensation(12);
        intakeConfig.smartCurrentLimit(IntakeConstants.INTAKE_MOTOR_CURRENT_LIMIT);
        //intakeConfig.encoder.positionConversionFactor(IntakeConstants.INTAKE_POWER);
        //intakeConfig.encoder.velocityConversionFactor(IntakeConstants.INTAKE_POWER);
        
        intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void grabBalls() {
        intakeMotor.set(1);
    }

    public void reverseIntake() {
        intakeMotor.set(-1);
    }

    public void stopRoller() {
        System.out.println("in stoproller");
        intakeMotor.set(0);
    }
}
