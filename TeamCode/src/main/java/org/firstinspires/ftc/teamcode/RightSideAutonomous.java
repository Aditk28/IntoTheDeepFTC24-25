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
                                outtake.liftUp(200)
                                //does NOT use passive power, might need to add depending on testing
                        )
                ))

                //goToSubmersible
                .splineToConstantHeading(new Vector2d(34.5, 4), 0)

                //after reaching, move lift down and open the claw
                .afterTime(0, new SequentialAction(
                        outtake.openClaw(),
//                        outtake.liftDown(0),
                        new SleepAction(.2)
                        //outtake.closeClaw()
                ))
                //.waitSeconds(1)

                //reset position
                .strafeToSplineHeading(new Vector2d(28, -5), Math.toRadians(6))
                .splineToConstantHeading(new Vector2d(15, -27), 0)
//                .strafeToConstantHeading(new Vector2d(10, 0))
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(8)), Math.toRadians(90))

                //bring back first brick

                .splineToConstantHeading(new Vector2d(53, -27   ), 0)
                .splineToConstantHeading(new Vector2d(53, -35), 0)
                .splineToConstantHeading(new Vector2d(10, -35), 0)

                //bring back second brick
                .splineToConstantHeading(new Vector2d(53, -38.75), 0)
                .splineToConstantHeading(new Vector2d(53, -48), 0)
                .splineToConstantHeading(new Vector2d(13, -48), 0)

                //bring back third brick
                .splineToLinearHeading(new Pose2d(53, -45, Math.toRadians(12)), 0)
                .splineToConstantHeading(new Vector2d(53, -50), 0)
                .splineToConstantHeading(new Vector2d(17, -50), 0)


                //reset position while moving arm to the intake position
                .afterTime(0, new ParallelAction(
                        outtake.armWallPosBack(),
                        outtake.openClaw())
                )
                .strafeToConstantHeading(new Vector2d(10, -32))

                //go to pickup the specimen
//                .splineToConstantHeading(new Vector2d(4.2, -34), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(4.75, -34), 0)

                //after reaching, close the claw, then move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.4),
                        outtake.armOuttakePos(),
//                        outtake.moveArm(.86),
                        outtake.liftUp(225)
                ))
                .waitSeconds(.5)

                //go to submersible with the picked up specimen
                .splineToConstantHeading(new Vector2d(34.5, 3), 0)

                //move the lift up (backwards outtake) then open the claw, then move the arm back to transfer position and the lift back down
                .afterTime(0, new SequentialAction(
//                        outtake.liftUp(2500),
                        outtake.openClaw()
                ))
                .waitSeconds(.1)

                //.setReversed(true)
                .afterTime(.2, new SequentialAction(
                        outtake.armWallPosBack(),
                        outtake.liftDown(50)
                ))
                //go to pick up the second clip
                .splineToConstantHeading(new Vector2d(25, 3), 0)
                .splineToConstantHeading(new Vector2d(4.4, -34), 0)
                //.setReversed(false)

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.1),
                        outtake.armOuttakePos(),
//                        outtake.moveArm(.86),
                        outtake.liftUp(250)
                ))
                .waitSeconds(.3)

                //go to submersible with the second picked up clip
                .splineToLinearHeading(new Pose2d(35, 6, Math.toRadians(4)), 0)

                //after reaching, move the lift up, open the claw, and then move lift back down, then also extend the intake











                //open the claw, then move the arm back to transfer position and the lift back down
                .afterTime(0, new SequentialAction(
                        outtake.openClaw()
                ))
                .waitSeconds(.1)

                //.setReversed(true)
                .afterTime(.2, new SequentialAction(
                        outtake.armWallPosBack(),
                        outtake.liftDown(50)
                ))
                //go to pick up the second clip
                .splineToConstantHeading(new Vector2d(25, 3), 0)
                .splineToConstantHeading(new Vector2d(4.4, -34), 0)
                //.setReversed(false)

                //after reaching, close the claw and move the arm to the outtake position
                .afterTime(0, new SequentialAction(
                        outtake.closeClaw(),
                        new SleepAction(.1),
                        new ParallelAction(
                                outtake.armOuttakePos(),
//                        outtake.moveArm(.86),
                                outtake.liftUp(250)
                        )
                ))
                .waitSeconds(.3)

                //go to submersible with the second picked up clip
                .splineToLinearHeading(new Pose2d(35, 6, Math.toRadians(4)), 0);



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

