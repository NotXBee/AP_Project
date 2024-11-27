package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.AngryBirds;

public class LoadGameScreen implements Screen {
    private static final int OPTION_Y = Gdx.graphics.getHeight() - 200; // Adjust Y to top
    private static final int OPTION_X = 100; // Adjust X to left
    private static final int OPTION_WIDTH = 150; // Increased width
    private static final int OPTION_HEIGHT = 150; // Increased height
    private static final int OPTION_SPACING = 20;

    private Texture background;
    private Texture option1;
    private Texture option2;
    private Texture option3;

    AngryBirds game;

    public LoadGameScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture("NewBackground.png");
        option1 = new Texture("1.png");
        option2 = new Texture("2.png");
        option3 = new Texture("3.png");
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1024, 1024);

        int option1X = OPTION_X;
        int option2X = option1X + OPTION_WIDTH + OPTION_SPACING;
        int option3X = option2X + OPTION_WIDTH + OPTION_SPACING;

        game.batch.draw(option1, option1X, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);
        game.batch.draw(option2, option2X, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);
        game.batch.draw(option3, option3X, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX > option1X && mouseX < option1X + OPTION_WIDTH && mouseY > OPTION_Y && mouseY < OPTION_Y + OPTION_HEIGHT) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGameScreen(game));
            }
        }

        if (mouseX > option2X && mouseX < option2X + OPTION_WIDTH && mouseY > OPTION_Y && mouseY < OPTION_Y + OPTION_HEIGHT) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGameScreen2(game));
            }
        }

        if (mouseX > option3X && mouseX < option3X + OPTION_WIDTH && mouseY > OPTION_Y && mouseY < OPTION_Y + OPTION_HEIGHT) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGameScreen3(game));
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
        option1.dispose();
        option2.dispose();
        option3.dispose();
    }
}
