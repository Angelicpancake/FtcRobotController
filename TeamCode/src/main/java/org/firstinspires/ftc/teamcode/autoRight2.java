package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Right Side Park2")
public class autoRight2 extends LinearOpMode {
    // need to make parameter for choosing left side or right side when we start
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor arm;
    private Servo claw;
    private static final double CLAW_OPEN = 0;
    private static final double CLAW_CLOSE = 0.8;

    // Constant for encoder ticks per tile (assuming 1060 ticks per tile)
    private static final int TICKS_PER_TILE = 1060;
    @Override
    public void runOpMode() {
        // Initialize motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        frontRight.setDirection(DcMotor.Direction.REVERSE);

        // Reverse the right motors so that forward is forward for all motors

        // Wait for the driver to press start
        waitForStart();

        clawClose();
        /*moveForward(50);
        rotateRight(1000);*/
        moveForward(-80);

        moveForward(650);
        moveArm(1400);
        sleep(500);
        moveForward(500);
        sleep(1000);
        clawOpen();
        moveBackward(400);
        clawClose();
        rotateRight(2400);
        moveArm(-1400);
        moveForward(1400);
    }

    // Method to move forward a certain distance (in ticks)
    public void moveArm(int ticks) {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset encoder
        arm.setTargetPosition(ticks); // Set target position
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Enable encoder control
        arm.setPower(0.5); // Set power for arm

        // Wait for arm to reach its target
        while (opModeIsActive() && arm.isBusy()) {
            telemetry.addData("Arm Status", "Moving...");
            telemetry.update();
            sleep(50); // Prevent loop from running too fast
        }

        arm.setPower(0); // Stop arm motor
    }

    // Method to open claw
    public void clawOpen() {
        claw.setPosition(CLAW_OPEN);
        telemetry.addData("Claw", "Open");
        telemetry.update();
    }

    // Method to close claw
    public void clawClose() {
        claw.setPosition(CLAW_CLOSE);
        telemetry.addData("Claw", "Closed");
        telemetry.update();
    }

    // Method to move forward
    public void moveForward(int distance) {
        moveMotors(-distance, -distance);
    }

    // Method to move backward
    public void moveBackward(int distance) {
        moveMotors(distance, distance);
    }

    // Method to rotate right
    public void rotateLeft(int distance) { // Rotate clockwise
        moveMotors(distance, -distance);
    }

    // Method to rotate left
    public void rotateRight(int distance) { // Rotate counter-clockwise
        moveMotors(-distance, distance);
    }

    // Method to move motors by setting their target positions
    public void moveMotors(int leftTarget, int rightTarget) {
        resetEncoders(); // Reset encoders before setting the target positions

        // Set target positions
        frontLeft.setTargetPosition(leftTarget);
        frontRight.setTargetPosition(rightTarget);

        // Set motors to run to the target position
        setAllMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power to move
        setMotorPower(0.4);

        // Wait until motors reach their target
        waitForMotors();

        // Stop motors once the target is reached
        stopMotors();
    }

    // Method to reset motor encoders
    public void resetEncoders() {
        setAllMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Method to wait until both motors reach their target position
    public void waitForMotors() {
        while (opModeIsActive() && (frontLeft.isBusy() || frontRight.isBusy())) {
            telemetry.addData("Status", "Motors Moving...");
            telemetry.update();
            sleep(50); // Small delay to prevent loop from running too fast
        }
    }

    // Helper method to set the same mode for both motors
    public void setAllMotorsMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
    }

    // Helper method to set the power for both motors
    public void setMotorPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
    }

    // Method to stop both motors
    public void stopMotors() {
        setMotorPower(0);
    }
}