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
@Autonomous(name = "5 SPEC AUTON", group = "Autonomous")
public class Spec5Auton extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-9.2, 62, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ActionClass.Intake intake = new ActionClass.Intake(hardwareMap);
        ActionClass.Outtake outtake = new ActionClass.Outtake(hardwareMap);

        Actions.runBlocking(outtake.closeClaw()); // ROBOT MOVES ON INITIALIZATION

        TrajectoryActionBuilder auto = drive.actionBuilder(initialPose)

                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //place first specimen
                .splineToConstantHeading(new Vector2d(-9, 35), Math.toRadians(90))

                //open claw after drop
                .afterTime(0, new ParallelAction(
                        outtake.openClaw(),
                        outtake.armOuttakePos2(),
                        new SleepAction(0.1),
                        outtake.armWallPosBack()
                ))

                //go to the samples on the floor
                .splineToConstantHeading((new Vector2d(-36, 36)), Math.toRadians(270))

                //bring back first brick
                .splineToConstantHeading(new Vector2d(-34, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34, 17), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-47, 17), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-47, 47), Math.toRadians(90))

//                bring back second brick
                .splineToConstantHeading(new Vector2d(-47, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-47, 9), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-58, 9), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-58, 45), Math.toRadians(90))

                //bring back third brick
                .splineToConstantHeading(new Vector2d(-58, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-58, 8), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-70.5, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-70.5, 45), Math.toRadians(90))

                //going to the pickup location
                .splineToConstantHeading(new Vector2d(-60, 45), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-49.5, 60.25), Math.toRadians(90))

                //after reaching, close the claw picking up the second specimen

                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

                .waitSeconds(0.1)

                // then move the arm to the outtake position while robot starts moving
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the second specimen
                .strafeToConstantHeading(new Vector2d(-9, 40))
                .splineToConstantHeading(new Vector2d(-8, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-8, 40))
//                .splineToConstantHeading(new Vector2d(-6, 29.5), Math.toRadians(90))

                //open claw after second specimen has been placed
                .afterTime(0, new ParallelAction(
                        outtake.openClaw(),
                        outtake.armOuttakePos2(),
                        new SleepAction(0.1),
                        outtake.armWallPosBack()
                ))

                //go to pick up the third specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61.25), Math.toRadians(90))

                //after reaching, close the claw picking up third specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

                .waitSeconds(.1)

                // then move the arm to the outtake position while robot starts moving
                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the third specimen
                .strafeToConstantHeading(new Vector2d(-8, 40))
                .splineToConstantHeading(new Vector2d(-7, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-7, 40))
//                .splineToConstantHeading(new Vector2d(-4.5, 30.5), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new ParallelAction(
                        outtake.openClaw(),
                        outtake.armOuttakePos2(),
                        new SleepAction(0.1),
                        outtake.armWallPosBack()
                ))

                //go back to pick up the fourth specimen
                .lineToYConstantHeading(33)
                //og y was 58.6
                .splineToConstantHeading(new Vector2d(-49.5, 61.25), Math.toRadians(90))

                //close claw picking up fourth specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

                .waitSeconds(.1)

                //reset outtake arm after short delay
                .afterTime(0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the fourth specimen
                .strafeToConstantHeading(new Vector2d(-7, 40))
                .splineToConstantHeading(new Vector2d(-6, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-6, 40))
//                .splineToConstantHeading(new Vector2d(-3, 30.5), Math.toRadians(90))

                .afterTime(0, new ParallelAction(
                        outtake.openClaw(),
                        outtake.armOuttakePos2(),
                        new SleepAction(0.1),
                        outtake.armWallPosBack()
                ))

                //go back to pick up the fifth specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61.25), Math.toRadians(90))

                //pick up fifth specimen
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw()
                ))

                .waitSeconds(.1)

                .afterTime(0.0, new SequentialAction(
                        outtake.armOuttakePos()
                ))

                //go to submersible with the fifth specimen
                .strafeToConstantHeading(new Vector2d(-6, 40))
                .splineToConstantHeading(new Vector2d(-5, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-5, 40))
//                .splineToConstantHeading(new Vector2d(-1.25, 30), Math.toRadians(90))

                //open claw after outtake
                .afterTime(0, new SequentialAction(
                        outtake.openClaw()
                ));

        waitForStart();
        if (isStopRequested()) return;

        //AUTONOMOUS START
        //run the autonomous
        Actions.runBlocking(
                auto.build()
        );
    }

}