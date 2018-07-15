public class asteroid
{
int x,y;
int speed;
boolean stillActive;

	public asteroid()
	{
		speed = (int)((Math.random() * 2) + 1);	
		x = (int)(Math.random() * (300 - 64));
		y = speed; // starts the comets velocity
		stillActive = true;
	}
	public void reset()
	{
		speed = (int)((Math.random() * 3) + 1);	
		x = (int)(Math.random() * (300 - 64));
		y = speed; // starts the comets velocity
		stillActive = true;	
	}
	public void handle(turret Turret, bullet Bullet, score Score)
	{
		if (y > (350 - 32))
		{
			stillActive = false;
			Score.subFromScore(20);
		}
		if ((Bullet.returnX() + (10 / 2)) < (returnX() + 32) && 
		    (Bullet.returnX() + (10 / 2)) > returnX() && 
		    (Bullet.returnY() + (16 / 2)) < (returnY() + 32) && 
		    (Bullet.returnY() + (16 / 2)) > returnY())
		{
			Score.addToScore(50);
			stillActive = false;
			Bullet.fireBullet(Turret);
		}		
		else
		{
			moveAsteroid();
		}
		
		if ((Turret.returnX() + (32 / 2)) < (returnX() + 32) && 
		    (Turret.returnX() + (32 / 2)) > returnX() &&
		    (Turret.returnY() + (52 / 2)) < (returnY() + 32) && 
		    (Turret.returnY() + (52 / 2)) > returnY())
		{
			Score.subFromLives();
			reset();
		}
	}
	private void moveAsteroid()
	{
		y += speed;
	}
	public int returnX()
	{
		return x;
	}
	public int returnY()
	{
		return y;
	}
	public boolean isStillActive()
	{
		return stillActive;
	}
}