package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="JoshuaTeleOp")
public class RobotCentric extends OpMode{
    private static DcMotor frontLeft;
    private static DcMotor backLeft;
    private static DcMotor frontRight;
    private static DcMotor backRight;
    private static DcMotor arm;

    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        arm = hardwareMap.get(DcMotor.class, "arm");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
    }
    @Override
    public void loop() {
        if (gamepad1.left_stick_y > 0.3) {
            frontLeft.setPower(0.6);
            backLeft.setPower(0.6);
            frontRight.setPower(0.6);
            backRight.setPower(0.6);
        }

        if (gamepad1.left_stick_y < -0.3) {
            frontLeft.setPower(0.6);
            backLeft.setPower(0.6);
            frontRight.setPower(0.6);
            backRight.setPower(0.6);
        }

        if (0.3 > gamepad1.left_stick_y && gamepad1.left_stick_y > -0.3) {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if (gamepad1.left_stick_x >= 0.3) {
            strafeRight();
        }

        if (gamepad1.left_stick_x <= 0.3) {
            strafeLeft();
        }

        if (0.3 > gamepad1.left_stick_x && gamepad1.left_stick_x > -0.3) {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if (gamepad1.left_bumper) {
            turnLeft();
        }

        if (gamepad1.right_bumper) {
            turnRight();
        }

        if (gamepad1.left_stick_x < 0.3 && gamepad1.left_stick_y < 0.3) {
            moveBackLeft();
        }

        if (gamepad1.left_stick_x < 0.3 && gamepad1.left_stick_y > 0.3) {
            moveFrontLeft();
        }

        if (gamepad1.left_stick_x > 0.3 && gamepad1.left_stick_y < 0.3) {
            moveBackRight();
        }

        if (gamepad1.left_stick_x > 0.3 && gamepad1.left_stick_y > 0.3) {
            moveFrontRight();
        }

        if (gamepad2.left_stick_y >= 0.3) {
            moveArm(0.8);
        }

        if (gamepad2.left_stick_y <= 0.3) {
            moveArm(-0.8);
        }
    }

    public void strafeLeft() {
        frontLeft.setPower(-0.6);
        backLeft.setPower(0.6);
        frontRight.setPower(0.6);
        backRight.setPower(-0.6);
    }

    public void strafeRight() {
        frontLeft.setPower(0.6);
        backLeft.setPower(-0.6);
        frontRight.setPower(-0.6);
        backRight.setPower(0.6);
    }

    public void turnLeft() {
        frontLeft.setPower(-0.6);
        backLeft.setPower(-0.6);
        frontRight.setPower(-0.6);
        backRight.setPower(-0.6);
    }

    public void turnRight() {
        frontLeft.setPower(0.6);
        backLeft.setPower(0.6);
        frontRight.setPower(0.6);
        backRight.setPower(0.6);
    }

    public void moveFrontRight() {
        frontLeft.setPower(0);
        backLeft.setPower(0.6);
        frontRight.setPower(0.6);
        backRight.setPower(0);
    }

    public void moveBackRight() {
        frontLeft.setPower(0);
        backLeft.setPower(-0.6);
        frontRight.setPower(-0.6);
        backRight.setPower(0);
    }

    public void moveFrontLeft() {
        frontLeft.setPower(0.6);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0.6);
    }

    public void moveBackLeft() {
        frontLeft.setPower(-0.6);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(-0.6);
    }

    public void moveArm(double speed) {
        arm.setPower(0.8);
    }
}