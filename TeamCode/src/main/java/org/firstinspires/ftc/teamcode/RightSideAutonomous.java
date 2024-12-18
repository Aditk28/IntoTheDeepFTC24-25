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


// Non-RR imports

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
        Actions.runBlocking(outtake.closeClaw());

        TrajectoryActionBuilder auto = drive.actionBuilder(initialPose)
                //goToSubmersible
                .strafeToConstantHeading(new Vector2d(-24, 0))

                //move lift up and arm to outtake position while going there
                .afterTime(0, new ParallelAction(
                        outtake.armOuttakePos2(),
                        new SequentialAction(
                                outtake.liftUp(200)
//                                outtake.passivePowerOn()
                        )
                ))

                //after reaching, move lift down and open the claw
                .afterDisp(-24, new SequentialAction(
                        outtake.liftDown(50),
                        outtake.openClaw()
                ))
                .waitSeconds(2)

                //reset
                .strafeToConstantHeading(new Vector2d(-5, 32))
                //bring back first brick
                .strafeToConstantHeading(new Vector2d(-52, 32))
                .strafeToConstantHeading(new Vector2d(-52, 36))
                .strafeToConstantHeading(new Vector2d(-2, 36))

                //bring back second brick
                .strafeToConstantHeading(new Vector2d(-52, 36))
                .strafeToConstantHeading(new Vector2d(-52, 40))
                .strafeToConstantHeading(new Vector2d(-2, 40))

                //reset while moving arm to the transfer position
                .strafeToConstantHeading(new Vector2d(-10, 32))
                .afterTime(0, outtake.armTransferPos())

                //go to pickup the specimen
                .strafeToConstantHeading(new Vector2d(-2, 32))

                //after reaching, close the claw, then move the arm to the outtake position
                .afterDisp(-8, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.5),
                        outtake.armOuttakePos2()
                ))
                .waitSeconds(1)

                //go to submersible with the picked up specimen
                .strafeToConstantHeading(new Vector2d(-24, 2))

                //move the lift up (backwards outtake) then open the claw, then move the arm back to transfer position and the lift back down
                .afterTime(3, new SequentialAction(
                        outtake.liftUp(200),
                        outtake.openClaw(),
                        new SleepAction(.2),
                        outtake.armTransferPos(),
                        outtake.liftDown(50)
                ))

                //go to pick up the second clip
                .strafeToConstantHeading(new Vector2d(-2, 32))

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(3, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.5),
                        outtake.armOuttakePos2()
                ))
                .waitSeconds(1)

                //go to submersible
                .strafeToConstantHeading(new Vector2d(-24, 2))

                //after reaching, move the lift up, open the claw, and then move lift back down, then also extend the intake
                .afterTime(3, new SequentialAction(
                        outtake.liftUp(200),
                        outtake.openClaw(),
                        outtake.liftDown(50),
                        intake.armMovePos()
                ));

        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(
                auto.build());
    }

}

