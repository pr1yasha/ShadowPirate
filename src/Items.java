import bagel.Image;

public abstract class Items extends Entity {
    boolean pickedUp = false;
    private static final int ICON_X = 20;
    private static final int ICON_Y = 60;
    private static final int ICON_DISTANCE = 40;
    private Image icon;

    public int getIconX(){
        return ICON_X;
    }
    public int getIconY(){
        return ICON_Y;
    }
    public int getIconDistance(){
        return ICON_DISTANCE;
    }
    public void setIcon(Image iconImage){
        icon = iconImage;
    }
    public Image getIcon(){
        return icon;
    }

    public Items(double x, double y, Image image) {
        super(x, y);
        super.setImage(image);
    }

    //only render items if they haven't been picked up yet
    @Override
    public void render() {
        if (!pickedUp) {
            super.getImage().drawFromTopLeft(getX(), getY());
        }
    }

}
