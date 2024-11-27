package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block implements Damageable{
    private Texture texture;
    private Texture halfHpTexture;
    private Body body;
    private float width;
    private float height;
    private int hp;
    private int maxHp;

    public Block(World world, String texturePath, String halfHpTexturePath, Vector2 position, float width, float height, int hp) {
        this.texture = new Texture(texturePath);
        this.halfHpTexture = new Texture(halfHpTexturePath);
        this.width = width;
        this.height = height;
        this.hp = hp;
        this.maxHp = hp;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x + width / 2, position.y + height / 2);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

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

    public Texture getTexture() {
        if (hp <= maxHp / 2) {
            return halfHpTexture;
        }
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
}