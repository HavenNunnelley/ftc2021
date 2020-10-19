package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.Helper.MoveHelper;

import java.util.List;

@Autonomous(name="ForwardTest", group="Autonomous")
public class ForwardTest extends OpMode{

    MoveHelper moveHelper;
    int state = 0;
    double lastTime;


    @Override
    public void init() {
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        //sensorRange = hardwareMap.get(DistanceSensor.class, "range");
        moveHelper.resetEncoders();
        moveHelper.runUsingEncoders();
        moveHelper.isPositionValid = true;
    }
    @Override
    public void loop() {

        switch (state) {
            case 0:
                lastTime = getRuntime();
                moveHelper.resetEncoders();
                state = 5;
                break;
            case 5:
                advanceToStateAfterTime(10,10);
                moveHelper.runMotorsToPosition(-600,-600,-600,-600);
                break;
            case 10:
                moveHelper.resetEncoders();
                state = 40;
                break;
        }


/*      telemetry.addData("Red", sensorColor.red());
        telemetry.addData("Green", sensorColor.green());
        telemetry.addData("Blue", sensorColor.blue()); */
        telemetry.addData("State", state);

        telemetry.update();
    }

    // Noticed that each case was similar, so created a procedure called advancedToStateAfterTime
    // parameters include the newState, which refers to the new value being assigned to state at end of duration
    // and duration, which refers to the amount of time before moving to the new state
    private void advanceToStateAfterTime(int newState, double duration) {

        if (getRuntime() > lastTime + duration) {
            lastTime = getRuntime();
            state = newState;
        }
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */

}
