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
@Autonomous(name = "BlueAutonRight", group = "Autonomous")
public class BlueAutonRight extends LinearOpMode {

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
        private Servo Claw;

        public Claw(HardwareMap hardwareMap) {
            Claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                Claw.setPosition(0.5);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                Claw.setPosition(0);
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
                .strafeToConstantHeading(new Vector2d(-23.2, -36));
        TrajectoryActionBuilder pickUpBrick = goToSubmersible.fresh()
                .strafeToLinearHeading(new Vector2d(-24,11),Math.toRadians(150));
        TrajectoryActionBuilder dropBrick = pickUpBrick.fresh()
                .strafeToLinearHeading(new Vector2d(-25,12),0);
        TrajectoryActionBuilder pickUpBrick2 = dropBrick.fresh()
                .strafeToLinearHeading(new Vector2d(-21,10),Math.toRadians(150));
        TrajectoryActionBuilder dropBrick2 = pickUpBrick2.fresh()
                .strafeToLinearHeading(new Vector2d(-25,12),0);
        TrajectoryActionBuilder pickUpClip = dropBrick2.fresh()
                .strafeToLinearHeading(new Vector2d(-1, -1),Math.toRadians(180));
        TrajectoryActionBuilder goToSubmersible2 = pickUpClip.fresh()
                .strafeToConstantHeading(new Vector2d(-24, -36));
        TrajectoryActionBuilder pickUpClip2 = goToSubmersible2.fresh()
                .strafeToLinearHeading(new Vector2d(-1, -1), Math.toRadians(180));
        TrajectoryActionBuilder goToSubmersible3 = pickUpClip2.fresh()
                .strafeToConstantHeading(new Vector2d(-24, -36));


//        while (!isStopRequested() && !opModeIsActive()) {
//            int position = visionOutputPosition;
//            telemetry.addData("Position during Init", position);
//            telemetry.update();
//        }
//        int startPosition = visionOutputPosition;
//        telemetry.addData("Starting Position", startPosition);
//        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;
        Servo rotater = rotater = hardwareMap.get(Servo.class, "rotater");
        rotater.setPosition(.5);

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                            goToSubmersible.build(),
                            outtake.liftUp(2400)
                        ),
                        outtake.liftDown(2000),
                        claw.openClaw(),
                        new ParallelAction(
                            outtake.liftDown(),
                            intake.liftOut(1000),
                            intake.moveArm(0.1),
                            intake.startIntaking(),
                            pickUpBrick.build()
                        ),
                        new SleepAction(2),
                        intake.stopIntake(),
                        dropBrick.build(),
                        intake.startOuttaking(),
                        new SleepAction(1),
                        intake.stopIntake(),
                        pickUpBrick2.build(),
                        intake.startIntaking(),
                        new SleepAction(2),
                        intake.stopIntake(),
                        dropBrick2.build(),
                        intake.startOuttaking(),
                        new SleepAction(2),
                        intake.stopIntake(),
                        new ParallelAction(
                                intake.moveArmIn(),
                                intake.liftIn(50),
                                pickUpClip.build(),
                                outtake.liftUp(500)
                        ),
                        new SleepAction(2),
                        claw.closeClaw(),
                        new SleepAction(1),
                        new ParallelAction(
                                goToSubmersible2.build(),
                                outtake.liftUp(2400)
                        ),
                        outtake.liftDown(2000),
                        claw.openClaw(),
                        new ParallelAction(
                                pickUpClip2.build(),
                                outtake.liftDown(460)
                        ),
                        new SleepAction(2),
                        claw.closeClaw(),
                        new SleepAction(1),
                        new ParallelAction(
                                goToSubmersible3.build(),
                                outtake.liftUp(2400)
                        ),
                        outtake.liftDown(2000),
                        claw.openClaw()

        ));
    }

}

