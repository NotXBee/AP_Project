package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.MainMenuScreen;

public class AngryBirds extends Game {
    public SpriteBatch batch;
    private Music soundtrack;

    @Override
    public void create() {
        batch = new SpriteBatch();
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.mp3"));
        soundtrack.setLooping(true);
        soundtrack.play();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        soundtrack.dispose();
    }
}