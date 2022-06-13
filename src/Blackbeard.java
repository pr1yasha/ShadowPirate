import bagel.Image;

public class Blackbeard extends Character{
    private final static Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image BLACKBEARD_HIT_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");
    private final static Image BLACKBEARD_HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static int BLACKBEARD_HEALTHPOINTS = 90;
    private final static int ATTACK_RANGE = 200;

    public Blackbeard (double startX, double startY, Level currentLevel) {
        super(startX, startY, currentLevel);
        this.setCurrentImage(BLACKBEARD_RIGHT);
        generateDirection();
        generateSpeed();
        updateImage(false);
        super.setFontSize(15);
        setMaxHealthPoints(BLACKBEARD_HEALTHPOINTS);
        setHealthPoints(BLACKBEARD_HEALTHPOINTS);
        setAttackRange(ATTACK_RANGE);
    }

    // Update images of blackbeard depending on current direction and state
    @Override
    public void updateImage(boolean ifInvincible) {
        if (ifInvincible){
            if (getCurrentDirection() == getLEFT()) {
                setCurrentImage(BLACKBEARD_HIT_LEFT);
            } else if (getCurrentDirection() == getRIGHT()) {
                setCurrentImage(BLACKBEARD_HIT_RIGHT);
            }
            else {
                setCurrentImage(BLACKBEARD_HIT_RIGHT);
            }
        }
        else {
            if (getCurrentDirection() == getLEFT()) {
                setCurrentImage(BLACKBEARD_LEFT);
            } else if (getCurrentDirection() == getRIGHT()) {
                setCurrentImage(BLACKBEARD_RIGHT);
            }
            else {
                setCurrentImage(BLACKBEARD_RIGHT);
            }
        }
    }
}
