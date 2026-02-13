package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.TurretSubsystem;
import frc.robot.Subsystems.CANShooterSubsystem;

/**
 * Configures controller bindings for the turret and ball-launch motor.
 *
 * <p><b>Two-controller mode</b> (operator controller on port 1):
 * <ul>
 *   <li>Right stick X-axis → proportional turret rotation</li>
 *   <li>X button → center turret to 0°</li>
 *   <li>Shooter fire is bound separately in RobotContainer (rightTrigger)</li>
 * </ul>
 *
 * <p><b>One-controller mode</b> (driver controller on port 0):
 * <ul>
 *   <li>Button 3 → rotate clockwise (hold)</li>
 *   <li>Button 4 → rotate counter-clockwise (hold)</li>
 *   <li>Button 5 → center turret to 0°</li>
 * </ul>
 *
 * Call {@link #configure} from {@code RobotContainer} after subsystems are created.
 * This class is self-contained and does not modify any existing files.
 */
public final class TurretControlBindings {

    private TurretControlBindings() {
        // Utility class — no instantiation
    }

    /**
     * Detect how many controllers are connected and bind accordingly.
     *
     * @param turret             the turret subsystem
     * @param shooter            the shooter subsystem (ball launcher)
     * @param driverController   the driver joystick (port 0)
     * @param operatorController the operator Xbox controller (port 1) — may not be connected
     */
    public static void configure(
            TurretSubsystem turret,
            CANShooterSubsystem shooter,
            Joystick driverController,
            CommandXboxController operatorController) {

        if (DriverStation.isJoystickConnected(1)) {
            // ── Two-controller mode ────────────────────────────────────────────
            // Operator right stick X → proportional turret rotation
            // Positive X = stick right = clockwise
            turret.setDefaultCommand(
                    TurretCommands.joystickControl(turret, () -> operatorController.getRightX()));

            // X button → center turret
            operatorController.x().onTrue(TurretCommands.center(turret));

            // Note: shooter fire is already bound in RobotContainer (rightTrigger).
            // Do NOT add a second shooter binding here to avoid CommandScheduler conflicts.

        } else {
            // ── One-controller mode (buttons on driver joystick) ───────────────
            // Button 3 → clockwise while held
            new JoystickButton(driverController, 3)
                    .whileTrue(TurretCommands.rotateClockwise(turret));

            // Button 4 → counter-clockwise while held
            new JoystickButton(driverController, 4)
                    .whileTrue(TurretCommands.rotateCounterClockwise(turret));

            // Button 5 → center turret
            new JoystickButton(driverController, 5)
                    .onTrue(TurretCommands.center(turret));
        }
    }
}
