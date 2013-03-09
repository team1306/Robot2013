/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.badgerbots;

import edu.wpi.first.wpilibj.*;
import org.badgerbots.lib.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends SimpleRobot {
    Joystick leftJoy;
    Joystick rightJoy;
    XBoxController xcon;
    Jaguar leftm;
    Jaguar rightm;
    TankDrive drive;
    Victor dumpHigh;
    Victor dumpLow;
    Victor climbHands;
    //Servo climbserva; //currently not in use
    //LimitSwitch climbsa; //limit switches currently unused
    //LimitSwitch climbsb;
    Compressor stingCompress;
    DoubleSolenoid sting;
    Solenoid stingRunLt;
    Solenoid stingChargeLt;
    DigitalInput stingSw;
    Stinger stinger;
    boolean driveMode;
    double driveModeLastTime;
    double compressorLastTime;
    boolean compstate;
    boolean tipping;
    boolean climbing;
    Servo latch;
    Timer Clock;
    int startTime;
    double startClock;
    Encoder righte;
    PIDController P;
    
    public Robot()
    {
        startTime = 0;
        Clock = new Timer();
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        xcon = new XBoxController(3);
        leftm = new Jaguar(1);
        rightm = new Jaguar(2);
        drive = new TankDrive(leftm, rightm, leftJoy, rightJoy);
        dumpHigh = new Victor (3);
        dumpLow = new Victor (4);
        climbHands = new Victor(5);
        //climbserva = new Servo (7); //currently not in use
        //climbsa = new LimitSwitch(1); //limit switches currently not used
        //climbsb = new LimitSwitch(2);
        stingCompress = new Compressor(5,1);
        sting = new DoubleSolenoid (2,1);
        stingRunLt = new Solenoid(3);
        stingChargeLt = new Solenoid(4);
        stingSw = new DigitalInput(6);
        stinger = new Stinger(sting, stingRunLt, stingChargeLt, xcon);
        driveMode = true;   //default to tank drive
        driveModeLastTime = 0;
        compressorLastTime = 0;
        tipping = false;
        compstate = false;
        climbing = false;
        latch = new Servo(8);
        righte = new Encoder(1,2);
        P = new PIDController(0.2, 0.2 , 0.2, righte, rightm);
    }
    
    public void autonomous() {
        while (isAutonomous()){ //While autonomous mode is activated
            if  (0 == startTime){ 
                P.enable();
                righte.setReverseDirection(true);
                righte.start();
                startTime = 1;
                startClock = Clock.get(); 
                Clock.start(); //Start the clock
                rightm.setSafetyEnabled(false);
                leftm.setSafetyEnabled(false);
                latch.setAngle(170);
                stingCompress.start();
                climbHands.set(1);
                P.setSetpoint(400);
            }
            if (Clock.get() > 0.1 + startClock){
                System.out.println("Motor Speed " + righte.getRate());
            }            
            /*if (Clock.get() > 1 + startClock){
                P.setSetpoint(0);
            }*/
                if (righte.getRaw() > 1000){
                    //climbHands.set(0);
                    rightm.setSafetyEnabled(true);
                    leftm.setSafetyEnabled(true);
                    System.out.println("Rotations: " + righte.getRaw());
                    // driveleft.set(.22);
                }
        }
    }
    
    
    public void tele() {
       if(rightJoy.getRawButton(10) && !climbing) 
       {
           climbing = true;
       }
       else if(rightJoy.getRawButton(10) && climbing)
       {
           climbing = false;
       }
       
       if(!climbing)
       { 
           if (leftJoy.getRawButton(11) && (Timer.getFPGATimestamp()-driveModeLastTime) > 2) 
           {
            driveMode = !driveMode;
            driveModeLastTime = Timer.getFPGATimestamp();
            }
           if (driveMode)
           {
            drive.drive();
           }
        else 
           {
            drive.arcadeDrive();
          } 
       }
       else {
        // climber code
        double h = leftJoy.getY();
        System.out.println("right joy " + h);
        if(Math.abs(h) < 0.15) {
         climbHands.set(0);
        }
        else {
            if(h > 0) {
                climbHands.set(h - 0.15);
            }
            else
            {
                climbHands.set(h + 0.15);
            }
        }
	drive.arcadeDrive();
        System.out.println(climbHands.get());
       }
       
       // compressor
       if(rightJoy.getRawButton(9) && (Timer.getFPGATimestamp()-compressorLastTime) > 2 && !stingCompress.enabled()) {
	  stingCompress.start();
                     compressorLastTime = Timer.getFPGATimestamp();
       }
       else if (rightJoy.getRawButton(9) && (Timer.getFPGATimestamp()-compressorLastTime) > 2 && stingCompress.enabled()) {
            stingCompress.stop();
             compressorLastTime = Timer.getFPGATimestamp();
        }
       
       
//       if (stingCompress.enabled()) //debug statement not needed
//       {
//           System.out.println("Compresor polling");
//       }
//       else
//       {
//           System.out.println("Compressor not polling");
//       }
       
       // stinger code
       if(xcon.getButtonB() && !stinger.isTipped) 
       {
	   stinger.tip();
       }
       if (xcon.getButtonX() && stinger.isTipped)
       {
           stinger.untip();
       }

       // dumper code
       double q = xcon.getLeftJoyY();
       double r = xcon.getRightJoyY();
       System.out.println(q);
       
       if(Math.abs(q) < 0.15) {
           dumpHigh.set(0);
       }
       else {
           if(q > 0) dumpHigh.set((q - 0.15)/2.5);
           else dumpHigh.set((q + 0.15)/6);
       }
       
       System.out.println(dumpHigh.get());
       
       if(Math.abs(r) < 0.15) {
           dumpLow.set(0);
       }
       else {
           if(r > 0) dumpLow.set((r - 0.15)/2);
           else dumpLow.set((r + 0.15)/2);
       }
    }
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    /*public void autonomous() 
    {
        auto();
    }*/

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl()
    {
        while(isOperatorControl()) {
            tele();
            Timer.delay(3/1000);
        }
    }
}
