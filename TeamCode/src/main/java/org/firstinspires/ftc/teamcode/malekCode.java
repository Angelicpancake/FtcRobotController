package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name ="Malek")
public class malekCode extends OpMode {//linearop - autonomous/ teleop -> opmode

    private DcMotor backL;
    private DcMotor backR;
    private DcMotor frontL;
    private DcMotor frontR;
    private DcMotor intake;
    private double powerInput;
    


    @Override
    public void loop(){

        double rightTrigger = gamepad1.right_trigger;
        double leftTrigger = gamepad1.left_trigger;

        boolean rightBumber = gamepad1.right_bumper;
        boolean leftBumber = gamepad1.left_bumper;

        boolean buttonUp = gamepad1.dpad_up;
        boolean buttonDown = gamepad1.dpad_down;
        boolean buttonRight = gamepad1.dpad_right;
        boolean buttonLeft = gamepad1.dpad_left;

        boolean buttonA = gamepad1.a;
        boolean buttonX = gamepad1.x;

        double rightStickX = gamepad1.right_stick_x;
        double rightStickY = gamepad1.right_stick_y;

        double leftStickX= gamepad1.left_stick_x;
        double leftStickY = gamepad1.left_stick_y;

        if(buttonUp){
            goForward();
        } else if (buttonDown){
            goBackwards();
        } else if (buttonLeft) {
            goLeft();
        } else if (buttonRight) {
            goRight();
        } else if (rightStickX > 0.3) {
            turnClockwise();
        } else if (rightStickX < -0.3) {
            turnCounterClockwise();
        } else if (buttonA) {
            intake();
        } else if (buttonX) {
            release();
        } 


    }

    public void intake(){
        intake.setPower(-powerInput);
    }
    public void release(){
        intake.setPower(-powerInput);
    }
    public void goForward(){
        backL.setPower(powerInput);
        backR.setPower(powerInput);
        frontL.setPower(powerInput);
        frontR.setPower(powerInput);
    }

    public void goBackwards(){
        backL.setPower(-powerInput);
        backR.setPower(-powerInput);
        frontL.setPower(-1 * powerInput);
        frontR.setPower(-1 * powerInput);
    }

    public void goLeft(){
        backL.setPower(powerInput);
        backR.setPower(-1 * powerInput);
        frontL.setPower(-1 * powerInput);
        frontR.setPower(powerInput);
    }

    public void goRight(){
        backL.setPower(-1 * powerInput);
        backR.setPower(powerInput);
        frontL.setPower(powerInput);
        frontR.setPower(-1 * powerInput);
    }

    public void goDiagonalRightForward(){
        frontL.setPower(powerInput);
        backR.setPower(powerInput);
    }

    public void goDiagonalRightBackwards(){
        frontL.setPower(-1 * powerInput);
        backR.setPower(-1 * powerInput);

    }

    public void goDiagonalLeftForwards(){
        frontR.setPower(powerInput);
        backL.setPower(powerInput);

    }

    public void goDiagonalLeftBackwards(){
        frontL.setPower(-1 * powerInput);
        backR.setPower(-1 * powerInput);

    }

    public void turnClockwise(){
        backR.setPower(-powerInput);
        frontR.setPower(-powerInput);
        backL.setPower(powerInput);
        frontL.setPower(powerInput);
    }

    public void turnCounterClockwise(){
        backR.setPower(powerInput);
        frontR.setPower(powerInput);
        backL.setPower(-powerInput);
        frontL.setPower(-powerInput);

    }

    @Override
    public void init(){
        backL = hardwareMap.get(DcMotor.class, "backLeft");
        backR = hardwareMap.get(DcMotor.class, "backRight");
        frontL = hardwareMap.get(DcMotor.class, "frontLeft");
        frontR = hardwareMap.get(DcMotor.class, "frontRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        powerInput = 1;

        frontR.setDirection(DcMotor.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

}