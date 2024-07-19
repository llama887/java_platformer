package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utils.Renderable;
import utils.Updateable;

public class Scene {
    ArrayList<Renderable> renderables = new ArrayList<Renderable>();
    ArrayList<Updateable> updateables = new ArrayList<Updateable>();

    public Scene() {
    }

    public void addToScene(Object gameObject) {
        if (gameObject instanceof Renderable) {
            renderables.add((Renderable) gameObject);
        }
        if (gameObject instanceof Updateable) {
            updateables.add((Updateable) gameObject);
        }
    }

    public void render(Graphics g) {
        for (Renderable renderable : renderables) {
            renderable.render(g);
        }
    }

    public void update() {
        for (Updateable updateable : updateables) {
            updateable.update();
        }
    }

}
