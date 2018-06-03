/*
 * This contains info on what "perspective" to use when rendering the screen.
 * 
 * The Z-distance is the distance from the grid origin to the "lens".
 * The focal length is the distance from the "lens" to the "eye". The larger this is, the less the image looks distorted.
 * 
 * To simplify the rendering calculation, we're making a few assumptions:
 *  - The camera is somewhere along the z-axis.
 *  - The camera can't move.
 *  - The camera can't rotate and will always point straight ahead along the positive Z axis.
 *  - The cube can rotate but will not move. It will always be fixed at the origin.
 */
public class Camera  
{
    public static final double Z_DISTANCE = 8.0;
    public static final double FOCAL_LENGTH = 70.0;
    public static final double ZOOM = 12.0;
}
