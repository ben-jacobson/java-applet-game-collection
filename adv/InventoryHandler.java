import java.applet.*;
import java.awt.*;

public class InventoryHandler extends Applet
{
static final int maxItems = 64;

static final int maxPlaceX = 4;
static final int maxPlaceY = 4;
static final int maxPageItems = maxPlaceX * maxPlaceY;
static final int maxPages = 4;

static final int windowX = 225, windowY = 60;
static final int windowWidth = 350, windowHeight = 410;

static final int panelTopLeftX = windowX + 10, panelTopLeftY = windowY + 10;
static final int panelWidth = windowWidth - 20, panelHeight = windowHeight - 60;

int currentPlaceX, currentPlaceY;
int invPages[][];
int currentPage, viewingPage;

InventoryItem itemList[];
boolean active[];

Icon closeButton;
Icon leftButton, rightButton;

boolean inventoryOpen;
boolean debugMode;

	InventoryHandler(String itemNames[], Image inventoryImages[], int cells[], Image close, Image lb, Image rb, int whichRoom[])
	{
	int scanning = 0;
		
		itemList = new InventoryItem[maxItems];
		active = new boolean[maxItems];
		
			while (scanning < maxItems)
			{
				itemList[scanning] = new InventoryItem(itemNames[scanning], inventoryImages[scanning], cells[scanning], whichRoom[scanning]);
					
					if (itemList[scanning].itemName != null)
					{
						active[scanning] = true;
					}
				scanning++;
			}	
			
		closeButton = new Icon(close, windowX + windowWidth - 40 - 10, windowY + windowHeight - 40 - 4, 40, 40);
		leftButton = new Icon(lb, 355, 420 + 5, 30, 20);
		rightButton = new Icon(rb, 405, 420 + 5, 30, 20);
			
		inventoryOpen = false;	
		currentPlaceX = 0;
		currentPlaceY = 0;	
		debugMode = false;
		
		invPages = new int[maxPages][maxPageItems];
		scanning = 0;
		
			while (scanning < maxPageItems)
			{
				invPages[0][scanning] = -1;
				invPages[1][scanning] = -1;			
				invPages[2][scanning] = -1;
				invPages[3][scanning] = -1;												
				scanning++;
			}			
		currentPage = 0;
		currentPlaceX = 0;
		currentPlaceY = 0;
		viewingPage = 0;
	}
	void setPositions(int x[], int y[], int animSpeed[])
	{
	int checking = 0;
	
			while(checking < maxItems)
			{
				itemList[checking].xinv = x[checking];
				itemList[checking].yinv = y[checking];
				itemList[checking].itemAnim.speed = animSpeed[checking];
				checking++;
			}
	}
	void Handle()
	{
	int checking = 0;
	
		while (checking < maxItems)
		{
				if (active[checking] == true)
				{
					itemList[checking].Handle();
				}
			checking++;
		}
	}
/*	void checkGrabs(int x, int y) // old function, might prove useful
	{
	int checking = 0;
	
			while (checking < maxItems)
			{
					if (active[checking] == true)
					{
						if (itemList[checking].pointingTo(x, y) == true)
						{
							itemList[checking].getItem(currentPlaceX, currentPlaceY, panelTopLeftX, panelTopLeftY);
							currentPlaceX++;
							
							
								if (currentPlaceX == maxPlaceX)
								{
									currentPlaceY++;
									currentPlaceX = 0;
								}
						}
					}
				checking++;
			}
	}*/
	void getItem(String name)
	{
	int checking = 0;
	
		while (checking < maxItems)
		{			
				if (active[checking] == true && itemList[checking].itemName == name)
				{
							itemList[checking].getItem(currentPlaceX, currentPlaceY, panelTopLeftX, panelTopLeftY);
							invPages[currentPage][(currentPlaceY * maxPlaceX) + currentPlaceX] = checking;							
							currentPlaceX++;
							
								if (currentPlaceX > maxPlaceX - 1)
								{
									currentPlaceX = 0;								
									currentPlaceY++;
								}
								if (currentPlaceY > maxPlaceY - 1)
								{							
									currentPage++;
									currentPlaceX = 0;
									currentPlaceY = 0;
								}								
							break;							
				}				
			checking++;
		}
	}
	void resetInvPlaces()
	{
	// 3. reset currentPlaceX and currentPlaceY to next available slot. effectively it can be just moved back one on X and/or Y
	int page = 0;
	int checking = 0;
	int x = 0, y = 0;	
	
			while (invPages[page][checking] != -1)
			{							
				checking++;
														
					if (checking > maxPageItems - 1)
					{
						checking = 0;
						page++;
					}
					else
					{
						break;
					}														
			}
			while (page < maxPages)
			{
				if (invPages[page][checking] == -1)
				{	
					invPages[page][checking] = invPages[page][checking + 1];
					invPages[page][checking + 1] = -1;					
				}
				checking++;
				x++;
				
					if (checking > maxPageItems - 1 - 1)
					{
							if (page < maxPages - 1)
							{
								if (invPages[page + 1][0] != -1)
								{
									invPages[page][checking] = invPages[page + 1][0];								
									invPages[page + 1][0] = -1;
								}
							}
						checking = 0;
						page++;
						y++;
					}			
			}
			
		viewingPage = 0;
		
			while (viewingPage < maxPageItems && isPageBlank(viewingPage) == true)
			{				
				viewingPage++;
			}							
	}
	boolean pointingTo(int itemNumber, int mouseX, int mouseY)
	{
	boolean returnType = false;
	int checking = 0;
	int xpos = 0, ypos = 0;

			while (checking < maxPageItems && returnType != true) // first check inv items
			{
					if (invPages[viewingPage][checking] > -1)
					{
						if (invPages[viewingPage][checking] == itemNumber && itemList[invPages[viewingPage][checking]].hasItem == true)
						{
							returnType = itemList[invPages[viewingPage][checking]].inventoryPointingTo(mouseX, mouseY, xpos, ypos);
							break;
						}
					}
				checking++;
				xpos++;
				
					if (xpos > maxPlaceX - 1)
					{
						xpos = 0;
						ypos++;
					}
			}
			
		checking = 0;
			
			if (itemList[itemNumber].hasItem == false && returnType != true)
			{
				returnType = itemList[itemNumber].pointingTo(mouseX, mouseY);	
			}
		return returnType;
	}
	void showNextPage()
	{
		if (viewingPage < 4 - 1 && isPageBlank(viewingPage + 1) == false)
		{
			viewingPage++;
		}
	}
	void showLastPage()
	{
		if (viewingPage > 0 && isPageBlank(viewingPage - 1) == false)
		{
			viewingPage--;
		}
	}
	boolean isPageBlank(int page)
	{
	int checking = 0;
	
			while (checking < maxPageItems && page < maxPages)
			{
					if (invPages[page][checking] < 0)
					{
						checking++;
					}
					else
					{
						break;
					}
			}
			if (checking > maxPageItems - 1)
			{
				return true;
			}
			else
			{
				return false;
			}
	}	
	boolean canMove(Player PlayerCheck, int room, int direction)
	{
	int checking = 0;
	boolean returnTrue = true;
	
				while(checking < maxItems)
				{
						if (active[checking] == true && itemList[checking].hasItem == false && itemList[checking].inRoom == room)
						{
						int xcheck = 0, ycheck = 0;
						
								switch(direction)
								{
									case Player.up:
									
										xcheck = PlayerCheck.topLeftX;
										
											if (PlayerCheck.topLeftY - PlayerCheck.walkSpeed < itemList[checking].bottomRightY &&
											    PlayerCheck.topLeftY - PlayerCheck.walkSpeed > itemList[checking].topLeftY)
											{
													while (xcheck < PlayerCheck.bottomRightX)
													{
															if (xcheck < itemList[checking].bottomRightX && xcheck > itemList[checking].topLeftX)
															{
																returnTrue = false;
																break;
															}
														xcheck++;
													}
										   }
											
									break;
									case Player.down:
									
										xcheck = PlayerCheck.topLeftX;
										
											if (PlayerCheck.bottomRightY + PlayerCheck.walkSpeed > itemList[checking].topLeftY &&
											    PlayerCheck.topLeftY + PlayerCheck.walkSpeed < itemList[checking].topLeftY)
											{
													while (xcheck < PlayerCheck.bottomRightX)
													{
															if (xcheck < itemList[checking].bottomRightX && xcheck > itemList[checking].topLeftX)
															{
																returnTrue = false;
																break;
															}
														xcheck++;
													}
											}									
									
									break;
									case Player.left:
									
										ycheck = PlayerCheck.topLeftY;
										
											if (PlayerCheck.topLeftX - PlayerCheck.walkSpeed < itemList[checking].bottomRightX  &&
											    PlayerCheck.bottomRightX - PlayerCheck.walkSpeed > itemList[checking].topLeftX)
											{
													while (ycheck < PlayerCheck.bottomRightY)
													{
															if (ycheck > itemList[checking].topLeftY && ycheck < itemList[checking].bottomRightY)
															{
																returnTrue = false;
																break;
															}
														ycheck++;
													}
											}										
									
									break;
									case Player.right:
									
										ycheck = PlayerCheck.topLeftY;
										
											if (PlayerCheck.bottomRightX + PlayerCheck.walkSpeed < itemList[checking].bottomRightX  &&
											    PlayerCheck.bottomRightX + PlayerCheck.walkSpeed > itemList[checking].topLeftX)
											{
													while (ycheck < PlayerCheck.bottomRightY)
													{
															if (ycheck > itemList[checking].topLeftY && ycheck < itemList[checking].bottomRightY)
															{
																returnTrue = false;
																break;
															}
														ycheck++;
													}
											}	
																			
									break;
								}
						}
					checking++;		
				}
			return returnTrue;
	}
	void Paint(Graphics g, int currentRoom)
	{
		if (inventoryOpen == true)
		{
		int itemCheck = 0;
		int x = 0, y = 0;
		
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(windowX, windowY, windowWidth, windowHeight);
			
			g.setColor(Color.WHITE);
			g.drawLine(windowX, windowY, windowX, windowY + windowHeight - 1);
			g.drawLine(windowX, windowY, windowX + windowWidth - 2, windowY);
			
			g.setColor(Color.GRAY);
			g.drawLine(windowX + 1, windowY + windowHeight, windowX + windowWidth, windowY + windowHeight);
			g.drawLine(windowX + windowWidth - 1, windowY + 1, windowX + windowWidth, windowY + windowHeight);
			
			g.setColor(Color.BLACK);
			g.fillRect(panelTopLeftX, panelTopLeftY, panelWidth, panelHeight);
					
				while (itemCheck < maxPageItems)
				{
					if (invPages[viewingPage][itemCheck] > -1)
					{
							if (active[invPages[viewingPage][itemCheck]] == true && itemList[invPages[viewingPage][itemCheck]].hasItem == true)
							{				
									itemList[invPages[viewingPage][itemCheck]].InventoryPaint(g, x, y, panelTopLeftX, panelTopLeftY);
									itemList[invPages[viewingPage][itemCheck]].PaintString(g, x, y, panelTopLeftX, panelTopLeftY);							
							}							
						x++;
						
							if (x > maxPlaceX - 1)
							{
								x = 0;
								y++;
							}
					}
					itemCheck++;
				}
												
			closeButton.Paint(g);
			leftButton.Paint(g);
			rightButton.Paint(g);
		}
	}	
}