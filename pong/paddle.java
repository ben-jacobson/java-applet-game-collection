import java.applet.*;
import java.awt.*;
import java.net.*;

public class paddle extends Applet
{
Image paddle;
 
static final int width = 10;
static final int height = 50;

static final int initialLX = 5;
static final int initialLY = (300 - height) / 2;
static final int initialRX = 300 - width - 5;
static final int initialRY = (300 - height) / 2;
int side;
int x, y;
int tx, ty;
int bx, by;

	paddle(Image paddleImg, int whichSide)
	{
		paddle = paddleImg;
		side = whichSide;
		
			if (side == 0) // left
			{
				x = initialLX;
				y = initialLY;			
			}
			if (side == 1) // right
			{
				x = initialRX;
				y = initialRY;
			}
			
		tx = x;
		ty = y;
		bx = x + width; 
		by = y + height; 
	}
	void handleCollide(ball playerBall)
	{
		tx = x;
		ty = y;
		bx = x + width; 
		by = y + height;
		
			if (side == 0) // left
			{			
				if (playerBall.tx < bx && playerBall.ty < by && playerBall.by > ty)
				{	
						if ((playerBall.y + (playerBall.r / 2)) < (y + (height / 2))) // upper
						{
							playerBall.yspeed = -2;
						}
						if ((playerBall.y + (playerBall.r / 2)) == (y + (height / 2))) // centre
						{
							playerBall.yspeed = 0; 													
						}
						if ((playerBall.y + (playerBall.r / 2)) > (y + (height / 2))) // lower
						{
							playerBall.yspeed = 2;							 						
						}
					playerBall.x += 10;
					playerBall.xspeed = -playerBall.xspeed;
				}
			}
			if (side == 1) // right
			{
				if (playerBall.bx > tx && playerBall.ty < by && playerBall.by > ty)
				{			
						if ((playerBall.y + (playerBall.r / 2)) < (y + (height / 2))) // upper
						{
							playerBall.yspeed = -4;
						}
						if ((playerBall.y + (playerBall.r / 2)) == (y + (height / 2))) // centre
						{
							playerBall.yspeed = 0; 												
						}
						if ((playerBall.y + (playerBall.r / 2)) > (y + (height / 2))) // lower
						{
							playerBall.yspeed = 4;						 						
						}				
					playerBall.x -= 10;
					playerBall.xspeed = -playerBall.xspeed;
				}				
			}
	}
	void aiHandle(ball playerBall)
	{
		if (playerBall.x > 90)
		{
			if (playerBall.y > (y + (playerBall.r / 2)))
			{
				moveDown();
			}
			if (playerBall.y < (y + (playerBall.r / 2)))
			{
				moveUp();
			}
		}			
	}
	public void moveUp()
	{	
		if (ty > 15 + 2)
		{
			y -= 3;
		}
	}
	public void moveDown()
	{
		if (by < 300 - 2)
		{	
			y += 3;
		}
	}
	void draw(Graphics g)
	{
//		g.setColor(Color.blue);
//		g.fillRect(tx, ty, bx - x, by - y);
		g.drawImage(paddle,x,y,this);
	}
}