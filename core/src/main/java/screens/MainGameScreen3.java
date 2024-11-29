package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.some_example_name.AngryBirds;
import entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainGameScreen3 implements Screen, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int PAUSE_BUTTON_X = 20;
    private static final int PAUSE_BUTTON_Y = 780;
    private static final int PAUSE_BUTTON_WIDTH = 50;
    private static final int PAUSE_BUTTON_HEIGHT = 50;
    private static final int NEXT_LEVEL_BUTTON_X = 950;
    private static final int NEXT_LEVEL_BUTTON_Y = 780;
    private static final int NEXT_LEVEL_BUTTON_WIDTH = 50;
    private static final int NEXT_LEVEL_BUTTON_HEIGHT = 50;
    private static final float NEXT_LEVEL_DELAY = 0.5f;
    private static final int SAVE_AND_EXIT_X = 282;
    private static final int SAVE_AND_EXIT_Y = 400;
    private static final int SAVE_AND_EXIT_WIDTH = 463;
    private static final int SAVE_AND_EXIT_HEIGHT = 104;
    private transient BitmapFont font;

    private transient Texture background;
    private transient Texture pauseButton;
    private transient Texture nextLevelButton;
    private transient Texture saveAndExit;
    private transient Texture retryButton;
    private transient AngryBirds game;
    private transient Texture levelPassedImage;
    private transient Texture levelFailedImage;

    private boolean c = true;
    private boolean isDragging = false;
    private Vector2 initialTouch = new Vector2();
    private Vector2 launchVelocity = new Vector2();

    // Box2D variables
    private transient World world;
    private transient Box2DDebugRenderer debugRenderer;
    private Bird bird;
    private Ground ground;
    private Catapult catapult;
    private List<Pig> pigs;

    private boolean isBird = true;

    private List<Block> blocks;

    private boolean isBirdLaunched = false;
    private boolean levelPassed = false;
    private int birdCounter = 1;
    private long birdTimer = 0;
    private boolean isSaveAndExitVisible = false;
    private boolean isPauseButtonPressed = false;
    private boolean isNextLevelButtonPressed = false;
    private boolean isRetryButtonPressed = false;
    private float nextLevelButtonPressedTime = 0;

    private Vector2 birdPosition;
    private Vector2 birdVelocity;
    private boolean paused = false;
    public MainGameScreen3(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (c) {
            background = new Texture("LevelBackground.jpg");
            pauseButton = new Texture("pause-circle.png");
            nextLevelButton = new Texture("NextLevel.png");
            retryButton = new Texture("Retry.png");
            saveAndExit = new Texture("SaveAndExit.png");
            // Initialize Box2D world
            world = new World(new Vector2(0, -9.8f), true);
            debugRenderer = new Box2DDebugRenderer();

            // Register collision listener
            world.setContactListener(new CollisionListener());

            // Create ground
            ground = new Ground(world, new Vector2(0, 0), 1030, 140);

            // Create bird
            bird = new Bird(world, "birdBigRed.png", new Vector2(135, 220), 35f);
            bird.setDensity(10);
            // Create catapult
            catapult = new Catapult("catapult.png", 100, 150, 100, 100);

            blocks = new ArrayList<>();
            blocks.add(new Block(world, "Metal_Horizontal_Full.png", "Metal_Horizontal_Half.png", new Vector2(640, 250), 150, 35, 10000));
            blocks.add(new Block(world, "Metal_Vertical_Full.png", "Metal_Vertical_Half.png", new Vector2(640, 140), 35, 110, 8000));
            blocks.add(new Block(world, "Metal_Vertical_Full.png", "Metal_Vertical_Half.png", new Vector2(755, 140), 35, 110, 8000));

            blocks.add(new Block(world, "Wood_Horizontal_Full.png", "Wood_Horizontal_Half.png", new Vector2(790, 250), 150, 35, 10000));
            blocks.add(new Block(world, "Wood_Vertical_Full.png", "Wood_Vertical_Half.png", new Vector2(790, 140), 35, 110, 5000));
            blocks.add(new Block(world, "Wood_Vertical_Full.png", "Wood_Vertical_Half.png", new Vector2(905, 140), 35, 110, 5000));

            blocks.add(new Block(world, "Glass_Vertical_Full.png", "Glass_Vertical_Half.png", new Vector2(710, 285), 35, 110, 3000));
            blocks.add(new Block(world, "Glass_Vertical_Full.png", "Glass_Vertical_Half.png", new Vector2(825, 285), 35, 110, 5000));
            blocks.add(new Block(world, "Glass_Horizontal_Full.png", "Glass_Horizontal_Half.png", new Vector2(710, 395), 150, 35, 3000));
            blocks.add(new Block(world, "transparent.png", "transparent.png", new Vector2(100, 140), 70, 45, 1000000));
            font = new BitmapFont();

            // Create pigs
            pigs = new ArrayList<>();
            pigs.add(new Pig(world, "pigPig.png", new Vector2(785, 325), 55, 55, 3000));
            pigs.add(new Pig(world, "pigKing.png", new Vector2(865, 160), 55, 55, 7000));
            pigs.add(new Pig(world, "pigHelmet.png", new Vector2(715, 160), 50, 50, 6000));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (!paused) {
            world.step(1 / 60f, 6, 2);
        }
        debugRenderer.render(world, game.batch.getProjectionMatrix());

        game.batch.begin();
        game.batch.draw(background, 0, 0, 1024, 1024);
        game.batch.draw(pauseButton, PAUSE_BUTTON_X, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        if(levelPassed){
            game.batch.draw(nextLevelButton, NEXT_LEVEL_BUTTON_X, NEXT_LEVEL_BUTTON_Y, NEXT_LEVEL_BUTTON_WIDTH, NEXT_LEVEL_BUTTON_HEIGHT);
        }else{
            game.batch.draw(retryButton, NEXT_LEVEL_BUTTON_X, NEXT_LEVEL_BUTTON_Y, NEXT_LEVEL_BUTTON_WIDTH, NEXT_LEVEL_BUTTON_HEIGHT);
        }

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

        // Draw pigs at their Box2D positions and remove if HP is zero
        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            if (pig.getHp() <= 0) {
                world.destroyBody(pig.getBody());
                pig.dispose();
                pigIterator.remove();
                continue;
            }
            Vector2 pigPosition = pig.getPosition();
            float pigWidth = pig.getWidth();
            float pigHeight = pig.getHeight();
            game.batch.draw(pig.getTexture(), pigPosition.x - pigWidth / 2, pigPosition.y - pigHeight / 2, pigWidth, pigHeight);
        }

        if (pigs.isEmpty()) {
            levelPassed = true;
        }

        if (isBird && isBirdLaunched && (TimeUtils.timeSinceMillis((long) birdTimer) > 30000)) {
            isBird = false;
            birdCounter--;
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX >= PAUSE_BUTTON_X && mouseX <= PAUSE_BUTTON_X + PAUSE_BUTTON_WIDTH && mouseY >= PAUSE_BUTTON_Y && mouseY <= PAUSE_BUTTON_Y + PAUSE_BUTTON_HEIGHT) {
            if (Gdx.input.isTouched() && !isPauseButtonPressed) {
                isSaveAndExitVisible = !isSaveAndExitVisible;
                paused = !paused;
                isPauseButtonPressed = true;
                birdTimer = TimeUtils.millis();
            }
        } else {
            isPauseButtonPressed = false;
        }

        if (!Gdx.input.isTouched()) {
            isPauseButtonPressed = false;
        }

        if (mouseX >= NEXT_LEVEL_BUTTON_X && mouseX <= NEXT_LEVEL_BUTTON_X + NEXT_LEVEL_BUTTON_WIDTH && mouseY >= NEXT_LEVEL_BUTTON_Y && mouseY <= NEXT_LEVEL_BUTTON_Y + NEXT_LEVEL_BUTTON_HEIGHT) {
            if (Gdx.input.isTouched() && !isNextLevelButtonPressed) {
                isNextLevelButtonPressed = true;
                nextLevelButtonPressedTime = 0;
            }
        }

        if (isNextLevelButtonPressed) {
            nextLevelButtonPressedTime += Gdx.graphics.getDeltaTime();
            if (levelPassed && nextLevelButtonPressedTime >= NEXT_LEVEL_DELAY) {
                game.setScreen(new MainMenuScreen(game));
            }else if (nextLevelButtonPressedTime >= NEXT_LEVEL_DELAY) {
                game.setScreen(new MainGameScreen3(game));
            }
        }

        if (isSaveAndExitVisible) {
            game.batch.draw(saveAndExit, SAVE_AND_EXIT_X, SAVE_AND_EXIT_Y, SAVE_AND_EXIT_WIDTH, SAVE_AND_EXIT_HEIGHT);
            if (mouseX >= SAVE_AND_EXIT_X && mouseX <= SAVE_AND_EXIT_X + SAVE_AND_EXIT_WIDTH && mouseY >= SAVE_AND_EXIT_Y && mouseY <= SAVE_AND_EXIT_Y + SAVE_AND_EXIT_HEIGHT) {
                if (Gdx.input.isTouched()) {
                    saveState("test.ser");
                    Gdx.app.exit();
                }
            }
        }
        // Handle bird dragging and launching
        if (!paused) {
            handleInput(birdPosition, birdSize);
        }
        game.batch.end();

        // Render trajectory if dragging
        if (!paused && isDragging) {
            renderTrajectory(birdPosition, launchVelocity);
        }

        if (levelPassed) {
            game.batch.begin();
            levelPassedImage = new Texture("LevelPassed.png");
            game.batch.draw(levelPassedImage, 300, 500);
            //font.draw(game.batch, "Level Passed!", 400, 500);
            game.batch.end();
        }


        if (!levelPassed && birdCounter == 0) {
            game.batch.begin();
            levelFailedImage = new Texture("LevelFailed.png");
            game.batch.draw(levelFailedImage, 300, 500);
            //font.draw(game.batch, "Game Over!", 400, 500);
            game.batch.end();
        }

        //debugRenderer.render(world, game.batch.getProjectionMatrix().cpy().scale(1, 1, 0));
    }

    private void handleInput(Vector2 birdPosition, float birdSize) {
        if (Gdx.input.isTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            Vector2 touchPosition = new Vector2(mouseX, mouseY);

            if (!isBirdLaunched && !isDragging && touchPosition.dst(birdPosition) <= birdSize / 2) {
                initialTouch.set(touchPosition);
                isDragging = true;

            } else if (!isBirdLaunched && isDragging) {
                launchVelocity.set(initialTouch).sub(touchPosition).scl(0.4f); // Adjust the scaling factor as needed

            }
        } else {
            if (isDragging && !isBirdLaunched) {
                bird.setLinearVelocity(launchVelocity);
                bird.setBodyType(BodyDef.BodyType.DynamicBody); // Change to DynamicBody when released
                isDragging = false;
                isBirdLaunched = true;
                birdTimer = TimeUtils.millis();
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
        for (Pig pig : pigs) {
            pig.dispose();
        }
        world.dispose();
        debugRenderer.dispose();
        for (Block block : blocks) {
            block.dispose();
        }
    }

    public void saveState(String filePath) {
        birdPosition = bird.getPosition();
        birdVelocity = bird.getLinearVelocity();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
            System.out.println("Game state saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in saving/loading game state");
        }
    }

    public static MainGameScreen3 loadState(String filePath, AngryBirds game) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            MainGameScreen3 screen = (MainGameScreen3) ois.readObject();
            screen.game = game; // Reassign the transient field
            screen.initializeTransientFields();
            screen.c = false;

            // Synchronize bird position and velocity
            if (screen.birdPosition != null) {
                screen.bird.setPosition(screen.birdPosition);
            }
            if (screen.birdVelocity != null) {
                screen.bird.setLinearVelocity(screen.birdVelocity);
                screen.bird.setBodyType(BodyDef.BodyType.DynamicBody);
            }

            // Set HP for blocks and pigs
            for (Block block : screen.blocks) {
                block.setHp(block.getHp());
            }
            for (Pig pig : screen.pigs) {
                pig.setHp(pig.getHp());
            }
            screen.birdTimer = TimeUtils.millis();
            return screen;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error in saving/loading game state");
            return null;
        }
    }

    private void initializeTransientFields() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        background = new Texture("LevelBackground.jpg");
        pauseButton = new Texture("pause-circle.png");
        nextLevelButton = new Texture("NextLevel.png");
        retryButton = new Texture("Retry.png");
        saveAndExit = new Texture("SaveAndExit.png");
        font = new BitmapFont();
        world.setContactListener(new CollisionListener());

        // Re-create bird with serialized properties
        bird = new Bird(world, "birdBigRed.png", birdPosition != null ? birdPosition : new Vector2(135, 220), 35f);
        bird.setDensity(10);
        if (birdVelocity != null) {
            bird.setLinearVelocity(birdVelocity);
            bird.setBodyType(BodyDef.BodyType.DynamicBody);
        }

        // Re-create ground
        ground = new Ground(world, new Vector2(0, 0), 1030, 140);

        // Re-create catapult
        catapult = new Catapult("catapult.png", 100, 150, 100, 100);

        // Re-create blocks and pigs
        blocks = blocks != null ? blocks : new ArrayList<>();
        for (Block block : blocks) {
            block.reinitialize(world);
        }

        pigs = pigs != null ? pigs : new ArrayList<>();
        for (Pig pig : pigs) {
            pig.reinitialize(world);
        }
    }
}