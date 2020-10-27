package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import org.firstinspires.ftc.teamcode.Helper.MoveHelper;

@TeleOp(name="TeleOpNew", group="TeleOp")
public class TeleOpNew extends OpMode{

    MoveHelper moveHelper;


    @Override
    public void init() {

        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        moveHelper.resetEncoders();
        moveHelper.runUsingEncoders();
        moveHelper.displayMoveOutputs = false;
    }

    public void init_loop() {
        if (moveHelper == null){
            moveHelper = new MoveHelper(telemetry, hardwareMap);
        }
        if (gamepad1.a){ // Left stick is cardinal moves, rotation on righton
            moveHelper.joystickJacob = true;
            telemetry.addData("Joystick setup", " Jacob's Way");
        }
        if (gamepad1.y) { // Left stick is rotation, cardinal moves on right
            moveHelper.joystickJacob = false;
            telemetry.addData("Joystick setup", " Thomas' Way");
        }
    }

    @Override
    public void loop() {

        if (!gamepad1.a && !gamepad1.y && !gamepad1.b && !gamepad1.x) {
            moveHelper.checkTeleOp(gamepad1, gamepad2);
    }
        else {
            //imuHelper.checkTeleOp(gamepad1, gamepad2);
            double LY = gamepad1.left_trigger / 2 - gamepad1.right_trigger / 2;

            if (gamepad1.a) {
                moveHelper.runBLMotor(1);
            } else {
                moveHelper.runBLMotor(0);
            }
            if (gamepad1.b) {
                moveHelper.runBRMotor(1);
            } else {
                moveHelper.runBRMotor(0);
            }
            if (gamepad1.x) {
                moveHelper.runFLMotor(1);
            } else {
                moveHelper.runFLMotor(0);
            }
            if (gamepad1.y) {
                moveHelper.runFRMotor(1);
            } else {
                moveHelper.runFRMotor(0);
            }
        }

        if (gamepad1.left_stick_button){        //Resets Encoder Values With Left Stick Button
            moveHelper.resetEncoders();
            moveHelper.runUsingEncoders();
        }

        if (gamepad1.right_stick_button){
            moveHelper.runWithoutEncoders();
        }
        moveHelper.showEncoderValues();
        //telemetry.update();
    }
    private boolean isSkyStone(int red, int green, int blue){
        double redToBlue = red / blue;
        double greenToBlue = green / blue;
        if (isCloseToBlock(red, green, blue)) {


            if (redToBlue < 2 && greenToBlue < 3) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    private boolean isCloseToBlock(int red, int green, int blue){
        double totalBlockSensorValues = red + blue + green;
        if (totalBlockSensorValues < 1500){
            return false;
        }
        return true;
    }
}