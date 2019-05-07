package com.tysiac.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

public class ThousandSchnapsen extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture img;
	AssetManager assetManager;

	Texture loadingBarBackground;
	Texture loadingBarProgress;

	Texture hearts9;
	Texture diamonds9;
	Texture clubs9;
	Texture spades9;

	Texture hearts10;
	Texture diamonds10;
	Texture clubs10;
	Texture spades10;

	Texture heartsj;
	Texture diamondsj;
	Texture clubsj;
	Texture spadesj;

	Texture heartsq;
	Texture diamondsq;
	Texture clubsq;
	Texture spadesq;

	Texture heartsk;
	Texture diamondsk;
	Texture clubsk;
	Texture spadesk;

	Texture heartsa;
	Texture diamondsa;
	Texture clubsa;
	Texture spadesa;

	Texture revers;
	Texture revers_r;

	TextureRegion loadingBarProgressStart;
	TextureRegion loadingBarProgressBody;
	TextureRegion loadingBarProgressEnd;

	BitmapFont font;

	public float x=0;
	public float y=0;

	private void LoadAssets(){

		assetManager.load("badlogic.jpg",Texture.class);
		assetManager.load("loadingbar/loadingbar_background.png",Texture.class);
		assetManager.load("loadingbar/loadingbar_progress.png",Texture.class);
		//czcionka
		assetManager.load("fonts/font.fnt", BitmapFont.class);
		//karty 1 talii
		assetManager.load("cards/deck_1/h9.png",Texture.class);
		assetManager.load("cards/deck_1/d9.png",Texture.class);
		assetManager.load("cards/deck_1/c9.png",Texture.class);
		assetManager.load("cards/deck_1/s9.png",Texture.class);

		assetManager.load("cards/deck_1/h10.png",Texture.class);
		assetManager.load("cards/deck_1/d10.png",Texture.class);
		assetManager.load("cards/deck_1/c10.png",Texture.class);
		assetManager.load("cards/deck_1/s10.png",Texture.class);

		assetManager.load("cards/deck_1/hj.png",Texture.class);
		assetManager.load("cards/deck_1/dj.png",Texture.class);
		assetManager.load("cards/deck_1/cj.png",Texture.class);
		assetManager.load("cards/deck_1/sj.png",Texture.class);

		assetManager.load("cards/deck_1/hq.png",Texture.class);
		assetManager.load("cards/deck_1/dq.png",Texture.class);
		assetManager.load("cards/deck_1/cq.png",Texture.class);
		assetManager.load("cards/deck_1/sq.png",Texture.class);

		assetManager.load("cards/deck_1/hk.png",Texture.class);
		assetManager.load("cards/deck_1/dk.png",Texture.class);
		assetManager.load("cards/deck_1/ck.png",Texture.class);
		assetManager.load("cards/deck_1/sk.png",Texture.class);

		assetManager.load("cards/deck_1/ha.png",Texture.class);
		assetManager.load("cards/deck_1/da.png",Texture.class);
		assetManager.load("cards/deck_1/ca.png",Texture.class);
		assetManager.load("cards/deck_1/sa.png",Texture.class);

		assetManager.load("cards/deck_1/revers.png",Texture.class);
		assetManager.load("cards/deck_1/revers_r.png",Texture.class);

		assetManager.finishLoading();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		this.LoadAssets();
		font = assetManager.get("fonts/font.fnt", BitmapFont.class);
		img = assetManager.get("badlogic.jpg", Texture.class);
		loadingBarBackground = assetManager.get("loadingbar/loadingbar_background.png",Texture.class);
		loadingBarProgress = assetManager.get("loadingbar/loadingbar_progress.png",Texture.class);

		//inicjalizacja assetow kart
		hearts9 = assetManager.get("cards/deck_1/h9.png",Texture.class);
		diamonds9 = assetManager.get("cards/deck_1/d9.png",Texture.class);
		clubs9 = assetManager.get("cards/deck_1/c9.png",Texture.class);
		spades9 = assetManager.get("cards/deck_1/s9.png",Texture.class);

		hearts10 = assetManager.get("cards/deck_1/h10.png",Texture.class);
		diamonds10 = assetManager.get("cards/deck_1/d10.png",Texture.class);
		clubs10 = assetManager.get("cards/deck_1/c10.png",Texture.class);
		spades10 = assetManager.get("cards/deck_1/s10.png",Texture.class);

		heartsj = assetManager.get("cards/deck_1/hj.png",Texture.class);
		diamondsj = assetManager.get("cards/deck_1/dj.png",Texture.class);
		clubsj = assetManager.get("cards/deck_1/cj.png",Texture.class);
		spadesj = assetManager.get("cards/deck_1/sj.png",Texture.class);

		// rewersy
		revers = assetManager.get("cards/deck_1/revers.png",Texture.class);
		revers_r = assetManager.get("cards/deck_1/revers_r.png",Texture.class);

		//test

		//dlugosc paska ladowania = 800px  start=20px body = 760px end = 20px
		loadingBarProgressStart = new TextureRegion(loadingBarProgress,0,0,20,loadingBarProgress.getHeight());
		loadingBarProgressBody = new TextureRegion(loadingBarProgress,20,0,760,loadingBarProgress.getHeight());
		loadingBarProgressEnd= new TextureRegion(loadingBarProgress,780,0,20,loadingBarProgress.getHeight());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();


		int initialPosX = 100;
		int initialPosY = 300;

		//współrzędne wielkości ekranu
		int kartyX = Gdx.graphics.getWidth();
		int kartyY = Gdx.graphics.getHeight();

		if(assetManager.update()){
			//ekran ładowania
			//batch.draw(img, 400, 1000);

			//talia gracza
			int graczX = (int)Math.ceil(kartyX/70);
			int graczY = (int)Math.ceil(kartyY/70);

			batch.draw(hearts9,graczX,graczY);
			batch.draw(diamonds9,graczX+95,graczY);
			batch.draw(clubs9,graczX+190,graczY);
			batch.draw(spades9,graczX+285,graczY);
			batch.draw(hearts10,graczX+380,graczY);
			batch.draw(diamonds10,graczX+475,graczY);
			batch.draw(clubs10,graczX+570,graczY);
			batch.draw(spades10,graczX+665,graczY);
			batch.draw(heartsj,graczX+760,graczY);
			batch.draw(diamondsj,graczX+850,graczY);

			//karty przeciwnika nr 1
			int przeciwnik1X = (int)Math.ceil(kartyX/70);
			int przeciwnik1Y = (int)Math.ceil(kartyY/2);

			batch.draw(revers,przeciwnik1X,przeciwnik1Y);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-50);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-100);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-150);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-200);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-250);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-300);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-350);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-400);
			batch.draw(revers,przeciwnik1X,przeciwnik1Y-450);

			//karty przeciwnika nr 2
			int przeciwnik2X = (int)Math.ceil(kartyX-215);
			int przeciwnik2Y = (int)Math.ceil(kartyY/2);

			batch.draw(revers,przeciwnik2X,przeciwnik2Y);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-50);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-100);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-150);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-200);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-250);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-300);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-350);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-400);
			batch.draw(revers,przeciwnik2X,przeciwnik2Y-450);

			Gdx.input.setInputProcessor(this);
		}
//		ekran ładowania
//		batch.draw(loadingBarBackground,initialPosX,initialPosY);
//		batch.draw(loadingBarProgressStart,initialPosX,initialPosY+20);
//		batch.draw(loadingBarProgressBody,initialPosX+loadingBarProgressStart.getRegionWidth(),initialPosY+20,loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),loadingBarProgressBody.getRegionHeight());
//		batch.draw(loadingBarProgressEnd,initialPosX+loadingBarProgressStart.getRegionWidth()+loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),initialPosY+20);
//		font.draw(batch,"Ladowanie..."+(int)assetManager.getProgress()*100+"%",initialPosX+300,initialPosY-100);


		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.x = screenX;
		this.y =  Gdx.graphics.getHeight()-screenY;
		System.out.println("touchdown działa!");
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.x = screenX;
		this.y = Gdx.graphics.getHeight()-screenY;
		System.out.println("touchup działa!");
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
