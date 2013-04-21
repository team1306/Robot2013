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
    Victor climbHands;
    Compressor stingCompress;
    //DoubleSolenoid sting;
    //Solenoid stingRunLt;
    //Solenoid stingChargeLt;
    //DigitalInput stingSw;
    //Stinger stinger;
    boolean driveMode;
    double driveModeLastTime;
    double compressorLastTime;
    //boolean compstate;
    //boolean tipping;
    //boolean climbing;
    //Servo blocker;
    Timer clock;
    int startTime;
    //double startClock;
    Encoder righte;
    PIDController P;
    AnalogChannel analog;
    double voltage;
    double T1, T2;
    Timer climberTimer;
    DigitalInput limit;
    double autostart;
    PneumaticClimber pneumaticClimber;
    DoubleSolenoid climbPneumatic;
    
    public Robot() {
        startTime = 0;
        T1 = 10;
        T2 = 10;
        climberTimer = new Timer();
        clock = new Timer();
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        xcon = new XBoxController(3);
        leftm = new Jaguar(1);
        rightm = new Jaguar(2);
        drive = new TankDrive(leftm, rightm, leftJoy, rightJoy);
        dumpHigh = new Victor (3);
        //climbHands = new Victor(5);
        stingCompress = new Compressor(5,1);
        stingCompress.start();
        //sting = new DoubleSolenoid (2,1);
        //stingRunLt = new Solenoid(3);
        //stingChargeLt = new Solenoid(4);
        //stingSw = new DigitalInput(6);
        //stinger = new Stinger(sting, stingRunLt, stingChargeLt, xcon);
        climbPneumatic = new DoubleSolenoid (2,1);
        pneumaticClimber = new PneumaticClimber(climbPneumatic);
        compressorLastTime = 0;
        //climbing = false;
        //blocker = new Servo(9);
        analog = new AnalogChannel(3);
        limit = new DigitalInput(1);
        autostart = 0;
    }
    
    public void auto()
    {
       // System.out.println(analog.getVoltage());
        if (voltage > 2.33333)
        {
            if ((Timer.getFPGATimestamp() - autostart) < 8)
            {
                    System.out.println("Timestamp: " +(Timer.getFPGATimestamp() - autostart)); 
                    double steer = voltage - 2.33333;
                    if (steer < 1.333335)
                    {
                        leftm.set(.3);
                        rightm.set(-((.075 * steer) +.2)); // .1 should be .2, otherwise it begins to turn.
                    }
                    else
                    {
                        double steer2 = steer - 1.3333335;
                         rightm.set(-.3);
                        leftm.set((.075 * steer2) +.2);
                    }
            }
            else if ((Timer.getFPGATimestamp() - autostart) <10 && ((Timer.getFPGATimestamp() - autostart) > 8))
            {
                rightm.set(0);
                leftm.set(0);
                dumpHigh.set(-.15);
            }
            else if (((Timer.getFPGATimestamp() - autostart) > 10) && ((Timer.getFPGATimestamp() - autostart) < 12))
            {
              dumpHigh.set(.3);  
              leftm.set(0);
              rightm.set(0);
            }
            else
            {
                dumpHigh.set(0);
                leftm.set(0);
                rightm.set(0);
            }
        }
        else if (voltage < 1)
        {
            Timer.delay(3/1000);
        }
        else
        {
            if (((Timer.getFPGATimestamp() - autostart) > 10) && ((Timer.getFPGATimestamp() - autostart) < 12))
            {
                dumpHigh.set(.3);
            }
        }
        if (!pneumaticClimber.isExtended) {
            pneumaticClimber.extend();
        }
        // deploy the hands and dumper
       //System.out.println(Timer.getFPGATimestamp() - autostart);
//        if(limit.get() && ((Timer.getFPGATimestamp() - autostart) < T2))
//        {
//                climbHands.set(1);
//         }
//        else 
//        {
//            climbHands.set(0);
//        }
      //  System.out.println(analog.getVoltage());
    }
    
    
    public void tele()
    {
       drive.drive();
       
       // Pneumatic climber code
       if (xcon.getButtonB() && !pneumaticClimber.isExtended) {
           pneumaticClimber.extend();
       }
       if (xcon.getButtonX() && pneumaticClimber.isExtended) {
           pneumaticClimber.retract();
       }
       
        // climber code
//        double h = -xcon.getRightJoyY();
//        if(Math.abs(h) < 0.15) {
//         climbHands.set(0);
//        }
//        else{
//            if(h > 0) {
//                if(limit.get())
//                {
//                    climbHands.set(h - 0.15);
//                }
//                else
//                {
//                    climbHands.set(0);
//                }
//            }
//            else
//            {
//                climbHands.set(h + 0.15);
//            }
//       }
       
       // stinger code
//       if(xcon.getButtonB() && !stinger.isTipped /*&& climbing*/) 
//       {
//            stinger.tip();
//       }
//       if (xcon.getButtonX() && stinger.isTipped /*&& climbing*/)
//       {
//            stinger.untip();
//       }
       // dumper code
       double q = xcon.getLeftJoyY();
       
       if(Math.abs(q) < 0.15)
       {
           dumpHigh.set(0);
       }
       else if(q > 0)
       {
               dumpHigh.set((q - 0.15)/2.5);
       }
       else 
       {
               dumpHigh.set((q + 0.15)/6);
       }     
       //System.out.println(analog.getVoltage());
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() 
    {
        clock.start();
        autostart = Timer.getFPGATimestamp();
        voltage = analog.getVoltage();
        System.out.println("Starting Voltage: " + voltage);
        while(isAutonomous())
        {
            auto();
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl()
    {
        while(isOperatorControl())
        {
            tele();
            Timer.delay(3/1000);
        }
    }
}
