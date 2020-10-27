package org.firstinspires.ftc.teamcode;

        import org.firstinspires.ftc.robotcore.external.ClassFactory;
        import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
        import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
        import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
        import org.firstinspires.ftc.teamcode.Helper.MoveHelper;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.vuforia.Device;

        import java.util.Base64;
        import java.util.List;

@Autonomous(name="EncoderTest", group="Autonomous")
public class EncoderTest extends OpMode{

    MoveHelper moveHelper;
    int state = 0;
    double lastTime;
    private static final String VUFORIA_KEY =
            "ATiC+z7/////AAABmXtA5vl7V0d0gQ4DVekIFZshpexpsey81tveHMKnl6UM/8RhLS9Y46sTtV8cNxkXG73m6igcTqMDrY4lOw0d9h0IvUNC2J5hf2/HfFx7ky9u8KTxh5aBkcTLUgon942jVe1udZoCjWh8k5U3c2Bg0mNiLBS93Y/KuYq9BOteOWfgY3L+igxnD0KnUwY5fhBLKlXR3wNllTnE5Wwhw+NxEzyMi/lzKKRdogcLzNoEGtZ5+pRHJ0JYWxY+n2/KLr6lx3qxz3saTxJNG45SBCUo/li/nLjNYD0oHz7dW5lzTZlLGHRt6AP8bq6lRK7xXZUTeZj/+eqAPqI8l/cM/fa4CmUQ+rqvMYfQcleIfVS8KvsP";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private String lastRecognitionType;


    @Override
    public void init() {
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        //sensorRange = hardwareMap.get(DistanceSensor.class, "range");
        moveHelper.resetEncoders();
        moveHelper.runUsingEncoders();
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }
    }
    @Override
    public void loop() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                lastRecognitionType = "";
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {

                    if (lastRecognitionType == ""){
                        lastRecognitionType = recognition.getLabel();
                    }
                   /* telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());

                    */
                }
            }
        }
        switch (state) {
            case 0:
                lastTime = getRuntime();
                moveHelper.resetEncoders();
                state = 5;
                break;
            case 5:
                advanceToStateAfterTime(10,2);
                moveHelper.runMotorsToPosition(-1100,-1100,-1100,-1100);
                break;
            case 10:
                moveHelper.resetEncoders();
                state = 40;
                break;
            case 40:
                advanceToStateAfterTime(100,4);

                if (lastRecognitionType == "Single") {
                    state = 200;
                }
                if (lastRecognitionType == "Quad") {
                    state = 300;
                }
                break;
            case 100:
                moveHelper.runMotorsToPosition(-1800,-1800,-1800,-1800);
                advanceToStateAfterTime(45,3);
                break;
            case 105:
                moveHelper.resetEncoders();
                state = 110;
                break;
            case 110:
                moveHelper.runMotorsToPosition(-200,-200,-200,-200);
                advanceToStateAfterTime(115,1);
                break;
            case 115:
                moveHelper.resetEncoders();
                state = 1050;
                break;
            case 200:
                moveHelper.runMotorsToPosition(-3000,-3000,-3000,-3000);
                advanceToStateAfterTime(205,4);
                break;
            case 205:
                moveHelper.resetEncoders();
                state = 220;
                break;
            case 220:
                moveHelper.runMotorsToPosition(-900,900,900,-900);
                advanceToStateAfterTime(225,2);
                break;
            case 225: //put wobble down, not in yet
                moveHelper.resetEncoders();
                state = 230;
                break;
            case 230:
                moveHelper.runMotorsToPosition(900,-900,-900,900);
                advanceToStateAfterTime(235,2);
                break;
            case 235:
                moveHelper.resetEncoders();
                state = 240;
                break;
            case 240:
                moveHelper.runMotorsToPosition(600,600,600,600);
                advanceToStateAfterTime(245,1);
                break;
            case 245:
                moveHelper.resetEncoders();
                state = 2050;
                break;
            case 300:
                moveHelper.runMotorsToPosition(-3800,-3800,-3800,-3800);
                advanceToStateAfterTime(305,5);
                break;
            case 305:
                moveHelper.resetEncoders();
                state = 310;
                break;
            case 310:
                moveHelper.runMotorsToPosition(1400, 1400, 1400, 1400);
                advanceToStateAfterTime(315,2);
                break;
            case 315:
                moveHelper.resetEncoders();
                state = 3050;
                break;

        }


/*      telemetry.addData("Red", sensorColor.red());
        telemetry.addData("Green", sensorColor.green());
        telemetry.addData("Blue", sensorColor.blue()); */
        telemetry.addData("State", state);
        telemetry.addData("ringdetection", lastRecognitionType);
        moveHelper.showEncoderValues();
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
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
