import java.applet.*;
import java.awt.*;
import java.net.*;

public class Adv extends Applet implements Runnable
{  
private Image dbImage;
private Graphics dbg;

GameHandler AdvGame;

      public void init() 
      {      		
      	System.out.println("Init");	      	      
	      AdvGame = new GameHandler(this);
	      
	      AdvGame.initPlayer("PlayerAnim.gif");            		            	      	      	     		                 												
			AdvGame.initIcons("HandButton.gif", "LookButton.gif", "TalkButton.gif", "InvButton.gif", "SettingsButton.gif");														                 
			AdvGame.initCursors("handCursor.gif", "lookCursor.gif", "talkCursor.gif", "waitCursor.gif", "standardCursor.gif");			
															             
			AdvGame.addRoom("room0.gif", "room0Control.gif", "room0Priority.gif");
			AdvGame.addRoom("room1.gif", "room1Control.gif", "room1Priority.gif");					
						
			AdvGame.addInventoryItem("Key", "Key.gif", 0, 550, 350);
			AdvGame.animateLastInventory(5, 5);
			
			AdvGame.addInventoryItem("Burger", "Burger.gif", -1, 330, 400);
			
			AdvGame.addInventoryItem("Shades", "sunglasses.gif", 1, 310, 400);			
			
			AdvGame.addStaticItem("Television", "Television.gif", 250, 260, 100, 80, 0);
			AdvGame.animateLastStatic(2, 4);
			AdvGame.addStaticItem("Poster", "Poster.gif", 470, 120, 75, 100, 1);
			
			AdvGame.finalInit();	//ensure this is the last of the init code!
      }
      public void start() 
      {
        	System.out.println("Start");
      	Thread th = new Thread(this);
	      th.start();
      } 
      public void run()
      {
      // main game variables go here
      boolean tvChannel = false, gotBurger = false;
      boolean doorLocked = true, posterMoved = false;     
      
         System.out.println("Run");
	      Thread.currentThread().setPriority(Thread.MIN_PRIORITY); // not sure if necessary     
	      
	      	while(true)
		      {
		      	repaint();
					AdvGame.Handle();
					
						// main game code starts here
						
						if (AdvGame.currentRoom() == 0)
						{
			 				if (AdvGame.usedItem("Television"))
			 				{
							 	if (AdvGame.inBounds("Television", 10, 10))
								{							 
				 					if (tvChannel == false)
				 					{
				 						AdvGame.print("You switch the channel on the tv");
					 					AdvGame.switchAnimation("Television", 2, 2, 1);
				 						AdvGame.print("Another boring Infomercial!");
										
					 						if (!AdvGame.hasInvItem("Burger") && gotBurger == false)
					 						{
					 							AdvGame.print("Theres a Burger behind the TV, you can have it");
					 							AdvGame.getInvItem("Burger");
												gotBurger = true;
				 							}
					 					tvChannel = true;
					 				}
					 				else
					 				{
				 						AdvGame.switchAnimation("Television", 0, 1, 5);
				 						tvChannel = false;
									}
								} 
								else
								{
									AdvGame.print("You need to get closer");
								}									
							}
							if (AdvGame.lookedItem("Television"))
							{
								if (tvChannel == true)
								{
									AdvGame.print("Its a really interesting infomercial about\nthe ADV adventure Game Engine");
								}
								else
								{
									AdvGame.print("A whole bunch of fuzzy static on the screen");
								}
							}
							if (AdvGame.talkItem("Television"))
							{
								AdvGame.print("Licking the television will electrocute you");
								AdvGame.print("Talking to the sceen will prove that your crazy");
							}								 
							if (AdvGame.usedItem("Key"))
							{
								if (AdvGame.inBounds("Key", 10, 10))
								{
									AdvGame.print("You got a key, shiny!");
									AdvGame.getInvItem("Key");
								}
								else
								{
									AdvGame.print("Please Get Closer to the item in question");
								}
							}
							if (AdvGame.lookedItem("Key"))
							{
								AdvGame.print("It's a picture of a key with an infinite loop in the animation");
								AdvGame.print("Just kidding, it really is a key!");
							}
							if (AdvGame.talkItem("Key"))
							{
								AdvGame.print("It doesn't have chocolate inside");
							}
							if (AdvGame.roomRedEvent())
							{
								AdvGame.print("You stepped into the next Room");
								AdvGame.changeRoom(1, 180, 250, AdvGame.right);
							}					
						}
						if (AdvGame.currentRoom() == 1)
						{
							if (AdvGame.usedInvItemOnPlayer("Shades"))
							{
								AdvGame.print("You got it mac, ill put em on");
								AdvGame.dropInvItem("Shades");
							}							
							if (AdvGame.grabbedScreen(90, 200, 120, 380))
							{
								if (AdvGame.inBounds(75, 320, 170, 410))
								{
									if (doorLocked == false)
									{
										AdvGame.print("Hmm this door doesnt animate,\nbut ill go through anyway");
										AdvGame.print("This doors version is no longer supported by its developers"); 
										AdvGame.print("We apologize for any inconveniance");
										AdvGame.changeRoom(0, 630, 250, AdvGame.left);
									}
									else
									{
										AdvGame.print("The door locked behind you, there was a key in the other room though");
										AdvGame.print("Was it too late to mention that? or did you take it");
										
											if (!AdvGame.hasInvItem("Key"))
											{
												AdvGame.print("Aparently so, nice work on locking yourself in");
											}
											else
											{
												AdvGame.print("Aparently not, smart move");
											}
									}
								}
								else
								{
									AdvGame.print("Please Get closer to the door");
								}
							}
							if (AdvGame.usedItemWithScreen("Key", 90, 200, 120, 380))
							{
								if (doorLocked == true)
								{
									if (AdvGame.inBounds(75, 320, 170, 410))
									{
										AdvGame.print("You unlock the door");
										doorLocked = false;
									}
									else
									{
										AdvGame.print("You can use that key, but please get closer");
									}
								}
								else
								{
									AdvGame.print("You already unlocked it");
								}
							}
							if (AdvGame.lookedScreen(90, 200, 120, 380))
							{
								AdvGame.print("Its an obsolete door, the programmers no longer support THAT version anymore");
								AdvGame.print("Coming soon, DOOR v7.0");
							}
							if (AdvGame.grabbedScreen(280, 130, 450, 210))
							{
								AdvGame.print("Please do not touch the sun");
							}
							if (AdvGame.lookedScreen(280, 130, 450, 210))
							{
								AdvGame.print("Too bad you cant see the topless woman sunbathing out there");
								AdvGame.print("Too bad this is a family game");
								AdvGame.print("Too bad... ill stop now!");
							}
							if (AdvGame.talkScreen(280, 130, 450, 210))
							{
								AdvGame.print("Tastes strangely like glass");
							}
							if (AdvGame.usedItem("Poster"))
							{
								if (AdvGame.inBounds(460, 300, 550, 330))
								{
									if (posterMoved == false)
									{
										AdvGame.setPlayerWait();									
										AdvGame.print("Hmm, this poster slides");
										AdvGame.staticItemMove("Poster", 545, 120, 2);
										posterMoved = true;
										AdvGame.setPlayerControl();
									}
									else
									{
										AdvGame.print("its stuck");
									}
								}
								else
								{
									AdvGame.print("Get Closer to the Poster");
								}
							}
							if (AdvGame.lookedItem("Poster"))
							{
								AdvGame.print("Its a poster");
							}
							if (AdvGame.grabbedScreen(475, 140, 540, 200))
							{
								if (posterMoved == true)
								{
									AdvGame.print("i woudlnt have a clue what to do with it");
								}
							}
							if (AdvGame.lookedScreen(475, 140, 540, 200))
							{
								if (posterMoved == true)
								{
									AdvGame.print("its the super secret ADV console");
								}
							}
							if (AdvGame.talkScreen(475, 140, 540, 200))
							{
								if (posterMoved == true)
								{
									AdvGame.print("it doesnt have voice recognition");
								}
							}
							if (AdvGame.lookedItem("Shades"))
							{
								AdvGame.print("Some cheap Looking Sun Glasses");
							}
							if (AdvGame.usedItem("Shades"))
							{
								if (AdvGame.inBounds("Shades", 10, 10))
								{
									AdvGame.print("More Crap to hold on to");
									AdvGame.getInvItem("Shades");
								}
								else
								{
									AdvGame.print("Please Get Closer");
								}							
							}													
						}
						if (AdvGame.grabbedPlayer())
						{
							AdvGame.print("This ISN'T leisure suit larry");
						}
						if (AdvGame.lookPlayer())
						{
							AdvGame.print("Its our lovable hero Sonny Bonds from the Police Quest series");
							AdvGame.print("He's still pixelated from his journey from SCI01 to ADV");
						}
						if (AdvGame.talkPlayer())
						{
							AdvGame.print("Talking to oneself is the first sign of madness");
						}
						if (AdvGame.inInventory())
						{						
							if (AdvGame.grabInventory("Burger"))
							{
								AdvGame.print("Mother always told me never to play with my food");
							}
							if (AdvGame.lookInventory("Burger"))
							{
								AdvGame.print("Its a Burger, looks about 3 weeks old, due to crappy art it looks fresh");
							}
							if (AdvGame.talkInventory("Burger"))
							{
								AdvGame.print("Yuck!!!");
								AdvGame.dropInvItem("Burger");
							}
							if (AdvGame.grabInventory("Key"))
							{
								AdvGame.print("This key is sharp");
							}
							if (AdvGame.lookInventory("Key"))
							{
								AdvGame.print("Shiny");
							}
							if (AdvGame.talkInventory("Key"))
							{
								AdvGame.print("Are you trying to talk to a key?");
							}							
							if (AdvGame.usedInvItemOnItem("Key", "Burger") || AdvGame.usedInvItemOnItem("Burger", "Key"))
							{
								AdvGame.print("These Dont work together");
							}							
						}																			
						AdvGame.clearInputEvents();
						// main game code ends here
		      }
      }     
      public void stop() 
      {
      	System.out.println("Stop");
      }
      public void destroy() 
      {
        	System.out.println("Destroy");      
      }
      public void paint (Graphics g) 
      {           
			AdvGame.Paint(g);				
      }
      public void update (Graphics g) // this method needs to be integrated with gameHandler
      {
				if (dbImage == null)
				{				
				      dbImage = createImage (this.getSize().width, this.getSize().height);
				      dbg = dbImage.getGraphics ();				
				}      
								
			dbg.setColor (getBackground ());
			dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
			
			dbg.setColor (getForeground());
			paint (dbg);
			
			g.drawImage (dbImage, 0, 0, this);			
      }
      public boolean keyUp(Event e, int key)
      {
      	return AdvGame.advKeyUp(e, key);
      }
      public boolean mouseDown(Event e, int x, int y)
      {
      	return AdvGame.advMouseDown(e, x, y);      
      }
      public boolean mouseUp(Event e, int x, int y)
      {  
      	return AdvGame.advMouseUp(e, x, y);
      }
      public boolean mouseMove(Event e, int x, int y)
      {  
      	return AdvGame.advMouseMove(e, x, y);
      }
}