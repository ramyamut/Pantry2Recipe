import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Runner {
	public static void main(String[] args) {
		List<String> p = new LinkedList();
		p.add("milk");
		p.add("eggs");
		
		
		Pantry ellie = new Pantry(p);
		
		List<String> i = new LinkedList();
		i.add("eggs");
		
		Recipe scrambledEggs = new Recipe(i,"scramble");
		
		double[] stats = scrambledEggs.ingredMatch(ellie.getInventory());
		System.out.println(Arrays.toString(stats));
	}
}
