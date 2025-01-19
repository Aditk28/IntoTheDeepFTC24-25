package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "Right-Side Autonomous", group = "Autonomous")
public class RightSideAutonomous extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ActionClass.Intake intake = new ActionClass.Intake(hardwareMap);
        ActionClass.Outtake outtake = new ActionClass.Outtake(hardwareMap);
        Servo leftSusServo = leftSusServo = hardwareMap.get(Servo.class, "leftSusServo");

        Actions.runBlocking(outtake.closeClaw()); // ROBOT MOVES ON INITIALIZATION
        leftSusServo.setPosition(.15);

        TrajectoryActionBuilder auto = drive.actionBuilder(initialPose)

                //move lift up and arm to outtake position while going there
                .afterTime(0, new ParallelAction(
                        outtake.armOuttakePos(),
                        new SequentialAction(
                                //lift up value is 200
                                outtake.liftUp(230)
                                //does NOT use passive power, might need to add depending on testing
                        )
                ))

                //goToSubmersible with preLoaded clip
                .splineToConstantHeading(new Vector2d(34.5, 6), 0)

                //after reaching, move lift down and open the claw
                .afterTime(0, new SequentialAction(
                        outtake.openClaw(),
//                        outtake.liftDown(0),
                        new SleepAction(.15)
                        //outtake.closeClaw()
                ))
                //.waitSeconds(1)

                //reset position
                .strafeToSplineHeading(new Vector2d(27, 0), Math.toRadians(6))
                .splineToConstantHeading(new Vector2d(27, -20), 0)
                .splineToConstantHeading(new Vector2d(31, -27), 0)
//                .strafeToConstantHeading(new Vector2d(10, 0))
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(8)), Math.toRadians(90))

                //bring back first brick

                .splineToConstantHeading(new Vector2d(48, -27   ), 0)
                .splineToConstantHeading(new Vector2d(48, -35.5), 0)
                .splineToConstantHeading(new Vector2d(10, -35.5), 0)

                //bring back second brick
                .splineToConstantHeading(new Vector2d(50, -38.75), 0)
                .splineToConstantHeading(new Vector2d(50, -49), 0)
                .splineToConstantHeading(new Vector2d(17, -49), 0)

                //bring back third brick
                .splineToLinearHeading(new Pose2d(55, -45, Math.toRadians(12)), 0)
                .splineToConstantHeading(new Vector2d(56, -47.5), 0)
                .splineToConstantHeading(new Vector2d(55, -53), 0)
                .splineToConstantHeading(new Vector2d(12, -53), 0)


                //reset position while moving arm to the intake position
                .afterTime(0, new ParallelAction(
                        outtake.armWallPosBack(),
                        outtake.openClaw(),
                        outtake.liftDown(10
                        ))
                )
                .strafeToConstantHeading(new Vector2d(10, -32))

                //go to pickup the specimen
//                .splineToConstantHeading(new Vector2d(4.2, -34), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(5, -34), 0)

                //after reaching, close the claw picking up first clip
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))
                .waitSeconds(.27)

                // then move the arm to the outtake position and raise lift while robot starts moving
                .afterTime(0.15, new SequentialAction(
                                //lift up value is 200
                                outtake.armOuttakePos(),
                                outtake.liftUp(250)
                ))

                //go to submersible with the first picked up specimen
                .splineToLinearHeading(new Pose2d(34.5, 5, Math.toRadians(-3)), 0)

                //open claw after specimen has been placed
                .afterTime(0, new SequentialAction(
//                        outtake.liftUp(2500),
                        outtake.openClaw()
                ))
                .waitSeconds(.1)

                //move then start the arm turn
//                .setReversed(true)
                //this is the arm turning while moving
                .afterTime(.1, new ParallelAction(
                        new SequentialAction(
                                outtake.armTransferPos(),
                                new SleepAction(.1),
                                outtake.armWallPosBack()
                        ),

                        outtake.liftDown(10)
                ))
//                .splineToConstantHeading(new Vector2d(15, 3), 0)
//                //go to pick up the second clip
////                .splineToConstantHeading(new Vector2d(25, 3), 0)
//                .splineToConstantHeading(new Vector2d(9, -34), 0)
//                .splineToConstantHeading(new Vector2d(5, -34), 0)
//                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(3.9, -37), Math.toRadians(2))

                //after reaching, close the claw picking up second clip
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))
                .waitSeconds(.2)
                // then move the arm to the outtake position and raise lift while robot starts moving
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos(),
                        outtake.liftUp(270)
                ))

                //go to submersible with the second picked up clip
                .splineToLinearHeading(new Pose2d(35, 3, Math.toRadians(-4)), 0)

                //open the claw
                .afterTime(0, new SequentialAction(
                        outtake.openClaw()
                ))

                //go back to pick up the third clip
                //.setReversed(true)
                .afterTime(0.1, new ParallelAction(
                        new SequentialAction(
                                outtake.armTransferPos(),
                                new SleepAction(.3),
                                outtake.armWallPosBack()
                        ),
                        outtake.liftDown(20)
                ))
//                .splineToConstantHeading(new Vector2d(23, 3), 0)
//
//
//
//
//                //pick up third clip
//                .splineToConstantHeading(new Vector2d(9, -34), 0)
//                .splineToConstantHeading(new Vector2d(5, -34), 0)
                .strafeToLinearHeading(new Vector2d(3.5, -35), Math.toRadians(2))
                //.setReversed(false)

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))
                .waitSeconds(.18)

                // then move the arm to the outtake position and raise lift while robot starts moving
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos(),
                        outtake.liftUp(275)
                ))

                //go to submersible with the third picked up clip
                .splineToLinearHeading(new Pose2d(35, 2.5, Math.toRadians(0)), 0)
                .afterTime(0, new SequentialAction(
                        outtake.openClaw()
                ))
                .waitSeconds(.3);



//                //THIS IS 5TH CLIP, EXPERIMENTAL
//
//                //open the claw
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //go back to pick up the fourth picked up clip
//                .splineToConstantHeading(new Vector2d(23, 3), 0)
//
//                //then move arm to intake position and lower lift while movement is happening
//                .afterTime(.1, new SequentialAction(
//                        outtake.armWallPosBack(),
//                        outtake.liftDown(50)
//                ))
//
//                //pick up fourth clip
//                .splineToConstantHeading(new Vector2d(4.6, -34), 0)
//                //.setReversed(false)
//
//                //after reaching, close the claw and move the arm to the outtake position
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw(),
//                        new SleepAction(.1)
//                ))
//
//                // then move the arm to the outtake position and raise lift while robot starts moving
//                .afterTime(0, new SequentialAction(
//                        outtake.armOuttakePos(),
//                        outtake.liftUp(250)
//                ))
//
//                //go to submersible with the fourth picked up clip
//                .splineToLinearHeading(new Pose2d(35, 7, Math.toRadians(4)), 0)
//
//                //open the claw
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ));

//                .afterTime(0, new SequentialAction(
////                        outtake.liftUp(2500),
//                        new SleepAction(.5),
//                        outtake.openClaw(),
//                        outtake.armOuttakePos2(),
//                        outtake.liftDown(50),
//                        intake.armMovePos()
//                ));

        waitForStart();
        if (isStopRequested()) return;

        //AUTONOMOUS START
        //run the autonomous
        Actions.runBlocking(
                auto.build()
        );
    }

}

