package entities;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Damageable && fixtureB.getBody().getUserData() instanceof Damageable) {
            Damageable damageableA = (Damageable) fixtureA.getBody().getUserData();
            Damageable damageableB = (Damageable) fixtureB.getBody().getUserData();

            float impactForce = contact.getWorldManifold().getPoints()[0].dst(contact.getWorldManifold().getPoints()[1]);
            int damage = (int) (impactForce * 10); // Adjust the damage calculation as needed

            damageableA.reduceHp(damage);
            damageableB.reduceHp(damage);
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}