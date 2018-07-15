public class bullet
{
int x,y;

	public bullet()
	{
		x = 0;
		y = 0;
	}
	public void handle(turret Turret)
	{
		if (returnY() < 1)
		{
			fireBullet(Turret);
		}
		else
		{
			moveBullet();
		}
	}
	public void fireBullet(turret Turret)
	{
		x = Turret.returnX() + (32 / 2) - (10 / 2);
		y = Turret.returnY() - 10;
	}
	private void moveBullet()
	{
		y -= 10;
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