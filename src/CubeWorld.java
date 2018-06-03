import greenfoot.*;

public class CubeWorld extends World
{
    private static final int WORLD_WIDTH = 400;
    private static final int WORLD_HEIGHT = 400;
    
    /*
     * Entry point for the program.
     * Nothing happens here. Stuff gets going in the Controller.
     */
    public CubeWorld()
    {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        
        Controller controller = new Controller(WORLD_WIDTH, WORLD_HEIGHT);
        addObject(controller, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
    }
}
