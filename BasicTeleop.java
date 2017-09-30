/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="5459 Teleop", group="WoodyBot")
public class BasicTeleop extends OpMode {

    /* Declare OpMode members. */
    WoodyBot         robot   = new WoodyBot();   // Use robot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    // code runs ONCE when driver hits INIT
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "I like potatos");    //
        updateTelemetry(telemetry);
    }

    // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() { }

    // code runs ONCE when the driver hits PLAY
    @Override
    public void start() { }

    // code runs REPEATEDLY when driver hits play
    // put driver controls (drive motors, servos, etc.) HERE
    @Override
    public void loop() {
        double ThrottleUp = gamepad1.left_trigger;
        boolean Throttledown = gamepad1.left_bumper;
        double ThrottleLeft = gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;

        // this is technically the correct way to do this. -PK
//        double ElevatorPower = 0;
//        if(gamepad1.left_bumper == true) {
//            ElevatorPower = -1;
//            telemetry.addData("Elevator","UP");
//        }
//        else if(gamepad1.left_trigger > 0.85) {
//            ElevatorPower = 1;
//            telemetry.addData("Elevator", "DOWN");
//        }
//        else {
//            ElevatorPower = 0;
//            telemetry.addData("Elevator","OFF");
//        }

        if(Throttledown == true)
        {
            robot.Elevator.setPower(-1);
            telemetry.addData("elevator","on");
        }
        else if(Throttledown == false)
        {
            robot.Elevator.setPower(0);
            telemetry.addData("elevator","off");
        }
        robot.Elevator.setPower(ThrottleUp);
        robot.FrontMotorLeft.setPower(ThrottleLeft);
        robot.FrontMotorRight.setPower(-ThrottleRight);

        // telemetry
        telemetry.addData("left", "%.2f", ThrottleLeft);
        telemetry.addData("right", "%.2f", ThrottleRight);
        telemetry.addData("up",".%.2f",ThrottleUp);

        updateTelemetry(telemetry);
    }
}