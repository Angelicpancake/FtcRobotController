package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "osteleop11/24 ", group = "TeleOp")
public class teleOp extends OpMode {

    private DcMotor frontLeftWheel;
    private DcMotor frontRightWheel;
    private DcMotor arm;
    private Servo clawServo;

    private static final double ARM_SPEED_SCALING_NORMAL = 0.4; // Default arm speed
    private static final double ARM_SPEED_SCALING_FAST = 1.0;  // Fast arm speed for slamming
    private static final double TRIGGER_SPEED_SCALING = 0.2;   // Precise driving adjustment
    private static final double SAMPLE_WEIGHT_SUPPORT = 0.2; // Additional power when holding a sample
    private double lockedClawPosition = 0.0; // Start claw fully opened

    private boolean isFastMode = false; // Toggle for arm fast mode
    private boolean previousBumperState = false; // Tracks the last state of the bumper

    // Proportional scaling factor constants
    private static final int ARM_POSITION_MIN = -730; // Lowest position of the arm
    private static final int ARM_POSITION_MAX = -50;  // Highest position of the arm
    private static final double ARM_MIN_SUPPORT_POWER = 0.1; // Minimum power at top
    private static final double ARM_MAX_SUPPORT_POWER = 0.12; // Maximum power at bottom

    @Override
    public void init() {
        // Initialize hardware
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");
        clawServo = hardwareMap.get(Servo.class, "claw");

        // Set motor directions if needed
        frontLeftWheel.setDirection(DcMotor.Direction.REVERSE); // Invert left wheel
        frontRightWheel.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.REVERSE);

        // Set the initial position of the claw to fully open
        clawServo.setPosition(lockedClawPosition);
    }

    @Override
    public void loop() {
        // Joystick control for wheels
        double leftPower = -gamepad1.left_stick_y; // Reverse Y-axis for left wheel
        double rightPower = -gamepad1.right_stick_y; // Y-axis for right wheel is correct

        // Trigger-based incremental speed control (gamepad1)
        double leftTriggerSpeed = gamepad1.left_trigger * 2 * TRIGGER_SPEED_SCALING;
        double rightTriggerSpeed = gamepad1.right_trigger * 2 * TRIGGER_SPEED_SCALING;

        // Combine joystick and trigger inputs for precise driving
        double finalLeftPower = Math.max(-1.0, Math.min(1.0, leftPower + leftTriggerSpeed));
        double finalRightPower = Math.max(-1.0, Math.min(1.0, rightPower + rightTriggerSpeed));

        frontLeftWheel.setPower(finalLeftPower);
        frontRightWheel.setPower(finalRightPower);

        // Arm control with Gamepad 2
        double armSpeedScaling = isFastMode ? ARM_SPEED_SCALING_FAST : ARM_SPEED_SCALING_NORMAL; // Toggle fast mode
        double armPower = gamepad2.left_stick_y * -armSpeedScaling; // Scale arm speed
        int armPosition = arm.getCurrentPosition();

        if (armPower > 0.2) {
            armUp(armPower);
        } else if (armPower < -0.2) {
            armDown(armPower);
        } else {
            // Gravity support logic with proportional adjustment
            if (armPosition > ARM_POSITION_MIN && armPosition < ARM_POSITION_MAX) {
                double supportPower = getProportionalArmPower(armPosition);
                if (lockedClawPosition == 0.8) { // Claw is closed (partially holding a sample)
                    supportPower += SAMPLE_WEIGHT_SUPPORT;
                }
                armUp(supportPower);
            } else {
                armOff();
            }
        }

        // Toggle arm fast mode with Gamepad 2 right bumper
        if (gamepad2.right_bumper && !previousBumperState) {
            isFastMode = !isFastMode; // Toggle fast mode
        }
        previousBumperState = gamepad2.right_bumper;

        // Claw control with Gamepad 2
        if (gamepad2.left_trigger > 0.2) {
            lockedClawPosition = 0.0; // Fully open claw
        } else if (gamepad2.right_trigger > 0.2) {
            lockedClawPosition = 0.8; // Close claw partially
        }
        clawServo.setPosition(lockedClawPosition);

        // Telemetry for debugging and feedback
        telemetry.addData("Left Wheel Power", finalLeftPower);
        telemetry.addData("Right Wheel Power", finalRightPower);
        telemetry.addData("Arm Position", armPosition);
        telemetry.addData("Claw Position", lockedClawPosition);
        telemetry.addData("Arm Fast Mode", isFastMode ? "Enabled" : "Disabled");
        telemetry.update();
    }

    // Calculate proportional arm power based on position
    private double getProportionalArmPower(int position) {
        // Normalize position to a 0 to 1 range
        double normalizedPosition = (double) (position - ARM_POSITION_MIN) / (ARM_POSITION_MAX - ARM_POSITION_MIN);

        // Scale power between ARM_MIN_SUPPORT_POWER and ARM_MAX_SUPPORT_POWER
        return ARM_MIN_SUPPORT_POWER + (1 - normalizedPosition) * (ARM_MAX_SUPPORT_POWER - ARM_MIN_SUPPORT_POWER);
    }

    // Arm movement methods
    public void armUp(double power) {
        arm.setPower(power);
    }

    public void armDown(double power) {
        arm.setPower(power);
    }

    public void armOff() {
        arm.setPower(0);
    }
}