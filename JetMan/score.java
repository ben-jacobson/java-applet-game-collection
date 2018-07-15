import java.applet.*;
import java.awt.*;

public class score extends Applet
{
Image hudOverlay;
int score, ammo, lives;
float fuel;
boolean outOfFuel, died;

	score(Image overlay)
	{
		hudOverlay = overlay;
		score = 0;
		lives = 3;
		fuel = 25;
		ammo = 5;
		outOfFuel = false;
		died = false;
	}
	public void draw(Graphics g) // draw hud overlay
	{
		g.drawImage(hudOverlay, 0, 500 - 25, this);
		g.setColor(Color.red);
		
		g.drawString("Score: " + score,15,500 - 13);
		g.drawString("Lives: " + lives,15,500 - 2);	
		
		g.drawString("Fuel: " + (int)(fuel),500 - 75,500 - 13);
		g.drawString("Ammo: " + ammo,500 - 75,500 - 2);				
	}
	public void addtoFuel(int amount)
	{
		if (fuel + amount < 100)
		{	
			fuel += amount;
		}
		else
		{
			fuel = 100;
		}		
	}
	public void lifeDown()
	{
		if (lives > 0)
		{
			lives--;
		}
		else
		{
			lives = -1;
			// this calls the gameOver safely without avoiding it
		}
	}
	public void lifeUp()
	{
		lives++;
	}
	public void handle()
	{	
		if (fuel < 1)
		{
			outOfFuel = true;
			fuel += 0.04; // makes an awesome spluttering effect
		}
	}
}