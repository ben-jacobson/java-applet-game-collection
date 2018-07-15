import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

public class GameHandler extends Applet
{
Applet thisApplet;  // used for getCodeBase to function properly
Toolkit toolkit; 

MediaTracker tracker;

static final int maxEvents = 64;

static final int handCursor = 0;
static final int lookCursor = 1;
static final int talkCursor = 2;
static final int waitCursor = 3;
static final int standardCursor = 4;

static final int walking = 0;
static final int grabbing = 1;
static final int looking = 2;
static final int talking = 3;
static final int inventory = 4;
static final int settings = 5;
static final int staticScreen = 6;
static final int textBox = 7;

static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

// final variables and child handlers
Player ego;
RoomHandler gameRooms;
MenuHandler GameMenu; 
Cursor gameCursors[];
InventoryHandler gameInventory;
StaticItemHandler gameStatics;
TextBox gameTextBox;

// temporary storage until finalInit()
Image player;
Image roomImages[], controlImages[], priorityImages[]; 
int roomPlace;   
       
Image gameCursorImages[];
Cursor inventoryCursor;
	
Image handIcon, lookIcon, talkIcon, invIcon, settingsIcon;
String inventoryNames[];
Image inventoryImages[];
int inventoryCellAmounts[];
int inventoryRoomNumbers[];
int inventoryX[], inventoryY[];
int inventoryAnimSpeeds[];
int invPlace, usingInvItem;

Image staticImages[];
String staticNames[];
int staticX[];
int staticY[];
int staticWidth[];
int staticHeight[];
int staticInRoom[];
int staticCellAmounts[];
int staticAnimSpeeds[];
int staticPlace;

Image closeButton;
Image leftButton, rightButton;

int gameState, gameInvState;
boolean debugMode;

int mouseEventX, mouseEventY;
boolean mouseEventClicked, eventEnter;

boolean recordingEvents;
int staticEvents[], currentEventPlace;

	GameHandler(Applet a)
	{					
			toolkit = Toolkit.getDefaultToolkit();	
			thisApplet = a;
	
			closeButton = thisApplet.getImage(thisApplet.getCodeBase(), "ExitButton.gif");
			leftButton = thisApplet.getImage(thisApplet.getCodeBase(), "leftButton.gif");
			rightButton = thisApplet.getImage(thisApplet.getCodeBase(), "rightButton.gif");			
				
			roomImages = new Image[RoomHandler.maxRooms];
	      controlImages = new Image[RoomHandler.maxRooms];
	      priorityImages = new Image[RoomHandler.maxRooms]; 
	      roomPlace = 0;    
	      
			gameCursorImages = new Image[5];	      
			gameCursors = new Cursor[5];
			
			inventoryImages = new Image[InventoryHandler.maxItems];			
			inventoryNames = new String[InventoryHandler.maxItems];
			invPlace = 0;	
			usingInvItem = -1; 				
			
			inventoryCellAmounts = new int[InventoryHandler.maxItems];
			inventoryAnimSpeeds = new int[InventoryHandler.maxItems];	
			
			inventoryRoomNumbers = new int[InventoryHandler.maxItems];
			
			inventoryX = new int[InventoryHandler.maxItems];
			inventoryY = new int[InventoryHandler.maxItems];
			
			staticImages = new Image[StaticItemHandler.maxStatics];
			staticNames = new String[StaticItemHandler.maxStatics];
			staticX = new int[StaticItemHandler.maxStatics];
			staticY = new int[StaticItemHandler.maxStatics];
			staticWidth = new int[StaticItemHandler.maxStatics];
			staticHeight = new int[StaticItemHandler.maxStatics];
			staticInRoom = new int[StaticItemHandler.maxStatics];
			staticCellAmounts = new int[StaticItemHandler.maxStatics];
			staticAnimSpeeds = new int[StaticItemHandler.maxStatics];			
			staticPlace = 0;
			
			gameState = 0;
			gameInvState = 0;		
	      debugMode = false;		
	      
	      mouseEventX = 0;
	      mouseEventY = 0;
	      mouseEventClicked = false;		
	      eventEnter = false;
	      
	      recordingEvents = false;
	      staticEvents = new int[maxEvents];
	      currentEventPlace = 0;
	}
	void initPlayer(String image)
	{
		player = thisApplet.getImage(thisApplet.getCodeBase(), image); 		
		ego = new Player(player);	
	}	
	void initIcons(String hand, String look, String talk, String inv, String settings)
	{
		handIcon = thisApplet.getImage(thisApplet.getCodeBase(), hand);
		lookIcon = thisApplet.getImage(thisApplet.getCodeBase(), look);
		talkIcon = thisApplet.getImage(thisApplet.getCodeBase(), talk);
		invIcon = thisApplet.getImage(thisApplet.getCodeBase(), inv);
		settingsIcon = thisApplet.getImage(thisApplet.getCodeBase(), settings);		
	}
	void initCursors(String hand, String look, String talk, String wait, String standard)
	{
	Point hotSpot = new Point(0, 0);		
	
			gameCursorImages[handCursor] = thisApplet.getImage(thisApplet.getCodeBase(), hand);
			gameCursorImages[lookCursor] = thisApplet.getImage(thisApplet.getCodeBase(), look);
			gameCursorImages[talkCursor] = thisApplet.getImage(thisApplet.getCodeBase(), talk);		
			gameCursorImages[waitCursor] = thisApplet.getImage(thisApplet.getCodeBase(), wait);
			gameCursorImages[standardCursor] = thisApplet.getImage(thisApplet.getCodeBase(), standard);	
			
			gameCursors[handCursor] = toolkit.createCustomCursor(gameCursorImages[handCursor], hotSpot, "Get");
			gameCursors[lookCursor] = toolkit.createCustomCursor(gameCursorImages[lookCursor], hotSpot, "Look");						
			gameCursors[talkCursor] = toolkit.createCustomCursor(gameCursorImages[talkCursor], hotSpot, "Talk");				
			gameCursors[waitCursor] = toolkit.createCustomCursor(gameCursorImages[waitCursor], hotSpot, "Wait");
			gameCursors[standardCursor] = toolkit.createCustomCursor(gameCursorImages[standardCursor], hotSpot, "Standard");
			
			thisApplet.setCursor(gameCursors[standardCursor]);			
	}
	void addRoom(String room, String control, String priority)
	{
		if (roomPlace < RoomHandler.maxRooms)
		{
			roomImages[roomPlace] = thisApplet.getImage(thisApplet.getCodeBase(), room);
			controlImages[roomPlace] = thisApplet.getImage(thisApplet.getCodeBase(), control);		
			priorityImages[roomPlace] = thisApplet.getImage(thisApplet.getCodeBase(), priority);
			roomPlace++;
		}
		else
		{
			System.out.println("Rooms cannot exceed" + RoomHandler.maxRooms);
		}
	}	
	void addInventoryItem(String Name, String itemImage, int roomNumber, int x, int y)
	{
		if (invPlace < InventoryHandler.maxItems)
		{
			inventoryImages[invPlace] = thisApplet.getImage(thisApplet.getCodeBase(), itemImage);
			inventoryNames[invPlace] = Name;
			inventoryCellAmounts[invPlace] = 0;
			inventoryAnimSpeeds[invPlace] = 1;
			inventoryRoomNumbers[invPlace] = roomNumber;
			inventoryX[invPlace] = x;
			inventoryY[invPlace] = y;
			invPlace++;
		}
		else
		{
			System.out.println("Inventory cannot exceed" + InventoryHandler.maxItems);
		}
	}
	void animateLastInventory(int cellsAnim, int animSpeed)
	{
		inventoryCellAmounts[invPlace - 1] = cellsAnim - 1;
		inventoryAnimSpeeds[invPlace - 1] = animSpeed;	
	}
	void addStaticItem(String objName, String imgName, int x, int y, int width, int height, int whichRoom)
	{
		if (staticPlace < StaticItemHandler.maxStatics)
		{	
			Image staticTemp = thisApplet.getImage(thisApplet.getCodeBase(), imgName);	
			
			staticImages[staticPlace] = staticTemp;
			staticNames[staticPlace] = objName;
			staticX[staticPlace] = x;
			staticY[staticPlace] = y;
			staticWidth[staticPlace] = width;
			staticHeight[staticPlace] = height;
			staticInRoom[staticPlace] = whichRoom;
			
			staticCellAmounts[staticPlace] = 0;
			staticAnimSpeeds[staticPlace] = 1;				
			
			staticPlace++;
		}
		else
		{
			System.out.println("Static items cannot exceed" + StaticItemHandler.maxStatics);
		}
	}
	void animateLastStatic(int cellsAnim, int speed)
	{
		staticCellAmounts[staticPlace - 1] = cellsAnim - 1;
		staticAnimSpeeds[staticPlace - 1] = speed;		
	}
	void finalInit()
	{
	int trackerIdCheck = 0;
	int check = 0;
	
			tracker = new MediaTracker(this);
									
			tracker.addImage(player, trackerIdCheck);
			trackerIdCheck++;
			
				while (check < roomPlace)
				{
					tracker.addImage(roomImages[check], trackerIdCheck);
					trackerIdCheck++;
					tracker.addImage(controlImages[check], trackerIdCheck);					
					trackerIdCheck++;
					tracker.addImage(priorityImages[check], trackerIdCheck);
					trackerIdCheck++;
					check++;
				}
				
			check = 0;	
			
				while (check < 5)
				{
					tracker.addImage(gameCursorImages[check], check);
					trackerIdCheck++;
					check++;
				}
				
			tracker.addImage(handIcon, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(lookIcon, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(talkIcon, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(invIcon, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(settingsIcon, trackerIdCheck);
			trackerIdCheck++;												
			check = 0;			
			
				while (check < invPlace)
				{	
					tracker.addImage(inventoryImages[check], trackerIdCheck);
					trackerIdCheck++;
					check++;
				}
			
			check = 0;
			
				while (check < staticPlace)
				{
					tracker.addImage(staticImages[check], trackerIdCheck);
					trackerIdCheck++;
					check++;
				}
			
			tracker.addImage(closeButton, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(leftButton, trackerIdCheck);
			trackerIdCheck++;
			tracker.addImage(rightButton, trackerIdCheck);			
			
      	try
	      {
		      tracker.waitForAll();
	      }
	      catch(InterruptedException ex) {}	
	      
	      gameRooms = new RoomHandler(roomImages, controlImages, priorityImages);
	      	
			gameInventory = new InventoryHandler(inventoryNames, inventoryImages, inventoryCellAmounts, closeButton, leftButton, rightButton, inventoryRoomNumbers);										
			gameInventory.setPositions(inventoryX, inventoryY, inventoryAnimSpeeds);	
			
			GameMenu = new MenuHandler(handIcon, lookIcon, talkIcon, invIcon, settingsIcon);
			gameStatics = new StaticItemHandler(staticNames, staticImages, staticX, staticY, staticWidth, staticHeight, staticInRoom, staticCellAmounts, staticAnimSpeeds);
			gameTextBox = new TextBox();
	}
	void Handle()
	{
	int checking = 0, counting = 0;
						
      gameInventory.Handle();      
		gameStatics.Handle(gameRooms.currentRoom);
		
				if (gameState == staticScreen && recordingEvents == false)
				{
					while (checking < maxEvents)	
					{
							if (staticEvents[checking] > -1)
							{
								if (gameStatics.staticItems[staticEvents[checking]].moving == false)
								{
									counting++;
								}
							}
						checking++;
					}
					if (counting > currentEventPlace - 1)
					{
						gameState = walking;
						recordingEvents = false;
						clearEvents();
						thisApplet.setCursor(gameCursors[standardCursor]);
						GameMenu.cursorType = standardCursor;
						gameState = walking;
						usingInvItem = -1;
					}
				}
		      if (gameState != inventory && gameState != settings && gameState != staticScreen && gameState != textBox)
		      {
		      	ego.Handle(gameRooms, gameInventory, gameStatics);			      
		      }
		      if (gameState == inventory)
		      {
					GameMenu.showIcons();		      
		      }
	      	try
		      {
		      	Thread.sleep(20);
		      }
		      catch(InterruptedException ex) {}		      		      	
	}
	void Paint(Graphics g)
	{	
	int checking = 0;
	int ycheck = 0;

			gameRooms.Paint(g);
	      	      
					while (ycheck < RoomHandler.roomHeight)  // for priority organisation
					{				
								while (checking < InventoryHandler.maxItems)
								{		
											if (gameInventory.active[checking] == true)
											{
														if ((gameInventory.itemList[checking].yinv + gameInventory.itemList[checking].height + 1) == ycheck && 
														    gameInventory.itemList[checking].inRoom(gameRooms.currentRoom) == true &&  
														    gameInventory.itemList[checking].hasItem == false)
														{										
															gameInventory.itemList[checking].Paint(g);
																
																if (gameInventory.debugMode == true)
																{
																	g.setColor(Color.RED);
																	g.drawRect(gameInventory.itemList[checking].topLeftX, gameInventory.itemList[checking].topLeftY, 
																	           gameInventory.itemList[checking].bottomRightX - gameInventory.itemList[checking].topLeftX, 
																	           gameInventory.itemList[checking].bottomRightY - gameInventory.itemList[checking].topLeftY);
																}																							
														}									
											}
											if (checking < staticPlace && gameStatics.staticItems[checking].inRoom == gameRooms.currentRoom)
											{
												if (gameStatics.staticItems[checking].bottomRightY == ycheck)
												{
													gameStatics.staticItems[checking].Paint(g);	
													
														if (gameInventory.debugMode == true) // same as Static Item Debug Mode
														{
															g.setColor(Color.RED);
															g.drawRect(gameStatics.staticItems[checking].topLeftX, gameStatics.staticItems[checking].topLeftY, 
															           gameStatics.staticItems[checking].bottomRightX - gameStatics.staticItems[checking].topLeftX,
															           gameStatics.staticItems[checking].bottomRightY - gameStatics.staticItems[checking].topLeftY);
															           
															g.setColor(Color.BLUE);
															g.drawRect(gameStatics.staticItems[checking].x, gameStatics.staticItems[checking].y,
															           gameStatics.staticItems[checking].width, gameStatics.staticItems[checking].height);
														}											
												}
											}
										checking++;
								}	
								
						checking = 0;							
						
								if (ego.bottomRightY + 1 == ycheck)
								{
									ego.Paint(g);
								}
								
								// these are priority bands for roomHandler
								if (ycheck  == 10)
								{
									gameRooms.PaintPriorityBand(g, 1);
								}
								if (ycheck  == 45)
								{
									gameRooms.PaintPriorityBand(g, 2);								
								}	
								if (ycheck  == 80)
								{
									gameRooms.PaintPriorityBand(g, 3);									
								}	
								if (ycheck  == 115)
								{
									gameRooms.PaintPriorityBand(g, 4);								
								}
								if (ycheck  == 150)
								{
									gameRooms.PaintPriorityBand(g, 5);									
								}
								if (ycheck  == 185)
								{
									gameRooms.PaintPriorityBand(g, 6);									
								}
								if (ycheck  == 220)
								{
									gameRooms.PaintPriorityBand(g, 7);								
								}
								if (ycheck  == 255)
								{
									gameRooms.PaintPriorityBand(g, 8);								
								}
								if (ycheck  == 290)
								{
									gameRooms.PaintPriorityBand(g, 9);									
								}
								if (ycheck  == 325)
								{
									gameRooms.PaintPriorityBand(g, 10);							
								}
								if (ycheck  == 360)
								{
									gameRooms.PaintPriorityBand(g, 11);								
								}
								if (ycheck  == 395)
								{
									gameRooms.PaintPriorityBand(g, 12);								
								}
								if (ycheck  == 430)
								{
									gameRooms.PaintPriorityBand(g, 13);								
								}
								if (ycheck  == 465)
								{
									gameRooms.PaintPriorityBand(g, 14);								
								}
						ycheck++;
					}
			gameRooms.PaintPriorityBand(g, 15); // draws on top of all being highest priority										
																			
			gameInventory.Paint(g, gameRooms.currentRoom);						
			GameMenu.Paint(g);	
			
				if (gameState == textBox)
				{
					gameTextBox.Paint(g);
				}		
	}
	boolean advKeyUp(Event e, int key)
	{
			      if (gameState == textBox && key == Event.ENTER)
			      {			
						gameState = walking;
			      }	
					if (gameState != inventory && gameState != settings && gameState != staticScreen && gameState != textBox)
			      {
					      if (debugMode == true)
					      {
					      	switch(key)
						      {
						      	case Event.F1:
							      
							      		if (gameRooms.debugViewControl == false)
									      {
									      	System.out.println("View Control Screens, press F1 to exit");
										      gameRooms.debugViewControl = true;
								      	}
									      else
									      {
										      gameRooms.debugViewControl = false;						      	
									      }
							      	break;
							      case Event.F2:
							      
							      		if (ego.debugViewCollision == false)
									      {
									      	System.out.println("View Player Collision Box and Coordinates, press F2 to exit");
										      ego.debugViewCollision = true;
								      	}
									      else
									      {
									      	ego.debugViewCollision = false;
									      }
							      	break;
								   case Event.F3:
											if (gameInventory.debugMode == false)
											{
												System.out.println("View Inventory and Static Item Collision Boxes, press F3 to exit");
												gameInventory.debugMode = true;											
											}
											else
											{
												gameInventory.debugMode = false;
											}
								   	break;
						      }
					      }      
				      	switch (key)
					      {
					      	case Event.UP:
						      	ego.Walk(ego.up);
						      	break;			      			      
					      	case Event.DOWN:
						      	ego.Walk(ego.down);		      
						      	break;	      
					      	case Event.LEFT:
						      	ego.Walk(ego.left);		      
						      	break;
					      	case Event.RIGHT:
						      	ego.Walk(ego.right);		      
						      	break;
							      
							   case Event.F12:
							   		if (debugMode == false)
									   {
									   	debugMode = true;
										   System.out.println("\nDebug Mode On\nF1: View Control Screens, F2: View player Collision box and Coordinates, F3: View Inventory and Static Item Collision Boxes");
								   	}
									   else
									   {
									   	debugMode = false;
										   gameRooms.debugViewControl = false;
										   ego.debugViewCollision = false;	
										   gameInventory.debugMode = false;
										   System.out.println("\nDebug Mode Off");
									   }
								   break;								  
					      }
				      }
			return true;	
	}
	boolean advMouseDown(Event e, int x, int y)
	{		
      	if (gameState != inventory && gameState != settings && gameState != staticScreen)
	      {      
	      	if (e.metaDown() == false)
		      {
		      	GameMenu.checkMouseDowns(x, y);
		      }
	      }
	      if (gameState == inventory)
	      {
	      	if (e.metaDown() == false)
		      {
		      	GameMenu.checkMouseDowns(x, y);
		      }	      
	      	if (gameInventory.closeButton.pointingTo(x, y) == true)
		      {
		      	gameInventory.closeButton.eventState = MenuHandler.pressed;
		      }
		      if (gameInventory.leftButton.pointingTo(x, y) == true)
		      {
		      	gameInventory.leftButton.eventState = MenuHandler.pressed;		      	
		      }
		      if (gameInventory.rightButton.pointingTo(x, y) == true)
		      {
		      	gameInventory.rightButton.eventState = MenuHandler.pressed;		      
		      }		      
	      }	
		return true;
	}
   boolean advMouseUp(Event e, int x, int y)
   { 	     
	   	if (gameState != inventory && gameState != settings && gameState != staticScreen && gameState != textBox)
	    	{      
		    		if (e.metaDown() == false)  // false is left click, true is right
		      	{		      
				      	if (GameMenu.inventoryPressed(x, y) == true)
					      {
					      	gameState = inventory;
						      gameInventory.inventoryOpen = true;
					  			thisApplet.setCursor(gameCursors[standardCursor]);
								usingInvItem = -1;
								gameInvState = 0;					      
						      return true;				      
					      }
					      if (GameMenu.settingsPressed(x, y) == true)
					      {
					      	//gameState = settings;
					  			thisApplet.setCursor(gameCursors[standardCursor]);	
								usingInvItem = -1;				      
						      System.out.println("Settings Coming Soon");	
								return true;				      		      
					      }
				      	if (GameMenu.inventoryPressed(x, y) == false && GameMenu.settingsPressed(x, y) == false)
					      {
					      	GameMenu.checkMouseUps(x, y);	
														
									if (GameMenu.cursorType < 3 + 1)
									{
										gameState = GameMenu.cursorType + 1;
										usingInvItem = -1;
									}
									else
									{
										gameState = walking;
									}
						      	if (usingInvItem == -1)
							      {					       
										thisApplet.setCursor(gameCursors[GameMenu.cursorType]);
									}																								
							}			    						
					}
					if (e.metaDown() == true)
				   {
				  		thisApplet.setCursor(gameCursors[standardCursor]);
						GameMenu.cursorType = standardCursor;
				   }	 
			  }  
			  if (gameState == inventory)
			  {	
			  int checking = 0;	
						  	  		  
					GameMenu.checkMouseUps(x, y); 	  
					gameInventory.closeButton.eventState = MenuHandler.unpressed;
					gameInventory.leftButton.eventState = MenuHandler.unpressed;	
					gameInventory.rightButton.eventState = MenuHandler.unpressed;						
					
						if (usingInvItem == -1)
						{
							gameInvState = GameMenu.cursorType + 1;						
							thisApplet.setCursor(gameCursors[GameMenu.cursorType]);
						}
											  			
				  		if (gameInventory.closeButton.pointingTo(x, y) == true)
						{
							gameState = walking;  
							gameInventory.inventoryOpen = false;							
						}
						if (gameInventory.leftButton.pointingTo(x, y) == true)
						{
							gameInventory.showLastPage();
						}	
						if (gameInventory.rightButton.pointingTo(x, y) == true)
						{						
							gameInventory.showNextPage();
						}							
						if (e.metaDown() == false)
						{							
								while (checking < InventoryHandler.maxItems)
								{
										if (gameInventory.itemList[checking].hasItem == true &&
										    gameInventory.pointingTo(checking, x, y) == true &&
										    GameMenu.cursorType == standardCursor && usingInvItem == -1)
										{	
											setInventoryCursor(checking);					
											break;
										}
									checking++;
								}
								
							checking = 0;																
						}
						if (e.metaDown() == true)
						{
					  		thisApplet.setCursor(gameCursors[standardCursor]);
							GameMenu.cursorType = standardCursor;
							usingInvItem = -1;
							gameInvState = 0;							
						}						
			  }
			  if (gameState == textBox && e.metaDown() == false)
			  {
			  		gameState = walking;
			  }			  	  
		mouseEventX = x;
		mouseEventY = y;
		mouseEventClicked = true;
   	return true;
   }
   boolean advMouseMove(Event e, int x, int y)
   {
	   	 if (gameState != inventory && gameState != settings && gameState != staticScreen && gameState != textBox)
		    {      
		    		GameMenu.checkMousePosition(x, y);
		    }
	    return true;
   }
   void clearInputEvents()
   {
   	mouseEventClicked = false;
	   eventEnter = false;
   }
   void clearEvents()
   {
  	int checking = 0;
	  
			while (checking < maxEvents)
			{
				staticEvents[checking] = -1; // no events
				checking++;
			}
			
		currentEventPlace = 0;		 	 
   }
   void setInventoryCursor(int invNum)
   {
	Point hotSpot = new Point(0, 0);
	int width = gameInventory.itemList[invNum].width, height = gameInventory.itemList[invNum].height;
	BufferedImage cursorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = cursorImage.createGraphics();
   
		usingInvItem = invNum;
  		g2.drawImage(gameInventory.itemList[invNum].itemAnim.theAnimation, 0, 0, width, height, 0, 0, width, height, this);
		g2.setColor(Color.RED);
		g2.fillRect(0,0,5,5);
		
		inventoryCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "Inventory Item Cursor"); // automaticall resise to 32x32 :P
		g2.dispose();
		
		thisApplet.setCursor(inventoryCursor);	   	   	
   }
   
// Event Handler Functions, these functions used by developers
  
	int currentRoom()
	{
		if (gameState != inventory && gameState != settings && gameState != staticScreen && gameState != textBox)
		{		
			return gameRooms.currentRoom;  
		}
		else
		{
			return -1;
		}
	}
	void changeRoom(int roomNumber)
	{
		gameRooms.changeRoom(roomNumber);
	}
	void changeRoom(int roomNumber, int playerX, int playerY, int playerDirection)
	{		
		gameRooms.changeRoom(roomNumber);
		ego.x = playerX;
		ego.y = playerY;
		ego.Walk(playerDirection);
		ego.walking = false;
	}
	void changeRoom(int roomNumber, int playerX, int playerY)
	{		
		gameRooms.changeRoom(roomNumber);
		ego.x = playerX;
		ego.y = playerY;
	}
	boolean inInventory()
	{
		if (gameState == inventory)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	void setPlayerWait()
	{	
		recordingEvents = true;
		clearEvents();
		thisApplet.setCursor(gameCursors[waitCursor]);
		GameMenu.cursorType = waitCursor;
		gameState = staticScreen;
		usingInvItem = -1; 
	}
	void addStaticEvent(String itemName)
	{
	int checking = 0;
	
		while (checking < maxEvents && recordingEvents == true && currentEventPlace < maxEvents)
		{
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
					staticEvents[currentEventPlace] = checking;
					currentEventPlace++;
				   break;
			   }		
			checking++;
   	}  
	}
	void setPlayerControl()
	{
		recordingEvents = false; 
	}			
   void print(String text)
   {
   int prevGameState = gameState;
    	
   	if (gameState != textBox)
	   {
			gameState = textBox;
			gameTextBox.paintMessage(text);	
		}
		if (gameState == textBox)
		{						
				while(gameState == textBox)
				{
					thisApplet.repaint();
				}
				
				if (prevGameState == inventory || prevGameState == settings || prevGameState == staticScreen)
				{
					gameState = prevGameState;
					gameInvState = 0;
				}
				else
				{
					gameState = walking;
				}		
		}
   } 
   void print(String text, int x, int y)
   {
   int prevGameState = gameState;
        
   	if (gameState != textBox)
	   {
			gameState = textBox;   
			gameTextBox.paintMessage(text);
			gameTextBox.windowX = x;
			gameTextBox.windowY = y;	
		}
		if (gameState == textBox)
		{ 				
				while(gameState == textBox)
				{
					thisApplet.repaint();
				}
				
				if (prevGameState == inventory || prevGameState == settings || prevGameState == staticScreen)
				{
					gameState = prevGameState;
					gameInvState = 0;					
				}
				else
				{
					gameState = walking;
				}				
			//gameState = prevGameState;		
		}		  
   }
   boolean inBounds(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
   {
   boolean returnValue = false;
   int xcheck = ego.topLeftX, ycheck = ego.topLeftY;
   
   		while (ycheck < ego.bottomRightY)
		   {
		   		if (xcheck < bottomRightX && xcheck > topLeftX &&
				       ycheck < bottomRightY && ycheck > topLeftY)
				   {
				   	returnValue = true;
					   break;
				   }
		   	xcheck++;
			   
			   	if (xcheck > ego.bottomRightX - 1)
				   {
				   	xcheck = ego.topLeftX;
					   ycheck++;
				   }
		   }
		      
   	return returnValue;
   }
	boolean inBounds(String objName, int xMargin, int yMargin)
	{
	boolean returnValue = false;
	int xcheck = ego.topLeftX, ycheck = ego.topLeftY;
	int topLeftX = 0, topLeftY = 0;
	int bottomRightX = 0, bottomRightY = 0;
	int checking = 0;
	
				while (checking < gameStatics.maxStatics && returnValue == false)
				{
						if (gameStatics.staticItems[checking].name == objName)
						{
							topLeftX = gameStatics.staticItems[checking].topLeftX - xMargin;
							topLeftY = gameStatics.staticItems[checking].topLeftY - yMargin;
							bottomRightX = gameStatics.staticItems[checking].bottomRightX + xMargin;
							bottomRightY = gameStatics.staticItems[checking].bottomRightY + yMargin;
												
								while (ycheck < ego.bottomRightY)
								{							
										if (xcheck < bottomRightX && xcheck > topLeftX &&
											 ycheck < bottomRightY && ycheck > topLeftY)
										{
											returnValue = true;
										}
									
									xcheck++;
									
										if (xcheck > ego.bottomRightX - 1)
										{
											xcheck = ego.topLeftX;
											ycheck++;
										}
								}
						}
					checking++;
				}
				
			checking = 0;
			xcheck = ego.topLeftX;
			ycheck = ego.topLeftY;			
			
				while (checking < InventoryHandler.maxItems && returnValue == false)
				{
						if (gameInventory.itemList[checking].itemName == objName)
						{
							topLeftX = gameInventory.itemList[checking].topLeftX - xMargin;
							topLeftY = gameInventory.itemList[checking].topLeftY - yMargin;
							bottomRightX = gameInventory.itemList[checking].bottomRightX + xMargin;
							bottomRightY = gameInventory.itemList[checking].bottomRightY + yMargin;
												
								while (ycheck < ego.bottomRightY)
								{							
										if (xcheck < bottomRightX && xcheck > topLeftX &&
											 ycheck < bottomRightY && ycheck > topLeftY)
										{
											returnValue = true;
										}
									
									xcheck++;
									
										if (xcheck > ego.bottomRightX - 1)
										{
											xcheck = ego.topLeftX;
											ycheck++;
										}
								}
						}
					checking++;
				}			
		return returnValue;
	}   
   boolean roomRedEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.red)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   }
   boolean roomYellowEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.yellow)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   }   
   boolean roomGreenEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.green)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   } 
   boolean roomLightBlueEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.lightBlue)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   }
   boolean roomBlueEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.blue)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   } 
   boolean roomPurpleEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.purple)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }
   }
   boolean noRoomEvent()
   {
   	if (gameRooms.returnRoomEventState(ego) == RoomHandler.black)
	   {
	   	return true;
	   }
	   else
	   {
	   	return false;
	   }   
   }                   
	boolean usedItem(String itemName)  // For testing if Static Objects where clicked with the hand icon
   {
   boolean returnValue = false;
   int checking = 0;
   
   int x = 0, y = 0, width = 0, height = 0;

		if (gameState == grabbing)
		{   
			   while (checking < StaticItemHandler.maxStatics && returnValue != true)
				{	   
				   		if (gameStatics.staticItems[checking].inRoom == gameRooms.currentRoom &&
						       gameStatics.staticItems[checking].name == itemName && gameStatics.staticItems[checking].hidden == false)
						   {
							   	x = gameStatics.staticItems[checking].x;
							   	y = gameStatics.staticItems[checking].y;
							   	width = gameStatics.staticItems[checking].width;
							   	height = gameStatics.staticItems[checking].height;					   					   					   
								   
							   		if (mouseEventClicked == true && 
									       mouseEventX < x + width && mouseEventX > x &&
									       mouseEventY < y + height && mouseEventY > y)
									   {
									   	returnValue = true;
									   }
								   break;
						   }
					   checking++;
				}
			
			checking = 0;
			
				while (checking < InventoryHandler.maxItems && returnValue != true)
				{
						if (gameInventory.active[checking] == true &&
						    gameInventory.itemList[checking].itemName == itemName &&
						    gameInventory.itemList[checking].inRoom == gameRooms.currentRoom)
						{
							x = gameInventory.itemList[checking].xinv;
							y = gameInventory.itemList[checking].yinv;
							width = gameInventory.itemList[checking].width;
							height = gameInventory.itemList[checking].height;
							
								if (mouseEventClicked == true &&
								    mouseEventX < x + width && mouseEventX > x &&
								    mouseEventY < y + height && mouseEventY > y)
								{
									returnValue = true;
								}
						}											
					checking++;
				}					
		}						
   	return returnValue;
   }      
   boolean usedStaticItemNumber(int itemNumber)  // For testing if Static Objects numbers where clicked with the hand icon
   {
   boolean returnValue = false;   
   int x = 0, y = 0, width = 0, height = 0;

		if (gameState == grabbing)
		{     
	   	x = gameStatics.staticItems[itemNumber].x;
	   	y = gameStatics.staticItems[itemNumber].x;
	   	width = gameStatics.staticItems[itemNumber].width;
	   	height = gameStatics.staticItems[itemNumber].height;					   					   					   
		   
	   		if (mouseEventClicked == true && 
			       mouseEventX < x + width && mouseEventX > x &&
			       mouseEventY < y + height && mouseEventY > y  && 
			       gameStatics.staticItems[itemNumber].hidden == false)
			   {
			   	returnValue = true;
			   }			      	
		}	       	
   	return returnValue;   
   }    
   boolean usedInventoryItemNumber(int itemNumber)
   {
	boolean returnValue = false;
	int x = 0, y = 0, width = 0, height = 0;	
	
			if (gameState == grabbing)
			{
					if (gameInventory.active[itemNumber] == true &&
					    gameInventory.itemList[itemNumber].inRoom == gameRooms.currentRoom)
					{
						x = gameInventory.itemList[itemNumber].xinv;
						y = gameInventory.itemList[itemNumber].yinv;
						width = gameInventory.itemList[itemNumber].width;
						height = gameInventory.itemList[itemNumber].height;
						
							if (mouseEventClicked == true &&
							    mouseEventX < x + width && mouseEventX > x &&
							    mouseEventY < y + height && mouseEventY > y)
							{
								returnValue = true;
							}
					}	
			}	
		return returnValue;
   } 
	boolean grabbedScreen(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < bottomRightX && mouseEventX > topLeftX &&
			    mouseEventY < bottomRightY && mouseEventY > topLeftY &&
			    gameState == grabbing)
			{
				returnValue = true;
			}
	
		return returnValue;
	}
	boolean usedInvItemOnItem(String invItemName, String itemName)
   {
   boolean returnValue = false;
   int checking = 0;
   
   int x = 0, y = 0, width = 0, height = 0;

		if (usingInvItem > -1)
		{
			if (gameInventory.itemList[usingInvItem].itemName == invItemName)
			{   
			   while (checking < StaticItemHandler.maxStatics && returnValue != true)
				{	   
				   		if (gameStatics.staticItems[checking].inRoom == gameRooms.currentRoom &&
						       gameStatics.staticItems[checking].name == itemName  && 
						       gameStatics.staticItems[checking].hidden == false)
						   {
							   	x = gameStatics.staticItems[checking].x;
							   	y = gameStatics.staticItems[checking].y;
							   	width = gameStatics.staticItems[checking].width;
							   	height = gameStatics.staticItems[checking].height;					   					   					   
								   
							   		if (mouseEventClicked == true && 
									       mouseEventX < x + width && mouseEventX > x &&
									       mouseEventY < y + height && mouseEventY > y)
									   {
									   	returnValue = true;
										   break;
									   }
						   }
					   checking++;
				}
			
			checking = 0;
			
				while (checking < InventoryHandler.maxItems && returnValue != true)
				{
						if (gameInventory.active[checking] == true &&
						    gameInventory.itemList[checking].itemName == itemName &&
						    gameInventory.itemList[checking].inRoom == gameRooms.currentRoom &&
						    gameInventory.itemList[checking].hasItem == false)
						{
							x = gameInventory.itemList[checking].xinv;
							y = gameInventory.itemList[checking].yinv;
							width = gameInventory.itemList[checking].width;
							height = gameInventory.itemList[checking].height;
							
								if (mouseEventClicked == true &&
								    mouseEventX < x + width && mouseEventX > x &&
								    mouseEventY < y + height && mouseEventY > y)
								{
									returnValue = true;
									break;
								}
						}											
					checking++;
				}
				
			checking = 0;
			
				while (checking < InventoryHandler.maxPageItems && returnValue != true)
				{
						if (gameInventory.invPages[gameInventory.viewingPage][checking] > -1 &&
							 gameInventory.active[gameInventory.invPages[gameInventory.viewingPage][checking]] == true &&
						    gameInventory.itemList[gameInventory.invPages[gameInventory.viewingPage][checking]].hasItem == true &&
						    gameInventory.itemList[gameInventory.invPages[gameInventory.viewingPage][checking]].itemName == itemName)
						{						
							if (mouseEventClicked == true &&
							    gameInventory.pointingTo(gameInventory.invPages[gameInventory.viewingPage][checking], mouseEventX, mouseEventY) == true)
							{
								returnValue = true;
								break;
							}							
						}						
					checking++;
				}				
			}					
		}						
   	return returnValue;
   }
	boolean usedItemWithScreen(String invItemName, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
	{
	boolean returnValue = false;
			
		if (usingInvItem != -1)
		{
			if (gameInventory.itemList[usingInvItem].itemName == invItemName)
			{ 			
				if (mouseEventClicked == true &&
				    mouseEventX < bottomRightX && mouseEventX > topLeftX &&
				    mouseEventY < bottomRightY && mouseEventY > topLeftY)
				{
					returnValue = true;
				}
			}
		}
		return returnValue;
	}    
	boolean grabbedPlayer()
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < ego.bottomRightX && mouseEventX > ego.x &&
			    mouseEventY < ego.bottomRightY && mouseEventY > ego.y &&
			    gameState == grabbing && gameRooms.currentRoom > 0 - 1)
			{
				returnValue = true;			
			}
		return returnValue;
	}
	boolean usedInvItemOnPlayer(String invItemName)
   {
   boolean returnValue = false;

		if (usingInvItem > -1)
		{
				if (gameInventory.itemList[usingInvItem].itemName == invItemName)
				{   
						if (mouseEventClicked == true &&
						    mouseEventX < ego.bottomRightX && mouseEventX > ego.x &&
						    mouseEventY < ego.bottomRightY && mouseEventY > ego.y)
						{
							returnValue = true;			
						}				
				}					
		}						
   	return returnValue;
   }		
	boolean grabInventory(String ItemName)
	{
	boolean returnValue = false;
	int checking = 0;
	
			while (checking < InventoryHandler.maxItems)
			{			
					if (gameInventory.itemList[checking].itemName == ItemName && 
					    gameInventory.itemList[checking].hasItem == true &&
					    gameInvState == grabbing)
					{
						if (gameInventory.pointingTo(checking, mouseEventX, mouseEventY) == true)
						{
							returnValue = true;
						}
					}
				checking++;
			}	
		return returnValue;
	}
	boolean lookInventory(String ItemName)
	{
	boolean returnValue = false;
	int checking = 0;
	
			while (checking < InventoryHandler.maxItems)
			{			
					if (gameInventory.itemList[checking].itemName == ItemName && 
					    gameInventory.itemList[checking].hasItem == true &&
					    gameInvState == looking)
					{
						if (gameInventory.pointingTo(checking, mouseEventX, mouseEventY) == true)
						{
							returnValue = true;
						}
					}
				checking++;
			}	
		return returnValue;
	}	
	boolean talkInventory(String ItemName)
	{
	boolean returnValue = false;
	int checking = 0;
	
			while (checking < InventoryHandler.maxItems)
			{			
					if (gameInventory.itemList[checking].itemName == ItemName && 
					    gameInventory.itemList[checking].hasItem == true &&
					    gameInvState == talking)
					{
						if (gameInventory.pointingTo(checking, mouseEventX, mouseEventY) == true)
						{
							returnValue = true;
						}
					}
				checking++;
			}	
		return returnValue;
	}			
   void getInvItem(String name)
   {
   	gameInventory.getItem(name);
   }
   void getInvItem(int number)
   {
   int currentPlaceX = gameInventory.currentPlaceX, currentPlaceY = gameInventory.currentPlaceY;
   int panelTopLeftX = gameInventory.panelTopLeftX, panelTopLeftY = gameInventory.panelTopLeftY;
   
   	gameInventory.itemList[number].getItem(currentPlaceX, currentPlaceY, panelTopLeftX, panelTopLeftY);	
   }
	boolean hasInvItem(String name)
	{
	int checking = 0;
	boolean returnValue = false;
	
			while (checking < InventoryHandler.maxItems)
			{
					if (gameInventory.active[checking] == true && gameInventory.itemList[checking].itemName == name &&
					    gameInventory.itemList[checking].hasItem == true)
					{
						returnValue = true;
					}
				checking++;
			}
		return returnValue;
	}
	boolean hasInvItem(int number)
	{
		return gameInventory.itemList[number].hasItem;	
	}
	void dropInvItem(String name)
	{
	int checking = 0;
	int page = 0, pageScan = 0;
	
			while (checking < InventoryHandler.maxItems)
			{
					if (gameInventory.active[checking] == true && gameInventory.itemList[checking].itemName == name &&
					    gameInventory.itemList[checking].hasItem == true)
					{
							while (page < gameInventory.maxPages)
							{
									if (gameInventory.invPages[page][pageScan] == checking)
									{
										gameInventory.invPages[page][pageScan] = -1;
										gameInventory.active[checking] = false;
										
											if (usingInvItem == checking)
											{
										  		thisApplet.setCursor(gameCursors[standardCursor]);
												GameMenu.cursorType = standardCursor;
												usingInvItem = 0;
											}
										break;
									}
								pageScan++;
									
									if (pageScan > gameInventory.maxPageItems - 1)
									{
										page++;
										pageScan = 0;
									}
							}
						gameInventory.itemList[checking].hasItem = false;
						gameInventory.resetInvPlaces();
					}
				checking++;
			}	
	}
	boolean lookedItem(String itemName)
	{
   boolean returnValue = false;
   int checking = 0;
   
   int x = 0, y = 0, width = 0, height = 0;

		if (gameState == looking)
		{   
			   while (checking < StaticItemHandler.maxStatics && returnValue != true)
				{	   
				   		if (gameStatics.staticItems[checking].inRoom == gameRooms.currentRoom &&
						       gameStatics.staticItems[checking].name == itemName  && 
						       gameStatics.staticItems[checking].hidden == false)
						   {
							   	x = gameStatics.staticItems[checking].x;
							   	y = gameStatics.staticItems[checking].y;
							   	width = gameStatics.staticItems[checking].width;
							   	height = gameStatics.staticItems[checking].height;					   					   					   
								   
							   		if (mouseEventClicked == true && 
									       mouseEventX < x + width && mouseEventX > x &&
									       mouseEventY < y + height && mouseEventY > y)
									   {
									   	returnValue = true;
									   }
								   break;
						   }
					   checking++;
				}
			
			checking = 0;
			
				while (checking < InventoryHandler.maxItems && returnValue != true)
				{
						if (gameInventory.active[checking] == true &&
						    gameInventory.itemList[checking].itemName == itemName &&
						    gameInventory.itemList[checking].inRoom == gameRooms.currentRoom)
						{
							x = gameInventory.itemList[checking].xinv;
							y = gameInventory.itemList[checking].yinv;
							width = gameInventory.itemList[checking].width;
							height = gameInventory.itemList[checking].height;
							
								if (mouseEventClicked == true &&
								    mouseEventX < x + width && mouseEventX > x &&
								    mouseEventY < y + height && mouseEventY > y)
								{
									returnValue = true;
								}
						}											
					checking++;
				}					
		}						
   	return returnValue;	
	}
	boolean lookedScreen(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < bottomRightX && mouseEventX > topLeftX &&
			    mouseEventY < bottomRightY && mouseEventY > topLeftY &&
			    gameState == looking)
			{
				returnValue = true;
			}
	
		return returnValue;
	}
	boolean lookPlayer()
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < ego.bottomRightX && mouseEventX > ego.x &&
			    mouseEventY < ego.bottomRightY && mouseEventY > ego.y &&
			    gameState == looking && gameRooms.currentRoom > 0 - 1)
			{
				returnValue = true;
			}
	
		return returnValue;
	}	
	boolean talkItem(String itemName)
	{
   boolean returnValue = false;
   int checking = 0;
   
   int x = 0, y = 0, width = 0, height = 0;

		if (gameState == talking)
		{   
			   while (checking < StaticItemHandler.maxStatics && returnValue != true)
				{	   
				   		if (gameStatics.staticItems[checking].inRoom == gameRooms.currentRoom &&
						       gameStatics.staticItems[checking].name == itemName  && 
						       gameStatics.staticItems[checking].hidden == false)
						   {
							   	x = gameStatics.staticItems[checking].x;
							   	y = gameStatics.staticItems[checking].y;
							   	width = gameStatics.staticItems[checking].width;
							   	height = gameStatics.staticItems[checking].height;					   					   					   
								   
							   		if (mouseEventClicked == true && 
									       mouseEventX < x + width && mouseEventX > x &&
									       mouseEventY < y + height && mouseEventY > y)
									   {
									   	returnValue = true;
									   }
								   break;
						   }
					   checking++;
				}
			
			checking = 0;
			
				while (checking < InventoryHandler.maxItems && returnValue != true)
				{
						if (gameInventory.active[checking] == true &&
						    gameInventory.itemList[checking].itemName == itemName &&
						    gameInventory.itemList[checking].inRoom == gameRooms.currentRoom)
						{
							x = gameInventory.itemList[checking].xinv;
							y = gameInventory.itemList[checking].yinv;
							width = gameInventory.itemList[checking].width;
							height = gameInventory.itemList[checking].height;
							
								if (mouseEventClicked == true &&
								    mouseEventX < x + width && mouseEventX > x &&
								    mouseEventY < y + height && mouseEventY > y)
								{
									returnValue = true;
								}
						}											
					checking++;
				}					
		}						
   	return returnValue;	
	}
	boolean talkScreen(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < bottomRightX && mouseEventX > topLeftX &&
			    mouseEventY < bottomRightY && mouseEventY > topLeftY &&
			    gameState == talking)
			{
				returnValue = true;
			}
	
		return returnValue;
	}
	boolean talkPlayer()
	{
	boolean returnValue = false;
	
			if (mouseEventClicked == true &&
			    mouseEventX < ego.bottomRightX && mouseEventX > ego.x &&
			    mouseEventY < ego.bottomRightY && mouseEventY > ego.y &&
			    gameState == talking && gameRooms.currentRoom > 0 - 1)
			{
				returnValue = true;
			}
	
		return returnValue;
	}				
   void switchAnimation(String staticItemName, int cellsFrom, int cellsTo, int speed) // changing which frames to animate of a static object
   {
   int checking = 0;
   
   	while(checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == staticItemName)
			   {
			   	gameStatics.staticItems[checking].item.switchAnimation(cellsFrom, cellsTo);
				   gameStatics.staticItems[checking].item.speed = speed;
				   break;
			   }
	   	checking++;
	   }
   } 
   void switchAnimation(int staticNumber, int cellsFrom, int cellsTo, int speed) // changing which frames to animate of a static object number
   {
		   gameStatics.staticItems[staticNumber].item.switchAnimation(cellsFrom, cellsTo);
			gameStatics.staticItems[staticNumber].item.speed = speed;			
   }
   void togglePlayPause(String itemName)
   {
   int checking = 0;
   
   	while (checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
						if (gameStatics.staticItems[checking].item.animating == true)
						{
							gameStatics.staticItems[checking].item.pause(); // item = animation class
						}
						else
						{
							gameStatics.staticItems[checking].item.play();
						}
				   break;
			   }
	   		if (gameInventory.itemList[checking].itemName == itemName)
			   {
						if (gameInventory.itemList[checking].itemAnim.animating == true)
						{
							gameInventory.itemList[checking].itemAnim.pause(); // item = animation class
						}
						else
						{
							gameInventory.itemList[checking].itemAnim.play();
						}
				   break;
			   }			   
	   	checking++;
	   }
   }
   void toggleLoop(String itemName)
   {
   int checking = 0;
   
   	while (checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
					gameStatics.staticItems[checking].item.toggleLoop(); // item = animation class
				   break;
			   }
	   		if (gameInventory.itemList[checking].itemName == itemName)
			   {
					gameInventory.itemList[checking].itemAnim.toggleLoop(); // item = animation class
				   break;
			   }			   
	   	checking++;
	   }   
   }
   void gotoFrame(String itemName, int cell)
   {
   int checking = 0;
   
   	while (checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
					gameStatics.staticItems[checking].item.currentCell = cell;
				   break;
			   }
	   		if (gameInventory.itemList[checking].itemName == itemName)
			   {
					gameInventory.itemList[checking].itemAnim.currentCell = cell;
				   break;
			   }			   
	   	checking++;
	   }
   }          
   void hideStaticItem(String itemName) // hides static item from view and processing
   {
   int checking = 0;
   
   	while (checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
			   	gameStatics.staticItems[checking].hidden = true;
				   break;
			   }
	   	checking++;
	   }
   }
   void showStaticItem(String itemName) // shows it
   {
   int checking = 0;
   
   	while (checking < StaticItemHandler.maxStatics)
	   {
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
			   	gameStatics.staticItems[checking].hidden = false;
				   break;
			   }
	   	checking++;
	   }
   }
   void staticItemMove(String itemName, int x, int y, int speed) // sets the item to move so that it will move while everything else is going on
   {
	int checking = 0;
	
		while (checking < StaticItemHandler.maxStatics)
		{
	   		if (gameStatics.staticItems[checking].name == itemName)
			   {
			   	gameStatics.staticItems[checking].setMove(x, y, speed);
					addStaticEvent(itemName);
				   break;
			   }		
			checking++;
   	}   
   }
}