package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="Joshua OmniDirectional")
public class Omnidirectional extends OpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private IMU imu;

    @Override
    public void init() {
        // Initialize motors
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        // Reverse direction for right-side motors
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        // Initialize the IMU
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
    }

    @Override
    public void loop() {
        // Fetch dynamic gamepad inputs in the loop
        double leftStick_x = gamepad1.left_stick_x;
        double leftStick_y = gamepad1.left_stick_y;
        double rightStickRotate_x = gamepad1.right_stick_x;

        // Reset IMU yaw if the 'options' button is pressed
        if (gamepad1.options) {
            imu.resetYaw();
        }

        // Calculate robot heading using IMU (in radians)
        AngleUnit angleUnit = AngleUnit.RADIANS;
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(angleUnit);

        // Field-centric calculations
        double rotX = leftStick_x * Math.cos(-botHeading) - leftStick_y * Math.sin(-botHeading);
        double rotY = leftStick_x * Math.sin(-botHeading) + leftStick_y * Math.cos(-botHeading);

        // Apply scaling for strafing correction
        rotX *= 1.1;

        // Normalize movement power values so that they stay between -1 and 1
        double denom = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rightStickRotate_x), 1);

        // Set motor powers for omnidirectional movement
        frontLeft.setPower((rotY + rotX + rightStickRotate_x) / denom);
        backLeft.setPower((rotY - rotX + rightStickRotate_x) / denom);
        frontRight.setPower((rotY - rotX - rightStickRotate_x) / denom);
        backRight.setPower((rotY + rotX - rightStickRotate_x) / denom);
    }
}