package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "nicolas")

public class Kelker extends OpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private double setPower =1;

    @Override
    public void loop() {

    }

    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        backRight = hardwareMap.get(DcMotor.class, "motorRight");
        frontLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        frontRight = hardwareMap.get(DcMotor.class, "motorRight");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }

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

}