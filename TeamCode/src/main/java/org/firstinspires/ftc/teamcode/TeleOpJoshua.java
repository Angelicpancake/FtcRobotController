package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOp Joshua")
public class TeleOpJoshua extends OpMode
{
    private DcMotor backLeft;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor frontRight;
    private DcMotor intake;

    public static double intakePower = 1.0;
    public static double movePower = 1.0;

    public void init()
    {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop()
    {
        double intakeInput = gamepad2.right_stick_y; //right joystick y direction
        double moveInputY = gamepad1.left_stick_y; //left joystick y direction
        double moveInputX = gamepad1.left_stick_x; //left joystick x direction
        boolean turningL = gamepad1.left_bumper; //left bumper
        boolean turningR = gamepad1.right_bumper; //right bumper

        if (intakeInput > 0.3)
            intake.setPower(intakePower);
        else if (intakeInput < -0.3)
            intake.setPower(-intakePower);
        /* intake section of code: if you move rightStickY power > 0.3 then setPower > 0 value
                                   if you move rightStickY power < -0.3 then setPower < 0 value
                                   0.3 value is to make small accidental movements not taken
         */

        if (moveInputY > 0.3)
            moveForward(movePower);
        else if (moveInputY < -0.3)
            moveForward(-movePower); //same as moveBackward

        if (moveInputX > 0.3)
            moveLeft(movePower);
        else if (moveInputX < -0.3)
            moveLeft(-movePower); //same sa moveLeft

        if (turningL)
            turnLeft(movePower);
        else if (turningR)
            turnRight(movePower);
    }

    /*
        movement methods
     */

    private void moveForward(double power)
    {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private void moveLeft(double power) //strafe
    {
        frontLeft.setPower(-power);
        backLeft.setPower(power);
        frontRight.setPower(power);
        backRight.setPower(-power);
    }

    private void turnLeft(double power)
    {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
        backLeft.setPower(power);
    }

    private void turnRight(double power)
    {
        frontRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }






}

