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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-9, 62, Math.toRadians(-90)))

                //conversion rule Auton (x, y) -- > (y - 9, -x + 62)

                //drop first specimen
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-3.2, 35), Math.toRadians(90))

                //bring back first brick
                .splineToConstantHeading(new Vector2d(-35, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-35, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-46, 50), Math.toRadians(90))

                //bring back second brick
                .splineToConstantHeading(new Vector2d(-46, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-46, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-57, 50), Math.toRadians(90))

                //bring back third brick
                .splineToConstantHeading(new Vector2d(-57, 20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-57, 14), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-61.5, 14), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-61.5, 58), Math.toRadians(90))
                .waitSeconds(.2)

                //go to submersible with the first picked up specimen
                .strafeToConstantHeading(new Vector2d(-11.3, 40))
                .splineToConstantHeading(new Vector2d(-2, 34), Math.toRadians(270))
                .waitSeconds(.2)

                //go to pick up second clip
                .strafeToConstantHeading(new Vector2d(-44.1, 59))
                .waitSeconds(.2)

                //go to submersible with the second picked up clip
                .strafeToConstantHeading(new Vector2d(0, 34))
                .waitSeconds(.2)

                //go back to pick up the third clip
                .strafeToConstantHeading(new Vector2d(-44.1, 59))
                .waitSeconds(.2)

                //go to submersible with the third picked up clip
                .strafeToConstantHeading(new Vector2d(2, 34))
                .waitSeconds(.2)

                //go back to pick up the fourth clip
                .strafeToConstantHeading(new Vector2d(-44.1, 59))
                .waitSeconds(.2)

                //go to submersible with the fourth picked up clip
                .strafeToConstantHeading(new Vector2d(4, 34))
                .waitSeconds(.2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}