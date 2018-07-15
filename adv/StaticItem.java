import java.applet.*;
import java.awt.*;

public class StaticItem extends Applet
{
int x, y;
int topLeftX, topLeftY;
int bottomRightX, bottomRightY;
int width, height;

Animation item;
int inRoom;

String name;
boolean active, hidden;
boolean moving;

int destinationX, destinationY;
int motionSpeed;

	StaticItem(Image staticItem, int xpos, int ypos, int w, int h, int whichRoom)
	{
		x = xpos;
		y = ypos;
		
		width = w;
		height = h;		
		
		topLeftX = xpos;
		topLeftY = ypos + height - 20;
		bottomRightX = x + width;
		bottomRightY = y + height;
		
		inRoom = whichRoom;
		
		item = new Animation(staticItem, w, h, 1);
		active = true;
		hidden = false;
		
		moving = false;
	}
	void setMove(int destX, int destY, int speed) // sets motion
	{
		destinationX = destX;
		destinationY = destY;
		moving = true;
		motionSpeed = speed;
	}
	void Handle()
	{
		if (hidden == false)
		{
			topLeftX = x;
			topLeftY = y + height - 20;
			bottomRightX = x + width;
			bottomRightY = y + height;		
			item.Handle(); // handles the animation
			
				if (moving == true)
				{				
				int speedCheck = 0;	
																				
						if (destinationX < x)
						{
							x -= motionSpeed;
							
								if (x - motionSpeed < destinationX)
								{
									x = destinationX;
								}								
						}
						if (destinationX > x)
						{						
							x += motionSpeed;
							
								if (x + motionSpeed > destinationX)
								{
									x = destinationX;
								}	
						}
						
					speedCheck = 0;

						if (destinationY < y)
						{
							y -= motionSpeed;
							
								if (y - motionSpeed < destinationY)
								{
									y = destinationY;
								}
						}
						if (destinationY > y)
						{						
							y += motionSpeed;
							
								if (y + motionSpeed > destinationY)
								{
									y = destinationY;
								}
						}
						
						if (x == destinationX && y == destinationY)
						{
							moving = false;
						}						
				}									
		}
	}
	void Paint(Graphics G)
	{
		if (hidden == false)
		{	
			item.Paint(x, y, G);
		}
	}
}