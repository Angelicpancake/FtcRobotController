package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name ="Malek")
public class malekCode extends OpMode {

    private DcMotor backL;
    private DcMotor backR;
    private DcMotor frontL;
    private DcMotor frontR;
    private double powerInput;


    @Override
    public void loop(){

    }

    @Override
    public void init(){
        backL = hardwareMap.get(DcMotor.class, "motorLeft");
        backR = hardwareMap.get(DcMotor.class, "motorRight");
        frontL = hardwareMap.get(DcMotor.class, "frontLeft");
        frontR = hardwareMap.get(DcMotor.class, "frontRight");

        frontR.setDirection(DcMotor.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

}