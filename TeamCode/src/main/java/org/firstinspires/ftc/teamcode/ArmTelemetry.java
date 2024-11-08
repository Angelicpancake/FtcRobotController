package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="JinkaArmFix")
public class ArmTelemetry extends OpMode {
    //initialization
    private DcMotor arm;





    @Override
    public void init(){
        //initializing the objects
        arm = hardwareMap.get(DcMotor.class, "arm");
        //initializing the telemetry
        telemetry.addData("Status", "Initialized");
        //sending data to controller hub
        telemetry.update();
        //rate at which data is sent to controller hub
        telemetry.setMsTransmissionInterval(50);



        //resets the encoders before using them
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //the motor will do no automatic action
        //but the encoder will still count
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //initial position
        arm.setTargetPosition(10);

        //arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //allows you to directly set the motor power without
        //looking at the encoder
        //it'll still keep track of encoder values

        //arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Allows you to set the speed of the motor
        //it will look at the encoder values and use
        //a pit loop to make sure it's
        //traveling at the right speed

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //allows you to set a target positions
        //which the motor will automatically go to
        //and hold its position there

        //arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //just resets encoder value to 0



    }

    @Override
    public void loop(){

        double armPower = gamepad1.left_stick_x;

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

        //telemetry
        telemetry.addData("Arm Position", arm.getCurrentPosition());
        //adding data with every new input
        telemetry.update();



    }

}
