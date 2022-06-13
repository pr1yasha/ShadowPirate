import bagel.Image;

public class Treasure extends Entity {

    private final static Image TREASURE = new Image("res/treasure.png");

    public Treasure(double x, double y) {
        super(x, y);
        super.setImage(TREASURE);
    }

    @Override
    public void ifCollidedInto(Entity entity){
        if (entity instanceof Sailor){
            ((Sailor) entity).ifTreasureFound = true;
        }
    }
}
