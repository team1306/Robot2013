/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.badgerbots;
import edu.wpi.first.wpilibj.*;
import org.badgerbots.lib.*;

/**
 *
 * @author Newton Wolfe
 */
public class PneumaticClimber {
    
    DoubleSolenoid solen;
    //Solenoid chargeLight;
    //Solenoid runLight;
    //DigitalInput manSwitch;
    //boolean lastSwitch;
    //boolean runLightSet;
    public boolean isExtended;
    //XBoxController xcon;
    //DoubleSolenoid.Value v;

    public PneumaticClimber(DoubleSolenoid sol) {
//        compress = c;
        solen = sol;
        //chargeLight = chargeLt;
        //runLight = runLt;
        isExtended = false;
	//runLightSet = false;
	//xcon = x;
	//lastSwitch = xcon.getButtonA();
    }
    
    public void extend()
    {
            solen.set(DoubleSolenoid.Value.kReverse);
        isExtended = true;
    }
    
    public void retract()
    {
        solen.set(DoubleSolenoid.Value.kForward);
        isExtended=false;
    }
}
