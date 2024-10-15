package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "malekArmCode")
public class ArmTestingMalek extends OpMode {
    private DcMotor arm;
    private double armPosition;




    @Override
    public void init(){
        arm = hardwareMap.get(DcMotor.class, "arm");


    }

    @Override
    public void loop() {

    }


}


