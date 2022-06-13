import bagel.Image;

public class Block extends Entity {
    private final static Image BLOCK = new Image("res/block.png");

    public Block(double x, double y) {
        super(x, y);
        super.setImage(BLOCK);
    }

    public void ifCollidedInto(Entity entity){
        // stops sailor from walking through blocks
        if (entity instanceof Sailor){
            ((Sailor) entity).moveBack();
        }
        // allows pirates and Blackbeard to bounce off
        if (entity instanceof Pirate || entity instanceof Blackbeard){
            ((Pirate) entity).moveBack();
            ((Pirate) entity).flipDirection();
        }
    }
}
