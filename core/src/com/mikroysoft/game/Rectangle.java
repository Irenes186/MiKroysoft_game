package com.mikroysoft.game;

/**
 * Represents a rectangle with methods to
 * detect where projectiles are in respect to it.
 */
public class Rectangle {
    private Coordinate centrePoint;
    private int width, height;
    private float angle;

    /**
     * Class Constructor.
     * Creates a rectangle given a position, size and angle.
     * 
     * @param centrePoint A coordinate that represents the centre of the rectangle.
     * @param width An integer to define the width in pixels.
     * @param height An integer to define the height in pixels.
     * @param angle An angle in degrees to align the rectangle position.
     */
    public Rectangle (Coordinate centrePoint, int width, int height, float angle) {
        this.centrePoint = centrePoint;
        this.width = width;
        this.height = height;
    }

    /**
     * Checks and returns if the provided rectangle intersects with this rectangle.
     * 
     * @param rectangle The other rectangle Object to check for intersection.
     */
    boolean intersect(Rectangle rectangle) {
        if (this.centrePoint.x < rectangle.centrePoint.x + rectangle.width / 2 &&
                this.centrePoint.x + this.width / 2 > rectangle.centrePoint.x &&
                this.centrePoint.y < rectangle.centrePoint.y + rectangle.height / 2 &&
                this.centrePoint.y + this.height / 2 > rectangle.centrePoint.y) {
            return true;
                }
        return false;
    }

    /**
     * Checks and returns if the provided point is in the rectangle.
     * 
     * @param point The point that is to be checked against this rectangle.
     */
    boolean pointInRectangle (Coordinate point) {
        float midPoints[] = getMidPoints();

        if (point.x < midPoints[0] && point.x > midPoints[2]
                && point.invertY().y < midPoints[1] && point.invertY().y > midPoints[3]) {
            return true;
                }
        return false;
    }

    /**
     * Returns an array of the midpoint coordinate components
     * of the lines that form the rectangle.
     * 
     * @return An array of coordinate components.
     */
    float[] getMidPoints() {
        float[] points =  {
            this.centrePoint.x + width / 2,
            this.centrePoint.y + height / 2,
            this.centrePoint.x - width / 2,
            this.centrePoint.y - height /2};
        return points;
    }

    /**
     * Sets the position of the rectangle to the provided location.
     * 
     * @param newPosition The new centrePoint of the rectangle.
     * @param direction The new angle that the rectangle will
     * point in provided in degrees.
     */
    public void updatePosition (Coordinate newPosition, float direction) {

        this.centrePoint = new Coordinate (newPosition.x - width / 2, newPosition.y - height / 2);
        this.angle = direction;
        this.centrePoint = this.centrePoint.invertY();
    }

    /**
     * Returns the angle the the rectangle points, measured in degrees.
     * 
     * @return The angle in degrees.
     */
    public float getAngle() {
        return angle;
    }
}
