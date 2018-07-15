import java.applet.*;
import java.awt.*;
import java.net.*;

public class Animation extends Applet
{
Image theAnimation;
int cellAmount, cellWidth, cellHeight;

int loopCellsFrom, loopCellsTo;
int currentCell; 
int counter, speed;

boolean animating, looping;

	Animation(Image animation, int cWidth, int cHeight, int animSpeed)
	{						
		cellWidth = cWidth;
		cellHeight = cHeight;
		
		theAnimation = animation;
		
		loopCellsFrom = 0;
		loopCellsTo = 0;
		currentCell = 0;
		
		counter = 0;
		speed = animSpeed;
		
		animating = false;
		looping = true;
	}	
	void switchAnimation(int cellsFrom, int cellsTo)
	{
		loopCellsFrom = cellsFrom;
		loopCellsTo = cellsTo;
		
		currentCell = cellsFrom;
		
		animating = true;
	}
	void toggleLoop()
	{
		if (looping == true)
		{
			looping = false;
		}
		else 
		{
			looping = true;
		}
	}
	void play()
	{
		animating = true;
	}
	void pause()
	{
		animating = false;
	}
	void Handle()
	{
		if (animating == true)
		{		
			if (counter < speed)
			{
				counter++;
			}
			else
			{
					if (currentCell < loopCellsTo)
					{
						currentCell++;		
					}
					else
					{
						if (looping == true)
						{
							currentCell = loopCellsFrom;
						}
					}
					if (looping == true)
					{					
						counter = 0;				
					}
			}
		}			
	}
	void Paint(int x, int y, Graphics g)
	{
		g.drawImage(theAnimation, x, y, x + cellWidth + 1, y + cellHeight, (currentCell * (cellWidth + 1)), 0, (currentCell * (cellWidth + 1)) + cellWidth, cellHeight, this);
	}
}