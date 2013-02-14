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
    
    Solenoid solen;
    Solenoid chargeLight;
    Solenoid runLight;
    DigitalInput manSwitch;
    boolean lastSwitch;
    boolean runLightSet;
    public boolean isTipped;
    XBoxController xcon;

    public Stinger(Solenoid sol, Solenoid chargeLt, Solenoid runLt, XBoxController x) {
//        compress = c;
        solen = sol;
        chargeLight = chargeLt;
        runLight = runLt;
        isTipped = false;
	runLightSet = false;
	xcon = x;
	lastSwitch = xcon.getButtonA();
    }
    
    public void tip() {
        solen.set(true);
        isTipped = true;
    }
}
