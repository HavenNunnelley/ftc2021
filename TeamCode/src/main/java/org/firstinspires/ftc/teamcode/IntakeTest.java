package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="IntakeTest", group="TeleOp")
public class IntakeTest extends OpMode {
    CRServo contServo;

    @Override
    public void init() {
        contServo = hardwareMap.crservo.get("armservo");

        contServo.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void loop() {
        contServo.setPower(gamepad1.left_stick_x);

        telemetry.addData("armservo power", gamepad1.left_stick_x);
        telemetry.update();
    }
}

