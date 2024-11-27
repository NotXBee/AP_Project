package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.AngryBirds;

public class MainGameScreen2 implements Screen {
    private static final int PAUSE_BUTTON_X = 20;
    private static final int PAUSE_BUTTON_Y = 780;
    private static final int PAUSE_BUTTON_WIDTH = 50;
    private static final int PAUSE_BUTTON_HEIGHT = 50;
    private static final int NEXT_LEVEL_BUTTON_X = 950;
    private static final int NEXT_LEVEL_BUTTON_Y = 780;
    private static final int NEXT_LEVEL_BUTTON_WIDTH = 50;
    private static final int NEXT_LEVEL_BUTTON_HEIGHT = 50;
    private static final int SAVE_AND_EXIT_X = 282;
    private static final int SAVE_AND_EXIT_Y = 400;
    private static final int SAVE_AND_EXIT_WIDTH = 463;
    private static final int SAVE_AND_EXIT_HEIGHT = 104;
    private static final float NEXT_LEVEL_DELAY = 0.5f; // 1 second delay

    private Texture background;
    private Texture pauseButton;
    private Texture nextLevelButton;
    private Texture saveAndExit;
    private Texture birdRed;
    private Texture birdBomb;
    private Texture catapult;
    private Texture elementWood012;
    private Texture elementWood019;
    private Texture elementGlass014;
    private Texture elementGlass021;
    private Texture pigHelmet;
    private Texture pigPig;
    private boolean isSaveAndExitVisible = false;
    private boolean isPauseButtonPressed = false;
    private boolean isNextLevelButtonPressed = false;
    private float nextLevelButtonPressedTime = 0;

    AngryBirds game;

    public MainGameScreen2(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture("LevelBackground.jpg");
        pauseButton = new Texture("pause-circle.png");
        nextLevelButton = new Texture("NextLevel.png");
        saveAndExit = new Texture("SaveAndExit.png");
        birdRed = new Texture("birdRed.png");
        birdBomb = new Texture("birdBomb.png");
        catapult = new Texture("catapult.png");
        elementWood012 = new Texture("Wood_Horizontal_Full.png");
        elementWood019 = new Texture("Wood_Vertical_Full.png");
        elementGlass014 = new Texture("Glass_Horizontal_Full.png");
        elementGlass021 = new Texture("Glass_Vertical_Full.png");
        pigHelmet = new Texture("pigHelmet.png");
        pigPig = new Texture("pigPig.png");

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(pauseButton, PAUSE_BUTTON_X, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        game.batch.draw(nextLevelButton, NEXT_LEVEL_BUTTON_X, NEXT_LEVEL_BUTTON_Y, NEXT_LEVEL_BUTTON_WIDTH, NEXT_LEVEL_BUTTON_HEIGHT);

        game.batch.draw(birdBomb, 100, 100, 50, 50);
        game.batch.draw(birdBomb, 40, 100, 50, 50);
        game.batch.draw(catapult, 150, 100, 100, 100);
        game.batch.draw(birdRed, 150, 160, 50, 50);

        game.batch.draw(elementWood019, 600, 100, 30, 90);
        game.batch.draw(elementWood019, 750, 100, 30, 90);
        game.batch.draw(elementWood012, 600, 190, 90, 30);
        game.batch.draw(elementWood012, 690, 190, 90, 30);
        game.batch.draw(elementGlass021, 600, 220, 30, 90);
        game.batch.draw(elementGlass021, 750, 220, 30, 90);
        game.batch.draw(elementGlass014, 600, 310, 90, 30);
        game.batch.draw(elementGlass014, 690, 310, 90, 30);
        game.batch.draw(pigHelmet, 655, 120, 70, 50);
        game.batch.draw(pigPig, 655, 240, 70, 50);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX >= PAUSE_BUTTON_X && mouseX <= PAUSE_BUTTON_X + PAUSE_BUTTON_WIDTH && mouseY >= PAUSE_BUTTON_Y && mouseY <= PAUSE_BUTTON_Y + PAUSE_BUTTON_HEIGHT) {
            if (Gdx.input.isTouched() && !isPauseButtonPressed) {
                isSaveAndExitVisible = !isSaveAndExitVisible;
                isPauseButtonPressed = true;
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
            if (nextLevelButtonPressedTime >= NEXT_LEVEL_DELAY) {
                game.setScreen(new MainGameScreen3(game));
            }
        }

        if (isSaveAndExitVisible) {
            game.batch.draw(saveAndExit, SAVE_AND_EXIT_X, SAVE_AND_EXIT_Y, SAVE_AND_EXIT_WIDTH, SAVE_AND_EXIT_HEIGHT);
            if (mouseX >= SAVE_AND_EXIT_X && mouseX <= SAVE_AND_EXIT_X + SAVE_AND_EXIT_WIDTH && mouseY >= SAVE_AND_EXIT_Y && mouseY <= SAVE_AND_EXIT_Y + SAVE_AND_EXIT_HEIGHT) {
                if (Gdx.input.isTouched()) {
                    Gdx.app.exit();
                }
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {}

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
        saveAndExit.dispose();
        birdRed.dispose();
        birdBomb.dispose();
        catapult.dispose();
        elementWood012.dispose();
        elementWood019.dispose();
        elementGlass014.dispose();
        elementGlass021.dispose();
        pigHelmet.dispose();
        pigPig.dispose();
    }
}
