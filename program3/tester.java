import java .awt.Color; 
import java.util.Arrays;
public class tester {

	public static void main(String[] args) 
	{
		String[] meep = {"puffin", "penguin","manatee", "seal lion"};
		Color[] quack = {Color.RED, Color.blue, Color.green,Color.PINK};
		GameBoard plop = new GameBoard(meep, quack);
		
		System.out.println(plop.toString());
		
		(plop.getTracks())[5].advance('b');
		System.out.println(plop.toString());
		
		(plop.getTracks())[5].advance('b');
		System.out.println(plop.toString());
		
		(plop.getTracks())[5].advance('b');
		System.out.println(plop.toString());
		
	}
}
