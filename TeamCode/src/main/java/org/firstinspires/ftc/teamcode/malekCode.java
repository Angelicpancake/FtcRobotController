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
    private double powerInput;
    


    @Override
    public void loop(){
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;

        if(y > 0){
            goForward();
        } else if (y < 0){
            goBackwards();
        }
        


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

    public void gpDiagonalLeftForwards(){
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
        backL = hardwareMap.get(DcMotor.class, "motorLeft");
        backR = hardwareMap.get(DcMotor.class, "motorRight");
        frontL = hardwareMap.get(DcMotor.class, "frontLeft");
        frontR = hardwareMap.get(DcMotor.class, "frontRight");

        powerInput = 1;

        frontR.setDirection(DcMotor.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

}