package entities;

import com.badlogic.gdx.graphics.Texture;

public class Catapult {
    private Texture texture;
    private float x, y, width, height;

    public Catapult(String texturePath, float x, float y, float width, float height) {
        texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void dispose() {
        texture.dispose();
    }
}