package  
{
	import flash.display.Shape;
	import net.flashpunk.Entity;
	import net.flashpunk.utils.Input;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.FP;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.Sfx;
	
	/**
	 * ...
	 * @author ...
	 */
	public class Trap extends Entity implements Obstacle 
	{
		[Embed(source="/../assets/trap frames.png")] private const TRAP:Class;
		public var spr:Spritemap = new Spritemap(TRAP, 100, 115);
		
		[Embed(source = "../assets/sound/trap.mp3")]private const CLOSE:Class;
		private var closeSound:Sfx = new Sfx(CLOSE);
		
		public var toRemove:Boolean = false;
		private var player:Player;
		
		/**
		 * Constructs a trap at a given position.
		 * @param	x The x-coord of the top-left pixel.
		 * @param	y The y-coord of the top-left pixel.
		 */
		public function Trap(posX:Number, posY:Number, player:Player) 
		{
			spr.add("idle", [ 0 ], 0, true);
			spr.add("hit", [ 1, 2, 3], 20, false);
			spr.add("killed", [ 1, 2, 3, 4], 20, false);
			spr.add("dead", [4], 0, true);
			spr.scale = MyWorld.difficulty * 0.5;
			this.graphic = spr;
			spr.play("idle");
			spr.y -= 16;
			this.player = player;
			
			setHitbox(80 * spr.scale, 100 * spr.scale, x, y);			
			type = "obstacle";
			x = posX;
			y = posY;
		}
		
		/**
		 * Removes this instance from the world.
		 */
		public function destroy():void 
		{
			toRemove = true;
		}
					
		private function inRange():Boolean
		{
			return (FP.distance(x, y, player.x, player.y) <= player.WHIP_RANGE);
		}
		
		/**
		 * Called every frame. Handles animation and mouse click events
		 */
		override public function update():void 
		{	
			if (collidePoint(x, y, FP.world.mouseX, FP.world.mouseY) &&
				inRange()) 
			{
				// If obstacle is clicked remove it from the world
				if (Input.mousePressed && this.collidable == true) {
					if ( spr.frame == 3 ) {
						spr.play("dead");
					} else {
						closeTrap(true);
					}
					this.collidable = false;
				}
			}
		}
		
		
		public function closeTrap(kill:Boolean):void
		{
			if (kill)			
				spr.play("killed", false, 0);
			else
				spr.play("hit", false, 0);
				
			closeSound.play();			
		}
	}
		

}