import bagel.Image;

public class Potion extends Items {
    private final static Image POTION_IMAGE = new Image("res/items/potion.png");
    private final int POTION_HEALTH_POINTS = 25;
    private final static Image ICON = new Image("res/items/potionIcon.png");


    public Potion(double x, double y) {
        super(x, y, POTION_IMAGE);
        setIcon(ICON);
    }

    @Override
    public void ifCollidedInto(Entity entity){
        // only affects sailor
        if (entity instanceof Sailor && !pickedUp){
            ((Sailor) entity).addToInventory(this);
            super.pickedUp = true;
            double newHP = ((Sailor) entity).getHealthPoints() + POTION_HEALTH_POINTS;

            // ensures potion doesn't give more health than the max amount
            if (newHP > ((Sailor) entity).getMaxHealthPoints()){
                newHP = ((Sailor) entity).getMaxHealthPoints();
            }
            ((Sailor) entity).setHealthPoints(newHP);
            System.out.println("Sailor finds Potion. Sailor's current health: " + (int) newHP + "/" + (int) ((Sailor) entity).getMaxHealthPoints());

        }
    }
}
