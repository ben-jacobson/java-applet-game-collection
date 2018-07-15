import java.applet.*;
import java.awt.*;

public class tetri extends Applet implements Runnable
{ 
private Image dbImage;
private Graphics dbg;

Image background;
Image whiteImg;
Image blackImg;
Image redImg;
Image greenImg;

blockMatrix tetriMatrix;
gui tetriScreen;
score sc;

int gameSpeed;

	public void init() 
	{ 
		System.out.println(">> Init <<");
		background = getImage(getCodeBase(),"gui.gif");
		whiteImg = getImage(getCodeBase(),"whiteBlock.gif");
		blackImg = getImage(getCodeBase(),"blackBlock.gif");
		redImg = getImage(getCodeBase(),"redBlock.gif");
		greenImg = getImage(getCodeBase(),"greenBlock.gif");						
		tetriMatrix = new blockMatrix(whiteImg, blackImg, redImg, greenImg);
		tetriScreen = new gui(background);
		sc = new score();
		gameSpeed = 20;						
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
		
			while (true && tetriMatrix.lost == false && tetriMatrix.won == false)
			{ 
				// repaint the applet
				repaint();
				
				tetriMatrix.handle(sc);

					try
					{ 
						// "sleep" for 20 millis
						Thread.sleep (gameSpeed);
						gameSpeed = sc.calculateSpeed() * 100;							
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
		tetriScreen.draw(g, sc);
		tetriMatrix.draw(g);
		
			if (tetriMatrix.won == true)
			{
				g.setColor(Color.white);
				g.fillRect(78,230,60,15);
				g.setColor(Color.black);			
				g.drawString("You won!",80,240);
			}
			if (tetriMatrix.lost == true)
			{
				g.setColor(Color.white);
				g.fillRect(78,230,60,15);
				g.setColor(Color.black);
				g.drawString("You lose",80,240);
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
	public boolean keyDown(Event e, int key)
	{
			if (key == Event.LEFT)
			{
				tetriMatrix.moveLeft();			
			}
			if (key == Event.RIGHT)
			{
				tetriMatrix.moveRight();		
			}
			if (key == Event.UP)
			{
				tetriMatrix.rotateBlock();
			}			
			if (key == Event.DOWN)
			{
				tetriMatrix.fallBlock();
			}
			if (key == 'q') // cheat code
			{
				sc.nextLevel();
			}
		return true;
	}
}