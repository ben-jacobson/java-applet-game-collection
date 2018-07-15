import java.applet.*;
import java.awt.*;
import java.net.*;

public class TetriII extends Applet implements Runnable
{ 
private Image dbImage;
private Graphics dbg;

static final int blockColours = 7;

static final int intro = 0;
static final int game = 1;
static final int gameOver = 2;

URL gameURL;

GUI gameGui;
Image splashScreen, gameScreen, gameOverScreen;

blockMatrix gameBlockMatrix;
Image []blockImage;
Image []nextBlockImage;

float timerDelay, timer;

boolean downKeyPressed;

	public void init() 
	{ 
	int imagePlace = 0;
	
		System.out.println(">> Init <<");
		
		gameURL = getCodeBase();
		
		splashScreen = getImage(gameURL, "splash.gif");
		gameScreen = getImage(gameURL, "gameScreen.gif");
		gameOverScreen = getImage(gameURL, "gameOverScreen.gif");
		gameGui = new GUI(splashScreen, gameScreen, gameOverScreen);
		
		blockImage = new Image[blockColours];
		blockImage[0] = getImage(gameURL, "greyBlock.gif");					
		blockImage[1] = getImage(gameURL, "goldBlock.gif");
		blockImage[2] = getImage(gameURL, "redBlock.gif");
		blockImage[3] = getImage(gameURL, "greenBlock.gif");
		blockImage[4] = getImage(gameURL, "blueBlock.gif");
		blockImage[5] = getImage(gameURL, "purpleBlock.gif");										
		blockImage[6] = getImage(gameURL, "yellowBlock.gif");	
		
		nextBlockImage = new Image[7];
		nextBlockImage[0] = getImage(gameURL, "nextblock.gif");
		nextBlockImage[1] = getImage(gameURL, "nextLblock.gif");
		nextBlockImage[2] = getImage(gameURL, "nextL2block.gif");
		nextBlockImage[3] = getImage(gameURL, "nextTblock.gif");
		nextBlockImage[4] = getImage(gameURL, "nextZblock.gif");
		nextBlockImage[5] = getImage(gameURL, "nextSblock.gif");
		nextBlockImage[6] = getImage(gameURL, "nextIblock.gif");												
			
		gameBlockMatrix = new blockMatrix(blockImage, nextBlockImage);
		
		timer = 0;
		
		downKeyPressed = false;
	}
	
	public void start() 
	{ 
		System.out.println(">> Start <<");
		Thread th = new Thread(this);
		th.start();
	}

	public void stop() 
	{ 
		System.out.println(">> Stop <<");
	}
	
	public void destroy() 
	{ 
		System.out.println(">> Destroy <<");
	}
	
	public void run () 
	{ 
		System.out.println(">> Run <<");
		
			while (true)
			{ 
				// repaint the applet
				repaint();
				
					if (gameGui.gameState == game)
					{	
						gameBlockMatrix.level = gameGui.level;		
						timerDelay = 5;
						timer += 5.5;	
						
							if (timer > timerDelay)
							{
								gameBlockMatrix.handle(downKeyPressed);
								gameGui.score = gameBlockMatrix.score;
									
									if (gameBlockMatrix.cannotFitBlock == true)
									{
										gameGui.gameState = gameOver;
									}							
								timer = 0;
							}																
					}

					try
					{ 
						// Stop thread for 20 milliseconds
						Thread.sleep (20);

					}
					catch (InterruptedException ex)
					{ 
						// do nothing
					}
					
//				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//			creates error
			}		
	}
	
	public void paint (Graphics g) 
	{ 
		System.out.println(">> Paint <<");
		gameGui.draw(g);
		
			if (gameGui.gameState == game)
			{
				gameBlockMatrix.draw(g);
			}
	}
	
	public boolean mouseDown(Event e, int x, int y)	
	{
			switch (gameGui.gameState)
			{
				case intro:
					gameGui.gameState = game;
				break;
				case gameOver:
					gameGui.gameState = intro;
					gameGui.resetValues();
					gameBlockMatrix = new blockMatrix(blockImage, nextBlockImage);
					//reset the game
				break;
			}
		return true;
	}
	
	public boolean keyDown(Event e, int key)	
	{		
			switch (key)
			{
				case Event.DOWN:
					downKeyPressed = true;
				break;
				case Event.LEFT:
					gameBlockMatrix.theBlock.moveLeft(gameBlockMatrix.gameBlockMatrix);		
				break;
				case Event.RIGHT:
					gameBlockMatrix.theBlock.moveRight(gameBlockMatrix.gameBlockMatrix);				
				break;									
			}				
		return true;	
	}
	
	public boolean keyUp(Event e, int key)	
	{	
			switch(key)
			{
				case Event.UP:
					gameBlockMatrix.theBlock.rotate(gameBlockMatrix.gameBlockMatrix);					
				break;			
				case Event.DOWN:
					downKeyPressed = false;
				break;
			}		
		return true;	
	}
		
	public void update (Graphics g)
	{
		// initialize buffer
		
			if (dbImage == null)
			{
				dbImage = createImage (this.getSize().width, this.getSize().height);
				dbg = dbImage.getGraphics ();
			}

		// clear screen in background
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// draw elements in background
		dbg.setColor (getForeground());
		paint (dbg);

		// draw image on the screen
		g.drawImage (dbImage, 0, 0, this);
	}

} 
