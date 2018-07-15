import java.awt.*;
import java.applet.*;

public class boulder extends Applet
{
static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

static final int disused = 0, rock = 1, gem = 2;

int x, y;
int state;

	boulder()
	{
		state = disused;
	}
	public void changeState(int toState)
	{
		state = toState;
	}
	public void changePosition(int xpos, int ypos)
	{
		x = xpos;
		y = ypos;
	}
	public void move(int direction)
	{
		switch(direction)
		{
			case up:
				y--;
				break;
			case down:
				y++;
				break;
			case left:
				x--;
				break;
			case right:
				x++;
				break;	
		}
	}
}