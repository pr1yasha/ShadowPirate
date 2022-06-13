import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.util.Colour;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.sqrt;

public abstract class Character extends Entity implements Movable{
    private double oldX;
    private double oldY;
    private double movementSpeed;
    private double healthPoints;
    private double maxHealthPoints;
    private Image currentImage;
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int UP = 2;
    private final int DOWN = 3;
    private int currentDirection;
    private final double MIN_PIRATE_SPEED = 0.2;
    private final double MAX_PIRATE_SPEED = 0.7;
    private Level currentLevel;
    private boolean ifInvincible = false;
    private final DrawOptions drawOptions = new DrawOptions();
    private int timeSinceInvincible = 0;
    private final static int INVINCIBLE_TIME = 1500;
    private int attackRange;
    private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    private boolean ifCooldown = false;
    private int cooldownTimer = 0;
    private final static int COOLDOWN_TIME =3000;
    private boolean ifAlive = true;
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private final static int MEDIUM_HEALTH = 65;
    private final static int LOW_HEALTH = 35;
    private Colour colour = GREEN;

    public boolean isIfAlive() {
        return ifAlive;
    }
    public Level getCurrentLevel() {
        return currentLevel;
    }
    public void setIfAlive(boolean status){
        ifAlive = status;
    }
    public boolean getIfInvicible(){
        return ifInvincible;
    }
    public void setIfInvincible(boolean status){
        ifInvincible = status;
    }

    // Generates a random direction
    public void generateDirection(){
        Random random = new Random();
        currentDirection = random.nextInt(DOWN + 1);
    }

    @Override
    // Draws characters on screen if they are alive
    public void render(){
        if (getHealthPoints() > 0){
            getImage().drawFromTopLeft(getX(), getY());
        }
    }

    // returns true if the character is in the bounds of the frame
    public boolean inBounds(double x, double y){
        if (x > currentLevel.getBottomRightXBound() || x < currentLevel.getTopLeftXBound()){
            return false;
        }
        return !(y > currentLevel.getBottomRightYBound()) && !(y < currentLevel.getTopLeftYBound());
    }

    //generates a random speed
    public void generateSpeed(){
        Random random = new Random();
        movementSpeed = MIN_PIRATE_SPEED + (MAX_PIRATE_SPEED - MIN_PIRATE_SPEED) * random.nextDouble();
    }

    public void update(Input input, ArrayList<Entity> entityList) {
        if (ifCooldown){
            cooldownTimer++;
        }
        double cooldownTime = cooldownTimer/(getRefreshRate()/getMilliseconds());
        if (cooldownTime > COOLDOWN_TIME){
            cooldownTimer = 0;
            ifCooldown = false;
        }

        for (int i = 0; i<projectileList.size(); i++){
            Projectile current = projectileList.get(i);
            if (!inBounds(current.getX(), current.getY()) || current.getIfStruckPlayer()){
                projectileList.remove(i);
            }
            else {
                current.render();
                current.update(input, entityList);
            }
        }
        if (ifInvincible){
            timeSinceInvincible++;
            double realTimeSinceInvincible = timeSinceInvincible/(60.0/1000.0);
            updateImage(ifInvincible);
            if (realTimeSinceInvincible > INVINCIBLE_TIME){
                ifInvincible = false;
                updateImage(ifInvincible);
                timeSinceInvincible = 0;
            }
        }
        setOldPoints();
        if (getCurrentDirection() == getRIGHT()){
            move(getMovementSpeed(),0);
        }
        else if (getCurrentDirection() == getLEFT()){
            move(-getMovementSpeed(),0);
        }
        else if (getCurrentDirection() == getDOWN()){
            move(0, getMovementSpeed());
        }
        else if (getCurrentDirection() == getUP()){
            move(0,-getMovementSpeed());
        }
        if (getHealthPoints() <= 0){
            if (projectileList.size() == 0){
                ifAlive = false;
            }
        }
        else {
            super.render();
            setHealth_x(super.getX());
            setHealth_y(super.getY() - 6);
            checkCollisions(entityList, this);
        }
        if (!inBounds(getX(), getY())){
            flipDirection();
            moveBack();
        }
        renderHealthPoints();
    }

    public boolean ifCollides(Entity entity1, Entity entity2){
        // ensures sailor can't bump into sailor in the list, etc
        if (!entity1.equals(entity2)) {
            Rectangle entity1Box = entity1.getBoundingBox();
            Rectangle entity2Box = entity2.getBoundingBox();
            if (entity1Box.intersects(entity2Box)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    private int fontSize = 30;
    private double health_x = 10;
    private double health_y = 25;


    @Override
    // Check if character has collided into sailor
    public void ifCollidedInto(Entity entity){
        if (entity instanceof Sailor){
            if (((Sailor) entity).getInAttack() && !getIfInvicible()){
                // Start invincible cooldown for character and inflict sailor's damage on character
                setIfInvincible(true);
                setHealthPoints(getHealthPoints() - entity.getDamagePoints());
                if (this instanceof Pirate) {
                    System.out.println("Sailor inflicts " + entity.getDamagePoints() + " damage points on Pirate. Pirate's current health: " + (int) getHealthPoints() + "/" + (int) getMaxHealthPoints());
                }
                else if (this instanceof Blackbeard){
                    System.out.println("Sailor inflicts " + entity.getDamagePoints() + " damage points on Blackbeard. Blackbeard's current health: " + (int) getHealthPoints() + "/" + (int) getMaxHealthPoints());
                }
            }
        }
    }

    public void setHealth_x(double health_x) {
        this.health_x = health_x;
    }

    public void setHealth_y(double health_y) {
        this.health_y = health_y;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void renderHealthPoints(){
        // Calculates health as percentage
        double percentageHP = (double) healthPoints;
        double percentage = ((healthPoints/maxHealthPoints) * 100);
        Font FONT = new Font("res/wheaton.otf", fontSize);
        if (percentageHP > 0){
            FONT.drawString((int) percentage + "%", health_x, health_y, drawOptions.setBlendColour(colour));
        }

        // Adjusts colour of health status
        colour = GREEN;
        if (percentage < MEDIUM_HEALTH){
            colour = ORANGE;
            if (percentage < LOW_HEALTH){
                colour = RED;
            }
        }

    }

    public Character(double startX, double startY, Level currentLevel){
        super(startX, startY);
        this.currentLevel = currentLevel;
    }

    public Image getCurrentImage() {return currentImage;}
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
        super.setImage(currentImage);}
    public double getHealthPoints() {return healthPoints;}
    public void setHealthPoints(double healthPoints) {this.healthPoints = healthPoints;}

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    // Flips direction of character
    public void flipDirection(){
        if (currentDirection == LEFT){
            currentDirection = RIGHT;
        }
        else if (currentDirection == RIGHT){
            currentDirection = LEFT;
        }
        else if (currentDirection == UP){
            this.currentDirection = DOWN;
        }
        else if (currentDirection == DOWN){
            currentDirection = UP;
        }
        // Updates image to match new direction
        updateImage(ifInvincible);
    }

    public abstract void updateImage(boolean ifInvincible);
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public double getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(double maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public void move(double xMove, double yMove){
        super.setX(super.getX() + xMove);
        super.setY(super.getY() + yMove);
    }

    public void setOldPoints(){
        oldX = super.getX();
        oldY = super.getY();
    }

    public void moveBack(){
        super.setX(oldX);
        super.setY(oldY);
    }

    public int getLEFT() {return LEFT;}
    public int getRIGHT() {return RIGHT;}
    public int getUP() {return UP;}
    public int getDOWN() {return DOWN;}


    // Checks if character collides with any entities
    public void checkCollisions(ArrayList<Entity> entityList, Entity entity){
        for (int i=0; i< entityList.size(); i++){
            if (ifCollides(entity, entityList.get(i))){
                entityList.get(i).ifCollidedInto(entity);
            }
            // Checks if sailor has entered character's attack range
            if (entityList.get(i) instanceof Sailor){
                calculateDistance(entityList.get(i));
            }
        }
    }

    // calculates distance from enemy to sailor and shoots projectile
    public void calculateDistance(Entity entity){
        double distance = sqrt(Math.pow(entity.getX() - getX(), 2) + Math.pow((entity.getY() - getY()), 2));
        //shoot projectile if sailor is within attack range, toggles cooldown period for shooting
        if (distance < attackRange && !ifCooldown){
            double direction = -Math.atan2((entity.getY()-getY()), (entity.getX()-getX()));
            projectileList.add(new Projectile(getX(), getY(), this, direction));
            ifCooldown = true;
        }
    }

}
