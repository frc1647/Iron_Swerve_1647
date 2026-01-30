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
import frc.robot.Subsystems.CANShooterSubsystem;
import frc.robot.Subsystems.CANShooterSubsystem;
import frc.robot.Subsystems.Camera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.units.*;



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

  // Add the robot's subsystems
  private final CANDriveSubsystem driveSubsystem = new CANDriveSubsystem();
  private final CANShooterSubsystem shooterSubsystem = new CANShooterSubsystem();

  //private final Camera camera = new Camera();

  // The driver's controller
  private final Joystick driverController = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);

  // The operator's controller
  private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    private Alliance currentAlliance = Alliance.Red;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
    public RobotContainer() {
    //camera.checkCamera();
    //buildNamedAutoCommands();

    // Initialize alliance (default to red if not present)
    onAllianceChanged(getAlliance());

    // Set up trigger to detect alliance changes
    new Trigger(() -> getAlliance() != currentAlliance)
        .onTrue(Commands.runOnce(() -> onAllianceChanged(getAlliance())).ignoringDisable(true));

    // Triggers for auto aim/pass poses
    new Trigger(() -> isInAllianceZone())
        .onChange(Commands.runOnce(() -> onZoneChanged()).ignoringDisable(true));

    new Trigger(() -> isOnAllianceOutpostSide())
        .onChange(Commands.runOnce(() -> onZoneChanged()).ignoringDisable(true));

    if (!Robot.isReal() || true) {
      DriverStation.silenceJoystickConnectionWarning(true);
    configureBindings();}

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

        shooterSubsystem.setDefaultCommand(shooterSubsystem.runShooter(shooterSubsystem, () -> operatorController.getLeftTriggerAxis()*0.6, () -> operatorController.getRightTriggerAxis()));

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

    private Alliance getAlliance() {
        return DriverStation.getAlliance().orElse(Alliance.Red);
    }

    private void onZoneChanged() {

        /* TODO: update this for our setup
        if (isInAllianceZone()) {
            superstructure.setAimPoint(Constants.AimPoints.getAllianceHubPosition());
        } else {
            if (isOnAllianceOutpostSide()) {
                superstructure.setAimPoint(Constants.AimPoints.getAllianceOutpostPosition());
            } else {
                superstructure.setAimPoint(Constants.AimPoints.getAllianceFarSidePosition());
            }
        }
        */
    }

    private void onAllianceChanged(Alliance alliance) {
        currentAlliance = alliance;

        // Update aim point based on alliance
        /* TODO : make this part work with our system
        if (alliance == Alliance.Blue) {
            superstructure.setAimPoint(Constants.AimPoints.BLUE_HUB.value);
        } else {
        superstructure.setAimPoint(Constants.AimPoints.RED_HUB.value);
        }
        */
        System.out.println("Alliance changed to: " + alliance);
    }
    autoChooser = AutoBuilder.buildAutoChooser();

    // Set the default auto (do nothing)
    autoChooser.setDefaultOption("Do Nothing", Commands.none());

    // Add a simple auto option to have the robot drive forward for 1 second then
    // stop
    autoChooser.addOption("Drive Forward", drivebase.driveForward().withTimeout(10));

    // Put the autoChooser on the SmartDashboard
    SmartDashboard.putData("Auto Chooser", autoChooser);

    private boolean isInAllianceZone() {
        Alliance alliance = getAlliance();
        Distance blueZone = Inches.of(182);
        Distance redZone = Inches.of(469);

        if (alliance == Alliance.Blue && drivebase.getPose().getMeasureX().lt(blueZone)) {
        return true;
        } else if (alliance == Alliance.Red && drivebase.getPose().getMeasureX().gt(redZone)) {
            return true;
        }

        return false;
    }

    private boolean isOnAllianceOutpostSide() {
        Alliance alliance = getAlliance();
        Distance midLine = Inches.of(158.84375);

        if (alliance == Alliance.Blue && drivebase.getPose().getMeasureY().lt(midLine)) {
            return true;
        } else if (alliance == Alliance.Red && drivebase.getPose().getMeasureY().gt(midLine)) {
            return true;
        }

        return false;
    }
}