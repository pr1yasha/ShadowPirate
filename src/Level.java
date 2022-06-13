import bagel.Image;

public abstract class Level {
    private double topLeftXBound;
    private double topLeftYBound;
    private double BottomRightXBound;
    private double BottomRightYBound;

    public Level(){}

    // Checks if win condition for the level has been met
    public abstract boolean checkIfWon(Sailor sailor);
    // Draws win screen for current level
    public abstract void drawWinScreen();
    public abstract void drawStartScreen();

    public abstract Image getBackground();
    public abstract String getWorldFile();
    public void setTopLeftXBound(double topLeftXBound) {
        this.topLeftXBound = topLeftXBound;
    }
    public void setTopLeftYBound(double topLeftYBound) {
        this.topLeftYBound = topLeftYBound;
    }
    public void setBottomRightXBound(double bottomRightXBound) {
        BottomRightXBound = bottomRightXBound;
    }
    public void setBottomRightYBound(double bottomRightYBound) {
        BottomRightYBound = bottomRightYBound;
    }
    public double getTopLeftXBound() {
        return topLeftXBound;
    }
    public double getTopLeftYBound() {
        return topLeftYBound;
    }
    public double getBottomRightXBound() {
        return BottomRightXBound;
    }
    public double getBottomRightYBound() {
        return BottomRightYBound;
    }
}
