package org.firstinspires.ftc.teamcode._Autonomous;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@Autonomous
public class Testing extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(25)
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .forward(25)
                .build();

        waitForStart();

        if(isStopRequested()) return;

        //Follow Both Trajectories
        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
    }
}
