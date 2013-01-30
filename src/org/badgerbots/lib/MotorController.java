package org.badgerbots.lib;

import edu.wpi.first.wpilibj.*;

/**
 * Provides a simple interface for ramping up motor speeds at a give rate
 * @author Badge
 */
public class MotorController
{
	private double endTime;
	private Timer timer;
	private SpeedController motor;
	private final double rate;
	private double speed = 0, oldSpeed = 0, mspeed = 0;

	/**
	 *
	 * @param motor
	 * @param rate the acceleration the controller will use. for example,
	 *        a rate of 0.1 will increase the Jaguar/Victor speed by 0.1 every
	 *        second.
	 */
	public MotorController(SpeedController motor, double rate)
	{
		this.motor = motor;
		this.rate = rate;
		timer = new Timer();
	}

	/**
	 * call this function regularly to update the motor speeds
	 */
	public void step()
	{
		if (Math.abs(mspeed - speed) < 0.01)
			mspeed = speed;
		else
			mspeed = oldSpeed + (speed - oldSpeed) / rate * timer.get();

		motor.set(mspeed);
	}

	/**
	 * @param speed the new motor speed to work towards
	 */
	public void set(double speed)
	{
		if (this.speed == speed)
			return;
		timer.start();
		oldSpeed = mspeed;
		this.speed = speed;
	}

	public boolean accelerating()
	{
		return accelerating(0.05);
	}

	public boolean accelerating(double error)
	{
		return motor.get() > speed - error && motor.get() < speed + error;
	}
}
