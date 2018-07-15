import java.applet.*;
import java.awt.*;
import java.net.*;

public class Pong extends Applet implements Runnable
{
static final int Left = 0;
static final int Right = 1; 
private Image dbImage;
private Graphics dbg;
ball Ball;
paddle leftPaddle;
paddle rightPaddle;

URL codeBase;

Image paddleImg;
Image background;

int wins;

	public void init() 
	{ 
		codeBase = getCodeBase();
		System.out.println(">> Init <<");
		background = getImage(codeBase,"background.gif");
		Ball = new ball(getImage(codeBase,"ball.gif"),150 - 10, 150 - 10, 20);
		paddleImg = getImage(codeBase,"paddle.gif");
		leftPaddle = new paddle(paddleImg,Left);
		rightPaddle = new paddle(paddleImg,Right);
		wins = 2;
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
		
			while (true && wins == 2)
			{ 
				// repaint the applet
				repaint();
				
					if (Ball.returnLeft() == 100)
					{
						wins = Left;
					}
					if (Ball.returnRight() == 100)
					{
						wins = Right;
					}
				
				Ball.handle();
				leftPaddle.handleCollide(Ball);				
				rightPaddle.handleCollide(Ball);
				rightPaddle.aiHandle(Ball);

					try
					{ 
						// Stop thread for 20 milliseconds
						Thread.sleep (20);

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
		g.drawImage(background,0,0,this);
		g.setColor(Color.black);
		g.drawRect(0,0,300 - 1,300 - 1);
		g.setColor(Color.gray);
		g.fillRect(0,0,300,15);
		g.setColor(Color.black);
		g.drawString(Ball.returnLeft() + ".", 15, 12);
		g.drawString("By Ben Jacobson, 2004.", 80, 12);		
		g.drawString(Ball.returnRight() + ".", 280, 12);			
		Ball.draw(g);
		leftPaddle.draw(g);
		rightPaddle.draw(g);
		
			if (wins == Left)
			{
				g.setColor(Color.black);
				g.drawString("Left wins (you)", 100, 160);	
			}
			if (wins == Right)
			{
				g.setColor(Color.black);			
				g.drawString("Right wins (the computer)", 100, 160);
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
			if (leftPaddle.y > y)
			{
				leftPaddle.moveUp();
			}
			if (leftPaddle.y < y)
			{
				leftPaddle.moveDown();
			}
		return true;
	}	
} 

