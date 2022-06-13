import bagel.Image;

public class Bomb extends Entity {
    private final static Image BOMB = new Image("res/bomb.png");
    private final static Image BOMB_EXPLOSION = new Image("res/explosion.png");
    private final static int BOMB_DMG_POINTS = 10;
    private boolean triggered = false;
    private final static int BOMB_COOLDOWN = 500;
    private double timeSinceDetonation = 0.0;

    public Bomb(double x, double y) {
        super(x, y);
        setImage(BOMB);
        setDamagePoints(BOMB_DMG_POINTS);
    }

    @Override
    public void render(){
        // Draw Bomb's detonation only during its cooldown period
        if (triggered){
            this.timeSinceDetonation++;
            double realTimeSinceDetonation = timeSinceDetonation/(getRefreshRate()/getMilliseconds());
            if (realTimeSinceDetonation > BOMB_COOLDOWN){
                return;
            }
            BOMB_EXPLOSION.drawFromTopLeft(getX(), getY());
        }
        else {
            BOMB.drawFromTopLeft(getX(), getY());
        }
    }

    @Override
    public void ifCollidedInto(Entity entity){

        // Attack sailor if collision occurs
        if (entity instanceof Sailor && triggered == false){
            // Allow the bomb to only attack the sailor once during detonation
            this.triggered = true;
            ((Sailor) entity).setHealthPoints(((Sailor) entity).getHealthPoints() - getDamagePoints());
            System.out.println("Bomb inflicts " + BOMB_DMG_POINTS + " damage points on Sailor. Sailor's current health: " + (int) ((Sailor) entity).getHealthPoints() + "/" + (int) ((Sailor) entity).getMaxHealthPoints());
        }

        // only prevents pirates and Blackbeard from walking through
            if (entity instanceof Pirate || entity instanceof Blackbeard) {
                ((Character) entity).moveBack();
                ((Character) entity).flipDirection();
            }
    }

}
