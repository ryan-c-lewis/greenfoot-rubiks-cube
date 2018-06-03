import greenfoot.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/*
 * This is the image that will draw onto the World.
 */
public class Screen extends GreenfootImage
{
    private static final int POINT_RADIUS = 4;
    private static final Color POINT_COLOR = Color.RED;
    
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    
    private Cube _cube;
    
    
    /*
     * We'll manually draw our polygons onto this, and this will be the only image
     * that's displayed onto the CubeWorld.
     */
    public Screen(int pWidth, int pHeight, Cube pCube)
    {
        super(pWidth, pHeight);
        
        _cube = pCube;
    }
    
    
    /*
     * Every time the Controller wants to redraw the screen, it will call this
     */
    public void render()
    {
        clear();
        
        drawBackground();
        drawPlanes(_cube.getPlanes());
    }
    
    
    /*
     * Given a set of points in 3D space, convert them to 2D points from the Camera's perspective
     */
    private ArrayList<Point> project3DPoints(ArrayList<Point3D> p3DPoints)
    {
        ArrayList<Point> projectedPoints = new ArrayList<Point>();
        
        for (Point3D point3D : p3DPoints)
        {
            Point projectedPoint = project3DPoint(point3D);
            projectedPoints.add(projectedPoint);
        }
        
        return projectedPoints;
    }
    
    
    /*
     * Given a point in 3D space, convert it to a 2D point from the Camera's perspective
     */
    private Point project3DPoint(Point3D p3DPoint)
    {
        // You can come up with this equation using proportional triangles.
        double scaleFactor = Camera.FOCAL_LENGTH / (Camera.FOCAL_LENGTH + Camera.Z_DISTANCE + p3DPoint.z());
        
        double projectedX = p3DPoint.x() * scaleFactor * Camera.ZOOM;
        double projectedY = -p3DPoint.y() * scaleFactor * Camera.ZOOM;
        
        return new Point((int)projectedX, (int)projectedY);
    }
    
    
    /*
     * Determine if a set of 2D points are ordered clockwise or not.
     * 
     * Adapted from Roberto Bonvallet's answer here:
     * http://stackoverflow.com/questions/1165647/how-to-determine-if-a-list-of-polygon-points-are-in-clockwise-order
     * 
     * Honestly, I don't know why this works. But it's super simple, so I'm using it.
     */
    private boolean pointsAreClockwise(ArrayList<Point> pPoints)
    {
        int edgeSum = 0;
        
        for (int n = 1; n <= pPoints.size(); n++)
        {
            int indexA = n - 1;
            int indexB = n % pPoints.size(); // usually just n, but needs to be 0 on the last iteration
            
            Point pointA = pPoints.get(indexA);
            Point pointB = pPoints.get(indexB);
            
            edgeSum += (pointB.x - pointA.x) * (pointB.y + pointA.y);
        }
        
        return (edgeSum <= 0);
    }
    
    
    /*
     * Draw the background color onto the image.
     */
    private void drawBackground()
    {
        setColor(BACKGROUND_COLOR);
        fillRect(0, 0, getWidth(), getHeight());
    }
    
    
    /*
     * Draw a polygon out of a set of points (ordered clockwise if facing the camera).
     */
    private void drawPolygon(ArrayList<Point> pPoints, Color pColor)
    {
        if (pointsAreClockwise(pPoints))
        {
            int[] xPoints = new int[pPoints.size()];
            int[] yPoints = new int[pPoints.size()];
            
            int halfScreenWidth = getWidth() / 2;
            int halfScreenHeight = getHeight() / 2;
            
            for (int n = 0; n < pPoints.size(); n++)
            {
                Point point = pPoints.get(n);
                xPoints[n] = point.x + halfScreenWidth;
                yPoints[n] = point.y + halfScreenHeight;
            }
            
            setColor(pColor);
            fillPolygon(xPoints, yPoints, pPoints.size());
        }
    }
    
    
    /*
     * Draw an outline of a polygon out of a set of points (ordered clockwise if facing the camera).
     */
    private void drawOutline(ArrayList<Point> pPoints, Color pColor)
    {
        if (pointsAreClockwise(pPoints))
        {
            int halfScreenWidth = getWidth() / 2;
            int halfScreenHeight = getHeight() / 2;
            
            setColor(pColor);
            for (int n = 1; n <= pPoints.size(); n++)
            {
                Point firstPoint = pPoints.get(n - 1);
                Point secondPoint = pPoints.get(n % pPoints.size());
                drawLine(
                    firstPoint.x + halfScreenWidth,
                    firstPoint.y + halfScreenHeight,
                    secondPoint.x + halfScreenWidth,
                    secondPoint.y + halfScreenHeight);
            }
        }
    }
    
    
    /*
     * Draw a set of 2D points onto the screen.
     */
    private void drawPoints(ArrayList<Point> pPoints)
    {
        for (Point point : pPoints)
        {
            drawPoint(point, POINT_COLOR);
        }
    }
    
    
    /*
     * Draw a set of planes onto the screen
     */
    private void drawPlanes(ArrayList<Plane> pPlanes)
    {
        Collections.sort(pPlanes);
        
        for (Plane plane : pPlanes)
        {
            ArrayList<Point3D> points = plane.points();
            ArrayList<Point> projectedPoints = project3DPoints(points);
            
            Color color = plane.color().rgb();
            drawPolygon(projectedPoints, color);
            drawOutline(projectedPoints, PlaneColor.GRAY_RGB);
        }
    }
    
    
    /*
     * Draw a 2D point onto the screen at a given coordinate.
     */
    private void drawPoint(Point pPoint, Color pColor)
    {
        int halfScreenWidth = getWidth() / 2;
        int halfScreenHeight = getHeight() / 2;
        
        int centerPositionX = pPoint.x - POINT_RADIUS + halfScreenWidth;
        int centerPositionY = pPoint.y - POINT_RADIUS + halfScreenHeight;
        int diameter = POINT_RADIUS * 2;
        
        setColor(pColor);
        fillOval(centerPositionX, centerPositionY, diameter, diameter);
    }
}
