package org.firstinspires.ftc.teamcode._TeleOPs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class SleepWorkaround extends LinearOpMode {
    private DcMotor FLW;
    private DcMotor BLW;
    private DcMotor FRW;
    private DcMotor BRW;

    private DcMotor Intake;
    private DcMotor BumpUp;
    private DcMotor Shooter;
    private DcMotor Arm;
    private Servo Claw;
    private Servo MagLift;
    private Servo MagTrigger;

    @Override
    public void runOpMode() throws InterruptedException {


        FLW = hardwareMap.dcMotor.get("FLW");
        BLW = hardwareMap.dcMotor.get("BLW");
        FRW = hardwareMap.dcMotor.get("FRW");
        BRW = hardwareMap.dcMotor.get("BRW");

        Intake = hardwareMap.dcMotor.get("Intake");
        BumpUp = hardwareMap.dcMotor.get("BumpUp");
        Shooter = hardwareMap.dcMotor.get("Shooter");
        Arm = hardwareMap.dcMotor.get("Arm");
        Claw = hardwareMap.servo.get("Claw");
        MagLift = hardwareMap.servo.get("MagLift");
        MagTrigger = hardwareMap.servo.get("MagTrigger");

        FRW.setDirection(DcMotorSimple.Direction.REVERSE);
        BRW.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if(gamepad1.left_bumper) {
                    FLW.setPower(.5);
                    BLW.setPower(-.5);
                    FRW.setPower(-.5);
                    BRW.setPower(.5);
                } else if(gamepad1.right_bumper) {
                    FLW.setPower(-.5);
                    BLW.setPower(.5);
                    FRW.setPower(.5);
                    BRW.setPower(-.5);
                } else {
                    FLW.setPower(gamepad1.left_stick_y*.75);
                    BLW.setPower(gamepad1.left_stick_y*.75);
                    FRW.setPower(gamepad1.right_stick_y*.75);
                    BRW.setPower(gamepad1.right_stick_y*.75);
                }
                if(gamepad1.a) {
                    Shooter.setPower(1);
                } else {
                    Shooter.setPower(0);
                }

                if(gamepad2.left_trigger > 0) {
                    Intake.setPower(1);
                    BumpUp.setPower(-1);
                    MagLift.setPosition(0.5);
                    MagTrigger.setPosition(0.32);
                } else if(gamepad2.right_trigger > 0) {
                    Intake.setPower(-1);
                    BumpUp.setPower(1);
                    MagLift.setPosition(0.5);
                    MagTrigger.setPosition(0.32);
                } else {
                    Intake.setPower(0);
                    BumpUp.setPower(0);
                }

                if(gamepad2.a) {
                    MagLift.setPosition(0.16);
                    MagTrigger.setPosition(0.32);
                }else if(gamepad2.b) {
                    MagLift.setPosition(0.5);
                    MagTrigger.setPosition(0.32);
                }

                if(gamepad2.x) {
                    MagTrigger.setPosition(0.5);
                    sleep(500);
                    MagTrigger.setPosition(0.0);
                }

                if(gamepad2.left_bumper) {
                    Arm.setPower(0.75);
                }else if(gamepad2.right_bumper) {
                    Arm.setPower(-0.75);
                }else {
                    Arm.setPower(0);
                }

                if(gamepad2.dpad_left) {
                    Claw.setPosition(0.5);
                }else if(gamepad2.dpad_right) {
                    Claw.setPosition(0.25);
                }

            }
        }
    }
}
