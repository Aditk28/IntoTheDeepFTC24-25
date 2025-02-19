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
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
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
    public DcMotorEx leftVerticalLift;
    public DcMotorEx rightVerticalLift;
    public Servo rightArm;
    public Servo leftArm;
    public Servo rotater;
    public Servo intakeRightArm;

    public Servo intakeLeftArm;

    public Servo intakeClawRight;

    public Servo intakeClawLeft;
    public Servo armClaw;

    public Servo outtakeRotater;
    public Servo rightSusServo;

    public Servo leftSusServo;


    //declaring action mechanisms
    public ActionClass.Intake intake;
    public ActionClass.Outtake outtake;

    public ActionClass.DistanceSensors distanceSensors;

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

    public static final double rightMoreClose = ActionClass.Intake.rightMoreClose;
    public static final double rightClosePos = ActionClass.Intake.rightClosePos;
    public static final double rightOpenPos = ActionClass.Intake.rightOpenPos;

    public static final double leftMoreClose = ActionClass.Intake.leftMoreClose;
    public static final double leftClosePos = ActionClass.Intake.leftClosePos;
    public static final double leftOpenPos = ActionClass.Intake.leftOpenPos;

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
        intake = new ActionClass.Intake(hardwareMap);
        outtake = new ActionClass.Outtake(hardwareMap);
        distanceSensors = new ActionClass.DistanceSensors(hardwareMap);

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
        //
        //horizontal slides

        if (gamepad2.right_bumper || gamepad1.right_trigger > 0) {
            horizontalLift.setTargetPosition(2100);
            horizontalLift.setPower(1.0);
        } else if (gamepad2.left_bumper || gamepad1.left_trigger > 0) {
            horizontalLift.setTargetPosition(0);
            horizontalLift.setPower(1.0); //try negative if this doesnt work
        } else {
            horizontalLift.setTargetPosition(0);
            horizontalLift.setPower(0);
            horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

//        if (slidesOut) {
//            horizontalLift.setTargetPosition(2100);
//            horizontalLift.setPower(0.9);
//        } else if (slidesIn) {
//            horizontalLift.setTargetPosition(0);
//            horizontalLift.setPower(0.9); //try negative if this doesnt work
//        } else if (slidesOff) {
//            horizontalLift.setTargetPosition(0);
//            horizontalLift.setPower(0);
//            horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//
//        if (gamepad2.right_bumper || gamepad1.right_trigger > 0) {
//            if (slidesIn) {
//                slidesOff = true;
//                slidesIn = false;
//                slidesOut = false;
//            }
//            else {
//                slidesOff = false;
//                slidesOut = true;
//            }
//        } else if (gamepad2.left_bumper || gamepad1.left_trigger > 0) {
//            if (slidesOut) {
//                slidesOff = true;
//                slidesIn = false;
//                slidesOut = false;
//            }
//            else {
//                slidesOff = false;
//                slidesIn = true;
//            }
//        }

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

        //RESET VERTICAL LIFT ENCODERS AND RUNNING ACTIONS, ONLY DO THIS WHEN LIFT IS DOWN
        if ((gamepad1.dpad_down)) {
            rightVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            leftVerticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftVerticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            runningActions.clear();
        }

        //these mechanisms will only work when there is no action being performed, or when "runnable"
        if (runnable) {

            //** MECHANISMS **: for mechanisms, we will use actions
            //AUTOMATIC EXTEND INTAKE
//            if (gamepad1.dpad_left || gamepad2.dpad_left) {
//                runningActions.add(new SequentialAction(
//                        intake.armMovePos(),
//                        new SleepAction(.5),
//                        new ParallelAction(
//                                intake.liftOut(1500),
//                                intake.defaultRotater()
//                        )
//                ));
//            }

            //AUTOMATIC BRING IN INTAKE, will only happen when horizontal lift is out
//            if ((gamepad1.dpad_right || gamepad2.dpad_right) && horizontalLift.getCurrentPosition() > 1300) {
//                runningActions.add(new SequentialAction(
//                        new ParallelAction(
//                                intake.armMovePos(),
//                                intake.liftIn(0)
//                        )
//                ));
//            }

//            //AUTOMATIC TRANSFER, will only happen when horizontal lift is in
//            else if ((gamepad1.dpad_right || gamepad2.dpad_right) && (horizontalLift.getCurrentPosition() < 1300)) {
//                runningActions.add(new SequentialAction(
//                        new ParallelAction(
//                                outtake.liftDown(50),
//                                outtake.openClaw(),
//                                intake.armMovePos(),
//                                intake.liftOut(400)
//                        ),
//                        new ParallelAction(
//                                intake.armTransferPos(),
//                                outtake.armTransferPos()
//                        ),
//                        new SleepAction(2),
//                        new ParallelAction(
//                                outtake.closeClaw(),
//                                new SleepAction(.5),
//                                intake.openClaw()
//                        )
//                ));
//            }

//            //AUTOMATIC GROUND OUTTAKE WHILE WALL INTAKE
//            if (gamepad2.dpad_down) {
//                runningActions.add(new SequentialAction(
//                        new ParallelAction(
//                                outtake.armTransferPos(),
//                                outtake.openClaw()
//                        ),
//                        new SleepAction(1),
//                        intake.armGrabPos(),
//                        intake.openClaw()
//                ));
//            }

//            //AUTOMATIC CLIP OUTTAKE
//            if (gamepad2.dpad_up) {
//                runningActions.add(new SequentialAction(
//                        outtake.armOuttakePos(),
//                        new SleepAction(1.5),
//                        outtake.liftDown(leftVerticalLift.getCurrentPosition() - 100),
//                        outtake.openClaw()
//                ));
//            }


            //** MANUAL CONTROLS **
            //armClaw
            if (gamepad2.a) {
                armClaw.setPosition(armClawOpen);
            }
            else if (gamepad2.y) {
                armClaw.setPosition(armClawClose);
            }

            if (gamepad1.a) {
                armClaw.setPosition(armClawOpen);
                outtakeRotater.setPosition(outtakeRotaterPickup);
                rightArm.setPosition(armWallPosBack);
            }
            else if (gamepad1.y) {
                armClaw.setPosition(armClawClose);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                outtakeRotater.setPosition(outtakeRotaterOuttake);
                rightArm.setPosition(armOuttakePos);
            }

            //rotater to preset position
            if ( gamepad1.right_bumper && rotater.getPosition() != rotaterDefault) {
                rotater.setPosition(rotaterDefault);
            }
            if ( gamepad1.left_bumper && rotater.getPosition() != rotaterTurned) {
                rotater.setPosition(rotaterTurned);
            }

            //rotater turn small amount
            if (gamepad1.dpad_left && rotater.getPosition() < .6) {
                runningActions.add(new SequentialAction(
                        intake.rotaterPos((rotater.getPosition() + .1)),
                        new SleepAction(.1)
                ));
            }
            if (gamepad1.dpad_right && rotater.getPosition() > .2) {
                runningActions.add(new SequentialAction(
                        intake.rotaterPos((rotater.getPosition() - .1)),
                        new SleepAction(.1)
                ));
            }

            //suspension
            if (gamepad2.dpad_up){
                rightSusServo.setPosition(0);
                leftSusServo.setPosition(1);
            }
            if (gamepad2.dpad_down){
                rightSusServo.setPosition(.5);
                leftSusServo.setPosition(.15);
            }


            //arm
            if (gamepad2.right_stick_y < -.85) {
                outtakeRotater.setPosition(outtakeRotaterOuttake);
                rightArm.setPosition(armOuttakePos);
                leftArm.setPosition(armOuttakePos);
            }
            else if (gamepad2.right_stick_x > .85) {
                outtakeRotater.setPosition(outtakeRotaterPickup);
                rightArm.setPosition(armOuttakePos2);
                leftArm.setPosition(armOuttakePos2);
            }
            else if (gamepad2.right_stick_y > .85) {
                outtakeRotater.setPosition(outtakeRotaterPickup);
                rightArm.setPosition(armWallPosBack);
                leftArm.setPosition(armWallPosBack);
            }
            else if (gamepad2.right_stick_x < -.85) {
                outtakeRotater.setPosition(outtakeRotaterOuttake);
                rightArm.setPosition(armTransferPos);
                leftArm.setPosition(armTransferPos);
            }

            //intakeClawRight
            if (gamepad1.x || gamepad2.x) {
                intakeClawRight.setPosition(rightMoreClose);
                intakeClawLeft.setPosition(leftMoreClose);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intakeRightArm.setPosition(intakeGrabPosRight);
                intakeLeftArm.setPosition(intakeGrabPosRight);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intakeRightArm.setPosition(intakeMovePosRight + 0.04);
                intakeLeftArm.setPosition(intakeMovePosRight + 0.04);
            }
            else if (gamepad1.b || gamepad2.b) {
                intakeClawRight.setPosition(rightOpenPos);
                intakeClawLeft.setPosition(leftOpenPos);
                intakeRightArm.setPosition(intakeMovePosRight);
                intakeLeftArm.setPosition(intakeMovePosRight);
            }

            //intakeRightArm
            if (intakeRightArm.getPosition() != intakeTransferPosRight && intakeLeftArm.getPosition() != intakeTransferPosRight && gamepad2.left_stick_x > .85) {
                intakeRightArm.setPosition(intakeTransferPosRight);
                intakeLeftArm.setPosition(intakeTransferPosRight);
            }
            else if (intakeRightArm.getPosition() != intakeMovePosRight && intakeLeftArm.getPosition() != intakeMovePosRight && gamepad2.left_stick_y < -.85) {
                intakeRightArm.setPosition(intakeMovePosRight);
                intakeLeftArm.setPosition(intakeMovePosRight);
            }
            else if (intakeRightArm.getPosition() != intakeGrabPosRight && intakeLeftArm.getPosition() != intakeGrabPosRight && gamepad2.left_stick_x < -.85) {
                intakeRightArm.setPosition(intakeGrabPosRight);
                intakeLeftArm.setPosition(intakeGrabPosRight);
            }

//            verticalLift
            if (
            ((gamepad2.left_trigger != 0)) ){
                rightVerticalLift.setPower(0.9);
                leftVerticalLift.setPower(-.9);
            } else if (
                    ((gamepad2.right_trigger != 0 && leftVerticalLift.getCurrentPosition() <= 4200) )) {
                rightVerticalLift.setPower(-0.9);
                leftVerticalLift.setPower(.9);
            }
            else if (leftVerticalLift.getCurrentPosition() > 2000) {
                rightVerticalLift.setPower(-0.02);
                leftVerticalLift.setPower(0.02);
            }
            else {
                rightVerticalLift.setPower(0.0);
                leftVerticalLift.setPower(0.0);
            }


//            if ((gamepad2.right_bumper && horizontalLift.getCurrentPosition() <= 2100)) {
//                horizontalLift.setPower(0.9);
//            } else if ((gamepad2.left_bumper && horizontalLift.getCurrentPosition() > 0)) {
//                horizontalLift.setPower(-0.9);
//            } else {
//                horizontalLift.setPower(0);
//                horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//            }
        }

        //these mechanisms can work while actions are happening

        //turtleMode
        if (gamepad1.right_stick_button && !turtleMode) {
            turtleMode = true;
            robotSpeed = TURTLE_SPEED;
        } else if (gamepad1.left_stick_button && turtleMode) {
            turtleMode = false;
            robotSpeed = NORMAL_SPEED;
        }

        //movement
        drive.setDrivePowers(
                new PoseVelocity2d(new Vector2d(-gamepad1.left_stick_y * robotSpeed,
                        -gamepad1.left_stick_x * robotSpeed),
                        -gamepad1.right_stick_x * robotSpeed * rotationSpeed
                )
        );

        drive.updatePoseEstimate();

        //telemetry data
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("DriveMode: ", (turtleMode) ? ("turtleMode") : ("Normal"));
        telemetry.addData("DriveType: ", (fieldOriented) ? ("Field-Oriented Drive") : ("Robot-Oriented"));
        telemetry.addData("rightLiftMotorPosition", rightVerticalLift.getCurrentPosition());
        telemetry.addData("leftLiftMotorPosition", leftVerticalLift.getCurrentPosition());
        telemetry.addData("horizontalliftMotorPosition", horizontalLift.getCurrentPosition());
        telemetry.addData("rightArmServoPos", rightArm.getPosition());
//        telemetry.addData("intakeClawRightPosition", intakeClawRight.getPosition());
        telemetry.addData("intakeRightArmPosition", intakeRightArm.getPosition());
        telemetry.addData("rotaterPosition", rotater.getPosition());
        telemetry.addData("gamepad2 left stick x and y: ", gamepad2.left_stick_x + " " + gamepad2.left_stick_y);
        telemetry.addData("Distance sensor left: ", distanceSensors.getLeftDistance());
        telemetry.addData("Distance sensor right: ", distanceSensors.getRightDistance());
    }
}





