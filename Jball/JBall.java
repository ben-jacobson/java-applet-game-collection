import java.applet.*;
import java.awt.*;
import java.net.*;

public class JBall extends Applet implements Runnable
{ 
private Image dbImage;
private Graphics dbg;

int gameState;

Image intro;
Image credits;
Image gameOver;

Image gui;
Image ball, goldBall;
Image paddle, gun;

Image blue, green, teal, red, purple, yellow;

Image cannon, earth, speed, gold, life;
powerups power;

ball playBall;
paddle playPaddle;
bricks levelSet;
gui hud;
score sc;

	public void init() 
	{ 
		System.out.println(">> Init <<");
		gameState = 0;
		intro = getImage(getCodeBase(), "intro.gif");
		credits = getImage(getCodeBase(), "credits.gif");	
		gameOver = getImage(getCodeBase(), "gameover.gif");	
		
		gui = getImage(getCodeBase(), "gui.gif");		
		ball = getImage(getCodeBase(), "ball.gif");
		goldBall = getImage(getCodeBase(), "goldBall.gif");
		paddle = getImage(getCodeBase(), "paddle.gif");
		gun = getImage(getCodeBase(), "paddleGun.gif");
		
		cannon = getImage(getCodeBase(), "cannon.gif");
		earth = getImage(getCodeBase(), "earth.gif");
		speed = getImage(getCodeBase(), "speed.gif");
		gold = getImage(getCodeBase(), "gold.gif");						
		life = getImage(getCodeBase(), "life.gif");		

		blue = getImage(getCodeBase(),"blue.gif");
		green = getImage(getCodeBase(),"green.gif");
		teal = getImage(getCodeBase(),"teal.gif");
		red = getImage(getCodeBase(),"red.gif");
		purple = getImage(getCodeBase(),"purple.gif");
		yellow = getImage(getCodeBase(),"yellow.gif");										


		sc = new score(ball);				
		playPaddle = new paddle(paddle, gun, 175, 350);				
		playBall = new ball(ball, goldBall, playPaddle, 10);
		levelSet = new bricks(blue, green, teal, red, purple, yellow, sc, playBall, playPaddle);
		hud = new gui(intro, gui, credits);
		power = new powerups(cannon, earth, speed, gold, life);		
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
		

			while (true && gameState != 4)
			{ 
				// repaint the applet
				repaint();
				
					if (gameState == 1) // do all main game stuff in here
					{
						playBall.handle(hud, playPaddle, sc);
						playPaddle.handle(hud);
						power.handle(hud);
						levelSet.handle(playBall, sc, power, playPaddle);
												
							if (sc.level > 5)
							{
								gameState = 2;
							}
							if (sc.lives < 1)
							{
								gameState = 4;
							}
					}

					try
					{ 
						if (playBall.speedBall == false)
						{
						// Stop thread for 20 milliseconds
							Thread.sleep (20);
						}
						else
						{
							Thread.sleep (19);						
						}
					}
					catch (InterruptedException ex)
					{ 
						// do nothing
					}
					
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			}		
	}
	
	public void paint (Graphics g) 
	{ 
		System.out.println(">> Paint <<");
		
			if (gameState == 0)
			{
				hud.drawIntro(g);
			}
			if (gameState == 1 || gameState == 4)
			{
				hud.drawGui(g);
				levelSet.draw(g);
				playBall.draw(g);
				playPaddle.draw(g);
				sc.draw(g);
				power.draw(g);
			}
			if (gameState == 2)
			{
				hud.drawCredits(g);
			}
			if (gameState == 4)
			{
				g.setColor(Color.black);
				g.drawImage(gameOver,0,0,this);
			}
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
	public boolean mouseMove(Event e, int x, int y)
	{
		playPaddle.relocate(hud, x - 25);
		return true;
	}
	public boolean mouseDown(Event e, int x, int y)
	{
			if (gameState == 0)
			{		
				gameState = 1;
			}
			if (gameState == 1)
			{
				playBall.goBall();
				playPaddle.shoot();
			}
		return true;		
	}
	public boolean keyDown(Event e, int key)
	{
			if (key == 'q')
			{
				levelSet.nextLevel(sc, playBall, playPaddle);
			}
		return true;
	}	
}