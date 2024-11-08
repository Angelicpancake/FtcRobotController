package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="LinearArmTest")
public class LinearArmTest extends OpMode{
    private DcMotor LinearSlide;
    private DcMotor arm;


    @Override
    public void init(){


        //setting the motors to variables
        LinearSlide = hardwareMap.get(DcMotor.class, "LinearSlide");
        arm = hardwareMap.get(DcMotor.class, "arm");

        //initializing telemetry
        telemetry.addData("Position", LinearSlide.getCurrentPosition());
        //sending data to controller hub
        telemetry.update();
        //rate at which data is sent to controller hub
        telemetry.setMsTransmissionInterval(50);


        //resetting encoders
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //the motor will do no automatic action
        //but the encoder will still count
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LinearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //setting target position before using
        //run to position
        arm.setTargetPosition(0);
        LinearSlide.setTargetPosition(0);


        //how it should determine positive/negative positions
        LinearSlide.setDirection(DcMotor.Direction.REVERSE);


        //The motor should stick to target
        //position until new position is given
        LinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }



    @Override
    public void loop(){




        //initializing telemetry
        telemetry.addData("Position", LinearSlide.getCurrentPosition());
        //sending data to controller hub
        telemetry.update();

        //can change gamepad settings later
        //setting power variables
        double armPower = gamepad1.left_stick_x;
        double LinearPower = Math.abs(gamepad1.right_stick_y);

        //movement for Linear slide
        //moving forward
        if (gamepad1.left_stick_y < -0.1 && LinearSlide.getCurrentPosition()< 3000) {
            LinearSlide.setPower(LinearPower);
            LinearSlide.setTargetPosition(LinearSlide.getCurrentPosition());
        }
        //reversing
        else if (gamepad1.left_stick_y > 0.1 && LinearSlide.getCurrentPosition() > 3) {
            LinearSlide.setPower(-LinearPower);
            LinearSlide.setTargetPosition(LinearSlide.getCurrentPosition());

        }
        //set the power to 0 if there is no input
        else {
            LinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LinearSlide.setPower(0.3);
        }

        //movement for Arm
        //moving up
        if (armPower > 0.3) {
            arm.setPower(0.4);
            arm.setTargetPosition(arm.getCurrentPosition());
        }
        //moving down
        else if (armPower < -0.3) {
            arm.setPower(-0.3);
            arm.setTargetPosition(arm.getCurrentPosition());
        }
        //set the power to 0 if there is no input
        else {
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(0.3);
        }







    }









}
