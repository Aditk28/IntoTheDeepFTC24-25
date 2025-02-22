//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//import java.util.List;
//
//public class aprilTagStartPos {
//    private AprilTagProcessor aprilTagProcessor;
//
//    private VisionPortal visionPortal;
//
//    public int runDetection(HardwareMap hardwareMap, Telemetry telemetry) {
//
//        aprilTagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagOutline(true)
//                .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)
//                .setDrawTagID(true)
//                .build();
//
//        visionPortal = new VisionPortal.Builder()
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .addProcessor(aprilTagProcessor)
//                .enableLiveView(true)
//                .build();
//
////        telemetry.addData("Starting X pos",)
//
//        List<AprilTagDetection> currentDetections = aprilTagProcessor.getFreshDetections();
//        return currentDetections.get(0).
//
//    }
//}
