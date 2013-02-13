/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.badgerbots;
import edu.wpi.first.wpilibj.*;
import org.badgerbots.lib.*;

/**
 *
 * @author Badge
 */
public class Stinger {
    
    Compressor compress;
    Solenoid solen;
    Solenoid chargeLight;
    Solenoid runLight;
    DigitalInput manSwitch;
    boolean lastSwitch;
    boolean runLightSet;
    public boolean isTipped;
    XBoxController xcon;

    public Stinger(Compressor c, Solenoid sol, Solenoid chargeLt, Solenoid runLt, XBoxController x)
    {
        compress = c;
        solen = sol;
        chargeLight = chargeLt;
        runLight = runLt;
        isTipped = false;
	runLightSet = false;
	xcon = x;
	lastSwitch = xcon.getButtonA();
    }
    
    public void runCompressor()
    {
        if (xcon.getButtonA() == true && xcon.getButtonA() != lastSwitch)
        {
            compress.start();
            runLightSet = true;
        }
        else if (xcon.getButtonA() == false && xcon.getButtonA() != lastSwitch)
        {
            compress.stop();
            runLightSet = false;
        }
        
        lastSwitch = xcon.getButtonA();
        chargeLight.set(compress.getPressureSwitchValue());
        runLight.set(runLightSet);
    }
    
    public void tip()
    {
        solen.set(true);
        isTipped = true;
    }
}
