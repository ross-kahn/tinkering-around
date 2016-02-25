package  
{
	import net.flashpunk.Entity;
	import net.flashpunk.graphics.Graphiclist;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.FP;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	
	/**
	 * ...
	 * @author People
	 */
	public class ThingyEntity extends Entity
	{
		[Embed(source="../assets/character.png")] private const THINGY:Class;
		public var spr:Spritemap = new Spritemap(THINGY, 32, 32);
		
		public function ThingyEntity() 
		{			
			spr.add("Happy", [ 0 ], 0, true);
			spr.add("Sad", [ 1 ], 0, true);
			this.graphic = spr;
		}
		
		override public function update():void 
		{
			super.update();
			
			x += 1 * FP.elapsed;
			
			if (Input.pressed(Key.SPACE))
			{
				if (spr.currentAnim == "Happy")
					spr.play("Sad");					
				else
					spr.play("Happy");
			}
			
		}
		
	}

}