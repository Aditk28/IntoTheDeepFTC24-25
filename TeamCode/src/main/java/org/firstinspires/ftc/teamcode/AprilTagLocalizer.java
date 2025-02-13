package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagLocalizer implements Localizer{
    private final AprilTagProcessor aprilTag;
    private final VisionPortal visionPortal;

    public AprilTagLocalizer(HardwareMap hardwareMap) {
        // Define camera position and orientation
        Position cameraPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
        YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, 0);

        // Initialize AprilTag processor
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .build();

        // Initialize VisionPortal
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    public Pose2d getFieldCentricPose() {
        List<AprilTagDetection> detections = aprilTag.getDetections();
        if (!detections.isEmpty()) {
            AprilTagDetection detection = detections.get(0); // Use the first detection
            if (detection.metadata != null) {
                return new Pose2d(
                        detection.robotPose.getPosition().x,
                        detection.robotPose.getPosition().y,
                        detection.robotPose.getOrientation().getYaw(AngleUnit.RADIANS)
                );
            }
        }
        return null; // No detection
    }

    public void resume() {
        visionPortal.resumeStreaming();
    }

    public void pause() {
        visionPortal.stopStreaming();
    }

    public void STOP() {
        visionPortal.close();
    }

    @Override
    public Twist2dDual<Time> update() {
        return null;
    }
}