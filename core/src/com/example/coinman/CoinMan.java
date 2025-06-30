package com.example.coinman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {

	SpriteBatch batch;

	Texture background;

	Texture[] man;

	int manState = 0;

	int pause = 0;

	float gravity = 0.2f;

	float velocity = 0;

	int manY = 0;

	Texture bomb;

	int bombCount = 0;

	Rectangle manRectangle;

	Texture coin;

	int coinCount = 0;

	Random random;

	int score = 0;

	BitmapFont font;

	BitmapFont font2;

	int gameState = 0;

	Texture dizzy;

	Preferences preferences;

	int highScore = 0;

	Sound sound;

	ArrayList<Integer> coinXs = new ArrayList<>();

	ArrayList<Integer> coinYs = new ArrayList<>();

	ArrayList<Rectangle> coinRectangles = new ArrayList<>();

	ArrayList<Integer> bombXs = new ArrayList<>();

	ArrayList<Integer> bombYs = new ArrayList<>();

	ArrayList<Rectangle> bombRectangles = new ArrayList<>();

	@Override
	public void create () {

		batch = new SpriteBatch();

		background = new Texture("bg.png");

		man = new Texture[4];

		man[0] = new Texture("frame-1.png");

		man[1] = new Texture("frame-2.png");

		man[2] = new Texture("frame-3.png");

		man[3] = new Texture("frame-4.png");

		manY = Gdx.graphics.getHeight() / 2;

		coin = new Texture("coin.png");

		bomb = new Texture("bomb.png");

		random = new Random();

		font = new BitmapFont();

		font.setColor(Color.WHITE);

		font.getData().scale(10);

		font2 = new BitmapFont();

		font2.setColor(Color.BLACK);

		font2.getData().scale(10);

		dizzy = new Texture("dizzy.png");

		preferences = Gdx.app.getPreferences("CoinMan.java");

		sound = Gdx.audio.newSound(Gdx.files.internal("slowmotion.mp3"));

	}

	public void makeCoin() {

		float height = random.nextFloat() * Gdx.graphics.getHeight();

		coinYs.add((int) height);

		coinXs.add(Gdx.graphics.getWidth());

	}

	public void makeBomb() {

		float height = random.nextFloat() * Gdx.graphics.getHeight();

		bombYs.add((int) height);

		bombXs.add(Gdx.graphics.getWidth());

	}

	@Override
	public void render () {

		batch.begin();

		sound.play();

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			//Game is live
			//bombs
			if (bombCount < 250) {

				bombCount++;

			} else {

				bombCount = 0;

				makeBomb();

			}

			bombRectangles.clear();

			for (int i=0; i < bombXs.size();i++) {

				batch.draw(bomb, bombXs.get(i), bombYs.get(i));

				bombXs.set(i, bombXs.get(i) - 8);

				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));

			}

			//coins
			if (coinCount < 100) {

				coinCount++;

			} else {

				coinCount = 0;

				makeCoin();

			}

			coinRectangles.clear();

			for (int i = 0; i < coinXs.size(); i++) {

				batch.draw(coin, coinXs.get(i), coinYs.get(i));

				coinXs.set(i, coinXs.get(i) - 4);

				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));

			}

			if (Gdx.input.justTouched()) {

				velocity = -10;

			}

			if (pause < 8) {

				pause++;

			} else {

				pause = 0;

				if (manState < 3) {

					manState++;

				} else {

					manState = 0;

				}

			}

			velocity += gravity;

			manY -= velocity;

			if (manY <= 0) {

				manY = 0;

			}

		} else if (gameState == 0) {

			//Waiting for game to start
			if (Gdx.input.justTouched()) {

				gameState = 1;

			}

		} else if (gameState == 2) {

			if (score > highScore) {

				highScore = score;

				preferences.putInteger("High Score", highScore);

				preferences.flush();

			} else {

				highScore = preferences.getInteger("High Score");

				preferences.flush();

			}

			font2.draw(batch, "Game Over Your\n Score is "+Integer.toString(score) +"\n Your High Score\n is "+Integer.toString(highScore), 100, 2000);

			//Game Over
			if (Gdx.input.justTouched()) {

				gameState = 1;

				manY = Gdx.graphics.getHeight() / 2;

				score = 0;

				velocity = 0;

				coinXs.clear();

				coinYs.clear();

				coinRectangles.clear();

				coinCount = 0;

				bombXs.clear();

				bombYs.clear();

				bombRectangles.clear();

				bombCount = 0;

			}
		}

		if (gameState == 2) {

			batch.draw(dizzy, 350, 350);

		} else {

			batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

		}

		manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY, man[manState].getWidth(), man[manState].getHeight());

		for (int i = 0; i < coinRectangles.size(); i++) {

			if (Intersector.overlaps(manRectangle, coinRectangles.get(i))) {

				score++;

				coinRectangles.remove(i);

				coinXs.remove(i);

				coinYs.remove(i);

				break;

			}
		}

		for (int i = 0; i < bombRectangles.size(); i++) {

			if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {

				gameState = 2;

			}
		}

		font.draw(batch, String.valueOf(score), 100, 200);

		batch.end();

	}

	@Override
	public void dispose () {

		sound.dispose();

		batch.dispose();

	}
}
