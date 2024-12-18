package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class ActionClass {

    public static class Intake{
        private DcMotorEx lift;
        private Servo rotater;
        private Servo rightIntakeArm;
        private Servo leftIntakeArm;
        private Servo intakeClaw;
        private Servo armClaw;

        public static final double intakeGrabPos = 0.15;
        public static final double intakeMovePos = 0.3;
        public static final double intakeTransferPos = 0.7;
        public static final double grabPos = .7;
        public static final double openPos = .5;
        public static final double rotaterDefault = .2;
        public static final double rotaterTurned = 0.52;


        public Intake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
            armClaw = hardwareMap.get(Servo.class, "armClaw");
            rotater = hardwareMap.get(Servo.class, "rotater");
            rightIntakeArm = hardwareMap.get(Servo.class, "intakeArm");
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
        public Action closeClaw() {
            return new Claw(grabPos);
        }
        public Action openClaw() {
            return new Claw(openPos);
        }

        public class Rotater implements Action{

            private double pos;
            public Rotater(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                intakeClaw.setPosition(pos);
                return false;
            }
        }
        public Action defaultRotater() {
            return new Rotater(rotaterDefault);
        }
        public Action turnedRotater() {
            return new Rotater(rotaterTurned);
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
        public Action armMovePos() { return moveArm(intakeMovePos); }
        public Action armGrabPos() { return moveArm(intakeGrabPos); }
        public Action armTransferPos() { return moveArm(intakeTransferPos); }

    }

    public static class Outtake{
        private DcMotorEx leftVerticalLift;
        private DcMotorEx rightVerticalLift;
        private Servo claw;
        private Servo rightArm;

        public static final double grabPos = 1;
        public static final double openPos = 0.7;
        public static final double armTransferPos = .89;
        public static final double armOuttakePos = .75;
        public static final double armOuttakePos2 = .5;

        public Outtake(HardwareMap hardwareMap){
            leftVerticalLift = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
            rightVerticalLift = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");
            claw = hardwareMap.get(Servo.class, "armClaw");
            rightArm = hardwareMap.get(Servo.class, "rightArm");
        }

        public class MoveArm implements Action {
            private double pos;
            public MoveArm(double pos) {
                this.pos = pos;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightArm.setPosition(pos);
                return false;
            }
        }
        public Action moveArm(double pos) {
            return new MoveArm(pos);
        }
        public Action armTransferPos() { return moveArm(armTransferPos); }
        public Action armOuttakePos() { return moveArm(armOuttakePos); }
        public Action armOuttakePos2() { return moveArm(armOuttakePos2); }

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
        public Action closeClaw() {
            return new Claw(grabPos);
        }
        public Action openClaw() {
            return new Claw(openPos);
        }

    }

}

