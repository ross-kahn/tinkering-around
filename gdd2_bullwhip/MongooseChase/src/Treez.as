package  
{
	import net.flashpunk.Entity;
	import net.flashpunk.graphics.Image;
	
	/**
	 * ...
	 * @author ...
	 */
	public class Treez extends Entity
	{
		[Embed(source = "../assets/treez.gif")] private const TREEZ:Class;
		
		
		public function Treez() 
		{
			var image:Image = new Image(TREEZ);
			image.scaleY = 3.5;
			graphic = image;
		}
		
	}

}