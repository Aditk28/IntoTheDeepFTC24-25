package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class ActionClass {

    public static class Intake{
        private DcMotorEx lift;
        private Servo rotater;
        private Servo intakeRightArm;
        private Servo intakeLeftArm;

        private Servo intakeCLawRight;

        private Servo intakeClawLeft;
        private Servo sweeper;

        private RevColorSensorV3 colorSensor;
        public static final double intakeGrabPos = 0.03;
        public static final double intakeMovePos = 0.35;
        public static final double intakeTransferPos = 0.75;

        public static final double rightMoreClose = 0.7;
        public static final double rightClosePos = 0.55;
        public static final double rightOpenPos = 0.0;

        public static final double leftMoreClose = 0.7;
        public static final double leftClosePos = 0.6;
        public static final double leftOpenPos = 0.35;
        public static final double rotaterDefault = 0.7;
        public static final double rotaterTurned = 1.0; //.35

        public static final double sweeperInPos = .8;

        public static final double sweeperOutPos = .8;




        public Intake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
            rotater = hardwareMap.get(Servo.class, "rotater");
            intakeRightArm = hardwareMap.get(Servo.class, "intakeRightArm");
            intakeLeftArm = hardwareMap.get(Servo.class, "intakeLeftArm");
            intakeLeftArm.setDirection(Servo.Direction.REVERSE);
            intakeCLawRight = hardwareMap.get(Servo.class, "intakeClawRight");
            intakeClawLeft = hardwareMap.get(Servo.class, "intakeClawLeft");
            intakeClawLeft.setDirection(Servo.Direction.REVERSE);
            sweeper = hardwareMap.get(Servo.class, "sweeper");
            colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
            colorSensor.enableLed(true);
        }

        public class sweeper implements Action{
            private double pos;

            public sweeper(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                sweeper.setPosition(pos);
                return false;
            }
        }

        public Action sweeperIn() { return new sweeper(sweeperInPos);}

        public Action sweeperOut() { return new sweeper(sweeperOutPos);}

        public class Claw implements Action{

            private double rightPos;
            private double leftPos;

            public Claw(double rightPos, double leftPos) {
                this.rightPos = rightPos;
                this.leftPos = leftPos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                intakeCLawRight.setPosition(rightPos);
                intakeClawLeft.setPosition(leftPos);
                return false;
            }
        }
        public Action closeClaw() {
            return new Claw(rightClosePos, leftClosePos);
        }
        public Action openClaw() {
            return new Claw(rightOpenPos, leftClosePos);
        }

        public class Rotater implements Action{

            private double pos;
            public Rotater(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                rotater.setPosition(pos);
                return false;
            }
        }
        public Action rotaterPos(double pos) { return new Rotater(pos); }
        public Action defaultRotater() {
            return new Rotater(rotaterDefault);
        }
        public Action turnedRotater() {
            return new Rotater(rotaterTurned);
        }

        public enum Color {
            NONE,
            RED,
            BLUE
        }
        public class LiftOut implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private int pos;
            private Color color;

            public LiftOut(int pos, Color color) {
                this.pos = pos;
                this.color = color;
            }

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // powers on motor, if it is not on
                if (!initialized) {
                    lift.setPower(.8);
                    initialized = true;
                }

                // checks lift's current position
                double currentPosition = lift.getCurrentPosition();
                packet.put("liftPos", currentPosition);

                if (currentPosition < pos) {
                    // true causes the action to rerun

                    //if a color is detected, stop
                    if (color.equals(Color.BLUE)) {
                        if (colorSensor.blue() > 500) {
                            return false;
                        }
                    }
                    else if (color.equals(Color.RED)) {
                        if (colorSensor.red() > 500) {
                            return false;
                        }
                    }

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
            return new LiftOut(pos, Color.NONE);
        }
        public Action liftOut(int pos, Color color) {
            return new LiftOut(pos, color);
        }

        public class LiftIn implements Action {
            private int pos;
            public LiftIn(int pos) {
                this.pos = pos;
            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                lift.setTargetPosition(pos);
                lift.setPower(1);
                return false;
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
                intakeRightArm.setPosition(pos);
                intakeLeftArm.setPosition(pos);
                return false;
            }
        }
        public Action moveArm(double pos) {
            return new MoveArm(pos);
        }
        public Action armMovePos() { return moveArm(intakeMovePos); }
        public Action armGrabPos() { return moveArm(intakeGrabPos); }
        public Action armTransferPos() { return moveArm(intakeTransferPos); }

    }

    public static class Outtake{
        private DcMotorEx leftVerticalLift;
        private DcMotorEx rightVerticalLift;
        private Servo claw;
        private Servo rightArm;
        private Servo leftArm;
        private Servo outtakeRotater;

        public static final double grabPos = .47; // .43
        public static final double almostClosedPos = .37; // .37
        public static final double halfCLosed = .34; // .34
        public static final double openPos = .31; // .33
        public static final double armTransferPos = .9;
        public static final double armOuttakePos = 0.6; //1
        public static final double armOuttakePos2 = .7;
        public static final double armWallPosBack = 0.2; //2

        public static final double armWallPos = .93;
        public static final double outtakeRotaterPickup = .74;
        public static final double outtakeRotaterOuttake = .16;


        public Outtake(HardwareMap hardwareMap){
            leftVerticalLift = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
            rightVerticalLift = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");
            rightVerticalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftVerticalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            claw = hardwareMap.get(Servo.class, "armClaw");
            rightArm = hardwareMap.get(Servo.class, "rightArm"); // og
            leftArm = hardwareMap.get(Servo.class, "leftArm");
            leftArm.setDirection(Servo.Direction.REVERSE);
            outtakeRotater = hardwareMap.get(Servo.class, "outtakeRotater");
        }

        public class MoveArm implements Action {
            private double pos;
            private double rotaterPos;
            public MoveArm(double pos, double rotaterPos) {
                this.pos = pos;
                this.rotaterPos = rotaterPos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightArm.setPosition(pos);
                leftArm.setPosition(pos);
                outtakeRotater.setPosition(rotaterPos);
                return false;
            }
        }

        public Action moveArm(double pos) {
            return new MoveArm(pos, outtakeRotater.getPosition());
        }
        public Action moveArm(double pos, double rotaterPos) {
            return new MoveArm(pos, rotaterPos);
        }
        public Action armTransferPos() { return moveArm(armTransferPos); }
        public Action armOuttakePos() { return moveArm(armOuttakePos, outtakeRotaterOuttake); }
        public Action armOuttakePos2() { return moveArm(armOuttakePos2, outtakeRotaterOuttake); }
        public Action armWallPosBack() { return moveArm(armWallPosBack, outtakeRotaterPickup); }
        public Action armWallPos() {
            return moveArm(armWallPos);
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
            return new PassivePower(.02);
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
        public Action closeClaw() {
            return new Claw(grabPos);
        }
        public Action almostCloseClaw() {return new Claw(almostClosedPos);}
        public Action halfClosed() {return new Claw(halfCLosed);}
        public Action openClaw() {
            return new Claw(openPos);
        }

    }

    public static class DistanceSensors {
        public  DistanceSensor distanceSensorLeft;
        public  DistanceSensor distanceSensorRight;

        public DistanceSensors(HardwareMap hardwareMap) {
            distanceSensorLeft = hardwareMap.get(DistanceSensor.class, "distanceSensorLeft");
            distanceSensorRight = hardwareMap.get(DistanceSensor.class, "distanceSensorRight");
        }

        public  double getLeftDistance() {
            return distanceSensorLeft.getDistance(DistanceUnit.INCH);
        }

        public  double getRightDistance() {
            return distanceSensorRight.getDistance(DistanceUnit.INCH);
        }
    }

//    public static class transfer {
//        Intake i;
//        Outtake o;
//
//        public transfer(HardwareMap hardwaremap) {
//             i = new Intake(hardwaremap);
//             o = new Outtake(hardwaremap);
//        }
//
//        public void doTransfer() {
//            o.armTransferPos();
//            o.openClaw();
//            i.defaultRotater();
//            i.armTransferPos();
//            i.liftIn();
//            o.liftDown();
//            o.closeClaw();
//            i.openClaw();
//        }
//    }

}