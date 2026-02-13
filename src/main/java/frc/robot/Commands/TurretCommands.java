package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Subsystems.TurretSubsystem;

/**
 * Factory class for turret-related commands.
 * All commands are created as static factories so they can be bound to buttons
 * without coupling the command logic to the controller layout.
 */
public final class TurretCommands {

    private TurretCommands() {
        // Utility class — no instantiation
    }

    /**
     * Proportional joystick control: the turret rotates at a speed proportional
     * to how far the joystick is deflected left/right.
     *
     * @param turret        the turret subsystem
     * @param axisSupplier  supplies the joystick axis value (−1 to +1, positive = clockwise)
     * @return a command that runs continuously while scheduled
     */
    public static Command joystickControl(TurretSubsystem turret, DoubleSupplier axisSupplier) {
        return new RunCommand(() -> {
            double raw = axisSupplier.getAsDouble();

            // Apply deadband
            if (Math.abs(raw) < TurretSubsystem.JOYSTICK_DEADBAND) {
                raw = 0.0;
            }

            // Convert joystick deflection to degrees-per-tick
            // 50Hz loop → divide deg/sec by 50 to get deg/tick
            double degreesPerTick = (raw * TurretSubsystem.MAX_SPEED_DEG_PER_SEC) / 50.0;

            if (degreesPerTick != 0.0) {
                turret.adjustAngleDegrees(degreesPerTick);
            }
        }, turret);
    }

    /**
     * Rotate the turret clockwise at a fixed rate while the command is active.
     * Intended for single-controller button-press mode.
     */
    public static Command rotateClockwise(TurretSubsystem turret) {
        return new RunCommand(
                () -> turret.adjustAngleDegrees(TurretSubsystem.BUTTON_SPEED_DEG_PER_TICK),
                turret);
    }

    /**
     * Rotate the turret counter-clockwise at a fixed rate while the command is active.
     * Intended for single-controller button-press mode.
     */
    public static Command rotateCounterClockwise(TurretSubsystem turret) {
        return new RunCommand(
                () -> turret.adjustAngleDegrees(-TurretSubsystem.BUTTON_SPEED_DEG_PER_TICK),
                turret);
    }

    /**
     * Center the turret to 0° and finish immediately.
     */
    public static Command center(TurretSubsystem turret) {
        return Commands.runOnce(() -> turret.centerTurret(), turret);
    }
}
