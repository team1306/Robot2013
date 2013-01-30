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
    XBoxController xcon;
    Joystick left;
    Joystick right;
    TankDrive drive;
    //Victor driveleft;
   // Victor driveright;
    //Joystick joy;
    //Victor upperseg;
    public Robot() {
        xcon = new XBoxController(3);
        left = new Joystick (1);
        right = new Joystick (2);
        drive = new TankDrive(1, 2, left, right);
      //  driveleft = new Victor(1);
    //    driveright = new Victor(2);
      //  joy = new Joystick(2);
      //  upperseg = new Victor(1);
       // upperseg.set(0);
    }
    
    public void auto()
    {
       // driveleft.set(.22);
        // Timer.delay(4/1000);
    }
    
    
    public void tele()
    {
        if(right.getTrigger()) {
            drive.arcadeDrive();
        }
        else {
            drive.drive();
        }
    /*    double l = left.getY();
        double r = right.getY();
        System.out.println(l + ", " + r);
        if(Math.abs(l) < 0.1) {
            driveleft.set(0);
        }
        else {
            driveleft.set(-l);
        }
        if(Math.abs(r) < 0.1) {
            driveright.set(0);
        }
        else {
            driveright.set(r);
        }*/
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() 
    {
        while (isAutonomous())
        {
        auto();
        Timer.delay(4/1000);
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
           /* if (joy.getTrigger()) { 
                upperseg.set((-joy.getZ()+1)/2);
                System.out.println("running");
            }
            else { 
            }                upperseg.set(0.0000);
             */
            Timer.delay(3/1000);
        }
    }
}
