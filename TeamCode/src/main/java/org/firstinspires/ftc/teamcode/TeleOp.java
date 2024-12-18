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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    public Servo rotater;
    public Servo intakeArm;
    public Servo intakeClaw;
    public Servo armClaw;

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

    public double armTransferPos = ActionClass.Outtake.armTransferPos;
    public double armOuttakePos = ActionClass.Outtake.armOuttakePos;
    public double armOuttakePos2 = ActionClass.Outtake.armOuttakePos2;

    public double armClawClose = ActionClass.Outtake.grabPos;
    public double armClawOpen = ActionClass.Outtake.openPos;
    public double intakeGrabPosRight = ActionClass.Intake.intakeGrabPos;
    public double intakeMovePosRight = ActionClass.Intake.intakeMovePos;
    public double intakeTransferPosRight = ActionClass.Intake.intakeTransferPos;
    public double grabPos = ActionClass.Intake.grabPos;
    public double openPos = ActionClass.Intake.openPos;
    public double rotaterDefault = ActionClass.Intake.rotaterDefault;
    public double rotaterTurned = ActionClass.Intake.rotaterTurned;

    //Drive Object
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
        rightArm = hardwareMap.get(Servo.class, "rightArm");
        armClaw = hardwareMap.get(Servo.class, "armClaw");
        rotater = hardwareMap.get(Servo.class, "rotater");
        intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        intake = new ActionClass.Intake(hardwareMap);
        outtake = new ActionClass.Outtake(hardwareMap);

        //resetting encoders cause why not
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

        //RESET VERTICAL LIFT ENCODERS AND RUNNING ACTIONS, ONLY DO THIS WHEN LIFT IS DOWN
        if ((gamepad1.a)) {
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
            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                runningActions.add(new SequentialAction(
                        intake.armMovePos(),
                        new SleepAction(.5),
                        new ParallelAction(
                                intake.liftOut(1500),
                                intake.defaultRotater()
                        )
                ));
            }

            //AUTOMATIC BRING IN INTAKE, will only happen when horizontal lift is out
            if ((gamepad1.dpad_right || gamepad2.dpad_right) && horizontalLift.getCurrentPosition() > 1300) {
                runningActions.add(new SequentialAction(
                        new ParallelAction(
                                intake.armMovePos(),
                                intake.liftIn(0)
                        )
                ));
            }

            //AUTOMATIC TRANSFER, will only happen when horizontal lift is in
            else if ((gamepad1.dpad_right || gamepad2.dpad_right) && (horizontalLift.getCurrentPosition() < 1300)) {
                runningActions.add(new SequentialAction(
                        new ParallelAction(
                                outtake.liftDown(50),
                                outtake.openClaw(),
                                intake.armMovePos(),
                                intake.liftOut(1150)
                        ),
                        new ParallelAction(
                                intake.armTransferPos(),
                                outtake.armTransferPos()
                        ),
                        new SleepAction(2),
                        new ParallelAction(
                                outtake.closeClaw(),
                                new SleepAction(.5),
                                intake.openClaw()
                        )
                ));
            }

            //AUTOMATIC GROUND OUTTAKE WHILE WALL INTAKE
            if (gamepad1.dpad_down || gamepad2.dpad_down) {
                runningActions.add(new SequentialAction(
                        new ParallelAction(
                                outtake.armTransferPos(),
                                outtake.openClaw()
                        ),
                        new SleepAction(1),
                        intake.armGrabPos(),
                        intake.openClaw()
                ));
            }

            //AUTOMATIC CLIP OUTTAKE
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                runningActions.add(new SequentialAction(
                        outtake.armOuttakePos(),
                        new SleepAction(1.5),
                        outtake.liftDown(leftVerticalLift.getCurrentPosition() - 100),
                        outtake.openClaw()
                ));
            }


            //** MANUAL CONTROLS **
            //armClaw
            if (gamepad2.a) {
                armClaw.setPosition(armClawOpen);
            }
            else if (gamepad2.y) {
                armClaw.setPosition(armClawClose);
            }

            //rotater
            if (gamepad1.right_bumper && rotater.getPosition() < .6) {
                runningActions.add(new SequentialAction(
                        intake.rotaterPos((rotater.getPosition() + .1)),
                        new SleepAction(.1)
                ));
            }
            if (gamepad1.left_bumper && rotater.getPosition() > .2) {
                runningActions.add(new SequentialAction(
                        intake.rotaterPos((rotater.getPosition() - .1)),
                        new SleepAction(.1)
                ));
            }

            //arm
            if (gamepad2.right_stick_y < -.85) {
                rightArm.setPosition(armOuttakePos);
            }
            else if (gamepad2.right_stick_x > .85) {
                rightArm.setPosition(armOuttakePos2);
            }
            else if (gamepad2.right_stick_x < -.85) {
                rightArm.setPosition(armTransferPos);
            }

            //intakeClaw
            if (gamepad1.x || gamepad2.x) {
                intakeClaw.setPosition(grabPos);
                intakeArm.setPosition(intakeGrabPosRight);
            }
            else if (gamepad1.b || gamepad2.b) {
                intakeClaw.setPosition(openPos);
                intakeArm.setPosition(intakeMovePosRight);
            }

            //intakeArm
            if (intakeArm.getPosition() != intakeTransferPosRight && gamepad2.left_stick_x > .85) {
                intakeArm.setPosition(intakeTransferPosRight);
                rotater.setPosition(rotaterTurned);
            }
            else if (intakeArm.getPosition() != intakeMovePosRight && gamepad2.left_stick_y < -.85) {
                intakeArm.setPosition(intakeMovePosRight);
                rotater.setPosition(rotaterDefault);
            }
            else if (intakeArm.getPosition() != intakeGrabPosRight && gamepad2.left_stick_x < -.85) {
                intakeArm.setPosition(intakeGrabPosRight);
                rotater.setPosition(rotaterDefault);
            }

            //verticalLift
            if ( ((gamepad1.left_trigger != 0 && leftVerticalLift.getCurrentPosition() >= 0)) ||
            ((gamepad2.left_trigger != 0 && leftVerticalLift.getCurrentPosition() >= 0)) ){
                rightVerticalLift.setPower(0.9);
                leftVerticalLift.setPower(-.9);
            } else if (((gamepad1.right_trigger != 0 && leftVerticalLift.getCurrentPosition() <= 4200)) ||
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

            //horizontalLift
            if ((gamepad2.right_bumper && horizontalLift.getCurrentPosition() <= 2100)) {
                horizontalLift.setPower(0.9);
            } else if ( (gamepad2.left_bumper && horizontalLift.getCurrentPosition() > 0)) {
                horizontalLift.setPower(-0.9);
            } else {
                horizontalLift.setPower(0);
                horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
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
        telemetry.addData("armClawPos", armClaw.getPosition());
        telemetry.addData("intakeArmPosition", intakeArm.getPosition());
        telemetry.addData("gamepad2 left stick x and y: ", gamepad2.left_stick_x + " " + gamepad2.left_stick_y);

    }
}





