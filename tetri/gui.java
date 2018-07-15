import java.applet.*;
import java.awt.*;

public class gui extends Applet
{
final static int appletWidth = 400;
final static int appletHeight = 500;

final static int playAreaTx = 10;
final static int playAreaTy = 10;
final static int playAreaBx = 300;
final static int playAreaBy = 490;

final static int nextAreaTx = 220;
final static int nextAreaTy = 10;
final static int nextAreaBx = 110;
final static int nextAreaBy = 390;

Image guiImg;

	gui(Image guiImage)
	{
		guiImg = guiImage;
	}
	public void draw(Graphics g, score sc)
	{
		g.drawImage(guiImg,0,0,this);
		g.setColor(Color.black);
		g.drawString("Level: " + sc.level + ".",230,200);		
		g.drawString("Score: " + sc.score + ".",230,215);
	}
}