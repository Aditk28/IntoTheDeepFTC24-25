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
                                outtake.liftUp(1200)
                                //does NOT use passive power, might need to add depending on testing
                        )
                ))

                //goToSubmersible
                .strafeToConstantHeading(new Vector2d(24.2, 0))


                //after reaching, move lift down and open the claw
                .afterTime(0, new SequentialAction(
                        outtake.liftDown(0),
                        new SleepAction(.2),
                        outtake.openClaw()
                ))
                .waitSeconds(1)

                //reset position
                .strafeToLinearHeading(new Vector2d(5, -28), Math.toRadians(8))

                //bring back first brick
                .strafeToConstantHeading(new Vector2d(56, -28))
                .strafeToConstantHeading(new Vector2d(56, -38.75))
                .strafeToConstantHeading(new Vector2d(6, -38.75))

                //bring back second brick
                .strafeToConstantHeading(new Vector2d(56, -38.75))
                .strafeToConstantHeading(new Vector2d(56, -47))
                .strafeToConstantHeading(new Vector2d(6, -47))

                //reset position while moving arm to the intake position
                .afterTime(0, outtake.moveArm(.25))
                .strafeToConstantHeading(new Vector2d(10, -32))

                //go to pickup the specimen
                .strafeToConstantHeading(new Vector2d(5, -32))

                //after reaching, close the claw, then move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.4),
                        outtake.armOuttakePos(),
                        outtake.liftUp(500)
                ))
                .waitSeconds(.5)

                //go to submersible with the picked up specimen
                .strafeToConstantHeading(new Vector2d(21.3, -2))

                //move the lift up (backwards outtake) then open the claw, then move the arm back to transfer position and the lift back down
                .afterTime(0, new SequentialAction(
                        outtake.liftUp(2000),
                        outtake.openClaw(),
                        new SleepAction(.3),
                        outtake.moveArm(.25),
                        outtake.liftDown(50)
                ))
                .waitSeconds(2)

                //go to pick up the second clip
                .strafeToConstantHeading(new Vector2d(5.3, -32))

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.4),
                        outtake.armOuttakePos(),
                        outtake.liftUp(500)
                ))
                .waitSeconds(1)

                //go to submersible with the second picked up clip
                .strafeToConstantHeading(new Vector2d(21.3, -4))

                //after reaching, move the lift up, open the claw, and then move lift back down, then also extend the intake
                .afterTime(0, new SequentialAction(
                        outtake.liftUp(2000),
                        new SleepAction(.5),
                        outtake.openClaw(),
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

