package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "nicolas")

public class Kelker extends OpMode {

    // makes DcMotors for each wheel
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;

    // sets power for use in movement code
    private double setPower =1;

    // loops for TeleOp so it continuously checks for inputs
    @Override
    public void loop() {
        double xM = gamepad1.left_stick_x;
        double yM = gamepad1.left_stick_y;
        double xR = gamepad1.right_stick_x;

        if (yM > 0.3 && xM==0)
            goForward();
        else if (yM < -0.3)
            goBackward();

        if (xM > 0.3)
            goRight();
        else if (xM < -0.3)
            goLeft();

        if(xM > 0.3 && yM > 0.3)
            goForwardRight();
        else if (xM < -0.3 && yM > 0.3)
            goForwardLeft();
        else if (xM > 0.3 && yM < -0.3)
            goBackwardRight();
        else if (xM < -0.3 && yM < -0.3)
            goBackwardLeft();

        
    }

    // initializes each motor for use
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        backRight = hardwareMap.get(DcMotor.class, "motorRight");
        frontLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        frontRight = hardwareMap.get(DcMotor.class, "motorRight");

        // makes it so all motors are positive when moving forward for easier understanding
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }


    // moves the robot forward
    public void goForward() {
        backLeft.setPower(setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(setPower);
    }

    public void goBackward() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower(-1 * setPower);
        frontRight.setPower(-1 * setPower);
    }

    public void goRight() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(-1 * setPower);
    }

    public void goLeft() {
        backLeft.setPower(setPower);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower(-1 * setPower);
        frontRight.setPower(setPower);
    }

    public void goForwardRight() {
        backLeft.setPower(0);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(0);
    }

    public void goForwardLeft() {
        backLeft.setPower(setPower);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(setPower);
    }

    public void goBackwardRight() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(-1 * setPower);
    }

    public void goBackwardLeft() {
        backLeft.setPower(0);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower( -1 * setPower);
        frontRight.setPower(0);
    }

    public void clockWise() {
        backLeft.setPower(setPower);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(-1 * setPower);
    }

    public void antiClockWise() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(-1 * setPower);
        frontRight.setPower(setPower);
    }



}