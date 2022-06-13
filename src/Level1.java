import bagel.Font;
import bagel.Image;
import bagel.Window;

public class Level1 extends Level{

    private final  String WORLD_FILE_1 = "res/level1.csv";
    private final Image BACKGROUND_IMAGE_1 = new Image("res/background1.png");
    private static final String WINNING_MESSAGE = "CONGRATULATIONS!";
    private final static int FONT_SIZE = 55;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final static int FONT_Y_POS = 402;
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private final static String INSTRUCTION_MESSAGE = "FIND THE TREASURE";
    private final static int INSTRUCTION_OFFSET = 70;

    public String getWorldFile() {
        return WORLD_FILE_1;
    }
    public Image getBackground() {
        return BACKGROUND_IMAGE_1;
    }

    @Override
    // Draws win screen for level 1
    public void drawWinScreen(){
        FONT.drawString(WINNING_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(WINNING_MESSAGE)/2.0)),
                FONT_Y_POS);
    }

    public void drawStartScreen(){
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET));
        FONT.drawString(INSTRUCTION_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(INSTRUCTION_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET + INSTRUCTION_OFFSET));
    }

    @Override
    // Checks if win condition for level 1 has been met
    public boolean checkIfWon(Sailor sailor) {
        if (sailor.ifTreasureFound){
            return true;
        }
        return false;
    }


}
