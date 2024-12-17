package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


// Non-RR imports

@Config
@Autonomous(name = "Right-Side Autonomous", group = "Autonomous")
public class RightSideAutonomous extends LinearOpMode {

    public static class Intake{
        private DcMotorEx lift;
        private Servo rotater;
        private Servo rightIntakeArm;
        private Servo leftIntakeArm;
        private Servo intakeClaw;
        private Servo armClaw;

        private final double intakeGrabPosRight = 0.15;
        private final double intakeMovePosRight = 0.3;
        public final double intakeTransferPosRight = 0.7;
        public final double intakeGrabPosLeft = .65;
        public final double intakeMovePosLeft = .5;
        public final double intakeTransferPosLeft = 0.2;
        public final double grabPos = .5;
        public final double openPos = .7;

        public Intake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
            armClaw = hardwareMap.get(Servo.class, "armClaw");
            rotater = hardwareMap.get(Servo.class, "rotater");
            rightIntakeArm = hardwareMap.get(Servo.class, "rightIntakeArm");
            leftIntakeArm = hardwareMap.get(Servo.class, "leftIntakeArm");
            intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        }

        public class Claw implements Action{

            private double pos;
            public Claw(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                intakeClaw.setPosition(pos);
                return false;
            }
        }
        public Action closeClaw(double pos) {
            return new Claw(grabPos);
        }
        public Action openClaw(double pos) {
            return new Claw(openPos);
        }

        public class LiftOut implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private int pos;
            public LiftOut(int pos) {
                this.pos = pos;
            }

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // powers on motor, if it is not on
                if (!initialized) {
                    lift.setPower(0.8);
                    initialized = true;
                }

                // checks lift's current position
                double currentPosition = lift.getCurrentPosition();
                packet.put("liftPos", currentPosition);

                if (currentPosition < pos) {
                    // true causes the action to rerun
                    return true;
                } else {
                    // false stops action rerun
                    lift.setPower(0);
                    return false;
                }
                // overall, the action powers the lift until it surpasses
                // 3000 encoder ticks, then powers it off
            }
        }

        public Action liftOut() {
            return liftOut(1000);

        }
        public Action liftOut(int pos) {
            return new LiftOut(pos);
        }

        public class LiftIn implements Action {
            private boolean initialized = false;
            private int pos;
            public LiftIn(int pos) {
                this.pos = pos;
            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(-0.8);
                    initialized = true;
                }

                double currentPosition = lift.getCurrentPosition();
                packet.put("liftPos", currentPosition);

                if (currentPosition > pos) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }
        }


        public Action liftIn(int pos) {
            return new LiftIn(pos);
        }
        public Action liftIn() {
            return liftIn(50);
        }



        public class MoveArm implements Action {
            private double pos;
            public MoveArm(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightIntakeArm.setPosition(pos);
                return false;
            }
        }
        public Action moveArm(double pos) {
            return new MoveArm(pos);
        }

        public class Rotater implements Action {
            private double pos;
            public Rotater(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotater.setPosition(pos);
                return false;
            }
        }
        public Action rotater(double pos) {
            return new Rotater(pos);
        }
    }

    public static class Outtake{
        private DcMotorEx leftVerticalLift;
        private DcMotorEx rightVerticalLift;
        private Servo claw;
        private Servo rightArm;

        public final double grabPos = 1;
        public final double openPos = 0.7;

        public Outtake(HardwareMap hardwareMap){
            leftVerticalLift = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
            rightVerticalLift = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");
            claw = hardwareMap.get(Servo.class, "armClaw");
            rightArm = hardwareMap.get(Servo.class, "rightArm");
        }

        public class PassivePower implements Action{

            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private double pow;
            public PassivePower(double pow) {
                this.pow = pow;
            }

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightVerticalLift.setPower(-pow);
                leftVerticalLift.setPower(pow);
                return false;
            }
        }

        public Action passivePowerOn() {
            return new PassivePower(.05);
        }
        public Action passivePowerOff() {
            return new PassivePower(0);
        }

        public class LiftUp implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private int pos;
            private final double outtakePos = .2;

            public LiftUp(int pos) {
                this.pos = pos;
            }

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // powers on motor, if it is not on
                if (!initialized) {
                    rightVerticalLift.setPower(-0.9);
                    leftVerticalLift.setPower(0.9);
                    initialized = true;
                }

                // checks lift's current position
                double currentPosition = leftVerticalLift.getCurrentPosition();
                packet.put("liftPos", currentPosition);
                if (currentPosition < pos) {
                    // true causes the action to rerun
                    return true;
                } else {
                    // false stops action rerun
                    leftVerticalLift.setPower(0);
                    rightVerticalLift.setPower(0);
                    return false;
                }
                // overall, the action powers the lift until it surpasses
                // 3000 encoder ticks, then powers it off
            }
        }

        public Action liftUp() {
            return liftUp(3000);
        }
        public Action liftUp(int pos) {
            return new LiftUp(pos);
        }

        public class LiftDown implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private int pos;
            private final double outtakePos = .2;

            public LiftDown(int pos) {
                this.pos = pos;
            }

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // powers on motor, if it is not on
                if (!initialized) {
                    rightVerticalLift.setPower(0.9);
                    leftVerticalLift.setPower(-0.9);
                    initialized = true;
                }

                // checks lift's current position
                double currentPosition = leftVerticalLift.getCurrentPosition();
                packet.put("liftPos", currentPosition);
                if (currentPosition > pos) {
                    // true causes the action to rerun
                    return true;
                } else {
                    // false stops action rerun
                    leftVerticalLift.setPower(0);
                    rightVerticalLift.setPower(0);
                    return false;
                }
            }
        }

        public Action liftDown(int pos) {
            return new LiftDown(pos);
        }
        public Action liftDown() {
            return liftDown(100);
        }

        public class Claw implements Action{

            private double pos;
            public Claw(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                claw.setPosition(pos);
                return false;
            }
        }
        public Action closeClaw(double pos) {
            return new Claw(grabPos);
        }
        public Action openClaw(double pos) {
            return new Claw(openPos);
        }

    }


    @Override
    public void runOpMode() throws InterruptedException {
        // instantiate your MecanumDrive at a particular pose.
//        Pose2d initialPose = new Pose2d(0, -24, 0);
//        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
//        // make a Claw instance
//        Claw claw = new Claw(hardwareMap);
//        // make a Lift instance
//        Intake intake = new Intake(hardwareMap);
//
//        Outtake outtake = new Outtake(hardwareMap);
//
//        TrajectoryActionBuilder goToSubmersible = drive.actionBuilder(initialPose)
//                .strafeToConstantHeading(new Vector2d(-29, -24))
//                .waitSeconds(.5);
//        TrajectoryActionBuilder pushBricks = goToSubmersible.fresh()
//                .strafeToConstantHeading(new Vector2d(-22,2))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-52, 2))
////                .waitSeconds(.5)
//                .turn(Math.toRadians(187))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-52, 15))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-5, 15))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-52, 15))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-52, 25))
////                .waitSeconds(.5)
//                .strafeToConstantHeading(new Vector2d(-5, 25))
//                .strafeToConstantHeading(new Vector2d(-15, 15))
//                .strafeToConstantHeading(new Vector2d(-.2, 15))
////        TrajectoryActionBuilder pickUpClip = pushBricks.fresh()
//                .waitSeconds(.2);
//        TrajectoryActionBuilder goToSubmersible2 = pushBricks.fresh()
//                .strafeToLinearHeading(new Vector2d(-30, -30), Math.toRadians(6))
//                .waitSeconds(.5);
//        TrajectoryActionBuilder pickUpClip2 = goToSubmersible2.fresh()
//                .strafeToLinearHeading(new Vector2d(-2, 1), Math.toRadians(180))
//                .waitSeconds(.5);
//        TrajectoryActionBuilder goToSubmersible3 = pickUpClip2.fresh()
//                .strafeToLinearHeading(new Vector2d(-26.5, -36), 0)
//                .waitSeconds(.5);
//        TrajectoryActionBuilder goToParkSubmersible = goToSubmersible3.fresh()
//                .strafeToConstantHeading(new Vector2d(0, 0))
//                .waitSeconds(.5)
//                ;
//
//                waitForStart();
//        if (isStopRequested()) return;
//        Servo rotater = rotater = hardwareMap.get(Servo.class, "rotater");
//        rotater.setPosition(.5);
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelAction(
//                            new SequentialAction(
//                                    new SleepAction(.7),
//                                    goToSubmersible.build()
//                            ),
//                            new SequentialAction(
//                                    outtake.liftUp(2050),
//                                    outtake.passivePowerOn()
//                            )
//                        ),
//                        new SleepAction(0.1),
//                        outtake.liftDown(1890),
//                        outtake.passivePowerOff(),
//                        new SleepAction(.2),
//                        claw.openClaw(),
//                        new SleepAction(0.2),
//
//                        new ParallelAction(
//                                new SequentialAction(
//                                        outtake.liftDown(430),
//                                        outtake.passivePowerOn()
//                                ),
//                                pushBricks.build()
//                        ),
//                        new SleepAction(.5),
//                        claw.closeClaw(),
//                        outtake.passivePowerOff(),
//                        new SleepAction(.5),
//                        new ParallelAction(
//                                goToSubmersible2.build(),
//                                new SequentialAction(
//                                        outtake.liftUp(2200),
//                                        outtake.passivePowerOn()
//                                 )
//                        ),
//                        new SleepAction(0.5),
//
//                        outtake.liftDown(1900),
//                        outtake.passivePowerOff(),
//                        new SleepAction(.1),
//                        claw.openClaw(),
//                        outtake.liftDown(0)

//                        new ParallelAction(
//                                pickUpClip2.build(),
//                                outtake.liftDown(450)
//                        ),
//                        new SleepAction(1),
//                        claw.closeClaw(),
//                        new SleepAction(1),
//                        new ParallelAction(
//                                goToSubmersible3.build(),
//                                new SequentialAction(
//                                        outtake.liftUp(2000),
//                                        outtake.passivePowerOn()
//                                )
//                        ),
//                        new SleepAction(0.5),
//                        outtake.liftDown(1800),
//                        claw.openClaw()

//        ));
    }

}

