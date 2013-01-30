/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.badgerbots.lib;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Badge
 */
public class EncoderSys_old
{

	private Encoder _leftEnc;
	private Encoder _rightEnc;
	TankDrive drive;
	public double lasttime;
	public static final double ACCEL = .01;

	public double leftRA;
	public double rightRA;

	double constant = 0.0;
	int offset;

	static final double gain = 0.05;
	
	private static final double FEET_PER_COUNT = 0.0020138414446088418195273355021;
	
	public EncoderSys_old(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel, TankDrive drive)
	{
		_leftEnc = new Encoder(leftAChannel, leftBChannel, true);
		_rightEnc = new Encoder(rightAChannel, rightBChannel, false);
		_leftEnc.setDistancePerPulse(FEET_PER_COUNT);
		_rightEnc.setDistancePerPulse(FEET_PER_COUNT);
		this.drive = drive;
	}

	public EncoderSys_old(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel)
	{
		_leftEnc = new Encoder(leftAChannel, leftBChannel, true);
		_rightEnc = new Encoder(rightAChannel, rightBChannel, false);
		drive = null;
	}

	public EncoderSys_old(Encoder a, Encoder b)
	{
		_leftEnc = a;
		_rightEnc = b;
	}

	public EncoderSys_old(Encoder a, Encoder b, TankDrive drive)
	{
		_leftEnc = a;
		_rightEnc = b;
		this.drive = drive;

	}

	public void start()
	{
		_leftEnc.start();
		_rightEnc.start();
	}

	public void syncDrive(SpeedController left, SpeedController right)
	{
		if(_leftEnc.getRate()<_rightEnc.getRate()&&_leftEnc.getRate()/_rightEnc.getRate()>.02)
		{
			if(_leftEnc.getRate()!=_rightEnc.getRate())
			{
				if (Math.abs(left.get())==1)
					right.set(right.get()-.01);
				else
					left.set(left.get()+.01);
			}
		}
		if (_rightEnc.getRate()<_leftEnc.getRate()&&_leftEnc.getRate()/_rightEnc.getRate()>.02)
		{
			if(_rightEnc.getRate()!=_leftEnc.getRate())
			{
				if (Math.abs(right.get())==1)
					left.set(left.get()-.1);
				else
					right.set(right.get()+.01);
			}
		}
	}

	public void syncDrive ()
	{
		if(_leftEnc.getRate()<_rightEnc.getRate()&&_leftEnc.getRate()/_rightEnc.getRate()>.02)
		{
				if (Math.abs(drive._leftJag.getSpeed())==1)
					drive.setRight(drive.getRight()-.01);
				else
					drive.setLeft(drive.getLeft()+.01);
		}

		if (_rightEnc.getRate()<_leftEnc.getRate()&&_leftEnc.getRate()/_rightEnc.getRate()>.02)
		{
				if (Math.abs(drive._rightJag.get())==1)
					drive.setLeft(drive.getLeft()-.01);
				else
					drive.setRight(drive.getRight()+.01);
		}
	}
	
	public void encDriveDist(double dist)
	{
		encDriveDist(dist, .3);
	}
	
	public void encDriveDist(double dist, double speed)
	{
		if (Math.abs(_leftEnc.getDistance())<=Math.abs(dist) && Math.abs(_rightEnc.getDistance())<=Math.abs(dist))
		{
			if (speed<0)
				drive.set(-speed);
			else
				drive.set(speed);
		}
		else
		{
			drive.set(0);
			System.out.println(getDistance());
		}
	}

	public void resetRate()
	{
		lasttime = Timer.getFPGATimestamp();
		reset();
	}

	public void setRate(double rate)
	{
		double dt = Timer.getFPGATimestamp()-lasttime;
		double rtinc = dt*ACCEL;
		double leftinc = dt*ACCEL;
		if (_leftEnc.getRate() > rate)
			leftinc = -leftinc;
		if (_rightEnc.getRate() > rate)
			rtinc = -rtinc;
		if (Math.abs(_leftEnc.getRate()- rate) > .01)
			drive.setLeft(drive.getLeft() + leftinc);
		if (Math.abs(_rightEnc.getRate()- rate) > .01)
			drive.setRight(-(drive.getRight() + rtinc));


		/*if (_leftEnc.getRate() != rate)
		{
			if (rate > 0 ^ _leftEnc.getRate() > rate)
				drive.setLeft(drive.getLeft() -.01);
			else
				drive.setLeft(drive.getLeft() + .01);
		}
		if (_rightEnc.getRate() != rate)
		{
			if (rate > 0 ^ _rightEnc.getRate() > rate)
				drive.setRight(drive.getRight() -.01);
			else
				drive.setRight(drive.getRight() + .01);
		}*/
		lasttime = Timer.getFPGATimestamp();
	}

	public double getRate()
	{
		return _leftEnc.getRate();
	}

	public double getRate(boolean right)
	{
		if (right)
		{
			return _rightEnc.getRate();
		}
		else
		{
			return _leftEnc.getRate();
		}
	}

	public double getLeftRate()
	{
		return _leftEnc.getRate();
	}

	public double getRightRate()
	{
		return _rightEnc.getRate();
	}

	public double getRateAverage()
	{
		return ((_leftEnc.getRate()+_rightEnc.getRate())/2);
	}

	public double getRateDifference()
	{
		return (_leftEnc.getRate()-_rightEnc.getRate());
	}

	public double getDistance()
	{
		return (_leftEnc.getDistance()+ _rightEnc.getDistance()) / 2;
	}

	public void setStep (double c)
	{
		constant = c;
	}

	public void step()
	{
		drive.setLeft((constant - gain * (_leftEnc.get() -_rightEnc.get() - offset)));
		drive.setRight((constant + gain * (_leftEnc.get() - _rightEnc.get() - offset)));
		System.out.println(_leftEnc.get() + " | " + (-(_rightEnc.get())) + " | " + drive.getLeft() + " | " + drive.getRight());
	}


	public void stop()
	{
		_leftEnc.stop();
		_rightEnc.stop();
	}


	public void reset()
	{
		_leftEnc.reset();
		_rightEnc.reset();
		_leftEnc.start();
		_rightEnc.start();
	}
}
