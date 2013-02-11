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
public class Robot extends SimpleRobot
{
    Joystick leftJoy;
    Joystick rightJoy;
    //Climber: 2 Jaguars, 4 Limit Switches, 2 Servos
    //Dumper: 2 Victors, 2 Analogs
    Victor hands;
    Victor feet;
    XBoxController xcon;
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
    boolean tipping;
    AnalogChannel TestA;
    
    
    public Robot()
    {
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        xcon = new XBoxController(3);
        drive = new TankDrive(1, 2, leftJoy, rightJoy);
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
        stingCompress = new Compressor(1, 5);
        sting = new DoubleSolenoid (1, 2);
        stingRunLt = new Solenoid(3);
        stingChargeLt = new Solenoid(4);
        stingSw = new DigitalInput(6);
        stinger = new Stinger(stingCompress, sting, stingRunLt, stingChargeLt, stingSw);
        driveMode = true;   //default to tank drive
        lastTime = 0;
        tipping = false;
        TestA = new AnalogChannel(1);
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
	// upperseg.set(0);
    }
    
    public void auto() {
	// driveleft.set(.22);
        // Timer.delay(4/1000);
    }
    
    
    public void tele() {
        //drive code
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
       
       if(xcon.getRightTrigger()){
	   double h = xcon.getRightJoyY();
	   double f = xcon.getLeftJoyY();
	   if(Math.abs(h) < 0.1) {
	       hands.set(0);
	   }
	   else {
	       hands.set(h);
	   }
	   if(Math.abs(f) < 0.1) {
	       feet.set(0);
	   }
	   else {
	       feet.set(f);
	   }
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
       }
       
       //climber code
       if (!(xcon.getButtonLB() && rightJoy.getRawButton(9))) {
	   if ((xcon.getButtonRB() && rightJoy.getRawButton(10) && !tipping) || stinger.isTipped) {
	       stinger.tip();
	   }
	   if (stinger.isTipped) {
	       //climb code
	   }
       }
       else { //stop method for invalid climb
	   //declimb code
	   stinger.untip();
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
