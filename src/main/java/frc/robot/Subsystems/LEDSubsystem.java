package frc.robot.Subsystems;


import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {

    public SparkMax ledControllerMotor;

    public static final double turretAimingColor = LEDConstants.Colors.RED;
    public static final double turretLockedOnColor = LEDConstants.Colors.LAWN_GREEN;

    public LEDSubsystem() {
        ledControllerMotor = new SparkMax(LEDConstants.BLINKIN_ID, MotorType.kBrushless);


        this.setColor(LEDConstants.Colors.OFF);
    }


    public void setColor(double color) {
        ledControllerMotor.set(color);
    }

    @Override
    public void periodic() {
        if (MathUtil.isNear(0, Camera.sauronHorizontal(), LEDConstants.AIM_TOLERANCE)) {
            setColor(turretLockedOnColor);
        }
        else {
            setColor(turretAimingColor);
        }
    }

    public void setAllianceColor() {
        switch(RobotContainer.getAlliance()) {
            case Red:
                setColor(LEDConstants.Colors.PATTERN_SHOT_RED);
                break;
            case Blue:
                setColor(LEDConstants.Colors.PATTERN_SHOT_BLUE);
            default:
                setColor(LEDConstants.Colors.OFF);
                break;
        }
    }
}