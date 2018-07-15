import java.applet.*;
import java.awt.*;

public class GUI extends Applet
{
static final int intro = 0;
static final int game = 1;
static final int gameOver = 2;

Image splashScreen, gameScreen, gameOverScreen;
int gameState;
int score, level, nextLevel;

	GUI(Image splash, Image game, Image gameOver)
	{
		gameState = 0;
		splashScreen = splash;
		gameScreen = game;
		gameOverScreen = gameOver;
		score = 0;		
	}
	public void draw(Graphics g)
	{
		switch(gameState)
		{
			case intro:
				g.drawImage(splashScreen, 0, 0, this);
			break;
			case game:
				g.drawImage(gameScreen, 0, 0, this);
				drawInfo(g);
			break;
			case gameOver:
				g.drawImage(gameOverScreen, 0, 0, this);
			break;
		}
	}
	public void resetValues()
	{
		score = 0; 
		level = (score / 1000) + 1;	
		nextLevel = level * 1000;
	}
	private void drawInfo(Graphics g)		
	{
	Font infoFont;
	
		level = (score / 1000) + 1;	
		nextLevel = level * 1000;		
		
		infoFont = new Font("Bookman Old Style", Font.PLAIN, 24);	
		g.setFont(infoFont);
		g.drawString("Score: " + score, 290, 180);
		g.drawString("Level: " + level, 290, 205);
		g.drawString("Next Level: " + nextLevel, 290, 230);
	}
}