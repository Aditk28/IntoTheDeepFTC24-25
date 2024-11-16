package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOp extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Hardware: Declaring all the robot parts
    public DcMotorEx horizontalLift;
    public Servo claw;
    public Servo rotater;
    public DcMotorEx verticalLift;

//    public DcMotorEx suspension;
    public CRServo intakeServo;
    public Servo armServo;

    //Reduces speed when true
    public boolean turtleMode = false;

    //Other variables
    public static final double NORMAL_SPEED = 1;
    public static final double TURTLE_SPEED = 0.4;
    public double robotSpeed = NORMAL_SPEED;
    public double rotationSpeed = 1;
    public boolean fieldOriented = false;

    public double closeClaw = .42;
    public double openClaw = 0;
    public double intakePos = .5;
    public double outtakePos = .2;
    private final int outPosition = 0;
    private final double inPosition = 1;
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
        verticalLift = hardwareMap.get(DcMotorEx.class, "verticalLift");
//        suspension = hardwareMap.get(DcMotorEx.class, "perp");
        horizontalLift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
        intakeServo = hardwareMap.crservo.get("intakeServo");
        claw = hardwareMap.get(Servo.class, "claw");
        rotater = hardwareMap.get(Servo.class, "rotater");
        armServo = hardwareMap.get(Servo.class, "armServo");
        verticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        verticalLift.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        horizontalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void loop()   {

        //actions
        TelemetryPacket packet = new TelemetryPacket();

        // updated based on gamepads
        // update running actions
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

        //automating intake & transfer
        RightSideAutonomous.Intake intake = new RightSideAutonomous.Intake(hardwareMap);
        RightSideAutonomous.Claw claws = new RightSideAutonomous.Claw(hardwareMap);
        RightSideAutonomous.Outtake outtake = new RightSideAutonomous.Outtake(hardwareMap);

        if (runnable && (gamepad1.y || gamepad2.y)){
            runningActions.add(new SequentialAction(
                        new ParallelAction(
                                intake.moveArmIn(),
                                intake.liftIn(0),
                                claws.openClaw(),
                                outtake.liftDown(25)
                        ),
                    new SleepAction(1.5),
                    intake.outtaking(-1.0),
                    new SleepAction(2),
                    intake.outtaking(0.0),
                    claws.closeClaw()
                    )
            );
        }

        if (runnable && (gamepad2.a || gamepad1.a)) {
//            verticalLift.
            verticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            verticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }


        //Nugget
        if (runnable) {
            //bounds
            if (gamepad2.dpad_right) removeBounds = true;
            if (gamepad2.dpad_left) removeBounds = false;

            //verticalLift
            if ( ((gamepad1.left_trigger != 0 && verticalLift.getCurrentPosition() >= 0) || (gamepad1.left_trigger != 0 && removeBounds)) ||
            ((gamepad2.left_trigger != 0 && verticalLift.getCurrentPosition() >= 0) || (gamepad2.left_trigger != 0 && removeBounds)) ){
                verticalLift.setPower(-0.9);
            } else if (((gamepad1.right_trigger != 0 && verticalLift.getCurrentPosition() <= 4200) || (gamepad1.right_trigger != 0 && removeBounds)) ||
                    ((gamepad2.right_trigger != 0 && verticalLift.getCurrentPosition() <= 4200) || (gamepad2.right_trigger != 0 && removeBounds))) {
                verticalLift.setPower(0.9);
            } else {
                verticalLift.setPower(0.0);
                if ((gamepad2.left_trigger == 0 && gamepad1.left_trigger == 0) && (!gamepad1.b && !gamepad2.b) && verticalLift.getCurrentPosition() >= 100) {
                    verticalLift.setPower(0.03);
                } else {
                    verticalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                }
            }
            //outtake7
            if (verticalLift.getCurrentPosition() < 400 && rotater.getPosition() != intakePos) {
                rotater.setPosition(intakePos);
            } else if (verticalLift.getCurrentPosition() >= 400 && rotater.getPosition() != outtakePos){
                rotater.setPosition(outtakePos);
            }

            if (gamepad1.x || gamepad2.x) {
                claw.setPosition(closeClaw);
            }
            else if (gamepad1.b || gamepad2.b) {
                claw.setPosition(openClaw);
            }

            //arm

            if (armServo.getPosition() != outPosition && horizontalLift.getCurrentPosition() >= 400) {
                armServo.setPosition(outPosition);
            }
            if (armServo.getPosition() != inPosition && horizontalLift.getCurrentPosition() < 400) {
                armServo.setPosition(inPosition);
            }

            //intake
            if (gamepad1.right_bumper || gamepad2.right_bumper) {
                intakeServo.setPower(1);
            }
            else if (gamepad1.left_bumper || gamepad2.left_bumper) {
                intakeServo.setPower(-1);
            }
            else {
                intakeServo.setPower(0);
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

        //suspension
//
//        if (gamepad2.dpad_right) {
//            suspension.setPower(.9);
//        }
//        else if (gamepad2.dpad_left) {
//            suspension.setPower(-0.9);
//        }else {
//            suspension.setPower(0);
//        }

        //movement
        drive.setDrivePowers(
                new PoseVelocity2d(new Vector2d(-gamepad1.left_stick_y * robotSpeed,
                        -gamepad1.left_stick_x * robotSpeed),
                        -gamepad1.right_stick_x * robotSpeed * rotationSpeed
                )
        );
        //}

        drive.updatePoseEstimate();

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("DriveMode: ", (turtleMode) ? ("turtleMode") : ("Normal"));
        telemetry.addData("DriveType: ", (fieldOriented) ? ("Field-Oriented Drive") : ("Robot-Oriented"));
        telemetry.addData("liftMotorPosition", verticalLift.getCurrentPosition());
        telemetry.addData("horizontalliftMotorPosition", horizontalLift.getCurrentPosition());
        telemetry.addData("rotaterPosition", rotater.getPosition());
        telemetry.addData("clawPos", claw.getPosition());


// ANKARA MESSI






    }
}





