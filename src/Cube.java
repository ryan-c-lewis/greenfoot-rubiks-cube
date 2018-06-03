import java.util.ArrayList;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;

/**
 * Represents the whole cube.
 */
public class Cube  
{
    private static final double SCALE = 10.0;
    private static final double PLANE_GAP = 0.2;
    private static final double SIDE_ROTATION_SPEED = 0.025;
    
    private ArrayList<Plane> _planes;
    private Rotation _rotation;
    
    private Side _rotatingSide = Side.right;
    private Rotation _sideRotation = new Rotation(0.0, 0.0, 0.0);
    private int _sideRotationDirection = 1;
    
    public Cube()
    {
        initializeCube();
    }
    
    
    public ArrayList<Plane> getPlanes()
    {
        return getRotatedPlanes();
    }
    
    
    public void changeXRotation(double pChange)
    {
        Rotation pChangeRotation = new Rotation(pChange, 0.0, 0.0);
        _rotation = _rotation.rotateFromCamera(pChangeRotation);
    }
    
    
    public void changeYRotation(double pChange)
    {
        Rotation pChangeRotation = new Rotation(0.0, pChange, 0.0);
        _rotation = _rotation.rotateFromCamera(pChangeRotation);
    }
    
    
    public void rotateSide()
    {
        if (_rotatingSide.isX())
        {
            double newX = _sideRotation.x() + _sideRotationDirection * SIDE_ROTATION_SPEED;
            _sideRotation = new Rotation(newX, _sideRotation.y(), _sideRotation.z());
        }
        else if (_rotatingSide.isY())
        {
            double newY = _sideRotation.y() + _sideRotationDirection * SIDE_ROTATION_SPEED;
            _sideRotation = new Rotation(_sideRotation.x(), newY, _sideRotation.z());
        }
        else if (_rotatingSide.isZ())
        {
            double newZ = _sideRotation.z() + _sideRotationDirection * SIDE_ROTATION_SPEED;
            _sideRotation = new Rotation(_sideRotation.x(), _sideRotation.y(), newZ);
        }
        
        // TEMPORARY
        if (Math.abs(_sideRotation.x() + _sideRotation.y() + _sideRotation.z()) >= Math.PI / 2)
        {
            shiftStickers(_rotatingSide, _sideRotationDirection);
            chooseNewRotation();
        }
    }
    
    
    public void chooseNewRotation()
    {
        Random rand = new Random(System.currentTimeMillis());
        
        Side[] possibleSides = {Side.front, Side.back, Side.left, Side.right, Side.top, Side.bottom};
        int[] possibleDirections = {-1, 1};
        
        Side chosenSide = possibleSides[rand.nextInt(possibleSides.length)];
        int chosenDirection = possibleDirections[rand.nextInt(possibleDirections.length)];
        
        _rotatingSide = chosenSide;
        _sideRotationDirection = chosenDirection;
        
        _sideRotation = new Rotation(0.0, 0.0, 0.0);
    }
    
    
    // This'll need some refactoring once I'm done. It's pretty messy.
    public void shiftStickers(Side pSide, int pDirection)
    {
        int[][] ringStickers = new int[0][0];
        int[][] sideStickers = new int[0][0];
        
        if (pSide == Side.left)
        {
            ringStickers = PlanePosition.leftRingPositions;
            sideStickers = PlanePosition.leftSidePositions;
        }
        else if (pSide == Side.right)
        {
            ringStickers = PlanePosition.rightRingPositions;
            sideStickers = PlanePosition.rightSidePositions;
        }
        else if (pSide == Side.top)
        {
            ringStickers = PlanePosition.topRingPositions;
            sideStickers = PlanePosition.topSidePositions;
        }
        else if (pSide == Side.bottom)
        {
            ringStickers = PlanePosition.bottomRingPositions;
            sideStickers = PlanePosition.bottomSidePositions;
        }
        else if (pSide == Side.front)
        {
            ringStickers = PlanePosition.frontRingPositions;
            sideStickers = PlanePosition.frontSidePositions;
        }
        else if (pSide == Side.back)
        {
            ringStickers = PlanePosition.backRingPositions;
            sideStickers = PlanePosition.backSidePositions;
        }
        
        ArrayList<SimpleEntry<Plane, PlaneColor>> planesToRecolor = new ArrayList<SimpleEntry<Plane, PlaneColor>>();
        
        for (int ringIndex = 0; ringIndex < ringStickers.length; ringIndex++)
        {
            // This is the plane we're going to recolor
            int[] toPositionArray = ringStickers[ringIndex];
            PlanePosition toPosition = new PlanePosition(toPositionArray[0], toPositionArray[1], toPositionArray[2]);
            Plane toPlane = Plane.getByPosition(_planes, toPosition);
            
            // This is the plane we're going to get the new color *from*
            int fromPositionIndex = (pDirection == 1) ? (ringIndex + 9) % 12 : (ringIndex + 3) % 12;
            int[] fromPositionArray = ringStickers[fromPositionIndex];
            PlanePosition fromPosition = new PlanePosition(fromPositionArray[0], fromPositionArray[1], fromPositionArray[2]);
            Plane fromPlane = Plane.getByPosition(_planes, fromPosition);
            
            // We can't just overwrite each plane's color as we go, b/c by the time we wrap around
            // and the "to" plane becomes the "from", we won't know what its original color was.
            // So, we need to store them in this tuple-type thing and then recolor them all at the end.
            SimpleEntry<Plane, PlaneColor> entry = new SimpleEntry<Plane, PlaneColor>(toPlane, fromPlane.color());
            planesToRecolor.add(entry);
        }
        
        for (int sideIndex = 0; sideIndex < sideStickers.length; sideIndex++)
        {
            // This is the plane we're going to recolor
            int[] toPositionArray = sideStickers[sideIndex];
            PlanePosition toPosition = new PlanePosition(toPositionArray[0], toPositionArray[1], toPositionArray[2]);
            Plane toPlane = Plane.getByPosition(_planes, toPosition);
            
            // This is the plane we're going to get the new color *from*
            int fromPositionIndex = (pDirection == 1) ? (sideIndex + 6) % 8 : (sideIndex + 2) % 8;
            int[] fromPositionArray = sideStickers[fromPositionIndex];
            PlanePosition fromPosition = new PlanePosition(fromPositionArray[0], fromPositionArray[1], fromPositionArray[2]);
            Plane fromPlane = Plane.getByPosition(_planes, fromPosition);
            
            // We can't just overwrite each plane's color as we go, b/c by the time we wrap around
            // and the "to" plane becomes the "from", we won't know what its original color was.
            // So, we need to store them in this tuple-type thing and then recolor them all at the end.
            SimpleEntry<Plane, PlaneColor> entry = new SimpleEntry<Plane, PlaneColor>(toPlane, fromPlane.color());
            planesToRecolor.add(entry);
        }
        
        for (SimpleEntry<Plane, PlaneColor> entry : planesToRecolor)
        {
            Plane plane = entry.getKey();
            PlaneColor color = entry.getValue();
            
            plane.setColor(color);
        }
    }
    
    
    /*
     * Create all the cube data.
     */
    private void initializeCube()
    {
        _rotation = new Rotation(0.0, 0.0, 0.0);
        createPlanes();
    }
    
    
    /*
     * Create the planes that make up the cube.
     * 
     * I bet these create methods could be combined in some way to reduce duplication.
     * But they're hard enough to understand already... so I'm going to leave them the way they are.
     */
    private void createPlanes()
    {
        _planes = new ArrayList<Plane>();
        
        createFrontBackPlanes(-1);
        createFrontBackPlanes(1);
        
        createLeftRightPlanes(-1);
        createLeftRightPlanes(1);
        
        createTopBottomPlanes(-1);
        createTopBottomPlanes(1);
    }
    
    
    private void createFrontBackPlanes(int pSide)
    {
        double pZ = pSide * SCALE;
        PlaneColor color = (pSide == 1) ? PlaneColor.blue() : PlaneColor.green();
        
        for (int x = -1; x <= 1; x++)
        {
            double centerX = SCALE * 2 / 3 * x;
            
            for (int y = -1; y <= 1; y++)
            {
                double centerY = SCALE * 2 / 3 * y;
                ArrayList<Point3D> planePoints = new ArrayList<Point3D>();
                
                int[][] pointPositions = (pSide == 1) ?
                    new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}} :
                    new int[][]{{1, -1}, {-1, -1}, {-1, 1}, {1, 1}};
                    
                for (int position = 0; position < pointPositions.length; position++)
                {
                    int positionX = pointPositions[position][0];
                    int positionY = pointPositions[position][1];
                    
                    double pX = centerX + ((SCALE / 3 - PLANE_GAP) * positionX);
                    double pY = centerY + ((SCALE / 3 - PLANE_GAP) * positionY);
                        
                    Point3D thisPoint = new Point3D(pX, pY, pZ);
                    planePoints.add(thisPoint);
                }
                
                PlanePosition thisPosition = new PlanePosition(x, y, 2 * pSide);
                
                Plane thisPlane = new Plane(planePoints, thisPosition, color);
                _planes.add(thisPlane);
            }
        }
    }
    
    
    private void createLeftRightPlanes(int pSide)
    {
        double pX = pSide * SCALE;
        PlaneColor color = (pSide == 1) ? PlaneColor.red() : PlaneColor.orange();
        
        for (int z = -1; z <= 1; z++)
        {
            double centerZ = SCALE * 2 / 3 * z;
            
            for (int y = -1; y <= 1; y++)
            {
                double centerY = SCALE * 2 / 3 * y;
                ArrayList<Point3D> planePoints = new ArrayList<Point3D>();
                
                int[][] pointPositions = (pSide == 1) ?
                    new int[][]{{-1, 1}, {1, 1}, {1, -1}, {-1, -1}} :
                    new int[][]{{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
                
                for (int position = 0; position < pointPositions.length; position++)
                {
                    int positionZ = pointPositions[position][0];
                    int positionY = pointPositions[position][1];
                    
                    double pZ = centerZ + ((SCALE / 3 - PLANE_GAP) * positionZ);
                    double pY = centerY + ((SCALE / 3 - PLANE_GAP) * positionY);
                        
                    Point3D thisPoint = new Point3D(pX, pY, pZ);
                    planePoints.add(thisPoint);
                }
                
                PlanePosition thisPosition = new PlanePosition(2 * pSide, y, z);
                
                Plane thisPlane = new Plane(planePoints, thisPosition, color);
                _planes.add(thisPlane);
            }
        }
    }
    
    
    private void createTopBottomPlanes(int pSide)
    {
        double pY = pSide * SCALE;
        PlaneColor color = (pSide == 1) ? PlaneColor.white() : PlaneColor.yellow();
        
        for (int x = -1; x <= 1; x++)
        {
            double centerX = SCALE * 2 / 3 * x;
            
            for (int z = -1; z <= 1; z++)
            {
                double centerZ = SCALE * 2 / 3 * z;
                ArrayList<Point3D> planePoints = new ArrayList<Point3D>();
                
                int[][] pointPositions = (pSide == 1) ?
                    new int[][]{{-1, 1}, {1, 1}, {1, -1}, {-1, -1}} :
                    new int[][]{{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
                
                
                for (int position = 0; position < pointPositions.length; position++)
                {
                    int positionX = pointPositions[position][0];
                    int positionZ = pointPositions[position][1];
                    
                    double pX = centerX + ((SCALE / 3 - PLANE_GAP) * positionX);
                    double pZ = centerZ + ((SCALE / 3 - PLANE_GAP) * positionZ);
                        
                    Point3D thisPoint = new Point3D(pX, pY, pZ);
                    planePoints.add(thisPoint);
                }
                
                PlanePosition thisPosition = new PlanePosition(x, 2 * pSide, z);
                
                Plane thisPlane = new Plane(planePoints, thisPosition, color);
                _planes.add(thisPlane);
            }
        }
    }
    
    
    /*
     * Get a set of planes with points that have had the cube's rotation applied to them.
     */
    private ArrayList<Plane> getRotatedPlanes()
    {
        ArrayList<Plane> planes = _planes;
        ArrayList<Plane> rotatedPlanes = new ArrayList<Plane>();
        
        ArrayList<Plane> sidePlanes = Plane.getBySide(planes, _rotatingSide);
        
        for (Plane plane : planes)
        {
            if (sidePlanes.contains(plane))
            {
                Plane rotatedPlane = plane.getRotatedPlane(_sideRotation);
                rotatedPlanes.add(rotatedPlane);
            }
            else
            {
                rotatedPlanes.add(plane);
            }
        }
        
        ArrayList<Plane> reorientedPlanes = new ArrayList<Plane>();
        
        for (Plane plane : rotatedPlanes)
        {
            Plane reorientedPlane = plane.getRotatedPlane(_rotation);
            reorientedPlanes.add(reorientedPlane);
        }
        
        return reorientedPlanes;
    }
}
