public class turret
{
static final int speed = 3;
int x,y;
int whichSide;

	public turret()
	{
		x = (300 / 2) - (32 / 2);
		y = 352 - 52;
	}
	public void moveLeft()
	{
		x -= speed;
	}
	public void moveRight()
	{
		x += speed;
	}
	public int checkBounds()
	{
			if (x < (32 - 1))
			{
				whichSide = 0;
			}
			if (x > ((300 - 32) - 1))
			{
				whichSide = 1;
			}
		return whichSide;
	}
	public int returnX()
	{
		return x;
	}
	public int returnY()
	{
		return y;
	}	
}