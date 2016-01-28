package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robotics on 11/19/2015.
 */
public class Auto_BP extends LinearOpMode {
    public Auto_BP(){

    }

    DcMotor MotorFrontRight;

    DcMotor MotorBackRight;

    DcMotor MotorFrontLeft;

    DcMotor MotorBackLeft;

    DcMotor MotorLiftExtend;

    DcMotor MotorLiftAngle;

    Servo servoright;

    Servo servoleft;

    String ml1 = Teleop5459.ML1;
    String ml2 = Teleop5459.ML2;
    String mr1 = Teleop5459.MR1;
    String mr2 = Teleop5459.MR2;
    String sl = Teleop5459.SL;
    String sr = Teleop5459.SR;
    //public static final String MLA = "LiftAngle";
    //public static final String MLE = "LiftExtend";

    //public GyroSensor gyro;
    //double gyro_rotations;
/*
    //PID
    long lastTime;
    double Input, Output, Setpoint;
    double errSum, lastInput;
    double kp, ki, kd;
    int SampleTime = 1000; //**

    void PID() {
        long now = System.currentTimeMillis();
        int timeChange = ((int)now - (int)lastTime);
        if(timeChange >= SampleTime) {
            double error = Setpoint - Input; //error
            errSum += error;
            double dInput = (Input - lastInput);

            Output = kp * error + ki * errSum  - kd * dInput;

            lastInput = Input;
            lastTime = now;
        }
    }

    void Tune(double Kp, double Ki, double Kd) {
        double SampleTimeInSec = ((double)SampleTime/1000);
        kp = Kp;
        ki = Ki * SampleTimeInSec;
        kd = Kd / SampleTimeInSec;
    }

    void SetSampleTime(int NewSampleTime) {
        if (NewSampleTime > 0) {
            double ratio = (double)NewSampleTime / (double)SampleTime;
            ki *= ratio;
            kd /= ratio;
            SampleTime = (int)NewSampleTime;
        }
    }

    public void turn_gyro(double angle, double power) {
        Input = 0; // reset Gyro
        double angle_rad = (angle*2*Math.PI)/360;
        while(Output < Math.abs(angle_rad)) {
            MotorFrontLeft.setPower(Math.signum(angle)*power);
            MotorBackLeft.setPower(Math.signum(angle) * power);
            MotorFrontRight.setPower(-1 * Math.signum(angle) * power);
            MotorFrontRight.setPower(-1 * Math.signum(angle) * power);
        }
        MotorFrontLeft.setPower(0);
        MotorBackLeft.setPower(0);
        MotorFrontRight.setPower(0);
        MotorFrontRight.setPower(0);
    }


*/

    ////

    public void Turn (double rad, double speed) {
        double leftpower;
        double rightpower;
        int revolutions = 1440;
        int correction = 1;
        double portion = rad * 9;
        double rad_wheel = (portion / (8 * 3.14159));
        double encoder_target = rad_wheel * revolutions * correction; // see bottom

        while ((Math.abs(MotorFrontRight.getCurrentPosition()) < encoder_target)) {
            MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            leftpower = Math.signum(rad) * speed;
            rightpower = -1 * Math.signum(rad) * speed;

            MotorFrontLeft.setPower(leftpower);
            MotorBackLeft.setPower(leftpower);
            MotorFrontRight.setPower(rightpower);
            MotorBackRight.setPower(rightpower);
        }

        MotorFrontLeft.setPower(0);
        MotorBackLeft.setPower(0);
        MotorFrontRight.setPower(0);
        MotorBackRight.setPower(0);

        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    ////

    @Override
    public void runOpMode() throws InterruptedException {
        MotorFrontLeft = hardwareMap.dcMotor.get("Drive_Front_Left");
        MotorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        MotorBackLeft = hardwareMap.dcMotor.get("Drive_Back_Left");
        //MotorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        MotorFrontRight = hardwareMap.dcMotor.get("Drive_Front_Right");
        MotorBackRight = hardwareMap.dcMotor.get("Drive_Back_Right");
        MotorBackRight.setDirection(DcMotor.Direction.REVERSE);
        MotorLiftAngle = hardwareMap.dcMotor.get("LiftAngle");
        MotorLiftExtend = hardwareMap.dcMotor.get("LiftExtend");
        //gyro = hardwareMap.gyroSensor.get("Gyro");
        //gyro_rotations = 0;
        int v_state = 0;

        waitOneFullHardwareCycle();

        waitForStart();

        while (opModeIsActive()) {
            switch (v_state) {
                case 0:
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                    if(MotorFrontRight.getCurrentPosition() == 0){
                        v_state++;
                    }

                case 1:

                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

                    MotorFrontRight.setPower(-1.0);
                    MotorFrontLeft.setPower(-1.0);
                    MotorBackRight.setPower(-1.0);
                    MotorBackLeft.setPower(-1.0);

                    v_state++;
                    break;

                case 2:
                    if((Math.abs(MotorFrontRight.getCurrentPosition()) > 1440)) {
                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                        MotorFrontRight.setPower(0.0);
                        MotorFrontLeft.setPower(0.0);
                        MotorBackRight.setPower(0.0);
                        MotorBackLeft.setPower(0.0);
                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

                        v_state++;
                    }

                    break;

                case 3:

                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(-1.0);
                    MotorFrontLeft.setPower(1.0);
                    MotorBackRight.setPower(-1.0);
                    MotorBackLeft.setPower(1.0);

                    v_state++;

                    break;

                case 4:

                    if((Math.abs(MotorFrontRight.getCurrentPosition()) > 720)) {
                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                        MotorFrontRight.setPower(0.0);
                        MotorFrontLeft.setPower(0.0);
                        MotorBackRight.setPower(0.0);
                        MotorBackLeft.setPower(0.0);
                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                        v_state++;
                    }

                    break;
/*
                case 5:

                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(1.0);
                    MotorFrontLeft.setPower(1.0);
                    MotorBackRight.setPower(1.0);
                    MotorBackLeft.setPower(1.0);

                    v_state++;

                        break;

                case 6:

                    if((Math.abs(MotorFrontRight.getCurrentPosition()) > 1000)) {

                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                        MotorFrontRight.setPower(0.0);
                        MotorFrontLeft.setPower(0.0);
                        MotorBackRight.setPower(0.0);
                        MotorBackLeft.setPower(0.0);
                        MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                        v_state++;
                    }

                    break;

                case 7:
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(-1.0);
                    MotorFrontLeft.setPower(1.0);
                    MotorBackRight.setPower(-1.0);
                    MotorBackLeft.setPower(1.0);
                    v_state++;


                    break;

                case 8:
                    if((Math.abs(MotorFrontRight.getCurrentPosition()) > 750)) {
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(0.0);
                    MotorFrontLeft.setPower(0.0);
                    MotorBackRight.setPower(0.0);
                    MotorBackLeft.setPower(0.0);
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                    v_state++;
                }
                    break;

                case 9:
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(-1.0);
                    MotorFrontLeft.setPower(-1.0);
                    MotorBackRight.setPower(-1.0);
                    MotorBackLeft.setPower(-1.0);

                    v_state++;
                    break;

                case 10:
                    if((Math.abs(MotorFrontRight.getCurrentPosition()) > 1000)) {
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower(0.0);
                    MotorFrontLeft.setPower(0.0);
                    MotorBackRight.setPower(0.0);
                    MotorBackLeft.setPower(0.0);
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                    v_state++;
                }

                    break;

               /*
               if((Math.abs(MotorFrontRight.getCurrentPosition()) > )) {
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                    MotorFrontRight.setPower();
                    MotorFrontLeft.setPower();
                    MotorBackRight.setPower();
                    MotorBackLeft.setPower();
                    MotorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                    v_state++;
                }
                */

            }
        }
    }
}
