package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Hardware: Declaring all the robot parts
    public DcMotorEx horizontalLift;
    public Servo claw;
    public Servo rotater;
    public DcMotorEx verticalLift;
    public CRServo intakeServo;

    public Servo outtakeServo;

    public Servo armServo;

//
//    public DistanceSensor distanceSensorLeft;
//
//    public DistanceSensor distanceSensorRight;

//    public double initialPositionRight;
//    public double initialPositionLeft;


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

    public double idealPosition() {
        return 0;
    }


    @Override
    public void runOpMode() throws InterruptedException {


        telemetry.addData("Status", "Robot is Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        // Most robots need the motor on one side to be reversed to drive forward - was done in Sample Mecanum Drive
        // Reverse the motor that runs backwards when connected directly to the battery
        verticalLift = hardwareMap.get(DcMotorEx.class, "verticalLift");
        horizontalLift = hardwareMap.get(DcMotorEx.class, "horizontalLift");
//        intakeServo = hardwareMap.crservo.get("intakeServo");
        claw = hardwareMap.get(Servo.class, "claw");
        rotater = hardwareMap.get(Servo.class, "rotater");
//        armServo = hardwareMap.get(Servo.class, "armServo");
        verticalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verticalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        verticalLift.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        horizontalLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

//        distanceSensorLeft = hardwareMap.get(DistanceSensor.class, "distanceSensorLeft");
//
//
//        distanceSensorRight = hardwareMap.get(DistanceSensor.class, "distanceSensorRight");
//        initialPositionLeft = leftRampServo.getPosition();
//        initialPositionRight = rightRampServo.getPosition();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            // autoposition

            // ramp servo positions

//            telemetry.addData("Left Distance Sensor", distanceSensorLeft.getDistance(DistanceUnit.INCH));
//            telemetry.addData("Right Distance Sensor", distanceSensorRight.getDistance(DistanceUnit.INCH));

            telemetry.update();



            //distance sensor
//            if (gamepad1.dpad_left) {
//
//                if (distanceSensorLeft.getDistance(DistanceUnit.INCH) >= distanceSensorRight.getDistance(DistanceUnit.INCH)) {
//                    while(distanceSensorLeft.getDistance(DistanceUnit.INCH) >= distanceSensorRight.getDistance(DistanceUnit.INCH)) {
//                        if (gamepad1.dpad_right) break;
//                        drive.setWeightedDrivePower(
//                                new Pose2d(
//                                        0,
//                                        0,
//                                        -0.2
//                                )
//                        );
//                    }
//                }
//
//                else {
//                    while(distanceSensorRight.getDistance(DistanceUnit.INCH) >= distanceSensorLeft.getDistance(DistanceUnit.INCH)) {
//                        if (gamepad1.dpad_right) break;
//                        drive.setWeightedDrivePower(
//                                new Pose2d(
//                                        0,
//                                        0,
//                                        0.2
//                                )
//                        );
//                    }
//                }
//
//                if (distanceSensorLeft.getDistance(DistanceUnit.INCH) > 8.5) {
//                    while (distanceSensorLeft.getDistance(DistanceUnit.INCH) >= 8.5) {
//                        drive.setWeightedDrivePower(
//                                new Pose2d(
//                                        0.2,
//                                        0,
//                                        0
//                                )
//                        );
//                    }
//                }
//            }



//
//                } else if (distanceSensorLeft.getDistance(DistanceUnit.INCH) > 7.559) {
//                    while (distanceSensorLeft.getDistance(DistanceUnit.INCH) >= 7.559) {
//                        drive.setWeightedDrivePower(
//                                new Pose2d(
//                                        -gamepad1.left_stick_y * robotSpeed,
//                                        -gamepad1.left_stick_x * robotSpeed,
//                                        -gamepad1.right_stick_x * robotSpeed * rotationSpeed
//                                )
//                        );
//                    }
//                }



            //intake
//            if (gamepad1.right_bumper || gamepad2.right_bumper) { //reverse intake
//                //robotSpeed = TURTLE_SPEED;
//                intakeServo.setPower(1);
//            } else if (gamepad1.left_bumper || gamepad2.left_bumper) { //intake
//                //robotSpeed = TURTLE_SPEED;
//                intakeServo.setPower(-1);
//            }
//            else {
//                //robotSpeed = NORMAL_SPEED;
//                intakeServo.setPower(0);
//            }
            //middle servo

//            //outtake
//
//            if (gamepad1.y || gamepad2.y){
//                outtake.setPower(-1);
//            } else if (gamepad1.a || gamepad2.a) {
//                outtake.setPower(1);
//            } else {
//                outtake.setPower(0);
//            }

            //verticalLift
            if (gamepad1.left_trigger != 0 && verticalLift.getCurrentPosition() >= 0) {
                verticalLift.setPower(-0.9);
            } else if (gamepad1.right_trigger != 0 && verticalLift.getCurrentPosition() <= 4200) {
                verticalLift.setPower(0.9);
            } else {
                verticalLift.setPower(0.0);
                if ((gamepad2.left_trigger == 0 && gamepad1.left_trigger == 0) && (!gamepad1.b && !gamepad2.b) && verticalLift.getCurrentPosition() >= 100) {
                    verticalLift.setPower(0.02);
                } else {
                    verticalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                }
            }

            //outtake
            if (verticalLift.getCurrentPosition() < 400 && rotater.getPosition() != intakePos) {
                rotater.setPosition(intakePos);
            } else if (verticalLift.getCurrentPosition() >= 400 && rotater.getPosition() != outtakePos){
                rotater.setPosition(outtakePos);
            }

            if (gamepad1.x) {
                claw.setPosition(closeClaw);
            }
            else if (gamepad1.b) {
                claw.setPosition(openClaw);
            }


            //horizontalLift
            if (gamepad1.dpad_up && horizontalLift.getCurrentPosition() <= 2100) {
                horizontalLift.setPower(0.9);
            } else if (gamepad1.dpad_down && horizontalLift.getCurrentPosition() > 0) {
                horizontalLift.setPower(-0.9);
            } else {
                    horizontalLift.setPower(0);
                    horizontalLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

//            //lift reset
            if ((gamepad2.b || gamepad1.b) && verticalLift.getCurrentPosition() > 0) {
                verticalLift.setPower(-0.50);
            }




            //turtleMode
            if (gamepad1.dpad_up && !turtleMode) {
                turtleMode = true;
                robotSpeed = TURTLE_SPEED;
            } else if (gamepad1.dpad_down && turtleMode) {
                turtleMode = false;
                robotSpeed = NORMAL_SPEED;
            }

            //fieldOriented toggle
//            if (gamepad1.dpad_left && !fieldOriented) {
//                drive.setPoseEstimate(new Pose2d(0,0,0));
//                fieldOriented = true;
//            } else if (gamepad1.dpad_right && fieldOriented) {
//                fieldOriented = false;
//            }


//            if (gamepad1.dpad_down || gamepad2.dpad_down) {
//                leftSuspension.setPower(0.2);
//                rightSuspension.setPower(-0.2);
//            }
//            else if (gamepad1.dpad_up || gamepad2.dpad_up) {
//                leftSuspension.setPower(-0.2);
//                rightSuspension.setPower(0.2);
//            }
//            else {
//                leftSuspension.setPower(0);
//                rightSuspension.setPower(0);
//            }


            //movement
            // Read pose
//            Pose2d poseEstimate = drive.getPoseEstimate();
//
//            // Create a vector from the gamepad x/y inputs
//            // Then, rotate that vector by the inverse of that heading
//
//            // if (d1 < 10 && d2 < 10 && d1 > 5 && d2 > 5) {
////            //    while (d1 < d2) {
//////                    moveLeftWheelForward();
////                }
////            //    while (d2 < d1) {
//////                    moveRightWheelForward();
////                }
////            }
//            Vector2d input = new Vector2d(
//                    -gamepad1.left_stick_y,
//                    -gamepad1.left_stick_x
//            ).rotated(-poseEstimate.getHeading());

            // Pass in the rotated input + right stick value for rotation
            // Rotation is not part of the rotated input thus must be passed in separately
//            if (fieldOriented) {
//                drive.setWeightedDrivePower(
//                        new Pose2d(
//                                input.getX() * robotSpeed,
//                                input.getY() * robotSpeed,
//                                -gamepad1.right_stick_x * robotSpeed * rotationSpeed
//                        )
//                );
//            }

            //else {

            drive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(gamepad1.left_stick_y * robotSpeed,
                            gamepad1.left_stick_x * robotSpeed),
                            -gamepad1.right_stick_x * robotSpeed * rotationSpeed
                    )
            );
            //}

            // Update everything. Odometry. Etc.
//            drive.update();
            drive.updatePoseEstimate();

            // Show the elapsed game time and wheel power.
//            telemetry.addData("tilt servo position", tiltServo.getPosition());
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
}





