package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range; //didnt need to do this, only used y input on controllers

/**
 * Created by Robotics on 10/26/2015.
 */
public class Teleop5459 extends OpMode {

    /* MOTORS */
    DcMotor drive_left_front;
    DcMotor drive_left_back;
    DcMotor drive_right_front;
    DcMotor drive_right_back;

    /* SERVOS */
    Servo ziplineLeft;
    Servo ziplineRight;
    Servo rodLeft;
    Servo rodRight;
    Servo rodCenter;
    Servo push;
    Servo wire;

    /* TITLES */
    public static final String DLF = "Drive_Front_Left";
    public static final String DLB = "Drive_Back_Left";
    public static final String DRF = "Drive_Front_Right";
    public static final String DRB = "Drive_Back_Right";
    public static final String ZL = "ZiplineLeft";
    public static final String ZR = "ZiplineRight";
    public static final String RL = "RodLeft";
    public static final String RC = "RodCenter";
    public static final String RR = "RodRight";
    public static final String WS = "Wire";
    public static final String PS = "Push";

    /* ZIPLINE VALUES */
    public static final double SPALeft = 0.0;
    public static final double SPARight = 1.0;
    public static final double SPCLeft = 0.5;
    public static final double SPCRight = 0.5;
    public static final double SPBLeft = 0.9;
    public static final double SPBRight = 0.1;
    public static final double RCi = 0.0;
    public static final double RLi = 0.0;
    public static final double RRi = 1.0;
    public static final double PSi = 0.0;       // THESE NEED TESTING
    public static final double WSi = 0.0;

    public long counter;
    public static final long threshhold = 70;

    // left inits 0.0
    // right inits 1.0
    // left position1 0.8
    // left position2 0.4
    // right position1 0.2
    // right position2 0.6

    boolean ziplineLeftPosition = false; // false = 0.8 /\ true = 0.4
    boolean ziplineRightPosition = false; // false = 0.2 /\ true = 0.6

    public double factur;
    boolean facButt;

    public Teleop5459() {

    }


    @Override
    public void init() {

        /* DRIVE MOTORS */
        drive_left_front = hardwareMap.dcMotor.get(DLF);
        drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get(DLB);
        //drive_left_back.setDirection(DcMotor.Direction.REVERSE);
        drive_right_front = hardwareMap.dcMotor.get(DRF);
        //drive_right_front.setDirection(DcMotor.Direction.REVERSE);//comment out
        drive_right_back = hardwareMap.dcMotor.get(DRB);
        drive_right_back.setDirection(DcMotor.Direction.REVERSE);

        //lift_angle_right = hardwareMap.dcMotor.get(MLA);
        //lift_extend_right = hardwareMap.dcMotor.get(MLE);
        //lift_extend_right.setDirection(DcMotor.Direction.REVERSE);

        /* SERVOS */
        ziplineLeft = hardwareMap.servo.get(ZL);
        ziplineRight = hardwareMap.servo.get(ZR);

        rodLeft = hardwareMap.servo.get(RL);
        rodCenter = hardwareMap.servo.get(RC);
        rodRight = hardwareMap.servo.get(RR);

        wire = hardwareMap.servo.get(WS);
        push = hardwareMap.servo.get(PS);

        ziplineLeft.setPosition(0.5);
        ziplineRight.setPosition(0.45);

        rodCenter.setPosition(RCi);
        rodLeft.setPosition(RLi);
        rodRight.setPosition(RRi);

        counter = 0;
        facButt = false;
    }

    @Override
    public void loop() {

        /* DRIVE MOTORS */
        double ThrottleLeft = gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;
        float Angle = gamepad2.right_stick_y; // 0.5<y<1 Power level lower "Initial burst then slow "
        float Extend = -1 * gamepad2.left_stick_y;
        boolean runZiplineLeft = gamepad2.x;
        boolean runZiplineRight = gamepad2.b;
        boolean Airhorn = gamepad1.dpad_left; // need to import sound from com.android.sti.stocksoundeffects.res.raw

        /* DISCRETE BUTTONS */
        if(counter > threshhold) {

            if (gamepad2.dpad_up) {
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                rodLeft.setPosition(posL + .05);
                rodRight.setPosition(posR + .05);

                counter = 0;
            }

            if (gamepad2.dpad_down) {
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                rodLeft.setPosition(posL - .05);
                rodRight.setPosition(posR - .05);

                counter = 0;
            }

            if (gamepad2.dpad_left) {
                double posC = rodCenter.getPosition();

                rodCenter.setPosition(posC + .05);

                counter = 0;
            }

            if (gamepad2.dpad_right) {
                double posC = rodCenter.getPosition();

                rodCenter.setPosition(posC - .05);

                counter = 0;
            }

            if (gamepad2.x || gamepad2.b) {
                rodCenter.setPosition(RCi);

                counter = 0;
            }

            /*
            if (gamepad2.y || gamepad2.a) {
                rodLeft.setPosition(RLi);
                rodRight.setPosition(RRi);

                counter = 0;
            }
            */

            if (gamepad1.x) { // left servo
                double setPos = !ziplineLeftPosition ? 0.95 : 0.5;
                ziplineLeft.setPosition(setPos);
                ziplineLeftPosition = !ziplineLeftPosition;

                counter = 0;
            }

            if (gamepad1.b) { // right servo
                double setPos = !ziplineRightPosition ? 0.03 : 0.45;
                ziplineRight.setPosition(setPos);
                ziplineRightPosition = !ziplineRightPosition;

                counter = 0;
            }

            if (gamepad1.a) {
                factur = !facButt ? 0.70 : 1;
                facButt = !facButt;

                counter = 0;
            }
        }

        /* MOTOR POWER SCALING */
        ThrottleLeft = (float)scaleInputs(ThrottleLeft);
        ThrottleRight = (float)scaleInputs(ThrottleRight);
        //AngleLeft = (float)scaleInputs(AngleLeft);
        Angle = (float)scaleInputs(Angle);
        //ExtendLeft = (float)scaleInputs(ExtendLeft);
        Extend = (float)scaleInputs(Extend);

        /* DRIVE MOTOR POWER */
        drive_left_front.setPower(ThrottleLeft);
        drive_left_back.setPower(ThrottleLeft);
        drive_right_front.setPower(ThrottleRight);
        drive_right_back.setPower(ThrottleRight);

        counter ++;
    }


    double scaleInputs(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;

    }
}