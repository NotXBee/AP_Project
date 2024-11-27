package entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Ground implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient Body body;
    private Vector2 position;
    private float width;
    private float height;

    public Ground(World world, Vector2 position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
        initialize(world);
    }

    private void initialize(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        body.createFixture(shape, 0.0f);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Reinitialize the transient field
        initialize(new World(new Vector2(0, -9.8f), true));
    }
}