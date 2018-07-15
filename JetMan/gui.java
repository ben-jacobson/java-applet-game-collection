import java.applet.*;
import java.awt.*;

public class gui extends Applet
{
Image[] backgrounds;
int current;

Image title, credits;
Image gameOver;

int gamePhase;

	gui(Image ititle, Image iCredits, Image iGameOver)
	{
		title = ititle;
		credits = iCredits;
		gameOver = iGameOver;
		backgrounds = new Image[5];		
		
		gamePhase = 0;
		current = 0;		
	}
	public void getBackgrounds(Image[] ibackgrounds)
	{
	int place = 0;
		
			while (place < 5)
			{
				backgrounds[place] = ibackgrounds[place];
				place++;
			}		
	}
	public void draw(Graphics g)
	{
		switch (gamePhase)
		{
			case 0: // title
				g.drawImage(title,0,0,this);	
			break;
			case 1: // game
				g.drawImage(backgrounds[current],0,0,this);			
			break;			
			case 2: // gameover
				g.drawImage(gameOver,0,0,this);
			break;			
			case 3: // credits
				g.drawImage(credits,0,0,this);
			break;			
		}
	}
	
}