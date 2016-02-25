package  
{
	/**
	 * ...
	 * @author ...
	 */
	import net.flashpunk.Entity;
	import net.flashpunk.Sfx;
	import net.flashpunk.utils.Input;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Key;
	import net.flashpunk.FP;
	import net.flashpunk.graphics.Spritemap;
	
	public class Player extends Entity
	{
		public const WHIP_RANGE:Number = 600;
		
		/**
		 * Speed of player in the x direction.
		 */
		public var xVelocity:Number = 0;		
		/**
		 * Speed of player in the y direction.
		 */
		public var yVelocity:Number = 0;		
		/**
		 * Change of speed of player in the x direction.
		 */
		public var xAccel:Number = 0;		
		/**
		 * Change of speed of player in the y direction.
		 */
		public var yAccel:Number = 0;		
		/**
		 * Friction applied to x movement.
		 */
		public var xFriction:Number = 0.95;
		/**
		 * Friction applied to y movement.
		 */
		public var yFriction:Number=0.99;
		
		private var power:Number = 0.2;
		
		private var gravity:Number = 0.3;	
		
		[Embed(source = "/../assets/mongoose run.png")] private const PLAYER:Class;
		[Embed(source = "../assets/mongoose_alone run.PNG")] private const PLAYER_NO_SNAKE:Class;
		public var spr:Spritemap = new Spritemap(PLAYER, 100, 114);
		public var spr_no_snake:Spritemap = new Spritemap(PLAYER_NO_SNAKE, 100, 114);
		

		
		public function Player() 
		{
			spr.add("run", [ 0, 1, 2, 3 ], 60, true);
			spr_no_snake.add("run", [ 0, 1, 2, 3 ], 60, true);
			this.graphic = spr;
			spr.play("run");
			spr_no_snake.play("run");
			
			setHitbox(80, 114);
			
		}
				
		override public function update():void 
		{			
			xVelocity += xAccel;
			xVelocity += power;
			
			
			yVelocity += yAccel;
			yVelocity += gravity;
			
			
			xVelocity *= xFriction;
			yVelocity *= yFriction;
			
			
			/*
			 * Collisions
			 */
			var obsCollision:Obstacle = collide("obstacle", x - width + 20, y) as Obstacle;
			if (obsCollision) 
			{	
				if ( obsCollision is Trap ) {
					var t:Trap = obsCollision as Trap;
					t.closeTrap(false);
				}
				xVelocity = 0;
			}			
			//  Fatal collisions set the player to the beginning of the level
			var fatalCollision:Enemy = collide("fatal", x, y) as Enemy;
			if (fatalCollision) 
			{
				FP.world = new LoseWorld();
			}
			
			adjustXPosition();
			adjustYPosition();

		}
				
		private function adjustXPosition():void 
		{
			for (var i:int=0; i<Math.abs(xVelocity); i++) {
				if (! collide("block",x+FP.sign(xVelocity),y)) {
					x += FP.sign(xVelocity);
					FP.camera.x += FP.sign(xVelocity);
				} else {
					xVelocity=0;
					break;
				}
			}
		}
		
		private function adjustYPosition():void 
		{
			for (var i:int = 0; i < Math.abs(yVelocity); i++) 
			{
				if (! collide("block", x, y + FP.sign(yVelocity))) 
				{
					y += FP.sign(yVelocity);					
				} else 
				{
					yVelocity=0;
					break;
				}
			}
		}
		

		
		
	}

}