/**
 * Represents a location in 3D space.
 * 
 * A Point3D is immutable (meaning, you can't change it; you just have to make a new one.)
 * This'll prevent me from accidentally moving points around in ways I don't intend to.
 */
public class Point3D  
{
    public static final Point3D UNITVECTOR_X = new Point3D(1.0, 0.0, 0.0);
    public static final Point3D UNITVECTOR_Y = new Point3D(0.0, 1.0, 0.0);
    public static final Point3D UNITVECTOR_Z = new Point3D(0.0, 0.0, 1.0);
    
    private double _x;
    private double _y;
    private double _z;
    
    // Since the point is immutable, these values are never going to change.
    // We don't want to keep repeating the same costly calculations, so let's cache them.
    private Double _xyzDistance = null;
    private Double _xyDistance = null;
    private Double _xzDistance = null;
    private Double _yzDistance = null;
    private Double _xRotation = null;
    private Double _yRotation = null;
    private Double _zRotation = null;
    
    
    public Point3D(double pX, double pY, double pZ)
    {
        _x = pX;
        _y = pY;
        _z = pZ;
    }
    
    public double x()
    {
        return _x;
    }
    
    public double y()
    {
        return _y;
    }
    
    public double z()
    {
        return _z;
    }
    
    
    public double xyzDistance()
    {
        if (_xyzDistance == null)
        {
            _xyzDistance = Math.sqrt(_x * _x + _y * _y + _z * _z);
        }
        
        return _xyzDistance;
    }
    
    
    public double xyDistance()
    {
        if (_xyDistance == null)
        {
            _xyDistance = Math.sqrt(_x * _x + _y * _y);
        }
        
        return _xyDistance;
    }
    
    
    public double xzDistance()
    {
        if (_xzDistance == null)
        {
            _xzDistance = Math.sqrt(_x * _x + _z * _z);
        }
        
        return _xzDistance;
    }
    
    
    public double yzDistance()
    {
        if (_yzDistance == null)
        {
            _yzDistance = Math.sqrt(_y * _y + _z * _z);
        }
        
        return _yzDistance;
    }
    
    
    public double xRotation()
    {
        if (_xRotation == null)
        {
            _xRotation = Math.atan2(_y, _z);
        }
        
        return _xRotation;
    }
    
    
    public double yRotation()
    {
        if (_yRotation == null)
        {
            _yRotation = Math.atan2(_z, _x);
        }
        
        return _yRotation;
    }
    
    
    public double zRotation()
    {
        if (_zRotation == null)
        {
            _zRotation = Math.atan2(_y, _x);
        }
        
        return _zRotation;
    }
    
    
    /*
     * Rotate a point around the origin.
     */
    public static Point3D applyRotation(Point3D pPoint, Rotation pRotation)
    {
        Point3D rotatedPoint = applyXRotation(pPoint, pRotation.x());
        rotatedPoint = applyYRotation(rotatedPoint, pRotation.y());
        rotatedPoint = applyZRotation(rotatedPoint, pRotation.z());
        
        return rotatedPoint;
    }
    
    
    /*
     * Rotate a point around the x-axis.
     */
    public static Point3D applyXRotation(Point3D pPoint, double pXRotation)
    {
      double yzDistance = pPoint.yzDistance();
      
      double xRotation = pPoint.xRotation();
      double newXRotation = xRotation + pXRotation;
      
      double newY = Math.sin(newXRotation) * yzDistance;
      double newZ = Math.cos(newXRotation) * yzDistance;
      
      return new Point3D(pPoint.x(), newY, newZ);
    }
    
    
    /*
     * Rotate a point around the y-axis.
     */
    public static Point3D applyYRotation(Point3D pPoint, double pYRotation)
    {
      double xzDistance = pPoint.xzDistance();
      
      double yRotation = pPoint.yRotation();
      double newYRotation = yRotation + pYRotation;
      
      double newX = Math.cos(newYRotation) * xzDistance;
      double newZ = Math.sin(newYRotation) * xzDistance;
      
      return new Point3D(newX, pPoint.y(), newZ);
    }
    
    
    /*
     * Rotate a point around the z-axis.
     */
    public static Point3D applyZRotation(Point3D pPoint, double pZRotation)
    {
      double xyDistance = pPoint.xyDistance();
      
      double zRotation = pPoint.zRotation();
      double newZRotation = zRotation + pZRotation;
      
      double newX = Math.cos(newZRotation) * xyDistance;
      double newY = Math.sin(newZRotation) * xyDistance;
      
      return new Point3D(newX, newY, pPoint.z());
    }
}
