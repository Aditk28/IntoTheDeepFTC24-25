package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


// Non-RR imports

@Config
@Autonomous(name = "Left-Side Autonomous", group = "Autonomous")
public class LeftSideAutonomous extends LinearOpMode {

    public static class Intake{
        private DcMotorEx lift;
        private Servo arm;

        private CRServo intakeServo;
        final double out = 0;
        final double in = 1;
        //   private Servo arm;


        public Intake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
            arm = hardwareMap.get(Servo.class, "armServo");
            intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        }

        public class Outtaking implements Action{
            private double pow;
            public Outtaking(double pow) {
                this.pow = pow;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                intakeServo.setPower(pow);
                return false;

            }
        }
        public Action outtaking(double pow) {
            return new Outtaking(pow);
        }
        public Action startIntaking() { return outtaking(1); }
        public Action startOuttaking() { return outtaking(-1); }
        public Action stopIntake() { return outtaking(0); }

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
                arm.setPosition(pos);
                return false;
            }
        }


        public Action moveArm(double pos) {
            return new MoveArm(pos);
        }
        public Action moveArmOut() { return moveArm(0); }
        public Action moveArmIn() { return moveArm(1); }
    }

    public static class Outtake{
        private DcMotorEx lift;
        private Servo claw;
        private Servo rotater;

        public Outtake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "verticalLift");
            claw = hardwareMap.get(Servo.class, "claw");
            rotater = hardwareMap.get(Servo.class, "rotater");
            lift.setDirection(DcMotorSimple.Direction.REVERSE);
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
                lift.setPower(pow);
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
                    lift.setPower(0.8);
                    initialized = true;
                }

                // checks lift's current position
                double currentPosition = lift.getCurrentPosition();
                packet.put("liftPos", currentPosition);
                if (rotater.getPosition() != outtakePos && currentPosition > 450) {
                    rotater.setPosition(outtakePos);
                }
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

        public Action liftUp() {
            return liftUp(3000);
        }
        public Action liftUp(int pos) {
            return new LiftUp(pos);
        }

        public class LiftDown implements Action {
            private boolean initialized = false;
            private int pos;
            private final double intakePos = .5;
            public LiftDown(int pos) {
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
                if (rotater.getPosition() != intakePos && currentPosition < 450) {
                    rotater.setPosition(intakePos);
                }
                if (currentPosition > pos) {
                    return true;
                } else {
                    lift.setPower(0);
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

    }


    public static class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.5);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {
        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        // make a Claw instance
        Claw claw = new Claw(hardwareMap);
        // make a Lift instance
        Intake intake = new Intake(hardwareMap);

        Outtake outtake = new Outtake(hardwareMap);

        TrajectoryActionBuilder goToSubmersible = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(-27, 36))
                .waitSeconds(.5);
        TrajectoryActionBuilder goToParkSubmersible = goToSubmersible.fresh()
                .strafeToConstantHeading(new Vector2d(-27, -3.25))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(-51, -3.25))
                .waitSeconds(.5)
                .strafeToLinearHeading(new Vector2d(-51, 0), Math.toRadians(90))
                .waitSeconds(.5)
                ;

        waitForStart();
        if (isStopRequested()) return;
        Servo rotater = rotater = hardwareMap.get(Servo.class, "rotater");
        rotater.setPosition(.5);

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                goToSubmersible.build(),
                                new SequentialAction(
                                        outtake.liftUp(2000),
                                        outtake.passivePowerOn()
                                )
                        ),
                        new SleepAction(0.3),
                        outtake.liftDown(1800),
                        outtake.passivePowerOff(),
                        new SleepAction(.3),
                        claw.openClaw(),
                        new SleepAction(0.5),
                        new ParallelAction(
                                new ParallelAction(
                                        outtake.liftDown(0),
                                        claw.openClaw()
                                ),
                                goToParkSubmersible.build()
                        )

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

                ));
    }

}

