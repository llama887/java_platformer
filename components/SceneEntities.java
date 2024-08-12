package components;

import java.awt.Graphics;
import java.util.ArrayList;

import utils.Renderable;
import utils.Updateable;

public class SceneEntities implements Renderable, Updateable {
    private ArrayList<Renderable> renderables = new ArrayList<Renderable>();
    private ArrayList<Updateable> updateables = new ArrayList<Updateable>();

    public void addToScene(Object gameObject) {
        if (gameObject instanceof Renderable) {
            renderables.add((Renderable) gameObject);
        }
        if (gameObject instanceof Updateable) {
            updateables.add((Updateable) gameObject);
        }
    }

    public void render(Graphics g, int xLevelOffset, int yLevelOffset) {
        for (Renderable renderable : renderables) {
            renderable.render(g, xLevelOffset, yLevelOffset);
        }
    }

    public void update() {
        for (Updateable updateable : updateables) {
            updateable.update();
        }
    }

}
