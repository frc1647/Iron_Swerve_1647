// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.lang.ModuleLayer.Controller;
import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Commands.Autos;    
import frc.robot.Subsystems.CANDriveSubsystem;

import frc.robot.Subsystems.Camera;
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final CANDriveSubsystem driveSubsystem = new CANDriveSubsystem();
//   private final CANClimber climber = new CANClimber();

  //private final Camera camera = new Camera();
  // The driver's controller
  private final Joystick driverController = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);

  // The operator's controller
  private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //camera.checkCamera();
    configureBindings();

    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption
    //autoChooser.setDefaultOption("Autonomous", Autos.exampleAuto(driveSubsystem));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Set the A button to run the "runRoller" command from the factory with a fixed
    // value ejecting the gamepiece while the button is held
    // operatorController.getRightTriggerAxis()
    //     .whileTrue(rollerSubsystem.runRoller(rollerSubsystem, () -> RollerConstants.ROLLER_EJECT_VALUE, () -> 0));
    // operatorController.getLeftTriggerAxis()
    //     .whileTrue(rollerSubsystem.runRoller(rollerSubsystem, () -> -RollerConstants.ROLLER_EJECT_VALUE, () -> 0));
    // operatorController.x().or(operatorController.y()).whileFalse(rollerSubsystem.runRoller(rollerSubsystem, () -> 0.0, () -> 0));

    //driverController.b()
        //.whileTrue(driveSubsystem.driveTank(DriveConstants.SPEED, DriveConstants.SPEED));
    //operatorController.x()
       // .whileTrue(rollerSubsystem.runRoller(rollerSubsystem, () -> RollerConstants.ROLLER_EJECT_VALUE, () -> 0));
    
    /*operatorController.a()
        .whileTrue(arm.runArm(arm, ArmConstants.ARM_SPEED_UP));
    operatorController.b()
        .whileFalse(arm.runArm(arm, ArmConstants.ARM_SPEED_DOWN));
    operatorController.y()
        .whileFalse(arm.runArm(arm, 0.0));*/
    // Set the default command for the drive subsystem to the command provided by
    // factory with the values provided by the joystick axes on the driver
    // controller. The Y axis of the controller is inverted so that pushing the
    // stick away from you (a negative value) drives the robot forwards (a positive
    // value)

  
    //, () -> -operatorController.button(8),0).getAsBoolean();
    /*operatorController.b()
        .whileTrue(arm.runArm(arm, ArmConstants.ARM_SPEED_DOWN));
    operatorController.y()
        .whileTrue(arm.runArm(arm, 0.0));
    operatorController.a()
        .whileFalse(arm.runArm(arm, ArmConstants.ARM_SPEED_UP));*/


    /*operatorController.a().whileTrue(armII.returnPosition(armII, 0.0));
    operatorController.b().whileTrue(armII.returnPosition(armII, 2.2));*/


    //operatorController.getRightY().whileTrue(armII.)
    
    
    
    // Set the default command for the roller subsystem to the command from the
    // factory with the values provided by the triggers on the operator controller
    //TALON for gogetter extension need to be left joystick on xbox
    //Roller on go getter must be x
    //b and a for arm pivoting
    //rt and lt for rollers on arm.

JoystickButton button6 = new JoystickButton(driverController, 6);

button6.whileTrue(
    driveSubsystem.slowCommand(
        () -> -driverController.getRawAxis(5),  // right stick Y
        () -> -driverController.getRawAxis(1)   // left stick Y
    )
);

// Default command for driving when no button is pressed
driveSubsystem.setDefaultCommand(
    new RunCommand(
        () -> driveSubsystem.driveTankII(
            -driverController.getRawAxis(5), // right stick Y
            -driverController.getRawAxis(1)  // left stick Y
        ),
        driveSubsystem
    )
);

    // driverController.button(8)
    //     .whileTrue(climber.run(climber, 0.3));
    // driverController.button(7)
    //     .whileTrue(climber.run(climber, -0.3));
    /*if(driverController.rightTrigger().getAsBoolean()){
        System.out.println("right trigger");
        //climber.run(climber, ClimberID.CLIMBER_UP_SPEED);
    }
    else if(driverController.leftTrigger().getAsBoolean()){
        System.out.println("left trigger");
        //climber.run(climber, ClimberID.CLIMBER_DOWN_SPEED);
    }
    else{
        System.out.println("neither trigger");
        //climber.run(climber, 0.0);
    }*/
    // driverController.button(8).or(driverController.button(7)).whileFalse(climber.run(climber, 0.0));
  }

  /**
   * Use this to pass the autonomous command to the main {@l ink Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(driveSubsystem);
  }
}