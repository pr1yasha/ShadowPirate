import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class Projectile extends Entity implements Movable {
    private double movementSpeed;
    private double direction;
    private static final Image PIRATE_PROJECTILE = new Image("res/pirate/pirateProjectile.png");
    private static final Image BLACKBEARD_PROJECTILE = new Image("res/blackbeard/blackbeardProjectile.png");
    private final DrawOptions drawOptions = new DrawOptions();
    private boolean ifStruckPlayer = false;
    private static final int PIRATE_PROJECTILE_DAMAGE = 10;
    private static final int BLACKBEARD_PROJECTILE_DAMAGE = 20;
    private static final double BLACKBEARD_PROJECTILE_SPEED = 0.8;
    private static final double PIRATE_PROJECTILE_SPEED = 0.4;
    private int attackDamage;

    @Override
    public void render(){
        super.getImage().drawFromTopLeft(getX(), getY(), drawOptions.setRotation(-direction));
    }

    public Projectile(double x, double y, Entity entity, double direction) {
        super(x, y);
        this.direction = direction;

        // Updates projectile stats depending on who has shot it
        if (entity instanceof Blackbeard) {
            attackDamage = BLACKBEARD_PROJECTILE_DAMAGE;
            super.setImage(BLACKBEARD_PROJECTILE);
            movementSpeed = BLACKBEARD_PROJECTILE_SPEED;
            setDamagePoints(attackDamage);
        } else {
            attackDamage = PIRATE_PROJECTILE_DAMAGE;
            super.setImage(PIRATE_PROJECTILE);
            movementSpeed = PIRATE_PROJECTILE_SPEED;
            setDamagePoints(attackDamage);
        }
    }


    // Function that checks if the projectile has collided with anything on screen
    public void checkCollisions(ArrayList<Entity> entityList, Entity entity){
        for (int i=0; i< entityList.size(); i++){

            // Attacks sailor if projectile collides with sailor
            if (entityList.get(i) instanceof Sailor){
                Sailor sailor = (Sailor) entityList.get(i);
                if (ifCollides(this, entityList.get(i))){
                    sailor.setHealthPoints(sailor.getHealthPoints() - this.getDamagePoints());
                    System.out.println("Projectile inflicts " + attackDamage + " damage points on Sailor. Sailor's current health: " + (int) sailor.getHealthPoints() + "/" + (int) sailor.getMaxHealthPoints());
                    // Ensures projectile only hurts sailor once in collision
                    ifStruckPlayer = true;
                }
            }
        }
    }

    // Checks if projectile collides with an entity
    public boolean ifCollides(Entity projectile, Entity entity){
        Rectangle projectileBox = new Rectangle(getX(), getY(), 1, 1);
        Rectangle entity2Box = entity.getBoundingBox();
        if (projectileBox.intersects(entity2Box)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getIfStruckPlayer() {
        return ifStruckPlayer;
    }

    //  Moves projectile
    public void move(double x, double y){
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);
    }


    // Moves projectile according to its calculated path
    public void update(Input input, ArrayList<Entity> entityList){
        if (Math.toDegrees(direction) < -90){
            move(movementSpeed * Math.cos(direction), movementSpeed * -Math.sin(direction));
        }
        else if (Math.toDegrees(direction) < 0){
            move(movementSpeed * Math.cos(direction), movementSpeed * -Math.sin(direction));
        }
        else if (Math.toDegrees(direction) > 90){
            move(movementSpeed * Math.cos(direction), movementSpeed * -Math.sin(direction));
        }
        else {
            move(movementSpeed * Math.cos(direction), movementSpeed * -Math.sin(direction));
        }
        // Check if any collisions have occured in the new position
        checkCollisions(entityList, this);
    }
}
