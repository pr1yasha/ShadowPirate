import bagel.Input;

import java.util.ArrayList;

// Interface for objects able to move
public interface Movable {
    void move(double xMove, double yMove);
    public void update(Input input, ArrayList<Entity> entityList);
    public void checkCollisions(ArrayList<Entity> entityList, Entity entity);
}
