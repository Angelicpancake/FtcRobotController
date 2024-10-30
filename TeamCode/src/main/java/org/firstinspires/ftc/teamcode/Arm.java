package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Adam Arm Test :)")
public class Arm extends OpMode {
    private DcMotor backLeft; //making back left motor
    private DcMotor frontLeft; //making front left motor
    private DcMotor backRight; //making back right motor
    private DcMotor frontRight; //making front right motor
    private DcMotor arm;
    private static double rSY;
    private static int armPosition;


    public void loop() {
        rSY = gamepad1.right_stick_y;

        if (rSY >= 0.3) {
            armUp();
        } else if (rSY <= -0.3) {
            armDown();
        } else {
            armOff();
        }

        armPosition = arm.getCurrentPosition();
        telemetry.addData("Power", rSY);
        telemetry.addData("Arm Position", armPosition);
        telemetry.update();
    }

    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft"); //mapping motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE); //reversing left side cuz mecanum wheels
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void armUp() {
        arm.setPower(0.5);
    }

    public void armDown() {
        arm.setPower(-0.5);
    }

    public void armOff() {
        arm.setPower(0);
    }
}