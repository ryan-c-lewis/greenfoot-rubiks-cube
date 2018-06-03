/**
 * This represents a "location" of where a plane/sticker is
 * in relation to the center of the cube.
 * 
 * A "2" represents that the axis is the "side" that the cube is on:
 *      x = 2  ->  right
 *      x = -2 ->  left
 *      y = 2  ->  top
 *      y = -2 ->  bottom
 *      z = 2  ->  back
 *      z = -2 ->  front
 *      
 *  A "1" represents that the sticker is in that direction 
 *  relative to the center of that side.
 *  
 *  So the position [0, 1, -2] is the top-middle sticker on the front.
 */
public class PlanePosition  
{
    public static final int[][] leftRingPositions = {
        {-1, 1, -2}, {-1, 0, -2}, {-1, -1, -2}, // front
        {-1, -2, -1}, {-1, -2, 0}, {-1, -2, 1}, // bottom
        {-1, -1, 2}, {-1, 0, 2}, {-1, 1, 2},  // back
        {-1, 2, 1}, {-1, 2, 0}, {-1, 2, -1} // top
    };
    
    public static final int[][] leftSidePositions = {
        {-2, 1, 1}, {-2, 1, 0}, {-2, 1, -1}, {-2, 0, -1},
        {-2, -1, -1}, {-2, -1, 0}, {-2, -1, 1}, {-2, 0, 1}
    };
    
    public static final int[][] rightRingPositions = {
        {1, 1, -2}, {1, 0, -2}, {1, -1, -2}, // front
        {1, -2, -1}, {1, -2, 0}, {1, -2, 1}, // bottom
        {1, -1, 2}, {1, 0, 2}, {1, 1, 2},  // back
        {1, 2, 1}, {1, 2, 0}, {1, 2, -1} // top
    };
    
    public static final int[][] rightSidePositions = {
        {2, 1, 1}, {2, 1, 0}, {2, 1, -1}, {2, 0, -1},
        {2, -1, -1}, {2, -1, 0}, {2, -1, 1}, {2, 0, 1}
    };
    
    
    public static final int[][] topRingPositions = {
        {-1, 1, -2}, {0, 1, -2}, {1, 1, -2}, // front
        {2, 1, -1}, {2, 1, 0}, {2, 1, 1}, // right
        {1, 1, 2}, {0, 1, 2}, {-1, 1, 2}, // back
        {-2, 1, 1}, {-2, 1, 0}, {-2, 1, -1}, // left
    };
    
    public static final int[][] topSidePositions = {
        {-1, 2, -1}, {0, 2, -1}, {1, 2, -1}, {1, 2, 0},
        {1, 2, 1}, {0, 2, 1}, {-1, 2, 1}, {-1, 2, 0}
    };
    
    public static final int[][] bottomRingPositions = {
        {-1, -1, -2}, {0, -1, -2}, {1, -1, -2}, // front
        {2, -1, -1}, {2, -1, 0}, {2, -1, 1}, // right
        {1, -1, 2}, {0, -1, 2}, {-1, -1, 2}, // back
        {-2, -1, 1}, {-2, -1, 0}, {-2, -1, -1}, // left
    };
    
    public static final int[][] bottomSidePositions = {
        {-1, -2, -1}, {0, -2, -1}, {1, -2, -1}, {1, -2, 0},
        {1, -2, 1}, {0, -2, 1}, {-1, -2, 1}, {-1, -2, 0}
    };
    
    
    public static final int[][] frontRingPositions = {
        {1, 2, -1}, {0, 2, -1}, {-1, 2, -1}, // top
        {-2, 1, -1}, {-2, 0, -1}, {-2, -1, -1}, // left
        {-1, -2, -1}, {0, -2, -1}, {1, -2, -1}, // bottom
        {2, -1, -1}, {2, 0, -1}, {2, 1, -1} // right
    };
    
    public static final int[][] frontSidePositions = {
        {-1, 1, -2}, {-1, 0, -2}, {-1, -1, -2}, {0, -1, -2},
        {1, -1, -2}, {1, 0, -2}, {1, 1, -2}, {0, 1, -2}
    };
    
    public static final int[][] backRingPositions = {
        {1, 2, 1}, {0, 2, 1}, {-1, 2, 1}, // top
        {-2, 1, 1}, {-2, 0, 1}, {-2, -1, 1}, // left
        {-1, -2, 1}, {0, -2, 1}, {1, -2, 1}, // bottom
        {2, -1, 1}, {2, 0, 1}, {2, 1, 1} // right
    };
    
    public static final int[][] backSidePositions = {
        {-1, 1, 2}, {-1, 0, 2}, {-1, -1, 2}, {0, -1, 2},
        {1, -1, 2}, {1, 0, 2}, {1, 1, 2}, {0, 1, 2}
    };
    
    private int _x;
    private int _y;
    private int _z;
    
    public PlanePosition(int pX, int pY, int pZ) throws IllegalArgumentException
    {
        if (pX < -2 || pX > 2 || 
            pY < -2 || pY > 2 || 
            pZ < -2 || pZ > 2)
        {
            throw new IllegalArgumentException("Each param must be -2 <= p <= 2");
        }
        
        _x = pX;
        _y = pY;
        _z = pZ;
    }
    

    public int x()
    {
        return _x;
    }
    
    public int y()
    {
        return _y;
    }
    
    public int z()
    {
        return _z;
    }
}
