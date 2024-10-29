package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Gaurang")
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



        //calculating telemetry
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop(){

        double armPower = gamepad1.left_stick_x;

        if (armPower > 0.3) {
            arm.setPower(0.4);  // up
        } else if (armPower < -0.3) {
            arm.setPower(-0.4); // down
        } else {
            arm.setPower(0); // deadzone
        }

        //telemetry
        telemetry.addData("Arm Position", arm.getCurrentPosition());
        //adding data with every new input
        telemetry.update();



    }

}
