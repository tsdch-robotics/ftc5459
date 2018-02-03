package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.ar.pl.SensorController;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

/**
 * This is NOT an opmode. This defines hardware on the robot.
 *
 */

public class WoodyBot {
    /* define all hardware on robot with global variables */
    // motors
    public DcMotor FrontMotorLeft;
    public DcMotor FrontMotorRight;
    public DcMotor Elevator1;
    public DcMotor Extender;
    //public DcMotor SideMotor;

    // servos
    public Servo left_door;
    //public Servo right_door;
    public Servo claw;
    public Servo rotator;
    public Servo ColorSense;

    public Servo Stupid;
    public Servo TwistyThingy;

    // sensors
    public ColorSensor CS;

    /* local OpMode members. */

    HardwareMap hwMap = null;
    protected ElapsedTime period = new ElapsedTime();

    public WoodyBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize motors
        Extender = hwMap.dcMotor.get("Extend");
        Elevator1 = hwMap.dcMotor.get("Elevator1");
        FrontMotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
        FrontMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontMotorRight = hwMap.dcMotor.get("FrontMotorRight");
        FrontMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // initialize servos
        claw = hwMap.servo.get("claw");
        ColorSense = hwMap.servo.get("ColorSense");
        left_door = hwMap.servo.get("left_door");
        //right_door = hwMap.servo.get("right_door");
        rotator = hwMap.servo.get("rot");
        Stupid = hwMap.servo.get("stupidstick");
        TwistyThingy = hwMap.servo.get("TwistyThingy");

        // initialize sensors
        CS = hwMap.get(ColorSensor.class, "CS");

        // Set all motors and servos to zero power
        FrontMotorLeft.setPower(0.0);
        FrontMotorRight.setPower(0.0);
        Elevator1.setPower(0.0);
        // SideMotor.setPower(0.0);
        //ColorSense.setPosition(.27);
        //right_door.setPosition(0.0);   //Enabling these caused the servos to move in init, which is illegal
//        //left_door.setPosition(0.0);
//        claw.setPosition(1);
//        rotator.setPosition(0.5);
//        CS.enableLed(false);

//        Stupid.setPosition(0);
//        ColorSense.setPosition(0.35);
//        TwistyThingy.setPosition(1);
    }


    //*
    //* waitForTick implements a periodic delay. However, this acts like a metronome with a regular
    //* periodic tick.  This is used to compensate for varying processing times for each cycle.
    //* The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
    //*
    //* @param periodMs  Length of wait cycle in mSec.
    //* @throws InterruptedException

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}