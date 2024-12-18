package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// Non-RR imports

@Config
@Autonomous(name = "Right-Side Autonomous", group = "Autonomous")
public class RightSideAutonomous extends LinearOpMode {
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

