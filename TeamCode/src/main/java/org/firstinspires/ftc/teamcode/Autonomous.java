package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "raymond said no")
public class Autonomous extends LinearOpMode {
// need to make parameter for choosing left side or right side when we start
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor arm;
    private Servo claw;
    private static final double CLAW_OPEN = 0.0;
    private static final double CLAW_CLOSE = 0.35;

    // Constant for encoder ticks per tile (assuming 1060 ticks per tile)
    private static final int TICKS_PER_TILE = 1060;
    @Override
    public void runOpMode() {
        // Initialize motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        // Reverse the right motors so that forward is forward for all motors
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the driver to press start
        waitForStart();

        moveForward(1400);
        moveArm(-200);
        moveBackward(100);
        openClaw();
        moveLeft(1000);
        rotateRight(1000);
        moveForward(1000);
    }

    // Method to move forward a certain distance (in ticks)
    public void moveForward(int distance) {
        moveMotors(distance, distance, distance, distance);
    }
    public void moveBackward(int distance) {
        moveMotors(-distance, -distance, -distance, -distance);
    }
    public void moveArm(int distance) {
        arm.setTargetPosition(distance);
    }
    public void openClaw() {
        claw.setPosition(CLAW_OPEN);
    }
    public void closeClaw() {
        claw.setPosition(CLAW_CLOSE);
    }
    public void moveLeft(int distance) {
        moveMotors(distance, -distance, -distance, distance);
    }
    public void moveRight(int distance) {
        moveMotors(-distance, distance, distance, -distance);
    }
    public void rotateLeft(int distance) {
        moveMotors(-distance, distance, -distance, distance);
    }
    public void rotateRight(int distance) {
        moveMotors(distance, -distance, distance, -distance);
    }
    // Method to move motors by setting their target positions

    public void moveMotors(int backL, int backR, int frontL, int frontR) {
        resetEncoders(); // Reset encoders before setting the target positions

        // Set target positions
        backLeft.setTargetPosition(backL);
        backRight.setTargetPosition(backR);
        frontLeft.setTargetPosition(frontL);
        frontRight.setTargetPosition(frontR);

        // Set motors to run to the target position
        setAllMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power to move
        setMotorPower(0.5);

        // Wait until motors reach their target
        waitForMotors();

        // Stop motors once the target is reached
        stopMotors();
    }

    // Method to reset motor encoders
    public void resetEncoders() {
        setAllMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Method to wait until all motors reach their target position
    public void waitForMotors() {
        // Wait until all motors are done moving to their target positions
        while (opModeIsActive() && (backLeft.isBusy() && backRight.isBusy() && frontLeft.isBusy() && frontRight.isBusy())) {
            telemetry.addData("Status", "Moving...");
            telemetry.update();
            sleep(50); // Small delay to prevent the loop from running too fast
        }
    }

    // Helper method to set the same mode for all motors
    public void setAllMotorsMode(DcMotor.RunMode mode) {
        backLeft.setMode(mode);
        backRight.setMode(mode);
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
    }

    // Helper method to set the power for all motors
    public void setMotorPower(double power) {
        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
    }

    // Method to stop all motors
    public void stopMotors() {
        setMotorPower(0);
    }
}