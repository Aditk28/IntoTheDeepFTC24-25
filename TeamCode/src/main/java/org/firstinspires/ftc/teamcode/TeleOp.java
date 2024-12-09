package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOp extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Hardware: Declaring all the robot parts
    public DcMotorEx horizontalLift;
    public DcMotorEx leftVerticalLift;
    public DcMotorEx rightVerticalLift;


    //Reduces speed when true
    public boolean turtleMode = false;

    //Other variables
    public static final double NORMAL_SPEED = 1;
    public static final double TURTLE_SPEED = 0.4;
    public double robotSpeed = NORMAL_SPEED;
    public double rotationSpeed = 1;
    public boolean fieldOriented = false;

    private MecanumDrive drive;

    private boolean removeBounds = false;
    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    private boolean runnable = true;
    @Override
    public void init() {
        telemetry.addData("Status", "Robot is Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        leftVerticalLift = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
        rightVerticalLift = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");
        horizontalLift = hardwareMap.get(DcMotorEx.class, "horizontalLift");

        leftVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        horizontalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        horizontalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void loop()   {

        //actions
        TelemetryPacket packet = new TelemetryPacket();

        if (!runningActions.isEmpty()) {
            runnable = false;
        }
        else {
            runnable = true;
        }
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;

        dash.sendTelemetryPacket(packet);

        telemetry.update();

        if ((gamepad1.a)) {
//            verticalLift.
            rightVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            leftVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            runningActions.clear();
        }


        if (runnable) {
            //bounds
            if (gamepad2.dpad_right) removeBounds = true;
            if (gamepad2.dpad_left) removeBounds = false;


            //verticalLift
            if ( ((gamepad1.left_trigger != 0 && rightVerticalLift.getCurrentPosition() >= 0) || (gamepad1.left_trigger != 0 && removeBounds)) ||
            ((gamepad2.left_trigger != 0 && rightVerticalLift.getCurrentPosition() >= 0) || (gamepad2.left_trigger != 0 && removeBounds)) ){
                rightVerticalLift.setPower(0.9);
                leftVerticalLift.setPower(-.9);
            } else if (((gamepad1.right_trigger != 0 && rightVerticalLift.getCurrentPosition() <= 4200) || (gamepad1.right_trigger != 0 && removeBounds)) ||
                    ((gamepad2.right_trigger != 0 && rightVerticalLift.getCurrentPosition() <= 4200) || (gamepad2.right_trigger != 0 && removeBounds))) {
                rightVerticalLift.setPower(-0.9);
                leftVerticalLift.setPower(.9);
            } else {
                rightVerticalLift.setPower(0.0);
                leftVerticalLift.setPower(0.0);
            }

            //horizontalLift
            if ((gamepad1.dpad_up && horizontalLift.getCurrentPosition() <= 2100) || (gamepad2.dpad_up && horizontalLift.getCurrentPosition() <= 2100)) {
                horizontalLift.setPower(0.9);
            } else if ((gamepad1.dpad_down && horizontalLift.getCurrentPosition() > 0) || (gamepad2.dpad_down && horizontalLift.getCurrentPosition() > 0)) {
                horizontalLift.setPower(-0.9);
            } else {
                horizontalLift.setPower(0);
                horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
        }




        //turtleMode
        if (gamepad1.dpad_right && !turtleMode) {
            turtleMode = true;
            robotSpeed = TURTLE_SPEED;
        } else if (gamepad1.dpad_left && turtleMode) {
            turtleMode = false;
            robotSpeed = NORMAL_SPEED;
        }

        //movement
        drive.setDrivePowers(
                new PoseVelocity2d(new Vector2d(gamepad1.left_stick_y * robotSpeed,
                        gamepad1.left_stick_x * robotSpeed),
                        -gamepad1.right_stick_x * robotSpeed * rotationSpeed
                )
        );
        //}

        drive.updatePoseEstimate();

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("DriveMode: ", (turtleMode) ? ("turtleMode") : ("Normal"));
        telemetry.addData("DriveType: ", (fieldOriented) ? ("Field-Oriented Drive") : ("Robot-Oriented"));
        telemetry.addData("rightLiftMotorPosition", rightVerticalLift.getCurrentPosition());
        telemetry.addData("leftLiftMotorPosition", leftVerticalLift.getCurrentPosition());
        telemetry.addData("horizontalliftMotorPosition", horizontalLift.getCurrentPosition());


// ANKARA MESSI






    }
}





