package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//2260
@TeleOp(name="TeleOp FINAL")
public class fieldCentric extends OpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor linearSlide1;
    private Servo claw2;
    private Servo claw1;
    private IMU imu;
    private DcMotor arm;

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
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        claw2 = hardwareMap.get(Servo.class, "slideClaw");
        claw1 = hardwareMap.get(Servo.class, "armClaw");

        linearSlide1 = hardwareMap.get(DcMotor.class, "slide");
        linearSlide1.setDirection(DcMotor.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);

        // Reset IMU heading
        imu.resetYaw();

        telemetry.addLine("Hardware Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Claw control with position tracking
        if (gamepad2.dpad_up) {
            claw2.setPosition(1);
            telemetry.addData("Claw2 Position", claw2.getPosition());
        } else if (gamepad2.dpad_down) {
            claw2.setPosition(0);
            telemetry.addData("Claw2 Position", claw2.getPosition());
        }

        if (gamepad2.right_bumper) {
            claw1.setPosition(1);
            lockedClawPosition = 1.0;
        } else if (gamepad2.left_bumper) {
            claw1.setPosition(0);
            lockedClawPosition = 0.0;
        }

        // Get drive inputs (negated Y because joystick Y is reversed)
        double x = gamepad1.left_stick_x * 0.55;
        double y = -gamepad1.left_stick_y * 0.5;  // Negated to match standard coordinate system
        double rx = gamepad1.right_stick_x * 0.5;

        // Get the robot's heading
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Apply field centric transformation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Calculate motor powers using transformed inputs
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        // Set motor powers
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // Linear slide control
        double power = 1;
        if (gamepad2.right_trigger > 0.3 && linearSlide1.getCurrentPosition() < 2260) {
            linearSlide1.setPower(gamepad2.right_trigger * power);
        } else if (gamepad2.left_trigger > 0.3) {
            linearSlide1.setPower(gamepad2.left_trigger * -1 * power);
        } else {
            linearSlide1.setPower(0.05);  // Hold position
        }

        // Arm control
        double armSpeedScaling = isFastMode ? ARM_SPEED_SCALING_FAST : ARM_SPEED_SCALING_NORMAL;
        double armPower = gamepad2.left_stick_y * -armSpeedScaling;
        int armPosition = arm.getCurrentPosition();

        if (armPower > 0.2) {
            armUp(armPower);
        } else if (armPower < -0.2) {
            armDown(armPower);
        } else {
            // Gravity support logic with proportional adjustment
            if (armPosition > ARM_POSITION_MIN && armPosition < ARM_POSITION_MAX) {
                double supportPower = getProportionalArmPower(armPosition);
                if (lockedClawPosition == 0.8) {
                    supportPower += SAMPLE_WEIGHT_SUPPORT;
                }
                armUp(supportPower);
            } else {
                armOff();
            }
        }

        // Debug telemetry
        telemetry.addData("Robot Heading", Math.toDegrees(botHeading));
        telemetry.addData("Slide Position", linearSlide1.getCurrentPosition());
        telemetry.addData("Arm Position", armPosition);
        telemetry.addData("Drive Data", "X (%.2f), Y (%.2f), RX (%.2f)", x, y, rx);
        telemetry.addData("Transformed", "rotX (%.2f), rotY (%.2f)", rotX, rotY);
        telemetry.addData("Claw1 Position", claw1.getPosition());
        telemetry.addData("Claw2 Position", claw2.getPosition());
        telemetry.update();
    }

    private double getProportionalArmPower(int position) {
        // Normalize position to a 0 to 1 range
        double normalizedPosition = (double) (position - ARM_POSITION_MIN) /
                (ARM_POSITION_MAX - ARM_POSITION_MIN);

        // Scale power between ARM_MIN_SUPPORT_POWER and ARM_MAX_SUPPORT_POWER
        return ARM_MIN_SUPPORT_POWER +
                (1 - normalizedPosition) * (ARM_MAX_SUPPORT_POWER - ARM_MIN_SUPPORT_POWER);
    }

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