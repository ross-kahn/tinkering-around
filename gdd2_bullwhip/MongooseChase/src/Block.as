package  
{
	/**
	 * ...
	 * @author ...
	 */
	import net.flashpunk.Entity;
	import net.flashpunk.graphics.Graphiclist;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.FP;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	public class Block extends Entity
	{
		[Embed(source = 'assets/block.png')]
		private const BLOCK:Class;
		public function Block(posX:int,posY:int) 
		{
			graphic = new Image(BLOCK);
			setHitbox(32, 32);
		  	type = "block";
			x = posX * 32;
			y = posY * 32;
		}
		
	}

}