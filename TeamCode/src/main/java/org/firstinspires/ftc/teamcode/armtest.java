package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Joshua Arm")
public class armtest extends OpMode {
    //op mode -> real time control, linearOp -> preset commands (auto)
    private DcMotor arm;

    @Override
    public void init(){
        arm = hardwareMap.get(DcMotor.class, "arm");

    }

    @Override
    public void loop() {
        double currentPosition = 0.0;


        double power = Math.abs(gamepad2.left_stick_y);

        // remember it is reversed -> init
        if (gamepad2.left_stick_y > 0.3 ) {
            arm.setPower(power);
        }


    }
}
