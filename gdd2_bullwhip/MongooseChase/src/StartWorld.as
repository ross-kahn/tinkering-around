package  
{
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.World;
	import net.flashpunk.FP;
	import net.flashpunk.utils.Input;
	/**
	 * ...
	 * @author Taylor Nycum
	 */
	public class StartWorld extends World
	{
		[Embed(source="../assets/start screen.PNG")] private const START_WORLD:Class;
		
		public function StartWorld() 
		{
			var i:Image = new Image(START_WORLD);
			addGraphic(i);
		}
		
		public override function update():void
		{
			if (Input.mousePressed)
			{
				FP.world = new MenuWorld();	
			}			
		}
		
	}

}