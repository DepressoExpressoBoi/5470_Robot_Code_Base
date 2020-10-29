package org.firstinspires.ftc.teamcode._Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class SimplePark extends LinearOpMode {
    private DcMotor FLW;
    private DcMotor BLW;
    private DcMotor FRW;
    private DcMotor BRW;

    private DcMotor Intake;

    private ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 383.6;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.937;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double SPEED = .7;

    @Override
    public void runOpMode() {
        FLW = hardwareMap.dcMotor.get("FLW");
        BLW = hardwareMap.dcMotor.get("BLW");
        FRW = hardwareMap.dcMotor.get("FRW");
        BRW = hardwareMap.dcMotor.get("BRW");

        Intake = hardwareMap.dcMotor.get("Intake");

        FRW.setDirection(DcMotorSimple.Direction.REVERSE);
        BRW.setDirection(DcMotorSimple.Direction.REVERSE);

        FLW.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLW.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRW.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRW.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FLW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        encoderDrive(SPEED, 5, 5, 5);
driveForward(.5, 40, 3);

    }

    public void driveForward(double speed, double dist, double timeout) {
        encoderDrive(speed, dist, dist, timeout);
    }

    public void encoderDrive(double speed, double distL, double distR, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = FLW.getCurrentPosition() + (int) (distL * COUNTS_PER_INCH);
            newRightTarget = FRW.getCurrentPosition() + (int) (distR * COUNTS_PER_INCH);
            FLW.setTargetPosition(newLeftTarget);
            BLW.setTargetPosition(newLeftTarget);
            FRW.setTargetPosition(newRightTarget);
            BRW.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            FLW.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BLW.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FRW.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BRW.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            FLW.setPower(Math.abs(speed));
            BLW.setPower(Math.abs(speed));
            FRW.setPower(Math.abs(speed));
            BRW.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (FLW.isBusy() && FRW.isBusy()) &&
                    (FLW.isBusy() && FRW.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        FLW.getCurrentPosition(),
                        BLW.getCurrentPosition(),
                        FRW.getCurrentPosition(),
                        BRW.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            FLW.setPower(0);
            BLW.setPower(0);
            FRW.setPower(0);
            BRW.setPower(0);

            // Turn off RUN_TO_POSITION
            FLW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BLW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FRW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BRW.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }


}
