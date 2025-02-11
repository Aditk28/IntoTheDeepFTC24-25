package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ActionClass;

@TeleOp(name = "PIDF Tuner", group = "Tuning")
public class PIDFSlideTuning extends LinearOpMode {

    private DcMotorEx motor;
    private double kP = 0.0, kI = 0.0, kD = 0.0, kF = 0.0;
    private double targetPosition = 0.0;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "horizontalLift");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Update PIDF coefficients from gamepad input
            if (gamepad1.dpad_up) kP += 0.001;
            if (gamepad1.dpad_down) kP -= 0.001;
            if (gamepad1.dpad_right) kI += 0.001;
            if (gamepad1.dpad_left) kI -= 0.001;
            if (gamepad1.right_bumper) kD += 0.001;
            if (gamepad1.left_bumper) kD -= 0.001;
            if (gamepad1.a) kF += 0.001;
            if (gamepad1.b) kF -= 0.001;

            // Clip the coefficients to ensure they are within a reasonable range
            kP = Range.clip(kP, 0, 1);
            kI = Range.clip(kI, 0, 1);
            kD = Range.clip(kD, 0, 1);
            kF = Range.clip(kF, 0, 1);

            // Set PIDF coefficients to the motor
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(kP, kI, kD, kF));

            // Update the target position from the gamepad triggers
            targetPosition += gamepad1.right_trigger - gamepad1.left_trigger;
            motor.setTargetPosition((int) targetPosition);

            // Display PIDF coefficients and motor status
            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("kP", kP);
            telemetry.addData("kI", kI);
            telemetry.addData("kD", kD);
            telemetry.addData("kF", kF);
            telemetry.addData("Motor Position", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
