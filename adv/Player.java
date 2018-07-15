import java.applet.*;
import java.awt.*;
import java.net.*;

public class Player extends Applet
{
static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

boolean walking;       // when you move manually
int walkingDirection;
int walkSpeed;
 
int walkingX, walkingY; // when you click to move
boolean reachedDestination, startedDestination;

Animation playerAnim;
int x, y;
int topLeftX, topLeftY, bottomRightX, bottomRightY;
int collisionWidth, collisionHeight;

boolean debugViewCollision;

	Player (Image playerImage)
	{
		playerAnim = new Animation(playerImage, 69, 150, 2);
		playerAnim.switchAnimation(0, 9);
		playerAnim.pause();
								
		x = 400;
		y = 250;	
		
		topLeftX = x;
		topLeftY = y + 135;
		bottomRightX = topLeftX + 70;
		bottomRightY = topLeftY + 20;	
		
		walkSpeed = 3;
		walkingDirection = 0;
		walking = false;
		reachedDestination = true;
		startedDestination = false;
	}
	void Paint(Graphics g)
	{																
		playerAnim.Paint(x,y,g);			
		
			if (debugViewCollision == true)
			{			
				g.setColor(Color.RED);
				g.drawRect(topLeftX, topLeftY, (bottomRightX - topLeftX), (bottomRightY - topLeftY));
				g.setColor(Color.WHITE);
				g.fillRect(10, 7, 600, 17);
				g.setColor(Color.BLACK);
				g.drawString("topLeftX: " + topLeftX + ", topLeftY: " + topLeftY + ".   bottomRightX: " + bottomRightX + ", bottomRightY: " + bottomRightY, 20, 20);
			}
	}
	void Handle(RoomHandler checkRoom, InventoryHandler checkInv, StaticItemHandler staticItems)
	{	
			if (walkingDirection == up || walkingDirection == down)
			{			
				topLeftX = x + 10;
				topLeftY = y + 135;
				bottomRightX = topLeftX + 50;
				bottomRightY = topLeftY + 20;	
			}		
			if (walkingDirection == left || walkingDirection == right)
			{			
				topLeftX = x;
				topLeftY = y + 135;
				bottomRightX = topLeftX + 70;
				bottomRightY = topLeftY + 20;	
			}		
			if (walking == true)
			{
				switch(walkingDirection)
				{
					case up:
					
							if (checkRoom.canMove(this, up) == true && checkInv.canMove(this, checkRoom.currentRoom, up) == true &&
							    staticItems.canMove(this, checkRoom.currentRoom, up) == true)
							{
								y -= (walkSpeed - 1);
							}
							else
							{
								walking = false;
							}
						break;
					case down:
					
							if (checkRoom.canMove(this, down) == true && checkInv.canMove(this, checkRoom.currentRoom, down) == true&&
							    staticItems.canMove(this, checkRoom.currentRoom, down) == true)
							{					
								y += (walkSpeed - 1);
							}
							else
							{
								walking = false;
							}
						break;
					case left:
					
							if (checkRoom.canMove(this, left) == true && checkInv.canMove(this, checkRoom.currentRoom, left) == true&&
							    staticItems.canMove(this, checkRoom.currentRoom, left) == true)
							{						
								x -= walkSpeed;
							}
							else
							{
								walking = false;
							}
						break;
					case right:
					
							if (checkRoom.canMove(this, right) == true && checkInv.canMove(this, checkRoom.currentRoom, right) == true&&
							    staticItems.canMove(this, checkRoom.currentRoom, right) == true)
							{						
								x += walkSpeed;						
							}
							else
							{
								walking = false;
							}
						break;
				}
			}
			if (walking == false)
			{
				playerAnim.pause();
			}
		playerAnim.Handle();
	}
	void Walk(int direction)
	{
		if (walkingDirection == direction)
		{
			if (walking == false)
			{
				walking = true;
				walkingDirection = direction;
			}
			else
			{
				walking = false;
				playerAnim.pause();
			}
		}
		else
		{
			walking = true;
			walkingDirection = direction;
		}
		if (walking == true)
		{
			switch(direction)
			{
				case up:
					playerAnim.switchAnimation(26, 31);								
					break;
				case down:
					playerAnim.switchAnimation(20, 25);						
					break;
				case left:
					playerAnim.switchAnimation(10, 19);													
					break;
				case right:
					playerAnim.switchAnimation(0, 9);						
					break;
			}
		}		
	}
}