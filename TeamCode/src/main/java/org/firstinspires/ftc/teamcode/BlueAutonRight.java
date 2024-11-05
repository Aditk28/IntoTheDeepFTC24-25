package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


// Non-RR imports

@Config
@Autonomous(name = "BLUE_TEST_AUTO_PIXEL", group = "Autonomous")
public class BlueAutonRight extends LinearOpMode {

    public class Intake{
        private DcMotorEx lift;
        private Servo arm;

        public Intake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "horizontaLift");
            arm = hardwareMap.get(Servo.class, "intakeServo");
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
            return new LiftOut(1000);
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

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 50.0) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }
        }

        public Action liftIn() {
            return new LiftIn(1000);
        }

        //NEED TO CODE AXON SERVO

    }

    public class Outtake{
        private DcMotorEx lift;
        private Servo claw;

        public Outtake(HardwareMap hardwareMap){
            lift = hardwareMap.get(DcMotorEx.class, "verticalLift");
            claw = hardwareMap.get(Servo.class, "outtakeServo");
        }

        public class LiftUp implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;
            private int pos;

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
                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 2100.0) {
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
            return new LiftUp(1000);
        }

        public class LiftDown implements Action {
            private boolean initialized = false;
            private int pos;
            public LiftDown(int pos) {
                this.pos = pos;
            }

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(-0.8);
                    initialized = true;
                }

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }
        }

        public Action liftDown() {
            return new LiftDown(1000);
        }

    }

    public class rotateClaw {
        private Servo rotateClaw;

        public rotateClaw(HardwareMap hardwareMap) {
            rotateClaw = hardwareMap.get(Servo.class, "claw");
        }

        public class RotateIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotateClaw.setPosition(0.0);
                return false;
            }
        }
        public Action rotateIntake() {
            return new RotateIntake();
        }

        public class RotateOuttake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotateClaw.setPosition(1.0);
                return false;
            }
        }
        public Action rotateOuttake() {
            return new RotateOuttake();
        }
    }

    public class Claw {
        private Servo Claw;

        public Claw(HardwareMap hardwareMap) {
            Claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                Claw.setPosition(0.3);
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
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        // make a Claw instance
        Claw claw = new Claw(hardwareMap);
        // make a Lift instance
        Intake hlift = new Intake(hardwareMap);
        Outtake vlift = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);


    }

}

