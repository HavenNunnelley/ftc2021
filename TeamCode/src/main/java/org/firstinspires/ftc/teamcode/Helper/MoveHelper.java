package org.firstinspires.ftc.teamcode.Helper;

        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorEx;
        import com.qualcomm.robotcore.hardware.Gamepad;
        import com.qualcomm.robotcore.hardware.HardwareMap;
        import com.qualcomm.robotcore.util.Range;
        import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by WilliamsburgRobotic on 10/31/2017.
 */

public class MoveHelper extends OperationHelper {

    private double powerMultiple = .75;

    private static final double ENCODER_POWER_LEVEL = 1;
    // declares the motors; gives them names we will use to call them later
    private DcMotorEx FLMotor;
    private DcMotorEx FRMotor;
    private DcMotorEx BLMotor;
    private DcMotorEx BRMotor;
    public boolean isPositionValid;
    public double encoderPowerLevel = 1;
    public boolean joystickJacob = true;
    public boolean displayMoveOutputs = true;
    public double rangedTarget = 20;


    public MoveHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }

    public void init( ) {
        //FLMotor = hardwareMap.dcMotor.get("FL");
        //FRMotor = hardwareMap.dcMotor.get("FR");
        //BLMotor = hardwareMap.dcMotor.get("BL");
        //BRMotor = hardwareMap.dcMotor.get("BR");

        FLMotor = hardwareMap.get(DcMotorEx.class, "FL");
        FRMotor = hardwareMap.get(DcMotorEx.class, "FR");
        BLMotor = hardwareMap.get(DcMotorEx.class, "BL");
        BRMotor = hardwareMap.get(DcMotorEx.class, "BR");

        BLMotor.setDirection(DcMotor.Direction.REVERSE);
        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void SetHighPower() {
        powerMultiple = 1;
    }

    public void SetLowPower() {
        powerMultiple =.33;
    }

    public void omniDrive(double lx,double ly, double rx){
        telemetry.addData("Drive input (lx,ly): ", lx + "," + ly);
        // omni-drive math, sets it up to run properly
        double fl = ly - lx - rx;
        double fr = ly + lx + rx;
        double bl = ly + lx - rx;
        double br = ly - lx + rx;

        double max = Math.max(Math.max(fl, fr), Math.max(bl, br));
        double min = Math.min(Math.min(fl, fr), Math.min(bl, br));

        if (max > 1 || min < 1) {
            double factor = Math.max(max, Math.abs(min));
            fl = fl / factor;
            fr = fr / factor;
            bl = bl / factor;
            br = br / factor;
        }

        String output = String.format("%1$.3f,%1$.3f,%1$.3f,%1$.3f",fl,fr,bl,br);
        telemetry.addData("Driving (fl,fr,bl,br): ", output);
        // sets power to the motors
        FLMotor.setPower(fl);
        FRMotor.setPower(fr);
        BLMotor.setPower(bl);
        BRMotor.setPower(br);

    }

    public void rturn(double rx){
        // method used to turn the robot
        FLMotor.setPower(rx);
        FRMotor.setPower(-rx);
        BLMotor.setPower(rx);
        BRMotor.setPower(-rx);
    }
    public void lturn(double lx){
        // method used to turn the robot
        FLMotor.setPower(-lx);
        FRMotor.setPower(lx);
        BLMotor.setPower(-lx);
        BRMotor.setPower(lx);
    }


    // actually turns on the motors/sets power??
    public void runFLMotor (double power){
        FLMotor.setPower(power);
    }
    public void runFRMotor (double power){
        FRMotor.setPower(power);
    }
    public void runBLMotor (double power){
        BLMotor.setPower(power);
    }
    public void runBRMotor (double power){
        BRMotor.setPower(power);
    }

    public void driveForward (double power){
        telemetry.addData("moving forward", power);
        FLMotor.setPower(power);
        FRMotor.setPower(power);
        BLMotor.setPower(power);
        BRMotor.setPower(power);
    }



    public void loop(){
    }
    public void showEncoderValues (){
        telemetry.addData("BR Encoder", BRMotor.getCurrentPosition());
        telemetry.addData("BL Encoder", BLMotor.getCurrentPosition());
        telemetry.addData("FR Encoder", FRMotor.getCurrentPosition());
        telemetry.addData("FL Encoder", FLMotor.getCurrentPosition());

    }
    public void resetEncoders (){
        BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        isPositionValid = false;                                    //Is this false supposed to be a true?
    }
    public void runUsingEncoders (){
        BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithoutEncoders(){
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void moveToPosition (int position){
        FLMotor.setTargetPosition(position);
        FLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getEncoderValue(){
        return FLMotor.getCurrentPosition();

    }

    public void runOneMotor(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(encoderPowerLevel);
    }

    public void continueOneMotor(DcMotor motor){
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(encoderPowerLevel);
        telemetry.addData("Continue rangedTarget: " + motor.getDeviceName(),motor.getTargetPosition());
    }

    public void runMotorsToPosition(int flPos, int frPos, int brPos, int blPos){
        if (!isPositionValid) {               //Talk to Will about this. It broke Thursday he was gone.
            runOneMotor(FLMotor, flPos);
            runOneMotor(FRMotor, frPos);
            runOneMotor(BRMotor, brPos);
            runOneMotor(BLMotor, blPos);
            isPositionValid = true;
        }
    }
    public boolean areMotorsBusy() {
        return FLMotor.isBusy() || FRMotor.isBusy() || BRMotor.isBusy() || BLMotor.isBusy();
    }

    public void continueToPosition(){
        if (isPositionValid) {
            continueOneMotor(FLMotor);
            continueOneMotor(FRMotor);
            continueOneMotor(BRMotor);
            continueOneMotor(BLMotor);
        }
    }

    public void checkTeleOp(Gamepad gamepad1,Gamepad gamepad2){
        // alaina is struggling to find a way to describe this
        double LY;
        double LX;
        double RX;

            LY = gamepad1.left_stick_y*powerMultiple;
            LX = gamepad1.left_stick_x*powerMultiple;
            RX = gamepad1.right_stick_x*powerMultiple;

        if (gamepad1.a) {
            SetLowPower();
        } else if (gamepad1.y) {
            SetHighPower();
        }

        if (gamepad1.dpad_left) {
            runUsingEncoders();
        }
        if (gamepad1.dpad_right) {
            runWithoutEncoders();
        }


        //Establishes floating variables linked to the gamepads
        if (displayMoveOutputs) {
            telemetry.addData("Left X", LX);
            telemetry.addData("Left Y", LY);
            telemetry.addData("Right X", RX);
            telemetry.addData("BR Encoder", BRMotor.getCurrentPosition());
            telemetry.addData("BL Encoder", BLMotor.getCurrentPosition());
            telemetry.addData("FR Encoder", FRMotor.getCurrentPosition());
            telemetry.addData("FL Encoder", FLMotor.getCurrentPosition());
        }

        LY = Range.clip(LY, -1, 1);
        LX = Range.clip(LX, -1, 1);
        RX = Range.clip(RX, -1, 1);

        omniDrive(LX, LY, RX);
/*
        if (gamepad1.a) {
            BLMotor.setPower(.3);
        }
        if (gamepad1.b) {
            BRMotor.setPower(.3);
        }
        if (gamepad1.x) {
            FLMotor.setPower(.3);
        }
        if (gamepad1.y) {
            FRMotor.setPower(.3);
        }
*/

    }
    public int GetBRMotorPosition(){
        return BRMotor.getCurrentPosition();
    }
    public int GetBLMotorPosition(){
        return BLMotor.getCurrentPosition();
    }
    public int GetFRMotorPosition(){
        return FRMotor.getCurrentPosition();
    }
    public int GetFLMotorPosition(){
        return FLMotor.getCurrentPosition();
    }

    double scale = 6; //98
    double angleScale = -1;

    public void driveBySensor(double currentAngleInRadians, double distance, double ly) {

        double trueDistance = getTrueDistance(distance, currentAngleInRadians);
        double gap = (trueDistance - rangedTarget);
        if (gap < -8) { // if we are suddenly too close to the wall, assume this is the alliance robot
            gap = 0;
        }
        double heading = (gap/scale);
        if (distance > 100) {
            heading = 0;
        }
        if (ly == 0) {
            heading = heading * 2;
        }
        omniDrive( heading, ly,currentAngleInRadians*angleScale);



        //telemetry.addData("range", String.format("%.01f in", distance));
        telemetry.addData("trueDistance", String.format("%.01f", trueDistance));
        telemetry.addData("currentAngle", String.format("%.01f", currentAngleInRadians));
    }

    public double getTrueDistance(double distance, double gyroAngle) {
        return distance * Math.cos(gyroAngle);
    }

}