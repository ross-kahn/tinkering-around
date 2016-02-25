package  
{
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
	public class Enemy extends Entity 
	{
		protected var speed:Number = -1;
		protected var reversalCooldown:Number = 0;
		protected var player:Player;
		public var toRemove:Boolean = false;
		[Embed(source="/../assets/hunter run.png")] private const ENEMY:Class;
		public var spr:Spritemap = new Spritemap(ENEMY, 100, 115);
		
		[Embed(source = "../assets/sound/scream.mp3")]private const SCREAM:Class;
		private var screamSound:Sfx = new Sfx(SCREAM);
		
		/**
		 * Constructs an enemy at a given position.
		 * @param	x The x-coord of the top-left pixel.
		 * @param	y The y-coord of the top-left pixel.
		 */
		public function Enemy(posX:Number, posY:Number, player:Player) 
		{
			spr.add("run", [ 0, 1, 2, 3 ], 20, true);
			this.graphic = spr;
			spr.play("run");
			spr.scale = 0.5*MyWorld.difficulty;
			
			setHitbox(100 * spr.scaleX, 115 * spr.scaleY);
			type = "fatal";
			x = posX;
			y = posY;
						
			this.player = player;
		}
		
		/**
		 * Removes this instance from the world.
		 */
		public function destroy():void 
		{
			screamSound.play();
			toRemove = true;
		}
				
		protected function inRange():Boolean
		{
			return (FP.distance(x, y, player.x, player.y) <= player.WHIP_RANGE);
		}
		
		/**
		 * Called every frame. Handles animation and mouse click events
		 */
		override public function update():void 
		{	
			reversalCooldown += FP.elapsed;
			if (collidePoint(x, y, FP.world.mouseX, FP.world.mouseY) &&
				inRange())
			{
				// If obstacle is clicked remove it from the world
				if (Input.mousePressed)
				{
					this.destroy();
				}
			}
			
			// Enemies walk back and forth, reversing direction randomly
			if (Math.random() > 0.98 && reversalCooldown > 1) 
			{
				speed *= -1;
				reversalCooldown = 0;
			} 
			
			( speed > 0 )  ? spr.flipped = false : spr.flipped = true;			
			x += speed;
		}
	}

}