// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Subsystems.Camera;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private final DifferentialDrive m_robotDrive;
  private final Joystick m_leftStick;
  private final Joystick m_rightStick;

  private final PWMSparkMax m_leftMotor = new PWMSparkMax(0);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(1);

  /** Called once at the beginning of the robot program. */
  public Robot() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);

    m_robotDrive = new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);

    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);
  }

  private long lastPrintTime = 0;
  private final long interval = 1000; // 1 sec

  @Override
  public void teleopPeriodic() {
    m_robotDrive.tankDrive(-m_leftStick.getY(), -m_rightStick.getY());

    long currentTime = System.currentTimeMillis();

    if (currentTime - lastPrintTime >= interval) {
      lastPrintTime = currentTime;

      //Watcher
      System.out.println(" -- TheWatcher -- ");
      System.out.println("Tag?" + Camera.watcherHasTag());
      System.out.println("ID" + Camera.watcherTagID());
      System.out.println("Horizontal angle" + Camera.watcherHorizontal());
      System.out.println("Vertical angle" + Camera.watcherVertical());
      //System.out.println();
      //System.out.println();

      //Sauron
      System.out.println(" -- EyeOfSauron -- ");
      System.out.println("Tag?" + Camera.sauronHasTag());
      System.out.println("ID" + Camera.sauronTagID());
      System.out.println("Horizontal angle" + Camera.sauronHorizontal());
      System.out.println("Vertical angle" + Camera.sauronVertical());
      //System.out.println();
      //System.out.println();
    }
  }
}
