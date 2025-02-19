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
@Autonomous(name = "TEST AUTON", group = "Autonomous")
public class TEST extends LinearOpMode {


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

                .waitSeconds(5);
//
//                .waitSeconds(0.1)
//
//                //place first specimen
//                .splineToConstantHeading(new Vector2d(-6, 35), Math.toRadians(90))
//
//                //open claw after drop
//                .afterTime(0, new SequentialAction(
//                        outtake.halfClosed()
//                ))
//
//                //move arm down out of way then back to pick up position
//                .afterTime(0.1, new ParallelAction(
//                                outtake.armWallPosBack()
//                        )
//                )
//
//                //go to the samples on the floor
//                .splineToConstantHeading((new Vector2d(-36, 36)), Math.toRadians(270))
//
//                //bring back first brick
//                .splineToConstantHeading(new Vector2d(-34, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-34, 17), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 17), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-46, 47), Math.toRadians(90))
//
////                bring back second brick
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 9), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-54, 9), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-54, 45), Math.toRadians(90))
//
//
//
//                //bring back third brick
//                .splineToConstantHeading(new Vector2d(-54, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-54, 10), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-70, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-70, 45), Math.toRadians(90))
//
//                .splineToConstantHeading(new Vector2d(-60, 45), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-49.5, 60), Math.toRadians(90))
//
//                //after reaching, close the claw picking up the second clip
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
////                .waitSeconds(.1)
//
//                // then move the arm to the outtake position while robot starts moving
//                .afterTime(0.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the first specimen
//                .strafeToConstantHeading(new Vector2d(-10, 40))
//                .splineToConstantHeading(new Vector2d(-9, 27.5), Math.toRadians(90))
//
////                .strafeToConstantHeading(new Vector2d(-8, 40))
////                .splineToConstantHeading(new Vector2d(-6, 29.5), Math.toRadians(90))
//
//                //open claw after second specimen has been  placed
//                .afterTime(0, new SequentialAction(
//                        outtake.halfClosed()
//                ))
//
//                //move arm down out of way then back to pick up position
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go to pick up the second specimen
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))
//
//                //after reaching, close the claw picking up third specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
////                .waitSeconds(.1)
//
//                // then move the arm to the outtake position while robot starts moving
//                .afterTime(0.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the second specimen
//                .strafeToConstantHeading(new Vector2d(-9, 40))
//                .splineToConstantHeading(new Vector2d(-8, 27.5), Math.toRadians(90))
//
////                .strafeToConstantHeading(new Vector2d(-7, 40))
////                .splineToConstantHeading(new Vector2d(-4.5, 30.5), Math.toRadians(90))
//
//                //open claw after outtake
//                .afterTime(0, new SequentialAction(
//                        outtake.halfClosed()
//                ))
//
//                //reset the outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the third specimen
//                .lineToYConstantHeading(33)
//                //og y was 58.6
//                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))
//
//                //close claw picking up fourth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
////                .waitSeconds(.1)
//
//                //reset outtake arm after short delay
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the third specimen
//                .strafeToConstantHeading(new Vector2d(-8, 40))
//                .splineToConstantHeading(new Vector2d(-7, 27.5), Math.toRadians(90))
//
////                .strafeToConstantHeading(new Vector2d(-6, 40))
////                .splineToConstantHeading(new Vector2d(-3, 30.5), Math.toRadians(90))
//
//                //open claw after outtake
//                .afterTime(0, new SequentialAction(
//                        outtake.halfClosed()
//                ))
//
//                //reset outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the fourth specimen
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))
//
//                //pick up fourth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
////                .waitSeconds(.1)
//
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the fourth specimen
//                .strafeToConstantHeading(new Vector2d(-10, 40))
//                .splineToConstantHeading(new Vector2d(-9, 27.5), Math.toRadians(90))
//
////                .strafeToConstantHeading(new Vector2d(-5, 40))
////                .splineToConstantHeading(new Vector2d(-1.25, 30), Math.toRadians(90))
//
//                //open claw after outtake
//                .afterTime(0, new SequentialAction(
//                        outtake.halfClosed()
//                ));

//                //reset outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the fifth specimen
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-45.5, 57.5), Math.toRadians(90))
//
//                //pick up fourth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the fourth specimen
//                .strafeToConstantHeading(new Vector2d(-5, 40))
//                .splineToConstantHeading(new Vector2d(-2.25, 31), Math.toRadians(90))
//
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ));

        //park
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-45.5, 57.7), Math.toRadians(90));

//                //reset the outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the fifth specimen
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-44.1, 60), Math.toRadians(90))
//
//                //pick up fifth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //place the fifth specimen
//                .strafeToConstantHeading(new Vector2d(-4, 40))
//                .splineToConstantHeading(new Vector2d(3, 31.5), Math.toRadians(90))
//
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //reset the outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2()
//                        )
//                ));

        //move lift up and arm to outtake position while going there

//
//                //move tworads the bricks on the floor
//                .splineToConstantHeading(new Vector2d(-37, 40), Math.toRadians(270))
//
//                //bring back first brick
//                .splineToConstantHeading(new Vector2d(-35, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-35, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-46, 50), Math.toRadians(90))
//
//                //reset arm
//                .afterTime(0.1, new ParallelAction(
//                                outtake.armWallPosBack()
//                        )
//                )
//
//                //bring back second brick
//                .turn(Math.toRadians(10))
//                .splineToConstantHeading(new Vector2d(-46, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-57, 50), Math.toRadians(90))
//
//                //bring back third brick
//                .splineToConstantHeading(new Vector2d(-57, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-63, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-63, 54.8), Math.toRadians(90))
//
//                //after reaching, close the claw picking up the second clip
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                // then move the arm to the outtake position and raise lift while robot starts moving
//                .afterTime(0.1, new SequentialAction(
//                        //lift up value is 200
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the second specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(-1.5, 30.5), Math.toRadians(270))
//
//                //open claw after second specimen has been placed
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //move arm down out of way then back to pick up position
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go to pick up the third specimen
//                .splineToConstantHeading((new Vector2d(-40, 50)), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-44.1, 58), Math.toRadians(90))
//
//                //after reaching, close the claw picking up third specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                // then move the arm to the outtake position while robot starts moving
//                .afterTime(0.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the third specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(0.25, 31), Math.toRadians(270))
//
//                //open claw after outtake
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //reset the outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the fourth specimen
//                .splineToConstantHeading((new Vector2d(-40, 50)), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.5), Math.toRadians(90))
//
//                //close claw picking up fourth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                //reset outtake arm after short delay
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the fourth specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(1.25, 31), Math.toRadians(270))
//
//                //open claw after outtake
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //reset outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go back to pick up the fifth specimen
//                .splineToConstantHeading((new Vector2d(-40, 50)), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.25), Math.toRadians(90))
//
//                //pick up fifth specimen
//                .afterTime(0, new SequentialAction(
//                        outtake.closeClaw()
//                ))
//
//                .waitSeconds(.1)
//
//                .afterTime(.1, new SequentialAction(
//                        outtake.armOuttakePos()
//                ))
//
//                //go to submersible with the fifth specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(3.25, 31), Math.toRadians(270))
//
//                .afterTime(0, new SequentialAction(
//                        outtake.openClaw()
//                ))
//
//                //reset the outtake arm
//                .afterTime(0.1, new ParallelAction(
//                        new SequentialAction(
//                                outtake.armOuttakePos2(),
//                                new SleepAction(.25),
//                                outtake.armWallPosBack()
//                        )
//                ))
//
//                //go to park
//                .splineToConstantHeading(new Vector2d(-4, 40), Math.toRadians(90))
////                .splineToConstantHeading(new Vector2d(-27, 48), Math.toRadians(90))
////                .splineToConstantHeading(new Vector2d(-36, 56), Math.toRadians(90))
//                .strafeToConstantHeading(new Vector2d(-44.1, 58.5));


        waitForStart();
        if (isStopRequested()) return;

        //AUTONOMOUS START
        //run the autonomous
        Actions.runBlocking(
                auto.build()
        );
    }

}