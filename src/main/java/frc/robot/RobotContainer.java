// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 *                                                                                                                                                                                                                                       
 *    I8,        8        ,8I            81                                                                                                                                                       88                                88  
 *    `8b       d8b       d8'            88                                                                ,d                                                                                      88                         ,d     88  
 *     "8,     ,8"8,     ,8"             88                                                                88                                                                                      88                         88     88  
 *      Y8     8P Y8     8P   ,adPPYba,  88   ,adPPYba,   ,adPPYba,   88,dPYba,,adPYba,    ,adPPYba,     MM88MMM  ,adPPYba,       ,adPPYba,   88       88  8b,dPPYba,     8b,dPPYba,   ,adPPYba,   88,dPPYba,    ,adPPYba,  MM88MMM  88  
 *      `8b   d8' `8b   d8'  a8P_____88  88  a8"     ""  a8"     "8a  88P'   "88"    "8a  a8P_____88       88    a8"     "8a     a8"     "8a  88       88  88P'   "Y8     88P'   "Y8  a8"     "8a  88P'    "8a  a8"     "8a   88     88  
 *       `8a a8'   `8a a8'   8PP"""""""  88  8b          8b       d8  88      88      88  8PP"""""""       88    8b       d8     8b       d8  88       88  88             88          8b       d8  88       d8  8b       d8   88     ""  
 *        `8a8'     `8a8'    "8b,   ,aa  88  "8a,   ,aa  "8a,   ,a8"  88      88      88  "8b,   ,aa       88,   "8a,   ,a8"     "8a,   ,a8"  "8a,   ,a88  88             88          "8a,   ,a8"  88b,   ,a8"  "8a,   ,a8"   88,    aa  
 *         `8'       `8'      `"Ybbd8"'  88   `"Ybbd8"'   `"YbbdP"'   88      88      88   `"Ybbd8"'       "Y888  `"YbbdP"'       `"YbbdP"'    `"YbbdP'Y8  88             88           `"YbbdP"'   8Y"Ybbd8"'    `"YbbdP"'    "Y888  88  
 *                                                                                                                                                                                                                                       
 *                                                                                                                                                                                                                                       
 */
package frc.robot;
import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.AprilTagIDs;
import frc.robot.Commands.AprilTagCommands;
import frc.robot.Commands.Autos;
import frc.robot.Subsystems.CANDriveSubsystem;
import frc.robot.Subsystems.CANShooterSubsystem;
import frc.robot.Subsystems.CANTurretSubsystem;
import frc.robot.Subsystems.CANIntakeSubsystem;
import frc.robot.Subsystems.Camera;
import frc.robot.Subsystems.Hopper;
import frc.robot.Subsystems.LEDSubsystem;
import frc.robot.AprilTags;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

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
    private final CANTurretSubsystem turretSubsystem = new CANTurretSubsystem();
    private final CANIntakeSubsystem intakeSubsystem = new CANIntakeSubsystem();
    private final LEDSubsystem ledSubsystem = new LEDSubsystem();

    // Add other components
    private final Hopper hopper = new Hopper();


    // The driver's controller
    public static final Joystick driverController = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);

    // The operator's controller
    public static final CommandXboxController operatorController = new CommandXboxController(
            OperatorConstants.OPERATOR_CONTROLLER_PORT);

    // The autonomous chooser
    public final SendableChooser<Command> autoChooser = new SendableChooser<>();

    private Alliance currentAlliance = Alliance.Red;
    public Boolean targetingHub = true;
    public int targetTagLeft;
    public int targetTagRight;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // camera.checkCamera();
        // buildNamedAutoCommands();

        // Initialize alliance (default to red if not present)
        onAllianceChanged(getAlliance());
        AprilTags.setAprilTagIDs();
        

        // Set up trigger to detect alliance changes
        new Trigger(() -> getAlliance() != currentAlliance)
                .onTrue(Commands.runOnce(() -> onAllianceChanged(getAlliance())).ignoringDisable(true));


        
        // Triggers for auto aim/pass poses
        // new Trigger(() -> isInAllianceZone())
        // .onChange(Commands.runOnce(() -> onZoneChanged()).ignoringDisable(true));

        // new Trigger(() -> isOnAllianceOutpostSide())
        // .onChange(Commands.runOnce(() -> onZoneChanged()).ignoringDisable(true));

        if (!Robot.isReal() || true) {
            DriverStation.silenceJoystickConnectionWarning(true);
            configureBindings();
        }

        // Set the options to show up in the Dashboard for selecting auto modes. If you
        // add additional auto modes you can add additional lines here with
        autoChooser.addOption("Auto 1", Autos.AutoOne(turretSubsystem, driveSubsystem, shooterSubsystem));        
        autoChooser.addOption("Auto 2", Autos.AutoTwo(turretSubsystem, driveSubsystem, shooterSubsystem));
        autoChooser.addOption("Auto 3", Autos.AutoThree(turretSubsystem, driveSubsystem, shooterSubsystem));

        autoChooser.setDefaultOption("Auto 1", Autos.AutoOne(turretSubsystem, driveSubsystem, shooterSubsystem));

        SmartDashboard.putData("Auto Selector", autoChooser);
        // Autos.exampleAuto(driveSubsystem));
        // Set the default command to force the shooter rest.
        // shooterSubsystem.setDefaultCommand(shooterSubsystem.set(0));
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

/**
 *                  _    _____        _                      ______                                  _         _              
 *       _         | |  (____ \      (_)                    / _____)                                | |       | |         _   
 *     _| |_ ___ __|_|   _   \ \ ____ _ _   _ ____  ____   | /      ___  ____  ____   ____ ____   _ | | ___   |_|__ ___ _| |_ 
 *    (_   _|___|___)   | |   | / ___) | | | / _  )/ ___)  | |     / _ \|    \|    \ / _  |  _ \ / || |/___)   (___|___|_   _)
 *      |_|        | |  | |__/ / |   | |\ V ( (/ /| |      | \____| |_| | | | | | | ( ( | | | | ( (_| |___ |  | |        |_|  
 *                 |_|  |_____/|_|   |_| \_/ \____)_|       \______)___/|_|_|_|_|_|_|\_||_|_| |_|\____(___/   |_|             
 *                                                                                                                            
 */

        // Default command for driving when no button is pressed
        driveSubsystem.setDefaultCommand(
                new RunCommand(
                        () -> driveSubsystem.driveTankII(
                                driverController.getRawAxis(1), // left stick Y
                                driverController.getRawAxis(5) // right stick Y
                        ),
                        driveSubsystem));

        JoystickButton button6 = new JoystickButton(driverController, 6);

        button6.whileTrue(
                driveSubsystem.slowCommand(
                        () -> driverController.getRawAxis(1), // left stick Y
                        () -> driverController.getRawAxis(5) // right stick Y
                ));
/**
 *                  _     _____                                             ______                                  _         _              
 *       _         | |   / ___ \                        _                  / _____)                                | |       | |         _   
 *     _| |_ ___ __|_|  | |   | |____   ____  ____ ____| |_  ___   ____   | /      ___  ____  ____   ____ ____   _ | | ___   |_|__ ___ _| |_ 
 *    (_   _|___|___)   | |   | |  _ \ / _  )/ ___) _  |  _)/ _ \ / ___)  | |     / _ \|    \|    \ / _  |  _ \ / || |/___)   (___|___|_   _)
 *      |_|        | |  | |___| | | | ( (/ /| |  ( ( | | |_| |_| | |      | \____| |_| | | | | | | ( ( | | | | ( (_| |___ |  | |        |_|  
 *                 |_|   \_____/| ||_/ \____)_|   \_||_|\___)___/|_|       \______)___/|_|_|_|_|_|_|\_||_|_| |_|\____(___/   |_|             
 *                              |_|                                                                                                          
 */

        targetingHub = true;
        targetTagLeft = AprilTags.homeHubFrontLeftID;
        targetTagRight = AprilTags.homeHubFrontRightID;



        turretSubsystem.setDefaultCommand(AprilTagCommands.AimTowardsAprilTag(turretSubsystem, targetTagRight));

/*
        operatorController.rightTrigger().whileTrue(Commands.run(() -> {
            shooterSubsystem.runShooterDirect();
            hopper.runPassageDirect();
            hopper.runSpindexerDirect();
        }, shooterSubsystem, hopper).finallyDo(() -> {
            shooterSubsystem.stopShooter();
            hopper.stopPassage();
            hopper.stopSpindexer();
            }
        )); // Fire
*/
        operatorController.rightTrigger().whileTrue(Commands.run
        (
            () -> 
                shooterSubsystem.runShooterDirect())
                .withTimeout(0.25)
                .andThen(() -> hopper.runUpperPassageDirect())
                .withTimeout(0.25)
                .andThen(() -> hopper.runLowerPassageDirect())
                .withTimeout(0.25)
                .andThen(() -> hopper.runSpindexerDirect())
                .withTimeout(0.25)
                .finallyDo(() -> {
                    shooterSubsystem.stopShooter();
                    hopper.stopPassage();
                    hopper.stopSpindexer();
                }
            )
        );



        operatorController.leftBumper().onTrue(AprilTagCommands.SwitchShootingMode(this)); // Change Aim Target
        //operatorController.a().whileTrue(AprilTagCommands.GoToAprilTag(driveSubsystem, null, 0));
        operatorController.b().whileTrue(intakeSubsystem.grabBalls());
 }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous



        return autoChooser.getSelected();
    }

    public static Alliance getAlliance() {
        return DriverStation.getAlliance().orElse(Alliance.Red);
    }

    private void onZoneChanged() {

        /*
         * TODO: update this for our setup
         * if (isInAllianceZone()) {
         * superstructure.setAimPoint(Constants.AimPoints.getAllianceHubPosition());
         * } else {
         * if (isOnAllianceOutpostSide()) {
         * superstructure.setAimPoint(Constants.AimPoints.getAllianceOutpostPosition());
         * } else {
         * superstructure.setAimPoint(Constants.AimPoints.getAllianceFarSidePosition());
         * }
         * }
         */
    }

    private void onAllianceChanged(Alliance alliance) {
        currentAlliance = alliance;
        AprilTags.setAprilTagIDs();

        // Update aim point based on alliance
        /*
         * TODO : make this part work with our system
         * if (alliance == Alliance.Blue) {
         * superstructure.setAimPoint(Constants.AimPoints.BLUE_HUB.value);
         * } else {
         * superstructure.setAimPoint(Constants.AimPoints.RED_HUB.value);
         * }
         */
        System.out.println("Alliance changed to: " + alliance);
    }

    {
        /*
         * autoChooser = AutoBuilder.buildAutoChooser();
         * 
         * // Set the default auto (do nothing)
         * autoChooser.setDefaultOption("Do Nothing", Commands.none());
         * 
         * // Add a simple auto option to have the robot drive forward for 1 second then
         * // stop
         * autoChooser.addOption("Drive Forward",
         * drivebase.driveForward().withTimeout(10));
         * 
         * // Put the autoChooser on the SmartDashboard
         * SmartDashboard.putData("Auto Chooser", autoChooser);
         */

        /*
         * private boolean isInAllianceZone() {
         * Alliance alliance = getAlliance();
         * Distance blueZone = Inches.of(182);
         * Distance redZone = Inches.of(469);
         * 
         * if (alliance == Alliance.Blue &&
         * drivebase.getPose().getMeasureX().lt(blueZone)) {
         * return true;
         * } else if (alliance == Alliance.Red &&
         * drivebase.getPose().getMeasureX().gt(redZone)) {
         * return true;
         * }
         * 
         * return false;
         * }
         * 
         * private boolean isOnAllianceOutpostSide() {
         * Alliance alliance = getAlliance();
         * Distance midLine = Inches.of(158.84375);
         * 
         * if (alliance == Alliance.Blue &&
         * drivebase.getPose().getMeasureY().lt(midLine)) {
         * return true;
         * } else if (alliance == Alliance.Red &&
         * drivebase.getPose().getMeasureY().gt(midLine)) {
         * return true;
         * }
         * 
         * return false;
         * }
         */
    }
}