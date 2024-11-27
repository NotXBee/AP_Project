package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.AngryBirds;
import entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainGameScreen implements Screen {
    private static final int PAUSE_BUTTON_X = 20;
    private static final int PAUSE_BUTTON_Y = 780;
    private static final int PAUSE_BUTTON_WIDTH = 50;
    private static final int PAUSE_BUTTON_HEIGHT = 50;
    private static final int NEXT_LEVEL_BUTTON_X = 950;
    private static final int NEXT_LEVEL_BUTTON_Y = 780;
    private static final int NEXT_LEVEL_BUTTON_WIDTH = 50;
    private static final int NEXT_LEVEL_BUTTON_HEIGHT = 50;

    private Texture background;
    private Texture pauseButton;
    private Texture nextLevelButton;

    AngryBirds game;

    private boolean isDragging = false;
    private Vector2 initialTouch = new Vector2();
    private Vector2 launchVelocity = new Vector2();

    // Box2D variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Bird bird;
    private Ground ground;
    private Catapult catapult;
    private Pig pig;

    private List<Block> blocks;

    public MainGameScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture("LevelBackground.jpg");
        pauseButton = new Texture("pause-circle.png");
        nextLevelButton = new Texture("NextLevel.png");

        // Initialize Box2D world
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Register collision listener
        world.setContactListener(new CollisionListener());

        // Create ground
        ground = new Ground(world, new Vector2(0, 0), 1030, 140);

        // Create bird
        bird = new Bird(world, "birdRed.png", new Vector2(135, 220), 20f);

        // Create catapult
        catapult = new Catapult("catapult.png", 100, 150, 100, 100);

        blocks = new ArrayList<>();
        blocks.add(new Block(world, "Glass_Horizontal_Full.png", "Glass_Horizontal_Half.png", new Vector2(710, 250), 150, 35, 3000));
        blocks.add(new Block(world, "Glass_Vertical_Full.png", "Glass_Vertical_Half.png", new Vector2(710, 140), 35, 110, 3000));
        blocks.add(new Block(world, "Glass_Vertical_Full.png", "Glass_Vertical_Half.png", new Vector2(825, 140), 35, 110, 3000));

        // Create pig
        pig = new Pig(world, "pigPig.png", new Vector2(775, 160), 50, 50, 3000); // Adjust size and hp as needed
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        world.step(1/60f, 6, 2);
        debugRenderer.render(world, game.batch.getProjectionMatrix());

        game.batch.begin();
        game.batch.draw(background, 0, 0, 1024, 1024);
        game.batch.draw(pauseButton, PAUSE_BUTTON_X, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        game.batch.draw(nextLevelButton, NEXT_LEVEL_BUTTON_X, NEXT_LEVEL_BUTTON_Y, NEXT_LEVEL_BUTTON_WIDTH, NEXT_LEVEL_BUTTON_HEIGHT);

        // Draw catapult
        game.batch.draw(catapult.getTexture(), catapult.getX(), catapult.getY(), catapult.getWidth(), catapult.getHeight());

        // Draw bird at its Box2D position with increased size
        Vector2 birdPosition = bird.getPosition();
        float birdSize = 40f; // Adjust this value to match the bird's radius
        game.batch.draw(bird.getTexture(), birdPosition.x - birdSize / 2, birdPosition.y - birdSize / 2, birdSize, birdSize);

        // Draw blocks with rotation and remove if HP is zero
        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            if (block.getHp() <= 0) {
                world.destroyBody(block.getBody());
                block.dispose();
                blockIterator.remove();
                continue;
            }
            Vector2 blockPosition = block.getPosition();
            float blockWidth = block.getWidth();
            float blockHeight = block.getHeight();
            float blockRotation = block.getAngle() * MathUtils.radiansToDegrees;
            game.batch.draw(block.getTexture(),
                    blockPosition.x - blockWidth / 2,
                    blockPosition.y - blockHeight / 2,
                    blockWidth / 2,
                    blockHeight / 2,
                    blockWidth,
                    blockHeight,
                    1,
                    1,
                    blockRotation,
                    0,
                    0,
                    block.getTexture().getWidth(),
                    block.getTexture().getHeight(),
                    false,
                    false);
        }

        // Draw pig at its Box2D position and remove if HP is zero
        if (pig != null && pig.getHp() > 0) {
            Vector2 pigPosition = pig.getPosition();
            float pigWidth = pig.getWidth();
            float pigHeight = pig.getHeight();
            game.batch.draw(pig.getTexture(), pigPosition.x - pigWidth / 2, pigPosition.y - pigHeight / 2, pigWidth, pigHeight);
        } else if (pig != null) {
            world.destroyBody(pig.getBody());
            pig.dispose();
            pig = null;
        }

        // Handle bird dragging and launching
        handleInput(birdPosition, birdSize);

        game.batch.end();

        // Render trajectory if dragging
        if (isDragging) {
            renderTrajectory(birdPosition, launchVelocity);
        }

        debugRenderer.render(world, game.batch.getProjectionMatrix().cpy().scale(1, 1, 0));
    }

    private void handleInput(Vector2 birdPosition, float birdSize) {
        if (Gdx.input.isTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            Vector2 touchPosition = new Vector2(mouseX, mouseY);

            if (!isDragging && touchPosition.dst(birdPosition) <= birdSize / 2) {
                initialTouch.set(touchPosition);
                isDragging = true;
            } else if (isDragging) {
                launchVelocity.set(initialTouch).sub(touchPosition).scl(0.4f); // Adjust the scaling factor as needed
            }
        } else {
            if (isDragging) {
                bird.setLinearVelocity(launchVelocity);
                bird.setBodyType(BodyDef.BodyType.DynamicBody); // Change to DynamicBody when released
                isDragging = false;
            }
        }
    }

    private void renderTrajectory(Vector2 startPosition, Vector2 velocity) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // White color for the trajectory dots

        float timeStep = 1 / 4f; // Increase the time step to reduce the distance between points
        Vector2 gravity = world.getGravity();
        Vector2 position = new Vector2(startPosition);
        Vector2 vel = new Vector2(velocity);

        for (int i = 0; i < 25; i++) { // Reduce the number of points to 1/4th
            position.add(vel.x * timeStep, vel.y * timeStep);
            vel.add(gravity.x * timeStep, gravity.y * timeStep);
            shapeRenderer.circle(position.x, position.y, 2); // Draw small circles as dots
        }

        shapeRenderer.end();
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.batch.dispose();
        background.dispose();
        pauseButton.dispose();
        nextLevelButton.dispose();
        bird.dispose();
        catapult.dispose();
        if (pig != null) {
            pig.dispose();
        }
        world.dispose();
        debugRenderer.dispose();
        for (Block block : blocks) {
            block.dispose();
        }
    }
}