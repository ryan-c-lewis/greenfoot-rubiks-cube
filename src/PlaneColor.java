import greenfoot.Color;

/**
 * Represents one of the 6 colors on the cube.
 * 
 * Can't create an instance of this due to the private constructor.
 * Sort of a singleton pattern.
 */
public class PlaneColor  
{
    public static final int WHITE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int BLUE = 4;
    public static final int ORANGE = 5;
    public static final int RED = 6;
    public static final int BLACK = 7;
    
    public static final Color WHITE_RGB = new Color(240, 240, 240);
    public static final Color YELLOW_RGB = new Color(245, 242, 61);
    public static final Color GREEN_RGB = new Color(88, 173, 81);
    public static final Color BLUE_RGB = new Color(82, 82, 179);
    public static final Color ORANGE_RGB = new Color(255, 168, 46);
    public static final Color RED_RGB = new Color(250, 65, 65);
    public static final Color GRAY_RGB = new Color(100, 100, 100);
    
    private static final PlaneColor _white = new PlaneColor(WHITE);
    private static final PlaneColor _yellow = new PlaneColor(YELLOW);
    private static final PlaneColor _green = new PlaneColor(GREEN);
    private static final PlaneColor _blue = new PlaneColor(BLUE);
    private static final PlaneColor _orange = new PlaneColor(ORANGE);
    private static final PlaneColor _red = new PlaneColor(RED);
    
    private int _color;
    
    
    private PlaneColor(int pColor) throws IllegalArgumentException
    {
        if (pColor < 1 || pColor > 6)
        {
            throw new IllegalArgumentException("pColor must be one of the PlaneColor values.");
        }
        
        _color = pColor;
    }
    
    
    public static PlaneColor white()
    {
        return _white;
    }
    
    public static PlaneColor yellow()
    {
        return _yellow;
    }
    
    public static PlaneColor green()
    {
        return _green;
    }
    
    public static PlaneColor blue()
    {
        return _blue;
    }
    
    public static PlaneColor orange()
    {
        return _orange;
    }
    
    public static PlaneColor red()
    {
        return _red;
    }
    
    
    public int value()
    {
        return _color;
    }
    
    
    public Color rgb() throws IllegalArgumentException
    {
        switch (_color)
        {
            case WHITE: return WHITE_RGB;
            case YELLOW: return YELLOW_RGB;
            case GREEN: return GREEN_RGB;
            case BLUE: return BLUE_RGB;
            case ORANGE: return ORANGE_RGB;
            case RED: return RED_RGB;
        }
        
        throw new IllegalArgumentException("Color not found.");
    }
}
