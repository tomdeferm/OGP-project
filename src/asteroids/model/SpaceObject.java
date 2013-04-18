package asteroids.model;



import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import asteroids.Util;

/**
 * A class of space objects: ships, asteroids and bullets.
 * 
 * @invar The coordinates of each space object must be valid.
 * 		  | isValidCoordinate(getX()) && isValidCoordinate(getY())
 * 
 * @invar The radius of each space object must be valid.
 * 		  | isValidRadius(getRadius())
 * 
 * @invar The velocity of each space ship must be valid.
 * 		  | isValidVelocity()
 * 
 * 
 * @author Tom
 * @version 0.1
 */
public abstract class SpaceObject {
	
	/**
	 * Initializes a space object with a given position, velocity and radius.
	 * @param x
	 * 		  The initial x-coordinate of the space object, expressed in km.
	 * @param y
	 * 		  The initial y-coordinate of the space object, expressed in km.
	 * @param xVelocity
	 * 		  The initial velocity in the x-direction the space object will have, expressed in km/s.
	 * @param yVelocity
	 * 		  The initial velocity in the y-direction the space object will have, expressed in km/s.
	 * @param radius
	 * 		  The space object's radius, expressed in km.
	 * 
	 * @post The given x-coordinate is set as the new x-coordinate of the space object.
	 * 		 |new.getX().equals(x)
	 * @post The given y-coordinate is set as the new y-coordinate of the space object.
	 * 		 |new.getY().equals(y)
	 * @post The given xVelocity is set as the new xVelocity of the space object.
	 * 		 |new.getXVelocity().equals(xVelocity)
	 * @post The given yVelocity is set as the new yVelocity of the space object.
	 * 		 |new.getYVelocity().equals(yVelocity)
	 * @post The given radius is set as the new radius of the space object.
	 * 		 |new.getRadius().equals(radius)
	 * 
	 * @throws IllegalArgumentException
	 * 		   If the given radius isn't valid.
	 * 		   |isValidRadius(getRadius())
	 *
	 */
	public SpaceObject(double x, double y, double xVelocity,
			double yVelocity, double radius) throws IllegalArgumentException {
		
		setXPos(x);
		setYPos(y);
		setXVelocity(xVelocity);				 
		setYVelocity(yVelocity);	
		this.speedLimit = 300000;							
		if (!isValidVelocity()) this.makeVelocityValid(xVelocity, yVelocity);
		if (isValidRadius(radius)) this.radius = radius; 
		else {
				throw new IllegalArgumentException ("The provided radius is either not a number or is too small.");
		}
		}
	
	/**
	 * @return The space object's current x-coordinate in km.
	 */
	@Basic
	public double getX() {
		
		return xPos;
	}
	
	/**
	 * @return The space object's current y-coordinate in km.
	 */
	@Basic
	public double getY() {
		
		return yPos;
	}
	
	
	
	
		
	/**
	 * Sets a new x-coordinate for this space object.
	 * @param newXPos
	 * 		  The new x-coordinate for this space object.
	 * @throws IllegalArgumentException
	 * 		   When the new x-coordinate isn't valid.
	 * 		   | (! isValidCoordinate(newXPos))
	 */
	public void setXPos(double newXPos) throws IllegalArgumentException {
		if(!isValidCoordinate(newXPos)) throw new IllegalArgumentException();
		else xPos = newXPos;	
	}
		
	/**
	 * Sets a new y-coordinate for this space object.
	 * @param newYPos
	 * 		  The new y-coordinate for this space object.
	 * @throws IllegalArgumentException
	 * 		   When the new y-coordinate isn't valid.
	 * 		   | (! isValidCoordinate(newYPos))
	 */
	public void setYPos(double newYPos) throws IllegalArgumentException {
		if(!isValidCoordinate(newYPos)) throw new IllegalArgumentException();
		else yPos = newYPos;
	}
			
	/**
	 * Checks whether the coordinate is a valid coordinate.
	 * @param coordinate
	 * @return True if the coordinate is a number, false if the coordinate isn't.
	 */
	private boolean isValidCoordinate(double coordinate) {
			
		if(Double.isNaN(coordinate)) return false;
		else return true;
			
	}
	
	/**
	 * The space object's current position along the x-axis.
	 */
	protected double xPos;
	
	/**
	 * The space object's current position along the y-axis.
	 */
	protected double yPos;
	
	/**
	 * Moves the space object for a duration dt according to its current state.
	 * The space object will be moved according to its current position and its current velocity during the specified duration <code>dt</code>.
	 * The specified duration <code>dt</code> can never be less than zero.
	 * If the space object is a ship, the ship will thrust.
	 * @param dt Specifies the duration of the move command.
	 * @post The new xPos (yPos) is equal to the old xPos (yPos) changed by the xVelocity (yVelocity) multiplied by the given duration. 
	 * 		| new this.getX() = this.getX() + getXVelocity*dt 
	 * 		| new this.getY() = this.getY() + getYVelocity*dt	 
	 */
	
	public void move(double dt){	
		
		if (dt>0){
		setXPos(getX() + this.getXVelocity()*dt);
		setYPos(getY() + this.getYVelocity()*dt);
		
		if (this instanceof Ship) {
			Ship ship = (Ship) this;
			ship.thrust(dt);
		}}
		
		
		
	}
	
	
	/**
	 * @return The space object's current velocity in the x-direction(km/s).
	 */
	@Basic
	public double getXVelocity() {
		
		return xVelocity;
	}
	/**
	 * @return The space object's current velocity in the y-direction(km/s).
	 */
	@Basic
	public double getYVelocity() {
		
		return yVelocity;
	}
	
	/**
	 * @return The space object's total velocity (in km/s).
	 */
	public double getVelocity() {
		
		return Math.sqrt(Math.pow(getXVelocity(),2)+Math.pow(getYVelocity(),2));
	}
	
	/**
	 * @return The space object's maximum speed.
	 */
	@Basic
	public double getSpeedLimit() {
		
		return speedLimit;
		
	}
	
	/**
	 * Sets the given xVelocity as the new xVelocity of the space object.
	 * @param xVelocity
	 * 		  The new xVelocity of the ship.
	 */
	@Raw
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	/**
	 * Sets the given yVelocity as the new yVelocity of the space object.
	 * @param yVelocity
	 * 		  The new yVelocity of the ship.
	 */
	@Raw
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Checks whether the velocity of the space object is valid.
	 * @return True if the velocity does not exceed the speedlimit, false if it does.
	 */
	public boolean isValidVelocity()
	{
		return (Util.fuzzyLessThanOrEqualTo(this.getVelocity(), this.getSpeedLimit()));
	}
	
	/**
	 * This method reduces a space object's velocity to make it comply with it's speed limit without changing the direction of movement.
	 * @param xVelocity The x-velocity the space object currently possesses.
	 * @param yVelocity The y-velocity the space object currently possesses.
	 * @post The new getXVelocity (getYVelocity) satisfies the upper speed limit of the space object and the direction of movement is the same as before.
	 * 		 | new getVelocity() <= this.speedLimit
	 * 		 | new atan2(getYVelocity(),getXVelocity()) = atan2(getYvelocity(), getXVelocity)	
	 */
	@Raw @Model
	public void makeVelocityValid(double Xvelocity, double Yvelocity) {
		
		double angleOfMovement = Math.atan2(Xvelocity, Yvelocity);
		double xvelocity = speedLimit*Math.cos(angleOfMovement);
		double yvelocity = speedLimit*Math.sin(angleOfMovement);
		setXVelocity(xvelocity);
		setYVelocity(yvelocity);
		
	}
	
	/**
	 * The space object's current velocity in the x-direction.
	 */
	protected double xVelocity;
	
	/**
	 * The space object's current velocity in the x-direction.
	 */
	protected double yVelocity; 
	
	/**
	 * The space object's maximum speed, cannot exceed the speed of light.
	 */
	protected double speedLimit;
	
	/**
	 * @return The world containing this object, null if this space object has no world.
	 */
	@Basic @Raw
	public World getWorld() {
		return world;
	}
	
	/**
	 * Puts this object in a particular world.
	 * @param world 
	 * 		  The world in which this object will be placed.
	 */
	@Basic @Raw
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Removes this object from his world.
	 */
	@Basic @Raw
	public void removeWorld () {
		this.world = null;
	}
	
	/**
	 * The world in which this object is placed. Null if this object doesn't have a world.
	 */
	private World world = null;
	
	/**
	 * @return The space object's radius (in km).
	 */
	@Basic @Immutable
	public double getRadius() {
		
		return radius;
	}
	
	/**
	 * @param secondObject
	 * @return The sum of the radii of this space object and secondObject. 
	 */
	private double getSumOfRadii(SpaceObject secondObject) {
		return this.getRadius() + secondObject.getRadius();
	}

	/**
	 * Checks whether the radius of this space object is valid.
	 * @param radius
	 * @return True if the radius is a number and greater than the minimumRadius.
	 */
	private boolean isValidRadius(double radius) {
		if(Double.isNaN(radius) || !Util.fuzzyLessThanOrEqualTo(minimumRadius, radius)) return false;
		else return true;
	}
	
	/**
	 * The minimum radius a space object must have.
	 */
	protected double minimumRadius = 0;
	
	/**
	 * The space object's radius (in km).
	 */
	protected double radius;
	
	/**
	 * @return Total mass of the space object.
	 */
	@Basic @Immutable
	public abstract double getMass();
	
	/**
	 * The current mass of the space object.
	 */
	protected double mass;
	
	/**
	 * Returns the distance between this space object and the given space object. This distance is the shortest distance the space objects would have to move
	 * to be adjacent to each other. When the given space object is the same as this space object, the distance is zero, when the space objects overlap, the distance is negative.
	 * @param secondObject The space object to which the distance should be calculated.
	 * @return The distance between the 2 space objects. The distance is negative if and only if the space objects overlap.
	 */
	public double getDistanceBetween(SpaceObject secondObject) {
		
		double centerDistanceSquared = Math.pow(secondObject.getX()-this.getX() , 2) + Math.pow(secondObject.getY()-this.getY(), 2);
		return Math.sqrt(centerDistanceSquared);
		
	}

	
	
	/**
	 * Returns true if and only if this space object overlaps with the given space object. A space object always overlaps with itself. Two adjacent space objects are considered to overlap.
	 * @param secondObject The space object to check for overlap with.
	 * @return True if this space object overlaps with the given space object, False if the ships do not overlap.
	 */
	public boolean overlap(SpaceObject secondObject) {
		
		double distance = this.getDistanceBetween(secondObject);
		double sumOfRadius = getSumOfRadii(secondObject);
		
		if(distance < sumOfRadius)	{
			return true;
		}
		
		else return false;
		
	}
	
	
	/**
	 * Returns the time left (in seconds) before this space object collides for the first time with the given space object or Double.POSITIVE_INTINITY if they never collide.
	 * A space object can never collide with itself.
	 * @param secondObject The space object to which the collision time should be calculated.
	 * @return The time, in seconds, to collision with the given space object.
	 * 		   Double.POSITIVE_INFINITY if they don't collide.
	 */
	
	public double getTimeToCollision(SpaceObject secondObject) {
		if (secondObject == null) throw new NullPointerException();
		
		else {
			double sigmaSquared = Math.pow(getSumOfRadii(secondObject), 2);
			double deltaVSquared = Math.pow(secondObject.getXVelocity() - this.getXVelocity(), 2) 
								 + Math.pow(secondObject.getYVelocity() - this.getYVelocity(), 2);	
			double deltaRSquared = Math.pow(secondObject.getX() - this.getX(), 2) + Math.pow(secondObject.getY() - this.getY(), 2);
			double deltaVDeltaR = (secondObject.getXVelocity() - this.getXVelocity())*(secondObject.getX() - this.getX()) 
								+ (secondObject.getYVelocity() - this.getYVelocity())*(secondObject.getY() - this.getY());
			double d  = Math.pow(deltaVDeltaR, 2) - deltaVSquared*(deltaRSquared - sigmaSquared);
			double deltaT;
			if(Util.fuzzyEquals(deltaVDeltaR, 0) || deltaVDeltaR > 0 ) deltaT = Double.POSITIVE_INFINITY;
			else if (Util.fuzzyLessThanOrEqualTo(d, 0)) deltaT = Double.POSITIVE_INFINITY;
			else deltaT = -(deltaVDeltaR + Math.sqrt(d))/(deltaVSquared);
			return deltaT;
		}
	}
	
	/**
	 * Returns the position of the collision with the given space object or Double.POSITIVE_INTINITY if they never collide.
	 * A space object can never collide with itself.
	 * @param secondObject The space object to which the collision position should be calculated.
	 * @return The position of the collision with the given space object.
	 */
	
	public double[] getCollisionPosition(SpaceObject secondObject) {
		if (secondObject == null) throw new NullPointerException();
		else {
			double deltaT = getTimeToCollision(secondObject);
			if(deltaT == Double.POSITIVE_INFINITY) return null;
			else {
				double[] collisionPos = new double[2];
				double theta = Math.atan2(secondObject.getY()-this.getY(), secondObject.getX()-this.getX());
				if ((secondObject.getX()-this.getX())<0) {
					theta+=Math.PI*2;
				}
				collisionPos[0] = this.getX() + this.radius * Math.cos(theta);
				collisionPos [1] = this.getY() + this.radius * Math.sin(theta);
				return collisionPos;
		}
		
		}
		
	}
	
	/**
	 * Resolves the collision between two ships or two asteroids by bouncing them off each other.
	 * @param secondObject
	 * 				The second object with which this object collides.
	 * 
	 * @post The speed of this object will be updated to resolve the collision.
	 * 			|new.getXVelocity() = 
	 */
	
	public void resolveCollision (SpaceObject secondObject) {
		
		if (secondObject == null) { throw new NullPointerException(); }
		
		assert(!secondObject.equals(this));
		
		double sigma = getSumOfRadii(secondObject);
		double deltaRx = secondObject.getX()-this.getX();
		double deltaRy = secondObject.getY()-this.getY();
		double deltaVx = secondObject.getXVelocity()- this.getXVelocity();
		double deltaVy = secondObject.getYVelocity()-this.getYVelocity();
		double deltaVR = (deltaVx*deltaRx)+(deltaVy*deltaRy);
	
		double J = (2*this.getMass()*secondObject.getMass()*deltaVR)/(sigma*(this.getMass()+secondObject.getMass()));
		double Jx = (J*deltaRx)/(sigma);
		double Jy = (J*deltaRy)/(sigma);
		setXVelocity(this.getXVelocity()+(Jx/this.getMass()));
		setYVelocity(this.getYVelocity()+(Jy/this.getMass()));
		secondObject.setXVelocity(secondObject.getXVelocity()-(Jx/secondObject.getMass()));
		secondObject.setYVelocity(secondObject.getYVelocity()-(Jy/secondObject.getMass()));
		if (!Util.fuzzyLessThanOrEqualTo(this.getVelocity(), this.getSpeedLimit() )) this.makeVelocityValid(xVelocity, yVelocity);
		if (!Util.fuzzyLessThanOrEqualTo(secondObject.getVelocity(), secondObject.getSpeedLimit() )) secondObject.makeVelocityValid(xVelocity, yVelocity);
			
	}
		
		
		
	
	/**
	 * Terminates this object. The object will be removed from its current world.
	 * 
	 * @post If this object is in a world, it will be removed from that world.
	 * 			| getWorld().removeObject(this)
	 * 			| removeWorld()
	 */
	protected void Die() {
		
		if(this.getWorld() != null) {
		this.getWorld().removeObject(this);
		this.removeWorld();
		}
		else {throw new NullPointerException();}
		
			
	}
	
	/**
	 * Returns the time (in seconds) when the object will collide with a boundary of his world.
	 * @return Positive_infinity if the object doesn't collide with a boundary.
	 * 			| if (getXvelocity() == 0 && getYVelocity() == 0)
	 * 			| then result = Double.POSITIVE_INFINITY
	 * @return The time (in seconds) when the object will collide with a boundary of his world.
	 * 			| 
	 */
	public double getTimeToBoundaryCollision() {
		
		double worldwidth = getWorld().getWorldWidth();
		double worldheight = getWorld().getWorldHeight();
		
		double timeToBoundaryX;
		double timeToBoundaryY;

		if(this.getYVelocity() < 0) {
			
			timeToBoundaryY = Math.abs((getY()- getRadius())/(getYVelocity()));}
		
		else if (getYVelocity() > 0) {
			
			timeToBoundaryY = Math.abs((worldheight - getY() - getRadius())/(getYVelocity()));
		}
		
		else {
			timeToBoundaryY = Double.POSITIVE_INFINITY;
		}
		
		if(this.getXVelocity() < 0) {
			
			timeToBoundaryX = Math.abs((getX()- getRadius())/(getXVelocity()));}
		
		else if (getXVelocity() > 0) {
			
			timeToBoundaryX = Math.abs((worldwidth - getX() - getRadius())/(getXVelocity()));
		}
		
		else {
			timeToBoundaryX = Double.POSITIVE_INFINITY;
		}
		
		
		
		
		return Math.min(timeToBoundaryX, timeToBoundaryY);
		
	}
	
	public void resolveBoundaryCollision()
	{
		
		if(Math.abs(getX() - getRadius()) < Util.EPSILON || 
				Math.abs(getX() + getRadius() - getWorld().getWorldWidth()) < Util.EPSILON) {
			
			setXVelocity(-(this.getXVelocity()));}
		
		else {
			setYVelocity(-(this.getYVelocity()));}
		
		makeVelocityValid(getXVelocity(),getYVelocity());
		
	}
	
	/**
	 * Resolves the collision between this space object and another space object.
	 * @param collisionobject
	 * 				The space object in collision with this space object.
	 */
	public abstract void resolve(SpaceObject collisionobject);
	
}
		
		
		
		
		
		

			
		
	
	
		
	
	
	
		


