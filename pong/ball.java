import java.applet.*;
import java.awt.*;
import java.net.*;

public class ball extends Applet
{
Image ball;
int leftScore, rightScore; // ball object also handles scoring, since a score class would be too ;
int initialX, initialY;    // to consider.
int x, y;				  
int tx, ty;
int bx, by;
int xspeed, yspeed;
int r;

	ball(Image theImage, int xpos, int ypos, int radius) // starting xposition and yposition, radius
	{
		ball = theImage;
		leftScore = 0;
		rightScore = 0;
		x = xpos;
		y = ypos;
		initialX = xpos;		
		initialY = ypos;
		tx = xpos;
		ty = ypos;
		r = radius;
		bx = xpos + radius;
		by = ypos + radius;
		xspeed = -5;
		yspeed = 0;
	}
	void handle()
	{
		tx = x;
		ty = y;
		bx = x + r;
		by = y + r;

			if (tx < (0 + 1))
			{
				rightScore += 10;
				reset();	
			}
			if (bx > (300 - 1))
			{					
				leftScore += 10;
				reset();
			}
			if (ty < (15 + 2) || by > (300 - 15))
			{
				yspeed = -yspeed;
			}
		x += xspeed;
		y += yspeed;
	}
	void draw(Graphics g)
	{
//		g.setColor(Color.green);
//		g.fillOval(x,y,r,r);
		g.drawImage(ball,x,y,this);
	}
	public void reset()
	{
		yspeed = 0;	
		x = initialX;
		y = initialY;
	}
	int returnLeft()
	{
		return leftScore;
	}	
	int returnRight()
	{
		return rightScore;
	}
}