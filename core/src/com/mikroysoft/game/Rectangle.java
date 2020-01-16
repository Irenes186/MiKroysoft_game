package com.mikroysoft.game;

public class Rectangle {
    private Coordinate centrePoint;
    private int width, height;
    private float angle;

    public Rectangle (Coordinate centrePoint, int width, int height, float angle) {
        this.centrePoint = centrePoint;
        this.width = width;
        this.height = height;
    }
    boolean intersect(Rectangle rectangle) {
        if (this.centrePoint.x < rectangle.centrePoint.x + rectangle.width / 2 &&
                this.centrePoint.x + this.width / 2 > rectangle.centrePoint.x &&
                this.centrePoint.y < rectangle.centrePoint.y + rectangle.height / 2 &&
                this.centrePoint.y + this.height / 2 > rectangle.centrePoint.y) {
            return true;
                }
        return false;
    }

    boolean pointInRectangle (Coordinate point) {
        float midPoints[] = getMidPoints();
        if (point.x < midPoints[0] && point.x > midPoints[2] && point.y < midPoints[1] && point.y > midPoints[3]) {
            return true;
        }
        return false;
    }
    float[] getMidPoints() {
        float[] points =  {
                this.centrePoint.x + width / 2,
                this.centrePoint.y + height / 2,
                this.centrePoint.x - width / 2,
                this.centrePoint.y - height /2};
        return points;
    }
}
