
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;

public class HybridLocalizer implements Localizer {
    private final ThreeDeadWheelLocalizer deadWheelLocalizer;
    private final AprilTagLocalizer aprilTagLocalizer;
    private final Pose2d initialPose;
    private Pose2d currentPose;


    public HybridLocalizer(ThreeDeadWheelLocalizer deadWheelLocalizer, AprilTagLocalizer aprilTagLocalizer, Pose2d initialPose) {
        this.deadWheelLocalizer = deadWheelLocalizer;
        this.aprilTagLocalizer = aprilTagLocalizer;
        this.initialPose = initialPose;
        currentPose = initialPose;
    }

    @Override
    public Twist2dDual<Time> update() {
        return null;
    }
}