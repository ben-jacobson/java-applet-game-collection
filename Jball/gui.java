import java.applet.*;
import java.awt.*;

public class gui extends Applet
{
Image intro;
Image gui;
Image credits;

int tx, ty; // playfield tx, ty
int bx, by; // playfield bx, by

	gui(Image in, Image gu, Image out)
	{
		intro = in;
		gui = gu;
		credits = out; 
		tx = 10;
		ty = 10;
		bx = 400 - 10;
		by = 400 - 30;
	}
	public void drawIntro(Graphics g)
	{
		g.drawImage(intro,0,0,this);
	}	
	public void drawGui(Graphics g)
	{
		g.drawImage(gui,0,0,this);
	}
	public void drawCredits(Graphics g)
	{
		g.drawImage(credits,0,0,this);
	}	
}