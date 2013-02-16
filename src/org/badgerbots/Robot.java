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
    Pot dumpLowPot;
    Pot dumpHighPot;
    Dumper dumper;
    Victor climbHands;
    Victor climbFeet;
    Servo climba;
    Servo climbb;
    LimitSwitch climbsa;
    LimitSwitch climbsb;
    LimitSwitch climbsc;
    LimitSwitch climbsd;
    Climber climber;
    Compressor stingCompress;
    DoubleSolenoid sting;
    Solenoid stingRunLt;
    Solenoid stingChargeLt;
    DigitalInput stingSw;
    Stinger stinger;
    boolean driveMode;
    double lastTime;
    double lastTime2;
    boolean compstate;
    boolean tipping;
    boolean climbing;
    Encoder lefte;
    Encoder righte;
    PIDController leftc;
    PIDController rightc;
   // AnalogChannel TestA;
    
    
    public Robot()
    {
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        xcon = new XBoxController(3);
	leftm = new Jaguar(1);
	rightm = new Jaguar(2);
        drive = new TankDrive(leftm, rightm, leftJoy, rightJoy);
        dumpHigh = new Victor (3);
        dumpLow = new Victor (4);
        dumpLowPot = new Pot (1);
        dumpHighPot = new Pot (2);
        dumper = new Dumper (dumpLowPot, dumpHighPot, dumpLow, dumpHigh);
        climbHands = new Victor(5);
        climbFeet = new Victor(6);
        climba = new Servo (7);
        climbb = new Servo(8);
        climbsa = new LimitSwitch(1);
        climbsb = new LimitSwitch(2);
        climbsc = new LimitSwitch(3);
        climbsd = new LimitSwitch(4);
        climber = new Climber(climbHands, climbFeet, climba, climbb, climbsa, climbsb, climbsc, climbsd);
        stingCompress = new Compressor(5,1);
	//stingCompress.start();
        sting = new DoubleSolenoid (1, 2);
        stingRunLt = new Solenoid(3);
        stingChargeLt = new Solenoid(4);
        stingSw = new DigitalInput(6);
        stinger = new Stinger(sting, stingRunLt, stingChargeLt, xcon);
        driveMode = true;   //default to tank drive
        lastTime = 0;
        lastTime2 = 0;
        tipping = false;
        compstate = false;
        climbing = false;
	lefte = new Encoder(7,8);
	righte = new Encoder(9,10);
	lefte.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
	righte.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
	leftc = new PIDController(0.2, 0.3, 0.2, lefte, leftm);
	rightc = new PIDController(0.2, 0.3, 0.2, righte, rightm);
        //TestA = new AnalogChannel(1);
	//Victor driveleft;
	// Victor driveright;	
	//Joystick joy;
	//Victor upperseg;
        // Check ports on everything later
	// driveleft = new Victor(1);
	// driveright = new Victor(2);
	// joy = new Joystick(2);
	// upperseg = new Victor(1);
	// upperseg.set(0);
	//Victor driveleft;
	//Victor driveleft;
	// Victor driveright;
	//Joystick joy;
	//Victor upperseg;
	//  driveleft = new Victor(1);
	//    driveright = new Victor(2);
	//  joy = new Joystick(2);
	//  upperseg = new Victor(1);
	// upperseg.sept(0);
    }

    public void driveStraight(double distance) {
	double rate = 5;
	righte.reset(); // sets encoder count to 0
	lefte.reset();
	rightc.setSetpoint(rate); // sets each PID controller to manage the speed and keep it close to rate
	leftc.setSetpoint(rate);
	
	while(Math.abs(distance - righte.get()) > 5 || Math.abs(distance - lefte.get()) > 5) { // do this until the robot has moved distance
	    Timer.delay(4/1000);
	}
    }
    
    public void auto() {
	// driveleft.set(.22);
        // Timer.delay(4/1000);
    }
    
    
    public void tele() {
       if(rightJoy.getRawButton(10) && !climbing) climbing = true;
       else if(rightJoy.getRawButton(10) && climbing) climbing = false;
       
       if(!climbing) {
         // drive code
        if (leftJoy.getRawButton(11) && (Timer.getFPGATimestamp()-lastTime) > 2000) {
            driveMode = !driveMode;
            lastTime = Timer.getFPGATimestamp();
        }
       
        if (driveMode) {
            drive.drive();
        }

        else {
            drive.arcadeDrive();
        }
       }
       else {
        // climber code
        double h = rightJoy.getY();
        double f = leftJoy.getY();
        System.out.println("right joy " + h + "     left joy "  + f);
        if(Math.abs(h) < 0.15) {
         climbHands.set(0);
        }
        else {
            if(h > 0) climbHands.set(h - 0.15);
            else climbHands.set(h + 0.15);
        }
        if(Math.abs(f) < 0.15) {
            climbFeet.set(0);
        }
        else {
           if(f > 0) climbFeet.set(f - 0.15);
           else climbFeet.set(f + 0.15);
        }
        System.out.println(climbHands.get() + "     " +climbFeet.get());
       }
       
       // compressor code
       /*if(leftJoy.getRawButton(7) && (Timer.getFPGATimestamp()-lastTime2) > 2000) {
	   compstate = !compstate;
                     lastTime = Timer.getFPGATimestamp();
       }*/
       
        if(rightJoy.getRawButton(10) && (Timer.getFPGATimestamp()-lastTime2) > 2000 && !stingCompress.enabled()) {
	  stingCompress.start();
                     lastTime = Timer.getFPGATimestamp();
       }
        else if (rightJoy.getRawButton(10) && (Timer.getFPGATimestamp()-lastTime2) > 2000 && stingCompress.enabled())
        {
            stingCompress.stop();
             lastTime = Timer.getFPGATimestamp();
        }
       
     
      /* if(compstate != stingCompress.enabled()) {
	   if(compstate) {
	       stingCompress.start();
	   }
	   else {
	       stingCompress.stop();
	   }
       }*/
       
       if (stingCompress.enabled())
       {
           System.out.println("Compresor polling");
       }
       else
       {
           System.out.println("Compressor not polling");
       }
       
       // stinger code
       if(xcon.getButtonB() && !stinger.isTipped) {
	   stinger.tip();
       }

       double q = xcon.getLeftJoyY();
       double r = xcon.getRightJoyY();
       
       if(Math.abs(q) < 0.15) {
           dumpHigh.set(0);
       }
       else {
           if(q > 0) dumpHigh.set(q - 0.15);
           else dumpHigh.set(q + 0.15);
       }
       
       if(Math.abs(r) < 0.15) {
           dumpLow.set(0);
       }
       else {
           if(r > 0) dumpLow.set(r - 0.15);
           else dumpLow.set(r + 0.15);
       }
       
       //dumper code
       /*
       System.out.println(TestA.getVoltage());
      
       if (xcon.getButtonA()) {
	   dumper.LowSlotLoad();
       }
       else if (xcon.getButtonB()) {
	   dumper.LowGoalDump();
       }
       else if (xcon.getButtonX()) {
	   dumper.PyramidClimb();
       }
       else if (xcon.getButtonY()) {
	   dumper.PyramidDump();
       }
       else if (xcon.getButtonLS()) {
	   dumper.MiddleSlotLoad();
       }
       else if (xcon.getButtonRS()) {
	   dumper.ResetOrStop();
       }*/
    }
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() 
    {
        while (isAutonomous()) {
            auto();
            Timer.delay(4/1000);
        }
    }

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
