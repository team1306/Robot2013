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
    
    public Stinger(Compressor c, DoubleSolenoid sol, Solenoid chargeLt, Solenoid runLt, DigitalInput manSw)
    {
        compress = c;
        solen = sol;
        chargeLight = chargeLt;
        runLight = runLt;
        lastSwitch = manSw.get();
        manSwitch = manSw;
        isTipped = false;
    }
    
    public void runCompressor()
    {
        if (manSwitch.get() == true && manSwitch.get() != lastSwitch)
        {
            compress.start();
            runLightSet = true;
        }
        else if (manSwitch.get() == false && manSwitch.get() != lastSwitch)
        {
            compress.stop();
            runLightSet = false;
        }
        
        lastSwitch = manSwitch.get();
        chargeLight.set(compress.getPressureSwitchValue());
        runLight.set(runLightSet);
    }
    
    public void tip()
    {
        
        isTipped = true;
    }
    
    public void untip()
    {
        
        isTipped = false;
    }
            
    
    Compressor compress;
    DoubleSolenoid solen;
    Solenoid chargeLight;
    Solenoid runLight;
    DigitalInput manSwitch;
    boolean lastSwitch;
    boolean runLightSet;
    public boolean isTipped;
}
