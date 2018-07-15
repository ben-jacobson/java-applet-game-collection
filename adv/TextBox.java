import java.applet.*;
import java.awt.*;

public class TextBox extends Applet
{
static final int maxLines = 20;

int windowX, windowY;
int windowWidth, windowHeight;

int paddingX, paddingY;

String tempMessage[];
int lines;

int textSize;
Font font;

	TextBox()
	{
		tempMessage = new String[maxLines];		
		lines = 1;
		
		windowX = 300;
		windowY = 200;
		windowWidth = 250;
		windowHeight = 100;
		
		paddingX = 25;
		paddingY = 25;
		
		font = new Font("Monospaced", Font.BOLD, 14); // most evenly spaced font there is, makes it simpler
	}
	void paintMessage(String text)
	{
	int checking = 0, subChecking = 0;
	int length = 0;
	int longestString = 0;
	char letter;
	
	String tempString[];
	char line[];
	
		line = new char[100];
		lines = 0;
		tempString = new String[maxLines];
		
			while (lines < maxLines) // split new-line Characters + calculate longest Lengths
			{
					while (subChecking < text.length())
					{
							if (text.charAt(subChecking) != '\n')
							{
								line[checking] = text.charAt(subChecking);
							}
							else
							{
								subChecking++;																						
								break;
							}
						subChecking++;
						checking++;
					}	
					
				tempString[lines] = tempString[lines].valueOf(line);
				tempString[lines] = tempString[lines].trim();									
				line = new char[100];
				checking = 0;
																		
					if (tempString[lines].length() > tempString[longestString].length())
					{
						longestString = lines;
					}		
					if (subChecking == text.length())
					{
						break;
					}													
				lines++;
			}
			
		checking = 0;				
		
			while (checking < tempString[longestString].length())
			{
				tempString[longestString].toLowerCase();
				letter = tempString[longestString].charAt(checking);
					
					if (letter != ' ')
					{
						length += 8;
					}
					else // check character lengths to proper add up
					{
							if (letter == 'c' || letter == 'g' || letter == 'i' || letter == 'l' || 
							    letter == 'o' || letter == 'q' || letter == 't' || letter == 'z')
							{
								length += 7;
							}
							if (letter == 'a' || letter == 'd' || letter == 'e' || letter == 'e' || 
							    letter == 'j' || letter == 's' || letter == 'a' || letter == 'u' ||
							    letter == 'v' || letter == 'w')
							{
								length += 8;
							}							
							if (letter == 'f' || letter == 'h' || letter == 'k' || letter == 'm' ||
							    letter == 'n' || letter == 'p' || letter == 'r' || letter == 'x' || 
							    letter == 'y')
							{
								length += 9;
							}
							else // to humor pesky characters
							{
								length += 8;
							}						
					}
				checking++;
			}
		
		paddingX = length / 4;
		windowWidth = length + paddingX;
		paddingY = (lines + 1) + 40;
		windowHeight = ((lines + 1) * 10) + paddingY;
		
		windowX = (800 - windowWidth) / 2;
		windowY = (500 - windowHeight) / 2;
		
		tempMessage = tempString;	
	}
	void Paint (Graphics g)
	{
	int checking = 0;
	int yincrement = windowY + (paddingY / 2);	
	
		g.setColor(new Color(198, 198, 198));
		g.fillRect(windowX, windowY, windowWidth, windowHeight);
		
		g.setColor(Color.WHITE);
		g.drawLine(windowX, windowY, windowX - 1 + windowWidth - 1, windowY);	
		g.drawLine(windowX, windowY, windowX, windowY - 1 + windowHeight - 1);
		g.setColor(Color.GRAY);
		g.drawLine(windowX + 1, windowY + windowHeight - 1, windowX + windowWidth - 1, windowY + windowHeight - 1);	
		g.drawLine(windowX + windowWidth - 1, windowY + 1, windowX + windowWidth - 1, windowY - 1 + windowHeight - 1);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		
			while(checking < maxLines && tempMessage[checking] != null)
			{				
				g.drawString(tempMessage[checking], windowX + (paddingX / 2), yincrement + (checking * 12) + 10);				
				checking++;
			}
	}
}