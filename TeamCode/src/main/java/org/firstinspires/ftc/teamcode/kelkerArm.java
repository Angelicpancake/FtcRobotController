package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "nicolasarm")

public class kelkerArm extends OpMode {

    private DcMotor arm;
    private double position = 1;

    @Override
    public void loop(){

        double yA = gamepad2.left_stick_y;

        if (yA > 0.3 )

            armUp(yA);
        else if (yA < - 0.3)
            armDown(yA);

    }

    public void init() {
        arm = hardwareMap.get(DcMotor.class, "arm");
    }

    public void armUp(double power) {
        arm.setPower(power);
    }

    public void armDown(double power) {
        arm.setPower(power);
    }
}
