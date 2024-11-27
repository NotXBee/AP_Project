package entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    private Body body;

    public Ground(World world, Vector2 position, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        body.createFixture(shape, 0.0f);
        shape.dispose();
    }
}