package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "nicolasmove")

public class kelkermove360 extends OpMode {

    // makes DcMotors for each wheel
    final double pi = Math.PI;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;

    // sets power for use in movement code
    private double setPowerR =1;


    // loops for TeleOp so it continuously checks for inputs
    @Override
    public void loop() {
        double xM = gamepad1.left_stick_x;
        double yM = gamepad1.left_stick_y;
        double xR = gamepad1.right_stick_x;

        if (Math.abs(xM) > 0.3 && Math.abs(yM) > 0.3)
            move360(xM, yM);

        if (xR > 0.3)
            clockwise();
        else if (xR < -0.3)
            counterclockwise();
    }

    // initializes each motor for use
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        // makes it so all motors are positive when moving forward for easier understanding
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }

    // moves the robot forward
    public void move360(double x, double y) {
        double angle = Math.atan2(x, y);
        double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        backLeft.setPower(mag * Math.sin(angle - 0.25 * pi));
        backRight.setPower(mag * Math.sin(angle + 0.25 * pi));
        frontLeft.setPower(mag * Math.sin(angle + 0.25 * pi));
        frontRight.setPower(mag * Math.sin(angle - 0.25 * pi));
    }

    public void clockwise() {
        backLeft.setPower(setPowerR);
        backRight.setPower(-1 * setPowerR);
        frontLeft.setPower(setPowerR);
        frontRight.setPower(-1 * setPowerR);
    }

    public void counterclockwise() {
        backLeft.setPower(-1 * setPowerR);
        backRight.setPower(setPowerR);
        frontLeft.setPower(-1 * setPowerR);
        frontRight.setPower(setPowerR);
    }



}