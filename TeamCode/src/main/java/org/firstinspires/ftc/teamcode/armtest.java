package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Joshua2' Arm")
public class armtest extends OpMode {
    private DcMotor arm;

    @Override
    public void init(){
        arm = hardwareMap.get(DcMotor.class, "arm");

        // Reset encoder and use it for motor position tracking
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double power = gamepad1.left_stick_y;

        // Apply power to the arm motor based on joystick movement with a deadzone of 0.3
        if (power > 0.3) {
            arm.setPower(0.4);  // fixed power for upward motion
        } else if (power < -0.3) {
            arm.setPower(-0.2); // fixed power for downward motion
        } else {
            arm.setPower(0); // stop the motor if within deadzone
        }

        // Get the current position from the motor's encoder
        int cP = arm.getCurrentPosition();

        // Display telemetry data for debugging
        telemetry.addData("Power", power);
        telemetry.addData("Arm Position", arm.getCurrentPosition());
        telemetry.update();
    }
}