package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="CLAW and ARM TESTER", group="Linear Opmode")
public class CLAWARM_TESTER extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Hardware: Declaring all the robot parts
    public DcMotorEx horizontalLift;
    public DcMotorEx leftVerticalLift;
    public DcMotorEx rightVerticalLift;
    public Servo rightArm;
    public Servo leftArm;
    public Servo rotater;

    public boolean option;
    public Servo intakeRightArm;

    public Servo intakeLeftArm;

    public Servo sweeper;

    public Servo intakeClawRight; // THEY ARE FLIPPED

    public Servo intakeClawLeft; //THEY R FLIPPED
    public Servo armClaw;

    public Servo outtakeRotater;
    public Servo rightSusServo;

    public Servo leftSusServo;


    //declaring action mechanisms
    public ActionClass.Intake intake;
    public ActionClass.Outtake outtake;

    //Reduces speed when true
    public boolean turtleMode = false;

    //Other variables
    public static final double NORMAL_SPEED = 1;
    public static final double TURTLE_SPEED = 0.4;
    public double robotSpeed = NORMAL_SPEED;
    public double rotationSpeed = 1;
    public boolean fieldOriented = false;

    public static final double armTransferPos = 1.0;
    public static final double armOuttakePos = 0.96; //1
    public static final double armOuttakePos2 = 1.0;
    public static final double armWallPosBack = 0.04; //2

    public double armClawClose = ActionClass.Outtake.grabPos;
    public double armClawOpen = ActionClass.Outtake.openPos;
    public double armCLawHalfCLosed = ActionClass.Outtake.halfCLosed;
    public double intakeGrabPosRight = ActionClass.Intake.intakeGrabPos;
    public double intakeMovePosRight = ActionClass.Intake.intakeMovePos;
    public double intakeTransferPosRight = ActionClass.Intake.intakeTransferPos;
//    public double grabPos = ActionClass.Intake.grabPos;
//    public double halfPos = ActionClass.Intake.halfPos;
//    public double openPos = ActionClass.Intake.openPos;
    public double rotaterDefault = ActionClass.Intake.rotaterDefault;
    public double rotaterTurned = ActionClass.Intake.rotaterTurned;

    public double outtakeRotaterPickup = ActionClass.Outtake.outtakeRotaterPickup;
    public double outtakeRotaterOuttake = ActionClass.Outtake.outtakeRotaterOuttake;

//    public static boolean slidesOut = false;
//
//    public static boolean slidesIn = false;
//
//    public static boolean slidesOff = true;

    private MecanumDrive drive;
    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    private boolean runnable = true;

    /* ROBOT CONTROLS LISTED
    Gamepad1
        left joystick -> move robot
        right joystick -> turn robot
        left trigger -> vertical lift down
        right trigger -> vertical lift up
        left bumper -> rotate claw
        right bumper -> rotate claw
        y -> outtake claw close
        a -> outtake claw open
        x -> intake claw close
        b -> intake claw open
        dpad_left -> automatic extend intake
        dpad_down -> automatic bring in intake/transfer
        dpad_right -> automatic drop block on ground
        dpad_up -> automatic drop specimen

    Gamepad2
        right joystick left -> big arm to transfer position / wall intake position
        right joystick up -> big arm to first outtake position
        right joystick right -> big arm to second outtake position, behind the robot
        left joystick left -> small arm to grab position
        left joystick up -> small arm to move position
        left joystick right -> small arm to transfer position
        left trigger -> vertical lift down
        right trigger -> vertical lift up
        left bumper -> horizontal lift retract
        right bumper -> horizontal lift extend
        y -> outtake claw close
        a -> outtake claw open
        x -> intake claw close
        b -> intake claw open
        dpad_left -> automatic extend intake
        dpad_down -> automatic bring in intake/transfer
        dpad_right -> automatic drop block on ground
        dpad_up -> automatic drop specimen
     */


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
        horizontalLift.setTargetPosition(0);
        rightArm = hardwareMap.get(Servo.class, "rightArm");
        leftArm = hardwareMap.get(Servo.class, "leftArm");
        leftArm.setDirection(Servo.Direction.REVERSE);
        armClaw = hardwareMap.get(Servo.class, "armClaw");
        rotater = hardwareMap.get(Servo.class, "rotater");
        outtakeRotater = hardwareMap.get(Servo.class, "outtakeRotater");
        intakeRightArm = hardwareMap.get(Servo.class, "intakeRightArm"); //og
        intakeLeftArm = hardwareMap.get(Servo.class, "intakeLeftArm");
        intakeLeftArm.setDirection(Servo.Direction.REVERSE);
        intakeClawRight = hardwareMap.get(Servo.class, "intakeClawRight");
        intakeClawLeft = hardwareMap.get(Servo.class, "intakeClawLeft");
        intakeClawLeft.setDirection(Servo.Direction.REVERSE);
        rightSusServo = hardwareMap.get(Servo.class, "rightSusServo");
        leftSusServo = hardwareMap.get(Servo.class, "leftSusServo");
        sweeper = hardwareMap.get(Servo.class, "sweeper");
        intake = new ActionClass.Intake(hardwareMap);
        outtake = new ActionClass.Outtake(hardwareMap);

        //resetting encoders cause why not
        leftVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        horizontalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        horizontalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        horizontalLift.setTargetPosition(0);
        horizontalLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

//        horizontalLift.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, new PIDFCoefficients(0, 0, 0, 0));
    }
    @Override
    public void loop()   {
        /*
        dpad left = 0.0
        dpad up = 0.1
        dpad right = 0.2
        dpad down = 0.3
        x = 0.4
        y = 0.5
        b = 0.6
        a = 0.7
        bumper left = 0.8
        bumper right = 0.9
        trigger right = 1.0
        */

//        //for the outtake Arm
//        option = gamepad1.left_trigger > 0;
//        if (option) {
//            if (gamepad1.dpad_left) {
//                intakeClawRight.setPosition(0.0);
//            }
//            if (gamepad1.dpad_up) {
//                intakeClawRight.setPosition(0.1);
//            }
//            if (gamepad1.dpad_right) {
//                intakeClawRight.setPosition(0.2);
//            }
//            if (gamepad1.dpad_down) {
//                intakeClawRight.setPosition(0.3);
//            }
//            if (gamepad1.x) {
//                intakeClawRight.setPosition(0.4);
//            }
//            if (gamepad1.y) {
//                intakeClawRight.setPosition(0.5);
//            }
//            if (gamepad1.b) {
//                intakeClawRight.setPosition(0.6);
//            }
//            if (gamepad1.a) {
//                intakeClawRight.setPosition(0.7);
//            }
//            if (gamepad1.left_bumper) {
//                intakeClawRight.setPosition(0.8);
//            }
//            if (gamepad1.right_bumper) {
//                intakeClawRight.setPosition(0.9);
//            }
//            if (gamepad1.right_trigger > 0) {
//                intakeClawRight.setPosition(1.0);
//            }
//        }
//        else {
//            if (gamepad1.dpad_left) {
//                intakeClawLeft.setPosition(0.0);
//            }
//            if (gamepad1.dpad_up) {
//                intakeClawLeft.setPosition(0.1);
//            }
//            if (gamepad1.dpad_right) {
//                intakeClawLeft.setPosition(0.2);
//            }
//            if (gamepad1.dpad_down) {
//                intakeClawLeft.setPosition(0.3);
//            }
//            if (gamepad1.x) {
//                intakeClawLeft.setPosition(0.4);
//            }
//            if (gamepad1.y) {
//                intakeClawLeft.setPosition(0.5);
//            }
//            if (gamepad1.b) {
//                intakeClawLeft.setPosition(0.6);
//            }
//            if (gamepad1.a) {
//                intakeClawLeft.setPosition(0.7);
//            }
//            if (gamepad1.left_bumper) {
//                intakeClawLeft.setPosition(0.8);
//            }
//            if (gamepad1.right_bumper) {
//                intakeClawLeft.setPosition(0.9);
//            }
//            if (gamepad1.right_trigger > 0) {
//                intakeClawLeft.setPosition(1.0);
//            }
//        }


        //for the sweeper
            if (gamepad1.dpad_left) {
                sweeper.setPosition(0.0);
            }
            if (gamepad1.dpad_up) {
                sweeper.setPosition(0.1);
            }
            if (gamepad1.dpad_right) {
                sweeper.setPosition(0.2);
            }
            if (gamepad1.dpad_down) {
                sweeper.setPosition(0.3);
            }
            if (gamepad1.x) {
                sweeper.setPosition(0.4);
            }
            if (gamepad1.y) {
                sweeper.setPosition(0.5);
            }
            if (gamepad1.b) {
                sweeper.setPosition(0.6);
            }
            if (gamepad1.a) {
                sweeper.setPosition(0.7);
            }
            if (gamepad1.left_bumper) {
                sweeper.setPosition(0.8);
            }
            if (gamepad1.right_bumper) {
                sweeper.setPosition(0.9);
            }
            if (gamepad1.right_trigger > 0) {
                sweeper.setPosition(1.0);
            }

    }
}





