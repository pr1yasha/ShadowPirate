import bagel.Image;

public class Elixir extends Items {
    private int ELIXIR_HEALTH_POINTS = 35;
    private final static Image ELIXIR_IMAGE = new Image("res/items/elixir.png");
    private final static Image ICON = new Image("res/items/elixirIcon.png");
    private double newMaxHP;

    public Elixir(double x, double y) {
        super(x, y, ELIXIR_IMAGE);
        setIcon(ICON);
    }

    public void ifCollidedInto(Entity entity){


        if (entity instanceof Sailor && !pickedUp){
            // Ensure sailor can only pick up the elixir once during collision
            Sailor sailor = (Sailor) entity;
            sailor.addToInventory(this);
            pickedUp = true;
            newMaxHP = sailor.getMaxHealthPoints() + ELIXIR_HEALTH_POINTS;

            // Set sailor's HP to new increased HP
            sailor.setMaxHealthPoints(newMaxHP);
            sailor.setHealthPoints(newMaxHP);
            System.out.println("Sailor finds Elixir. Sailor's current health: " + (int) sailor.getHealthPoints() + "/"
                    + (int) sailor.getMaxHealthPoints());
        }
    }

}
