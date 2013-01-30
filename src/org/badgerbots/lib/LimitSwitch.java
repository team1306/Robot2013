package org.badgerbots.lib;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Max Vrany
 */
public class LimitSwitch
{
	DigitalInput input;
	
	public LimitSwitch (int port)
	{
		input = new DigitalInput (port);
	}

	public boolean get()
	{
		return !input.get();
	}
}
