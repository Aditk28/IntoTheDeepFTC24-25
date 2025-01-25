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
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-32.3, 62, Math.toRadians(-90)))

                //conversion rule Auton (x, y) -- > (y - 9, -x + 62)

                //this is the auton that goes straight for the placed samples

                .splineToConstantHeading((new Vector2d(-36, 40)), Math.toRadians(270))

                //bring back first brick
                .splineToConstantHeading(new Vector2d(-34, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-45, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-45, 50), Math.toRadians(90))
                .turn(Math.toRadians(5))

//                bring back second brick
                .splineToConstantHeading(new Vector2d(-45, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-45, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-55, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-55, 50), Math.toRadians(90))

                //bring back third brick
                .splineToConstantHeading(new Vector2d(-52, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-52, 10), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-66, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-66, 54), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(-60, 50), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-44.1, 51), Math.toRadians(90))
                .waitSeconds(.1)

                //go to submersible with the first specimen
                .strafeToConstantHeading(new Vector2d(-8, 40))
                .splineToConstantHeading(new Vector2d(-6, 30.5), Math.toRadians(90))

                //go to pick up second clip
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-44.1, 58), Math.toRadians(90))
                .waitSeconds(.1)

                //go to submersible with the second picked up clip
                .strafeToConstantHeading(new Vector2d(-7, 40))
                .splineToConstantHeading(new Vector2d(0.25, 31.5), Math.toRadians(90))

                //go back to pick up the third clip
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-44.1, 58), Math.toRadians(90))
                .waitSeconds(.1)

                //go to submersible with the third picked up clip
                .strafeToConstantHeading(new Vector2d(-6, 40))
                .splineToConstantHeading(new Vector2d(2.25, 31.5), Math.toRadians(90))

                //go back to pick up the fourth clip
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-44.1, 58), Math.toRadians(90))
                .waitSeconds(.1)


                //go to submersible with the fourth picked up clip
                .strafeToConstantHeading(new Vector2d(-5, 40))
                .splineToConstantHeading(new Vector2d(4.25, 31.5), Math.toRadians(90))

                //go back to pick up the fifth clip
                .lineToYConstantHeading(33)
                .splineToConstantHeading(new Vector2d(-44.1, 58), Math.toRadians(90))
                .waitSeconds(.1)

                //place fifth clip
                .strafeToConstantHeading(new Vector2d(-4, 40))
                .splineToConstantHeading(new Vector2d(6, 31.5), Math.toRadians(90))


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