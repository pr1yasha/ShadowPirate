import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Entity {
    private int damagePoints;
    private Image image;
    private double x;
    private double y;
    private final static double REFRESH_RATE = 60.0;
    private final static double MILLISECONDS = 1000.0;

    public static double getRefreshRate() {
        return REFRESH_RATE;
    }

    public static double getMilliseconds(){
        return MILLISECONDS;
    }

    public Entity(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(new Point(x, y));
    }

    public int getDamagePoints() {return damagePoints;}

    public void render(){image.drawFromTopLeft(x, y);}

    public void setDamagePoints(int damagePoints) {this.damagePoints = damagePoints;}

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    // Disables sailor from walking through entity
    public void ifCollidedInto(Entity entity){
        if (entity instanceof Sailor){
            ((Sailor) entity).moveBack();
        }
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
