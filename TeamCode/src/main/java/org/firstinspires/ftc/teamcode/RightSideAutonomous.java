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
@Autonomous(name = "Right-Side Autonomous", group = "Autonomous")
public class RightSideAutonomous extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ActionClass.Intake intake = new ActionClass.Intake(hardwareMap);
        ActionClass.Outtake outtake = new ActionClass.Outtake(hardwareMap);

        Actions.runBlocking(outtake.closeClaw()); // ROBOT MOVES ON INITIALIZATION

        TrajectoryActionBuilder auto = drive.actionBuilder(initialPose)

                //move lift up and arm to outtake position while going there
                .afterTime(0, new ParallelAction(
                        outtake.armOuttakePos(),
                        new SequentialAction(
                                outtake.liftUp(475)
                                //does NOT use passive power, might need to add depending on testing
                        )
                ))

                //goToSubmersible
                .strafeToConstantHeading(new Vector2d(30, 0))


                //after reaching, move lift down and open the claw
                .afterTime(0, new SequentialAction(
                        outtake.openClaw(),
                        outtake.liftDown(0),
                        new SleepAction(.2),
                        outtake.closeClaw()

                ))
                .waitSeconds(1)

                //reset position
                .strafeToLinearHeading(new Vector2d(5, -28), Math.toRadians(8))
//                .strafeToConstantHeading(new Vector2d(10, 0))
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(8)), Math.toRadians(90))

                //bring back first brick
                .splineToConstantHeading(new Vector2d(56, -28), 0)
                .splineToConstantHeading(new Vector2d(56, -35), 0)
                .splineToConstantHeading(new Vector2d(6, -35), 0)

                //bring back second brick
                .strafeToConstantHeading(new Vector2d(56, -38.75))
                .strafeToConstantHeading(new Vector2d(56, -45))
                .strafeToConstantHeading(new Vector2d(6, -45))

                //reset position while moving arm to the intake position
                .afterTime(0, new ParallelAction(
                        outtake.armWallPosBack(),
                        outtake.openClaw())
                )
                .strafeToConstantHeading(new Vector2d(10, -32))

                //go to pickup the specimen
//                .splineToConstantHeading(new Vector2d(4.2, -34), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(5, -34))

                //after reaching, close the claw, then move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.4),
                        outtake.armOuttakePos(),
//                        outtake.moveArm(.86),
                        outtake.liftUp(1650)
                ))
                .waitSeconds(.5)

                //go to submersible with the picked up specimen
                .strafeToConstantHeading(new Vector2d(31, 3))

                //move the lift up (backwards outtake) then open the claw, then move the arm back to transfer position and the lift back down
                .afterTime(0, new SequentialAction(
//                        outtake.liftUp(2500),
                        outtake.openClaw(),
                        new SleepAction(.2),
                        outtake.armWallPosBack(),
                        outtake.liftDown(50)
                ))
                .waitSeconds(1)

                //go to pick up the second clip
                .strafeToConstantHeading(new Vector2d(4.55, -34))

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.4),
//                        outtake.armOuttakePos(),
                        outtake.moveArm(.86),
                        outtake.liftUp(1800)
                ))
                .waitSeconds(1)

                //go to submersible with the second picked up clip
                .strafeToConstantHeading(new Vector2d(32, 6))

                //after reaching, move the lift up, open the claw, and then move lift back down, then also extend the intake
                .afterTime(0, new SequentialAction(
//                        outtake.liftUp(2500),
                        new SleepAction(.5),
                        outtake.openClaw(),
                        outtake.armOuttakePos2(),
                        outtake.liftDown(50),
                        intake.armMovePos()
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

