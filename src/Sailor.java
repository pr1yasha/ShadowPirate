import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

public class Sailor extends Character {
    private final static Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final static Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final static Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final static Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");
    private final static int ATTACK_COOLDOWN = 1000;
    private final static double DEFAULT_MAX_HP = 100.0;
    private static double timer_since_attack = 0.0;
    private static boolean inAttack = false;
    public static boolean ifWon = false;
    public boolean ifTreasureFound = false;
    private final static int SAILOR_DAMAGE = 15;
    private final static int SAILOR_SPEED = 1;
    private ArrayList<Items> inventory = new ArrayList<Items>();

    public void updateImage(boolean ifShooting){
    }

    public Sailor (double startX, double startY, Level currentLevel) {
        super(startX, startY, currentLevel);
        this.setCurrentImage(SAILOR_RIGHT);
        super.setImage(getCurrentImage());
        super.setMovementSpeed(SAILOR_SPEED);
        super.setHealthPoints(DEFAULT_MAX_HP);
        super.setMaxHealthPoints(DEFAULT_MAX_HP);
        super.setDamagePoints(SAILOR_DAMAGE);
    }

    // Adds item to sailor's inventory
    public void addToInventory(Items item){
        inventory.add(item);
    }

    public void update(Input input, ArrayList<Entity> entityList){
        // Timer to count attack cooldown
        if (inAttack) {
            timer_since_attack++;
        }
        double time_since_attack = timer_since_attack/(60.0/1000.0);
        // restarts timer after cooldown is finished
        if (time_since_attack > ATTACK_COOLDOWN){
            time_since_attack = 0;
            inAttack = false;
        }

        // Updates player image based on arrow keys player presses
        if (input.wasPressed(Keys.S) && inAttack == false){
            // Starts attack cooldown
            inAttack = true;
            timer_since_attack = 0;
            if (this.getCurrentImage().equals(SAILOR_LEFT)){
                this.setCurrentImage(SAILOR_HIT_LEFT);
            }
            else {
                this.setCurrentImage(SAILOR_HIT_RIGHT);
            }
        }
        if (input.isDown(Keys.S)){
            if (getCurrentDirection() == getLEFT()){
                setCurrentImage(SAILOR_HIT_LEFT);
            }
            else {
                setCurrentImage(SAILOR_HIT_RIGHT);
            }
        }
        if (input.isDown(Keys.UP)){
            setOldPoints();
            move(0,-getMovementSpeed());
        } else if (input.isDown(Keys.DOWN)){
            setOldPoints();
            move(0, getMovementSpeed());
        } else if (input.isDown(Keys.LEFT)){
            setOldPoints();
            move(-getMovementSpeed(),0);
            if (time_since_attack > ATTACK_COOLDOWN || !inAttack) {
                setCurrentImage(SAILOR_LEFT);
            }
            if (inAttack){
                setCurrentImage(SAILOR_HIT_LEFT);
            }
                setCurrentDirection(getLEFT());
        } else if (input.isDown(Keys.RIGHT)){
            setOldPoints();
            move(getMovementSpeed(),0);
            if (time_since_attack > ATTACK_COOLDOWN || !inAttack) {
                setCurrentImage(SAILOR_RIGHT);
            }
            if (inAttack){
                setCurrentImage(SAILOR_HIT_RIGHT);
            }
            setCurrentDirection(getRIGHT());
        }
        super.render();
        displayInventory();
        // Checks if sailor hsa collided with anything
        checkCollisions(entityList, this);
        // Disables sailor from leaving bounds
        if (!inBounds(getX(), getY())){
            moveBack();
        }
        // Checks if player is alive
        if (getHealthPoints() <= 0){
            super.setIfAlive(false);
        }
        // Renders HP and checks if game has been won
        renderHealthPoints();
        ifWon();
    }

    // checks if the sailor has met the win conditions for the current level
    public void ifWon(){
        if (super.getCurrentLevel().checkIfWon(this)){
            ifWon = true;
        }
    }

    // Function to display inventory of items picked up
    public void displayInventory(){
        if (inventory.size() > 0){
            for (int i=0; i<inventory.size(); i++){
                Items item= inventory.get(i);
                item.getIcon().draw(item.getIconX(), item.getIconY() + (i*item.getIconDistance()));
            }
        }
    }

    public static boolean getInAttack() {
        return inAttack;
    }
}
