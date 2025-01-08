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
@Autonomous(name = "Left-Side Autonomous", group = "Autonomous")
public class LeftSideAutonomous extends LinearOpMode {


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
                                outtake.liftUp(920)
                                //does NOT use passive power, might need to add depending on testing
                        )
                ))

                //goToSubmersible
                .strafeToConstantHeading(new Vector2d(30, 0))


                //after reaching, move lift down and open the claw
                .afterTime(0, new SequentialAction(
                        outtake.openClaw(),
                        outtake.liftDown(0),
                        new SleepAction(.2)

                ))
                .waitSeconds(1);

        waitForStart();
        if (isStopRequested()) return;

        //AUTONOMOUS START
        //run the autonomous
        Actions.runBlocking(
                auto.build()
        );
    }

}

