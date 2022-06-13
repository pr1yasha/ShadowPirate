import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

public class Pirate extends Character {
    private final static Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final static Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final static Image PIRATE_HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");
    private final static Image PIRATE_HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final static int PIRATE_DAMAGE = 20;
    private final static int PIRATE_HEALTHPOINTS = 45;
    private final static int ATTACK_RANGE = 100;

    public Pirate(double startX, double startY, Level currentLevel) {
        super(startX, startY, currentLevel);
        this.setCurrentImage(PIRATE_RIGHT);
        generateDirection();
        generateSpeed();
        updateImage(false);
        super.setMaxHealthPoints(PIRATE_HEALTHPOINTS);
        super.setHealthPoints(PIRATE_HEALTHPOINTS);
        super.setFontSize(15);
        super.setAttackRange(ATTACK_RANGE);
    }

    @Override
    // Draws pirate image depending on current state and direction
    public void updateImage(boolean ifInvincible){
        if (ifInvincible){
            if (getCurrentDirection() == getLEFT()) {
                setCurrentImage(PIRATE_HIT_LEFT);
            } else if (getCurrentDirection() == getRIGHT()) {
                setCurrentImage(PIRATE_HIT_RIGHT);
            }
            else {
                setCurrentImage(PIRATE_HIT_RIGHT);
            }
        }
        else {
            if (getCurrentDirection() == getLEFT()) {
                setCurrentImage(PIRATE_LEFT);
            } else if (getCurrentDirection() == getRIGHT()) {
                setCurrentImage(PIRATE_RIGHT);
            }
            else {
                setCurrentImage(PIRATE_RIGHT);
            }
        }
    }


}
