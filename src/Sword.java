import bagel.Image;

public class Sword extends Items {
    private final static Image SWORD = new Image("res/items/sword.png");
    private final static Image ICON = new Image("res/items/swordIcon.png");
    private final int SWORD_DAMAGE_POINTS = 15;

    public Sword(double x, double y) {
        super(x, y, SWORD);
        setIcon(ICON);
    }

    @Override
    public void ifCollidedInto(Entity entity){
        // adjusts damage points if the sailor picks sword up
        if (entity instanceof Sailor && !pickedUp){
            ((Sailor) entity).addToInventory(this);
            // ensures sailor can only pick the sword up once during collision
            super.pickedUp = true;
            int newDamagePoints = entity.getDamagePoints() + SWORD_DAMAGE_POINTS;
            System.out.println("Sailor finds Sword. Sailor's damage points increased to " + newDamagePoints);
            entity.setDamagePoints(newDamagePoints);
        }
    }

}
