import greenfoot.*;

/*
 * This'll be the puppet master, if you will.
 * Everything that happens will be because of something in here.
 */
public class Controller extends Actor
{
    private static final double ROTATION_SPEED = 0.04;
    
    private Screen _screen;
    private Cube _cube;
    
    
    /*
     * All the initial setup for the program should happen here.
     */
    public Controller(int pWidth, int pHeight)
    {
        _cube = new Cube();
        
        _screen = new Screen(pWidth, pHeight, _cube);
        setImage(_screen);
    }
    
    
    /*
     * Called on a timer from CubeWorld
     */
    public void act()
    {
        if (Greenfoot.isKeyDown("left"))
        {
            _cube.changeYRotation(-ROTATION_SPEED);
        }
        else if (Greenfoot.isKeyDown("right"))
        {
            _cube.changeYRotation(ROTATION_SPEED);
        }
        
        if (Greenfoot.isKeyDown("up"))
        {
            _cube.changeXRotation(-ROTATION_SPEED);
        }
        else if (Greenfoot.isKeyDown("down"))
        {
            _cube.changeXRotation(ROTATION_SPEED);
        }
        
        rotateSide();
    
        _screen.render();
    }  
    
    
    public void rotateSide()
    {
        _cube.rotateSide();
    }
}
