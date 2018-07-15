import java.applet.*;
import java.awt.*;

public class block extends Applet
{
Image whiteBlock;
Image blackBlock;
Image redBlock;
Image greenBlock;

int[] block1x; // square shape
int[] block1y;

int[] block2x; // line peice
int[] block2y;

int[] block3x; // L peice
int[] block3y;

int[] block4x; // S peice right
int[] block4y;

int[] block5x; // T peice
int[] block5y;

int[] block6x; // S peice 2
int[] block6y;

int[] block7x; // L peice 2
int[] block7y;

	block(Image white, Image black, Image red, Image green)
	{			
		whiteBlock = white;
		blackBlock = black;
		redBlock = red;
		greenBlock = green;	
			
		block1x = new int[4]; // square shape
		block1y = new int[4];
		
		block2x = new int[4];  // line shape generic
		block2y = new int[4];

		block3x = new int[4]; // L shape
		block3y = new int[4];
		
		block4x = new int[4]; // S shape
		block4y = new int[4];
		
		block5x = new int[4]; // T shape
		block5y = new int[4];
		
		block6x = new int[4]; // T shape
		block6y = new int[4];
		
		block7x = new int[4]; // T shape
		block7y = new int[4];						
					
		block1x[0] = 5; block1y[0] = 0; // square shape generic
		block1x[1] = 6; block1y[1] = 0;
		block1x[2] = 5; block1y[2] = 1;
		block1x[3] = 6; block1y[3] = 1;	
		
		block2x[0] = 5; block2y[0] = 0; // line shape generic
		block2x[1] = 5; block2y[1] = 1;
		block2x[2] = 5; block2y[2] = 2;
		block2x[3] = 5; block2y[3] = 3;
		
		block3x[0] = 5; block3y[0] = 0; // L shape generic
		block3x[1] = 5; block3y[1] = 1;
		block3x[2] = 5; block3y[2] = 2;
		block3x[3] = 6; block3y[3] = 2;	
		
		block4x[0] = 5; block4y[0] = 0; // S shape generic
		block4x[1] = 6; block4y[1] = 0;
		block4x[2] = 6; block4y[2] = 1;
		block4x[3] = 7; block4y[3] = 1;
		
		block5x[0] = 4; block5y[0] = 0; // T shape generic
		block5x[1] = 5; block5y[1] = 0;
		block5x[2] = 6; block5y[2] = 0;
		block5x[3] = 5; block5y[3] = 1;
		
		block6x[0] = 7; block6y[0] = 0; // S shape 2
		block6x[1] = 6; block6y[1] = 0;
		block6x[2] = 6; block6y[2] = 1;
		block6x[3] = 5; block6y[3] = 1;
		
		block7x[0] = 6; block7y[0] = 0; // L shape 2
		block7x[1] = 6; block7y[1] = 1;
		block7x[2] = 6; block7y[2] = 2;
		block7x[3] = 5; block7y[3] = 2;																																					
	}
	public void drawBlack(Graphics g, int x, int y)
	{
		g.drawImage(blackBlock,x,y,this);
	}
	public void drawWhite(Graphics g, int x, int y)
	{
		g.drawImage(whiteBlock,x,y,this);	
	}	
	public void drawRed(Graphics g, int x, int y)
	{
		g.drawImage(redBlock,x,y,this);	
	}	
	public void drawGreen(Graphics g, int x, int y)
	{
		g.drawImage(greenBlock,x,y,this);	
	}					
}