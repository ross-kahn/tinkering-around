package  
{
	import net.flashpunk.graphics.Text;
	import net.flashpunk.World;
	import net.flashpunk.FP;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	/**
	 * ...
	 * @author Nathan Popham
	 */
	public class LoseWorld extends World
	{
		
		public function LoseWorld() 
		{
			addGraphic(new Text("You lose... :( Press R to play again.", FP.width / 2 - 150, FP.height / 2));
		}
		
		public override function update():void
		{
			if (Input.pressed(Key.R))
			{
				FP.world = new MenuWorld();				
			}
			
		}
		
	}

}