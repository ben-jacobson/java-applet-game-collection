import java.applet.*;
import java.awt.*;

public class score extends Applet
{
Image ball;
int score, lives, level;

	score(Image b)
	{
		ball = b;
		score = 0;
		lives = 3;
		level = 0;
	}
	public void addToScore(int amount)
	{
		score += amount;
	}
	public void lifeUp()
	{
		if (lives < 3)
		{
			lives++;
		}
	}
	public void lifeDown()
	{
		if (lives > 0)
		{
			lives--;
		}	
	}
	public void nextLevel()
	{
		if (level < 5 + 1)
		{
			level++;
		}
	}
	public void draw(Graphics g)
	{
		g.setColor(Color.green);
		g.drawString("Level: " + level + ".", 275, 385);	
		g.drawString("Score: " + score + ".", 275, 396);		
			
			switch(lives)
			{
				case 1:
					g.drawImage(ball, 355, 380, this);				
				break;
				case 2:
					g.drawImage(ball, 355, 380, this);	
					g.drawImage(ball, 365 + 1, 380, this);									
				break;
				case 3:
					g.drawImage(ball, 355, 380, this);				
					g.drawImage(ball, 365 + 1, 380, this);	
					g.drawImage(ball, 375 + 2, 380, this);											
				break;
			}			
	}	
}