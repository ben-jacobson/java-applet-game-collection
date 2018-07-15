import java.applet.*;
import java.awt.*;

public class InventoryItem extends Applet
{
public static int width = 60;
public static int height = 60;

Animation itemAnim;
String itemName;

Font font;

boolean hasItem;
int xinv,  yinv;

int topLeftX, topLeftY;
int bottomRightX, bottomRightY;

int inRoom;

	InventoryItem(String name, Image item, int animCells, int whichRoom)
	{
		itemName = name;
				
		hasItem = false;
		
		itemAnim = new Animation(item, width, height, 1);
		itemAnim.switchAnimation(0, animCells);
		
		inRoom = whichRoom;
		
		font = new Font("Monospaced", Font.BOLD, 14);				
	}
	void getItem(int xplace, int yplace, int topLeftPanelX, int topLeftPanelY)
	{
		if (hasItem == false)
		{
			hasItem = true;
			xinv = 10 + (xplace * width) + topLeftPanelX + (xplace * 20);
			yinv = 10 + (yplace * height) + topLeftPanelY + (yplace * 20);
		}
	}
	void Handle()
	{
		topLeftX = xinv;
		topLeftY = yinv + 50;
		bottomRightX = topLeftX + 60;
		bottomRightY = topLeftY + 15;
		itemAnim.Handle();
	}
	boolean pointingTo(int mouseX, int mouseY)
	{
			if (mouseX < xinv + width && mouseX > xinv &&	
				 mouseY < yinv + height && mouseY > yinv)
			{
				return true;
			}
			else
			{
				return false;
			}
	}
	boolean inventoryPointingTo(int mouseX, int mouseY, int posX, int posY)
	{
	int xplace = 10 + (posX * width) + InventoryHandler.panelTopLeftX + (posX * 20);
	int yplace = 10 + (posY * height) + InventoryHandler.panelTopLeftY + (posY * 20);
	
			if (mouseX < xplace + width && mouseX > xplace &&	
				 mouseY < yplace + height && mouseY > yplace)
			{
				return true;
			}
			else
			{
				return false;
			}
	}			
	boolean inRoom(int room)
	{
		if (room == inRoom)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	void Paint(Graphics g)
	{
		itemAnim.Paint(xinv, yinv, g);	
	}
	void InventoryPaint(Graphics g, int invX, int invY, int topLeftPanelX, int topLeftPanelY)
	{
	int x = 10 + (invX * width) + topLeftPanelX + (invX * 20);
	int y = 10 + (invY * height) + topLeftPanelY + (invY * 20);
	
		itemAnim.Paint(x, y, g);			
	}	
	void PaintString(Graphics g, int invX, int invY, int topLeftPanelX, int topLeftPanelY)
	{
	int x = 10 + (invX * width) + topLeftPanelX + (invX * 20);
	int y = 10 + (invY * height) + topLeftPanelY + (invY * 20);
		
		g.setFont(font);		
		g.setColor(Color.WHITE);
		g.drawString(itemName, x + 30 - (itemName.length() * 2) - 10, y + 60 + 15);		
	}	
}