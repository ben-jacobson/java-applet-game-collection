import java.applet.*;
import java.awt.*;

public class ball extends Applet
{
Image ball, gold;

int x, y;
int diameter;
int centerx, centery;
int xspeed, yspeed;
boolean goldBall, speedBall;
boolean stopped;
boolean setBoolsFalse; // for passing into bricks class

	ball(Image gif, Image goldb, paddle pd, int d)
	{
		ball = gif;
		gold = goldb;
		x = pd.x + (pd.width / 2) - (d / 2);
		y = pd.y - d - 5;
		diameter = d;
		stopBall();	
		setBoolsFalse = false;
		
		goldBall = false;
		speedBall = false;
	}
	private void refresh()
	{
		centerx = x + (diameter / 2);
		centery = y + (diameter / 2);			
	}
	public void handle(gui bounds, paddle pd, score sc)
	{
		refresh();
			
			if (centerx < bounds.bx - 1 - (diameter / 2) && centerx > bounds.tx + (diameter / 2) + 1)
			{
				x += xspeed;
			}
			else
			{
				xspeed = -xspeed;
				x += xspeed;
			}
			if (centery > bounds.ty + (diameter / 2) + 1)			
			{
				y += yspeed;
			}
			else
			{
				yspeed = -yspeed;
				y += yspeed;				
			}				
			if (y + diameter > bounds.by - 1)
			{
				sc.lifeDown();
				x = pd.x + (pd.bx / 2);
				y = pd.y - diameter;
				stopBall();
				resetBall(pd);
				setBoolsFalse = true;
			}
			
			if ((y + diameter) > pd.ty + 1 && (y + diameter) < pd.by &&
			    (x + diameter) < pd.bx + 2 && x > pd.tx - 2)
			{				
				yspeed = -yspeed;
				y += yspeed;	
			}
			
			if (stopped == true)
			{
				x = pd.x + (pd.width / 2) - (diameter / 2);
				y = pd.y - diameter - 5;			
			}			
	}
	public void stopBall()
	{
		if (stopped == false)
		{
			stopped = true;
			xspeed = 0;
			yspeed = 0;
		}
	}
	public void goBall()
	{
		if (stopped == true)
		{
			stopped = false;
			xspeed = 2;
			yspeed = -6;
		}
	}
	public void resetBall(paddle pd)
	{
		x = pd.x + (pd.width / 2) - (diameter / 2);
		y = pd.y - diameter - 5;	
	}
	public void draw(Graphics g)
	{
		if (goldBall == false)
		{
			g.drawImage(ball, x, y, this);	// regular ball
		}
		if (goldBall == true)		
		{
			g.drawImage(gold, x, y, this);	// gold ball		
		}
	}	
}