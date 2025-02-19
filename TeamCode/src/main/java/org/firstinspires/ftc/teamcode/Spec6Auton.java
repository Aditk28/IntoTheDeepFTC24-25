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

@Config
@Autonomous(name = "6 SPEC AUTON", group = "Autonomous")
public class Spec6Auton extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-9.2, 62, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ActionClass.Intake intake = new ActionClass.Intake(hardwareMap);
        ActionClass.Outtake outtake = new ActionClass.Outtake(hardwareMap);

        Actions.runBlocking(outtake.closeClaw()); // ROBOT MOVES ON INITIALIZATION

        TrajectoryActionBuilder auto = drive.actionBuilder(initialPose)

                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))
                .strafeToConstantHeading(new Vector2d(-9.2, 61.2))



                .waitSeconds(0.1)



                //place first specimen
                .splineToConstantHeading(new Vector2d(-6, 35), Math.toRadians(90))

                //open claw after drop


                //move arm down out of way then back to pick up position



                //go to the samples on the floor
                .splineToSplineHeading((new Pose2d(-25, 40, Math.toRadians(-225))), Math.toRadians(270))

                //sweep first brick
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperOut()
                ))
                .strafeToLinearHeading(new Vector2d(-34, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperIn()
                ))

                //sweep second brick
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperOut()
                ))
                .strafeToLinearHeading(new Vector2d(-42, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperIn()
                ))

                //sweep third brick
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperOut()
                ))
                .strafeToLinearHeading(new Vector2d(-52, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))
                .afterTime(0.0, new SequentialAction(
                        intake.sweeperIn()
                ))

                .splineToLinearHeading(new Pose2d(-49.5, 60, Math.toRadians(-90)), Math.toRadians(90))

                //after reaching, close the claw picking up the second specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

//                .waitSeconds(.1)

                // then move the arm to the outtake position while robot starts moving
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the second specimen
                .strafeToConstantHeading(new Vector2d(-7, 40))
                .splineToConstantHeading(new Vector2d(-6, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-8, 40))
//                .splineToConstantHeading(new Vector2d(-6, 29.5), Math.toRadians(90))

                //open claw after second specimen has been placed
                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))

                //move arm down out of way then back to pick up position
                .afterTime(0, new ParallelAction(
                                outtake.armWallPosBack()
                        )
                )

                //go to pick up the third specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //after reaching, close the claw after picking up third specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

//                .waitSeconds(.1)

                // then move the arm to the outtake position while robot starts moving
                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the third specimen
                .strafeToConstantHeading(new Vector2d(-6, 40))
                .splineToConstantHeading(new Vector2d(-5, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-7, 40))
//                .splineToConstantHeading(new Vector2d(-4.5, 30.5), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))

                //reset the outtake arm
                .afterTime(0, new ParallelAction(
                                outtake.armWallPosBack()
                        )
                )

                //go back to pick up the fourth specimen
                .lineToYConstantHeading(33)
                //og y was 58.6
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //close claw picking up fourth specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

//                .waitSeconds(.1)

                //reset outtake arm after short delay
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the fourth specimen
                .strafeToConstantHeading(new Vector2d(-5, 40))
                .splineToConstantHeading(new Vector2d(-4, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-6, 40))
//                .splineToConstantHeading(new Vector2d(-3, 30.5), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))

                //reset outtake arm
                .afterTime(0.0, new ParallelAction(
                                outtake.armWallPosBack()
                        )
                )

                //go back to pick up the fifth specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //pick up fifth specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

//                .waitSeconds(.1)

                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the fifth specimen
                .strafeToConstantHeading(new Vector2d(-4, 40))
                .splineToConstantHeading(new Vector2d(-3, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-5, 40))
//                .splineToConstantHeading(new Vector2d(-1.25, 30), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))

                //go back to pick up the sixth specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //pick up sixth specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

//                .waitSeconds(.1)

                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the sixth specimen
                .strafeToConstantHeading(new Vector2d(-3, 40))
                .splineToConstantHeading(new Vector2d(-2, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-5, 40))
//                .splineToConstantHeading(new Vector2d(-1.25, 30), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new SequentialAction(
                        outtake.halfClosed()
                ))

                //park in loading zone
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90));

        waitForStart();
        if (isStopRequested()) return;

        //AUTONOMOUS START
        //run the autonomous
        Actions.runBlocking(
                auto.build()
        );
    }

}