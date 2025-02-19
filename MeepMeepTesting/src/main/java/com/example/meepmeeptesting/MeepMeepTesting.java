package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 9)
                .setDimensions(18, 18)
                .build();
                //x = -9.2
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-9.2, 62, Math.toRadians(-90)))

                //conversion rule Auton (x, y) -- > (y - 9, -x + 62)



                .strafeToConstantHeading(new Vector2d(-9.2, 61.2))



                .waitSeconds(0.1)



                //place first specimen
                .splineToConstantHeading(new Vector2d(-6, 35), Math.toRadians(90))

                //open claw after drop


                //move arm down out of way then back to pick up position



                //go to the samples on the floor
                .splineToSplineHeading((new Pose2d(-25, 40, Math.toRadians(-225))), Math.toRadians(270))

                //bring back first brick
                .strafeToLinearHeading(new Vector2d(-34, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))

                .strafeToLinearHeading(new Vector2d(-42, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))

                .strafeToLinearHeading(new Vector2d(-52, 37), Math.toRadians(-125))
                .turnTo(Math.toRadians(-225))

                .splineToLinearHeading(new Pose2d(-49.5, 60, Math.toRadians(-90)), Math.toRadians(90))

                //after reaching, close the claw picking up the second clip


//                .waitSeconds(.1)

                // then move the arm to the outtake position while robot starts moving


                //go to submersible with the first specimen
                .strafeToConstantHeading(new Vector2d(-10, 40))
                .splineToConstantHeading(new Vector2d(-9, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-8, 40))
//                .splineToConstantHeading(new Vector2d(-6, 29.5), Math.toRadians(90))

                //open claw after second specimen has been  placed

                //move arm down out of way then back to pick up position


                //go to pick up the second specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //after reaching, close the claw picking up third specimen


//                .waitSeconds(.1)

                // then move the arm to the outtake position while robot starts moving


                //go to submersible with the second specimen
                .strafeToConstantHeading(new Vector2d(-9, 40))
                .splineToConstantHeading(new Vector2d(-8, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-7, 40))
//                .splineToConstantHeading(new Vector2d(-4.5, 30.5), Math.toRadians(90))

                //open claw after outtake


                //reset the outtake arm


                //go back to pick up the third specimen
                .lineToYConstantHeading(33)
                //og y was 58.6
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                //close claw picking up fourth specimen


//                .waitSeconds(.1)

                //reset outtake arm after short delay


                //go to submersible with the third specimen
                .strafeToConstantHeading(new Vector2d(-8, 40))
                .splineToConstantHeading(new Vector2d(-7, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-6, 40))
//                .splineToConstantHeading(new Vector2d(-3, 30.5), Math.toRadians(90))

                //open claw after outtake


                //reset outtake arm


                //go back to pick up the fourth specimen
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(-10, 40))
                .splineToConstantHeading(new Vector2d(-9, 27.5), Math.toRadians(90))

                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-49.5, 61), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(-10, 40))
                .splineToConstantHeading(new Vector2d(-9, 27.5), Math.toRadians(90))

//                .strafeToConstantHeading(new Vector2d(-5, 40))
//                .splineToConstantHeading(new Vector2d(-1.25, 30), Math.toRadians(90))

                //open claw after outtake


                //go back to pick up the fifth clip
//                .lineToYConstantHeading(33)
//                .splineToConstantHeading(new Vector2d(-45.5, 58.6), Math.toRadians(90))
//                .waitSeconds(.1)
//
//                //place fifth clip
//                .strafeToConstantHeading(new Vector2d(-4, 40))
//                .splineToConstantHeading(new Vector2d(6, 31.5), Math.toRadians(90))


//                //auton with strafe placements starting with samples
//                //go to samples
//                .splineToConstantHeading((new Vector2d(-37, 40)), Math.toRadians(270))
//
//                //bring back first brick
//                .splineToConstantHeading(new Vector2d(-35, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-35, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-46, 50), Math.toRadians(90))
//
//                //bring back second brick
//                .turn(Math.toRadians(-5))
//                .splineToConstantHeading(new Vector2d(-46, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-57, 50), Math.toRadians(90))
//
//                //bring back third brick
//                .splineToConstantHeading(new Vector2d(-57, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 12), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-63, 12), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-63, 55), Math.toRadians(90))
//                .waitSeconds(.2)
//
//                //go to submersible with the first picked up specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(-1.5, 33), Math.toRadians(270))
//
//                //go to pick up second clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the second picked up clip
//                .strafeToConstantHeading(new Vector2d(0.25, 34))
//                .waitSeconds(.2)
//
//                //go back to pick up the third clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the third picked up clip
//                .strafeToConstantHeading(new Vector2d(2.25, 34))
//                .waitSeconds(.2)
//
//                //go back to pick up the fourth clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the fourth picked up clip
//                .strafeToConstantHeading(new Vector2d(4.25, 34))
//                .waitSeconds(.2)
//
//                //go back to pick up the fifth clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the fifth picked up clip
//                .strafeToConstantHeading(new Vector2d(5, 34))
//                .waitSeconds(.2)


                //auton with splines no strafes starts with specimen

//                //drop first specimen
//                .setTangent(Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(-3.2, 35), Math.toRadians(90))
//
//
//                .splineToConstantHeading((new Vector2d(-37, 40)), Math.toRadians(270))
//
//                //bring back first brick
//                .splineToConstantHeading(new Vector2d(-35, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-35, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-46, 50), Math.toRadians(90))
//
//                //bring back second brick
//                .turn(Math.toRadians(-5))
//                .splineToConstantHeading(new Vector2d(-46, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-57, 50), Math.toRadians(90))
//
//                //bring back third brick
//                .splineToConstantHeading(new Vector2d(-57, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 12), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-63, 12), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-63, 55), Math.toRadians(90))
//                .waitSeconds(.1)
//
//                //go to submersible with the first picked up specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(-1.5, 32), Math.toRadians(90))
//
//                //go to pick up second clip
////                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .splineToConstantHeading((new Vector2d(-40, 54)), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.5), Math.toRadians(90))
//                .waitSeconds(.1)
//
//                //go to submersible with the second picked up clip
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(0.25, 32.5), Math.toRadians(270))
//
//                //go back to pick up the third clip
//                .splineToConstantHeading(new Vector2d(-5, 40), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-27, 48), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-36, 56), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.5), Math.toRadians(90))
//                .waitSeconds(.1)
//
//                //go to submersible with the third picked up clip
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(2.25, 32.5), Math.toRadians(270))
//
//                //go back to pick up the fourth clip
//                .splineToConstantHeading(new Vector2d(-5, 40), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-27, 48), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-36, 56), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.5), Math.toRadians(90))
//                .waitSeconds(.1)
//
//
//                //go to submersible with the fourth picked up clip
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(4.25, 32.5), Math.toRadians(270))
//
//                //go to park
//                .splineToConstantHeading(new Vector2d(-5, 40), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-27, 48), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-36, 56), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-44.1, 58.5), Math.toRadians(90))


                //strafe auton that starts with clips

//                //drop first specimen
//                .setTangent(Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(-3.2, 35), Math.toRadians(90))
//
//
//                .splineToConstantHeading((new Vector2d(-37, 40)), Math.toRadians(270))
//
//                //bring back first brick
//                .splineToConstantHeading(new Vector2d(-35, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-35, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-46, 50), Math.toRadians(90))
//
//                //bring back second brick
//                .turn(Math.toRadians(-5))
//                .splineToConstantHeading(new Vector2d(-46, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-57, 50), Math.toRadians(90))
//
//                //bring back third brick
//                .splineToConstantHeading(new Vector2d(-57, 20), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-57, 12), Math.toRadians(270))
//                .splineToConstantHeading(new Vector2d(-63, 12), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-63, 55), Math.toRadians(90))
//                .waitSeconds(.2)
//
//                //go to submersible with the first picked up specimen
//                .strafeToConstantHeading(new Vector2d(-11.3, 40))
//                .splineToConstantHeading(new Vector2d(-1.5, 33), Math.toRadians(270))
//
//                //go to pick up second clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the second picked up clip
//                .strafeToConstantHeading(new Vector2d(0.25, 34))
//                .waitSeconds(.2)
//
//                //go back to pick up the third clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the third picked up clip
//                .strafeToConstantHeading(new Vector2d(2.25, 34))
//                .waitSeconds(.2)
//
//                //go back to pick up the fourth clip
//                .strafeToConstantHeading(new Vector2d(-44.1, 58))
//                .waitSeconds(.2)
//
//                //go to submersible with the fourth picked up clip
//                .strafeToConstantHeading(new Vector2d(4.25, 34))
//                .waitSeconds(.2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}