import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @Priyasha
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    // Under the assumption the game begins with level0.
    private Level currentLevel = new Level0();
    private ArrayList<Entity> entityList = readCSV();
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private int winningMessageCounter = 0;

    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String INSTRUCTION_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private final static String END_MESSAGE = "GAME OVER";

    private boolean gameOn;
    private boolean gameEnd;

    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        gameOn = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    // function that reads the initial render locations for each object on screen from level0.csv
    private ArrayList readCSV(){
        String fileName = (currentLevel.getWorldFile());

        ArrayList<Entity> entityList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = br.readLine()) != null) {

                // split each row in csv into type of sprite and their coordinates
                String[] row = text.split(",");
                double xCoord = Integer.parseInt(text.split(",")[1]);
                double yCoord = Integer.parseInt(text.split(",")[2]);


                // Creates entity types
                if (row[0].equals("Sailor")){
                    entityList.add(new Sailor(xCoord, yCoord, currentLevel));
                }
                else if (row[0].equals("Pirate")) {
                    entityList.add(new Pirate(xCoord, yCoord, currentLevel));
                } else if (row[0].equals("Blackbeard")) {
                    entityList.add(new Blackbeard(xCoord, yCoord, currentLevel));
                } else if (row[0].equals("Block")){
                    if (currentLevel instanceof Level1) {
                        entityList.add(new Bomb(xCoord, yCoord));
                    }
                    else {
                        entityList.add(new Block(xCoord, yCoord));
                    }
                } else if (row[0].equals("Elixir")) {
                    entityList.add(new Elixir(xCoord, yCoord));
                } else if (row[0].equals("Potion")) {
                    entityList.add(new Potion(xCoord, yCoord));
                } else if (row[0].equals("Sword")) {
                    entityList.add(new Sword(xCoord, yCoord));
                } else if (row[0].equals("Treasure")) {
                    entityList.add(new Treasure(xCoord, yCoord)); }

                // Creates level bounds for each level
                else if (row[0].equals("TopLeft")) {
                    currentLevel.setTopLeftXBound(xCoord);
                    currentLevel.setTopLeftYBound(yCoord);
                } else if (row[0].equals("BottomRight")){
                    currentLevel.setBottomRightXBound(xCoord);
                    currentLevel.setBottomRightYBound(yCoord);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityList;
    }
    
    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        // Starts game
        if (input.wasPressed(Keys.SPACE)){
            gameOn = true;
        }
        // Draws background if game has started
        if (gameOn && !Sailor.ifWon){
            currentLevel.getBackground().draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        // Draws start screen before particular level
        if (!gameOn && !Sailor.ifWon && !gameEnd){
            currentLevel.drawStartScreen();
        }
        // Checks if game has been won
        if (Sailor.ifWon == true) {
            gameOn = false;
            // won only first level
            if (currentLevel instanceof Level0) {

                // Counts how long win screen has been displayed
                winningMessageCounter++;
                double counter = winningMessageCounter/(60.0/1000.0);
                currentLevel.drawWinScreen();

                // starts new level once win screen has been displayed for long enough
                if (counter > 3000){
                    currentLevel = new Level1();
                    entityList = readCSV();
                    Sailor.ifWon = false;
                }
            }
            // won second level
            if (currentLevel instanceof Level1){
                currentLevel.drawWinScreen();
            }
        }
        if (gameOn){
            for (int i=0; i<entityList.size(); i++){
                if (entityList.get(i) instanceof Character){
                    if (entityList.get(i) instanceof Sailor){
                        ((Sailor) entityList.get(i)).update(input, entityList);

                        // Checks if sailow is alive
                        if (!((Sailor) entityList.get(i)).isIfAlive()){
                            gameEnd = true;
                            gameOn = false;
                        }
                    }
                    // Deletes enemies from entityList once they're dead and have no projectiles on screen
                    if (!((Character) entityList.get(i)).isIfAlive()){
                        entityList.remove(i);
                    }
                    else {
                        ((Character) entityList.get(i)).update(input, entityList);
                    }
                    }
                else {
                    entityList.get(i).render();

                }
                }
            }

        // If player loses
        if (gameEnd){
            drawEndScreen();
        }
        }

    // Function that draws ending screen
    private void drawEndScreen(){
        FONT.drawString(END_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(END_MESSAGE)/2.0)),
                FONT_Y_POS);
    }
}
