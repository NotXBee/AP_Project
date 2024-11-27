package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.AngryBirds;

public class MainMenuScreen implements Screen {
    private static final int LOGO_X = 212;
    private static final int LOGO_Y = 600;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 153;
    private static final int NEW_GAME_X = 282;
    private static final int NEW_GAME_Y = 400;
    private static final int NEW_GAME_WIDTH = 463;
    private static final int NEW_GAME_HEIGHT = 104;
    private static final int LOAD_GAME_X = 282;
    private static final int LOAD_GAME_Y = 250;
    private static final int LOAD_GAME_WIDTH = 460;
    private static final int LOAD_GAME_HEIGHT = 100;
    private static final int EXIT_X = 282;
    private static final int EXIT_Y = 100;
    private static final int EXIT_WIDTH = 462;
    private static final int EXIT_HEIGHT = 102;

    private static final float LOAD_GAME_DELAY = 0.1f; // 1 second delay

    private Texture background;
    private Texture logo;
    private Texture newGame;
    private Texture loadGame;
    private Texture exit;

    private float loadGameTimer;
    private boolean loadGameClicked;

    AngryBirds game;

    public MainMenuScreen(AngryBirds game) {
        this.game = game;
        this.loadGameTimer = 0;
        this.loadGameClicked = false;
    }

    @Override
    public void show() {
        background = new Texture("NewBackground.png");
        logo = new Texture("NewLogo.png");
        newGame = new Texture("NewGame.png");
        loadGame = new Texture("LoadGame.png");
        exit = new Texture("Exit.png");
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1024, 1024);
        game.batch.draw(logo, LOGO_X, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
        game.batch.draw(newGame, NEW_GAME_X, NEW_GAME_Y, NEW_GAME_WIDTH, NEW_GAME_HEIGHT);
        game.batch.draw(loadGame, LOAD_GAME_X, LOAD_GAME_Y, LOAD_GAME_WIDTH, LOAD_GAME_HEIGHT);
        game.batch.draw(exit, EXIT_X, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX > EXIT_X && mouseX < EXIT_X + EXIT_WIDTH && mouseY > EXIT_Y && mouseY < EXIT_Y + EXIT_HEIGHT) {
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }

        if (mouseX > NEW_GAME_X && mouseX < NEW_GAME_X + NEW_GAME_WIDTH && mouseY > NEW_GAME_Y && mouseY < NEW_GAME_Y + NEW_GAME_HEIGHT) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGameScreen(game));
            }
        }

        if (mouseX > LOAD_GAME_X && mouseX < LOAD_GAME_X + LOAD_GAME_WIDTH && mouseY > LOAD_GAME_Y && mouseY < LOAD_GAME_Y + LOAD_GAME_HEIGHT) {
            if (Gdx.input.isTouched()) {
                loadGameClicked = true;
            }
        }

        if (loadGameClicked) {

            loadGameTimer += Gdx.graphics.getDeltaTime();
            if (loadGameTimer >= LOAD_GAME_DELAY) {
                MainGameScreen loadedScreen = MainGameScreen.loadState("test.ser", game);
                if (loadedScreen != null) {
                    game.setScreen(loadedScreen);
                }
                //game.setScreen(new LoadGameScreen(game));
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
        logo.dispose();
        newGame.dispose();
        loadGame.dispose();
        exit.dispose();
    }
}
