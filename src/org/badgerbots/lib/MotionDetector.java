
package org.badgerbots.lib;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

/**
 * Provides a simple interface for controlling, reading, and calculating with
 * the accelerometer and gyro.
 *
 * Uses the gyro to calculate the current angle and current running average angle
 * Uses the accelerometer to calculate the current and running average acceleration,
 *   as well as the current velocity and position
 *
 *
 * @author Jon Morton
 */
public class MotionDetector
{

	private Accelerometer2 _accelerometer;
	private Gyro _gyro;

	private double _oldTime;
	
	// raw values
	private double _angle;
	private Point  _accel;
	private Point  _accelZero;
	private Point  _velocity;
	private Point  _position;

	// averages
	private double _avAngle;
	private Point  _avAccel;

	/**
	 * @param gyroPort slot in the analog module where the gyro is plugged in
	 */
	public MotionDetector(int gyroPort)
	{
		_gyro   = new Gyro(gyroPort);
		_accelerometer  = new Accelerometer2();
		reset();
	}

	/**
	 * Sets all data values back to zero, including position and velocity
	 */
	public final void reset()
	{
		_position = new Point();
		_velocity = new Point();

		_accelZero = new Point();
		for (int i = 0; i < 5000; i++)
			_accelZero.add(_accelerometer.getAccel());
		_accelZero = _accelZero.multiply(1/5000.0);
		
		_accel     = _accelZero.copy();
		_avAccel   = _accelZero.copy();
		_avAngle  = _angle;
		_angle    = 0;
	}

	/**
	 * Call this function regularly to update the data from the gyro and
	 * accelerometer, and perform all calculations.
	 * 
	 */
	public void tick()
	{
		_angle         = _gyro.getAngle();
		_accelerometer.getAccel(_accel);
		_accel.isub(_accelZero);

		double curTime = Timer.getFPGATimestamp();
		double dt      = curTime - _oldTime;
		
		_avAccel       = _avAccel.multiply(0.3).add(_accel.multiply(0.7));
		_avAngle       = _avAngle   * 0.3 + _angle   * 0.7;
		_velocity.iadd(_accel.multiply(dt));
		_position.iadd(_velocity.multiply(dt));
		_oldTime = curTime;
	}

	public Point  velocity() { return _velocity.copy(); }
	public Point  position() { return _position.copy(); }

	public Point  accel() { return _accel.copy(); }
	public double angle() { return _avAngle; }

	public Point  rawAccel() { return _accel.copy(); }
	public double rawAngle() { return _angle; }

}
