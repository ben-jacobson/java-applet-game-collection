import java.awt.*;
import java.applet.*;

public class Player extends Applet
{
static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

static final int tileWidth = 16;
static final int tileHeight = 16;

Image playerImg;
int x, y;

	Player(Image playerImage)
	{
		playerImg = playerImage;
	}
	public void setPosition(int playerX, int playerY)
	{
		x = playerX;
		y = playerY;
	}
	public void draw(Graphics g)
	{
		g.drawImage(playerImg, x * tileWidth, y * tileHeight, this);
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