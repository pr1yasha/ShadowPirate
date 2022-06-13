import bagel.*;

public class Level0 extends Level{
    private static final int WIN_X = 990;
    private static final int WIN_Y = 630;
    private static final String WINNING_MESSAGE = "LEVEL COMPLETE!";
    private final static int FONT_SIZE = 55;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final static int FONT_Y_POS = 402;
    private final Image BACKGROUND_IMAGE_0 = new Image("res/background0.png");
    private final String WORLD_FILE_0 = "res/level0.csv";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private final static String INSTRUCTION_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private final static int INSTRUCTION_OFFSET = 70;


    // Draw the winning screen for level0
    @Override
    public void drawWinScreen(){
        FONT.drawString(WINNING_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(WINNING_MESSAGE)/2.0)),
                FONT_Y_POS);
    }

    // Check if sailor has met win conditions for the level
    @Override
    public boolean checkIfWon(Sailor sailor){
        if (sailor.getX() >= WIN_X && sailor.getY() > WIN_Y){
            return true;
        }
        else {
            return false;
        }
    }

    public void drawStartScreen(){
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET));
        FONT.drawString(INSTRUCTION_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(INSTRUCTION_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET + INSTRUCTION_OFFSET));
    }


    public String getWorldFile() {
        return WORLD_FILE_0;
    }
    public Image getBackground() {
        return BACKGROUND_IMAGE_0;
    }
}
