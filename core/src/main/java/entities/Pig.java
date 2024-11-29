// Pig.java
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Pig implements Damageable, Serializable {
    private static final long serialVersionUID = 1L;

    private transient Texture texture;
    private transient Body body;
    private String texturePath;
    private Vector2 position;
    private float width;
    private float height;
    private int hp;

    public Pig(World world, String texturePath, Vector2 position, float width, float height, int hp) {
        this.texturePath = texturePath;
        this.position = position;
        this.width = width;
        this.height = height;
        this.hp = hp;
        initialize(world);
    }

    private void initialize(World world) {
        texture = new Texture(texturePath);
        createPhysicsBody(world);
    }

    private void createPhysicsBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef);
        this.body.setUserData(this);

        shape.dispose();
    }

    public void reinitialize(World world) {
        createPhysicsBody(world);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void reduceHp(int amount) {
        hp -= amount;
        if (hp < 0) {
            hp = 0;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public float getAngle() {
        return body.getAngle();
    }

    public Body getBody() {
        return body;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initialize(new World(new Vector2(0, -9.8f), true));
    }
}