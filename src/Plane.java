import java.util.ArrayList;

/*
 * Represents one of the stickers on a cube.
 * Consists of 4 points (ordered clockwise if facing the camera) and a color.
 */
public class Plane implements Comparable<Plane>
{
    ArrayList<Point3D> _points;
    PlanePosition _position;
    PlaneColor _color;
    
    
    public Plane(ArrayList<Point3D> pPoints, PlanePosition pPosition, PlaneColor pColor) throws IllegalArgumentException
    {
        if (pPoints.size() != 4)
        {
            throw new IllegalArgumentException("Plane must consist of 4 points.");
        }
        
        _points = pPoints;
        _position = pPosition;
        _color = pColor;
    }
    
    
    public ArrayList<Point3D> points()
    {
        return _points;
    }
    
    
    public PlanePosition position()
    {
        return _position;
    }
    
    
    public PlaneColor color()
    {
        return _color;
    }
    
    
    public void setColor(PlaneColor pColor)
    {
        _color = pColor;
    }
    
    
    /*
     * Given a Rotation, return a plane with points that have the Rotation applied to them.
     */
    public Plane getRotatedPlane(Rotation pRotation)
    {
        ArrayList<Point3D> rotatedPoints = new ArrayList<Point3D>();
        
        for (Point3D point : _points)
        {
            Point3D rotatedPoint = Point3D.applyRotation(point, pRotation);
            rotatedPoints.add(rotatedPoint);
        }
        
        return new Plane(rotatedPoints, _position, _color);
    }
    
    
    public static ArrayList<Plane> getBySide(ArrayList<Plane> pPlanes, Side pSide)
    {
        ArrayList<Plane> sidePlanes = new ArrayList<Plane>();
        
        for (Plane plane : pPlanes)
        {
            if (plane.isOnSide(pSide))
            {
                sidePlanes.add(plane);
            }
        }
        
        return sidePlanes;
    }
    
    
    public static Plane getByPosition(ArrayList<Plane> pPlanes, PlanePosition pPosition)
    {
        //System.out.println(pPosition.x() + ", " + pPosition.y() + ", " + pPosition.z());
        
        for (Plane plane : pPlanes)
        {
            PlanePosition thisPosition = plane.position();
            
            if (thisPosition.x() == pPosition.x() &&
                thisPosition.y() == pPosition.y() &&
                thisPosition.z() == pPosition.z())
            {
                return plane;
            }
        }
        
        return null;
    }
    
    
    public boolean isOnSide(Side pSide)
    {
        if (pSide.x() != null)
        {
            
            return _position.x() == pSide.x() || _position.x() == 2 * pSide.x();
        }
        else if (pSide.y() != null)
        {
            
            return _position.y() == pSide.y() || _position.y() == 2 * pSide.y();
        }
        else if (pSide.z() != null)
        {
            
            return _position.z() == pSide.z() || _position.z() == 2 * pSide.z();
        }
        
        // If we ever reach this, there's a problem
        System.out.println("WARNING: Plane.isOnSide result indeterminate");
        
        return false;
    }
    
    
    public double averageZ()
    {
        double zTotal = 0.0;
        
        for (Point3D point : _points)
        {
            zTotal += point.z();
        }
        
        return zTotal / _points.size();
    }
    
    
    public int compareTo(Plane pOther)
    {
        return (int)pOther.averageZ() - (int)this.averageZ();
    }
}
