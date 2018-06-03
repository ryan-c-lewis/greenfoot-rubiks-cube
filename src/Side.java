/**
 * This represents a "slice" of the cube.
 * 
 * For example: the front side/slice of the cube is all the stickers on the front (z = -2 PlanePosition)
 * along with all the stickers on the top, bottom, left, and right in the z = -1 PlanePosition.
 */
public class Side  
{
    private Integer _x = null;
    private Integer _y = null;
    private Integer _z = null;
    
    public static final Side left = new Side(-1, null, null);
    public static final Side middleX = new Side(0, null, null);
    public static final Side right = new Side(1, null, null);
    
    public static final Side top = new Side(null, 1, null);
    public static final Side middleY = new Side(null, 0, null);
    public static final Side bottom = new Side(null, -1, null);
    
    public static final Side front = new Side(null, null, -1);
    public static final Side middleZ = new Side(null, null, 0);
    public static final Side back = new Side(null, null, 1);
    
    
    private Side(Integer pX, Integer pY, Integer pZ)
    {
        int numNotNull = 0;
        numNotNull += (pX != null) ? 1 : 0;
        numNotNull += (pY != null) ? 1 : 0;
        numNotNull += (pZ != null) ? 1 : 0;
        
        if (numNotNull != 1)
        {
            throw new IllegalArgumentException("Exactly one of the parameters must be not null.");
        }
        
        if ((pX != null && (pX < -1 || pX > 1)) || 
            (pY != null && (pY < -1 || pY > 1)) || 
            (pZ != null && (pZ < -1 || pZ > 1)))
        {
            throw new IllegalArgumentException("The not-null param must be -1 <= p <= 1");
        }
        
        _x = pX;
        _y = pY;
        _z = pZ;
    }

    
    public Integer x()
    {
        return _x;
    }
    
    public Integer y()
    {
        return _y;
    }
    
    public Integer z()
    {
        return _z;
    }
    
    
    public boolean isX()
    {
        return _x != null;
    }
    
    public boolean isY()
    {
        return _y != null;
    }
    
    public boolean isZ()
    {
        return _z != null;
    }
}
