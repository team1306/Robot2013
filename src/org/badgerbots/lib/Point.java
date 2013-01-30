
package org.badgerbots.lib;

/**
 * Represents a point in 3d space, with an x y and z component.
 * Supports operations to add and multiply these points
 * @author Jon Morton
 */
public class Point
{
	public double x;
	public double y;
	public double z;

	public Point()
	{
		this(0, 0, 0);
	}

	public Point(double ix, double iy, double iz)
	{
		x = ix;
		y = iy;
		z = iz;
	}

	public Point copy()
	{
		return new Point(x, y, z);
	}

	public Point add(Point other)
	{
		return new Point(x + other.x, y + other.y, z + other.z);
	}

	public Point add(double other)
	{
		return new Point(x + other, y + other, z + other);
	}
	public void iadd(Point other)
	{
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public void iadd(double val)
	{
		x += val;
		y += val;
		z += val;
	}

	public void isub(Point other)
	{
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}

	public Point multiply(Point other)
	{
		return new Point(x * other.x, y* other.y, z * other.z);
	}

	public Point multiply(double val)
	{
		return new Point(x * val, y * val, z * val);
	}

	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
