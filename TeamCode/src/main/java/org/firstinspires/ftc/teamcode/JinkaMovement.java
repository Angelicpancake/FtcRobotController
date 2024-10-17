package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/*autonomous -> Opmode **this is for autonomous
 * Linear Opmode -> extends Opmode **this is for teleop */
@TeleOp(name = "Gaurang")

public class JinkaMovement extends OpMode {

    /*DcMotor is an object type like scanner*/
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    /*set power is a variable*/
    private double setPower = 1;
    //we need the loop to keep on looking for inputs.
    // theres nothing here now cus we got nothing lol
    @Override
    public void loop() {
        //gamepad1 is an object
        // leftstick_x is a variable of the object
        //M means movement
        //R means rotation
        double xM = gamepad1.left_stick_x;
        double yM = gamepad1.left_stick_y;
        double xR = gamepad1.right_stick_x;



        //checks for diagonal movements first to not interfere with axis movements
        //if statements for diagonal movement
        if(xM > 0.3 && yM > 0.3)
            forwardRight();
        else if (xM < -0.3 && yM > 0.3)
            forwardLeft();
        else if (xM > 0.3 && yM < -0.3)
            backwardRight();
        else if (xM < -0.3 && yM < -0.3)
            backwardLeft();
        //if statements for right, left, forward, backwards
        else if (yM > 0.3 && xM==0)
            forward();
        else if (yM < -0.3 && xM==0)
            backward();
        else if (xM > 0.3 && yM==0)
            right();
        else if (xM < -0.3 && yM==0)
            left();

        //movement for clockwise and counterclockwise
        //only uses x axis for rotation
        if (xR > 0.3)
            rotateClockwise();
        else if(xR <-0.3)
            rotateCounterclockwise();




    }

    @Override
    //init -> initializes the motors for the hub to read
    //creates devices in the driver hub
    // the driver hub is the orange thingy
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        backRight = hardwareMap.get(DcMotor.class, "motorRight");
        frontLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        frontRight = hardwareMap.get(DcMotor.class, "motorRight");
        //we add the reverse for quality of life
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }


//    public void Diagonalmovement(){
//        backLeft.setPower();
//    }

//    public void moveAdHoc(double x, double y){
  ///      
   // }



    //forward moves the bot forward
    public void forward() {
        backLeft.setPower(setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(setPower);
    }
    //backward moves the bot backwards
    public void backward() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower(-1 * setPower);
        frontRight.setPower(-1 * setPower);
    }
    //right moves the bot right
    public void right() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(-1 * setPower);
    }
    //left moves the bot left
    public void left() {
        backLeft.setPower(setPower);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower(-1 * setPower);
        frontRight.setPower(setPower);
    }
    //forwardRight moves the bot 45 degrees upright
    public void forwardRight() {
        backLeft.setPower(0);
        backRight.setPower(setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(0);
    }
    //forwardLeft moves the bot 135 degrees upleft
    public void forwardLeft() {
        backLeft.setPower(setPower);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(setPower);
    }
    //backwardRight move the bot 315 degrees down-right
    public void backwardRight() {
        backLeft.setPower(-1 * setPower);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(-1 * setPower);
    }
    //backwardLeft move the bot 225 degrees down-left
    public void backwardLeft() {
        backLeft.setPower(0);
        backRight.setPower(-1 * setPower);
        frontLeft.setPower( -1 * setPower);
        frontRight.setPower(0);
    }
    //rotateClockwise rotates the bot clockwise ->positive degree change
    public void rotateClockwise(){
        backLeft.setPower(setPower);
        backRight.setPower(-1*setPower);
        frontLeft.setPower(setPower);
        frontRight.setPower(-1*setPower);
    }
    //rotateCounterclockwise rotates the bot counterclockwise ->negative degree change
    public void rotateCounterclockwise(){
        backLeft.setPower(-1*setPower);
        backRight.setPower(setPower);
        frontLeft.setPower(-1*setPower);
        frontRight.setPower(setPower);
    }

}
