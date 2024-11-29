package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandomParameters;

public class Bird implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Texture texture;
    private transient Body body;

    private String texturePath;
    private Vector2 position;
    private float radius;
    public Bird(World world, String texturePath, Vector2 position, float radius) {
        this.texturePath = texturePath;
        this.position = position;
        this.radius = radius;
        initialize(world);
    }

    private void initialize(World world) {
        texture = new Texture(texturePath);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody; // Initially set to Kinematic
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void setDensity(int density) {
        Fixture fixture = body.getFixtureList().first();
        fixture.setDensity(density);
        body.resetMassData();
    }
    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position, 0);
    }


    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public void setBodyType(BodyDef.BodyType bodyType) {
        body.setType(bodyType);
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Reinitialize the transient fields
        initialize(new World(new Vector2(0, -9.8f), true));
    }
}