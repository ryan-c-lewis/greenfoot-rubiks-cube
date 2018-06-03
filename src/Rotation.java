/**
 * This represents a rotation *relative to the origin/camera orientation* - not relative to the object.
 * So this isn't your typical roll, pitch, and yaw.
 * 
 * Each value is an angle (in radians) around that axis.
 * 
 * A rotation is applied in x, y, then z order.
 */
public class Rotation  
{
    private double _xRotation;
    private double _yRotation;
    private double _zRotation;
    
    public Rotation(double pX, double pY, double pZ)
    {
        _xRotation = pX;
        _yRotation = pY;
        _zRotation = pZ;
    }
    
    
    public double x()
    {
        return _xRotation;
    }
    
    
    public double y()
    {
        return _yRotation;
    }
    
    
    public double z()
    {
        return _zRotation;
    }
    
    
    /*
     * Calculate a single rotation that would be equivalent to
     * the current rotation + some change in rotation.
     */
    public Rotation rotateFromCamera(Rotation pChange)
    {
        Point3D unitVectorX = Point3D.UNITVECTOR_X;
        Point3D unitVectorY = Point3D.UNITVECTOR_Y;
        
        // Rotate the unit vectors by the current rotation
        unitVectorX = Point3D.applyRotation(unitVectorX, this);
        unitVectorY = Point3D.applyRotation(unitVectorY, this);
        
        // Now rotate the unit vectors by the amount we want to change the rotation
        unitVectorX = Point3D.applyRotation(unitVectorX, pChange);
        unitVectorY = Point3D.applyRotation(unitVectorY, pChange);
        
        // Calculate what *single rotation* would put the vectors in this position
        return calculateFromUnitVectors(unitVectorX, unitVectorY);
    }
    
    
    /*
     * Given a rotated pair of x and y unit vectors, calculate a rotation that
     * would put them in that position.
     */
    public static Rotation calculateFromUnitVectors(Point3D pXVector, Point3D pYVector)
    {
        double zRotation = pXVector.zRotation();
        pXVector = Point3D.applyZRotation(pXVector, -zRotation);
        pYVector = Point3D.applyZRotation(pYVector, -zRotation);
        
        double yRotation = pXVector.yRotation();
        pYVector = Point3D.applyYRotation(pYVector, -yRotation);
        
        double xRotation = pYVector.xRotation() - Math.PI / 2;
        
        return new Rotation(xRotation, yRotation, zRotation);
    }
}
