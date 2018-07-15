import java.applet.*;
import java.awt.*;
import java.net.*;

public class Asteroids extends Applet implements Runnable
{
int amount = 5; // max amount of comets on screen at once
int n = 0, i = 0;
boolean running = true;

public turret Turret;
public score Score;
public bullet Bullet;
public asteroid Asteroid[];

Image background;
Image cannon;
Image missile;
Image comet;
 
private Image dbImage;
private Graphics dbg;

	public void init() 
	{ 
		System.out.println(">> Init <<");			
		Turret = new turret();
		Score = new score();
		Bullet = new bullet();
		Asteroid = new asteroid[amount];
		background = getImage(getCodeBase(),"background.gif");
		cannon = getImage(getCodeBase(),"turret.gif");
		missile = getImage(getCodeBase(),"missile.gif");
		comet = getImage(getCodeBase(),"asteroid.gif");
		
			while (n < amount)
			{
				Asteroid[n] = new asteroid();
				n++;
			}
		n = 0;	
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
		
			while (running == true && Score.returnScore() < 10000)
			{ 
				repaint();
				Bullet.handle(Turret);
							
					while (n < amount)
					{
							if (Asteroid[n].isStillActive() != false)
							{
								Asteroid[n].handle(Turret, Bullet, Score);						
							}
							else
							{
								Asteroid[n].reset();	
							}
						n++;
					}
					if (n == amount)
					{
						n = 0;
					}
					if (Score.returnLives() == 0)
					{
						running = false;
						break;
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
				// set ThreadPriority to maximum value
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			}		
	}
	
	public void paint (Graphics g) 
	{ 
		System.out.println(">> Paint <<");
		g.drawImage(background,0,0,this);
		g.drawImage(cannon,Turret.returnX(),Turret.returnY(),this);
		g.drawImage(missile,Bullet.returnX(),Bullet.returnY(),this);
		
			while (i < amount)
			{
				g.drawImage(comet,Asteroid[i].returnX(),Asteroid[i].returnY(),this);
				i++;
			}
			if (i == amount)
			{
				i = 0;
			}
			if (running == false)
			{
				g.setColor(Color.red);
				g.drawString("Game Over!", (300 / 2 - 40), 400 / 2);
			}
			if (Score.returnScore() > 10000 - 1)
			{
				g.drawString("You have won!", (300 / 2 - 40), 400 / 2);				
			}
		g.setColor(Color.gray);
		g.fillRect(0,0,300,20);
		g.setColor(Color.white);
		g.drawString("Score: " + Score.returnScore() + ". Lives: " + Score.returnLives(), 10, 15);
		g.drawString("By Ben Jacobson", 200, 15);			
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
	public boolean mouseMove (Event e, int x, int y)
	{
		if (Turret.x > x)
		{
			Turret.moveLeft();
		}
		if (Turret.x < x)
		{
			Turret.moveRight();
		}
		return true;
	}	
}