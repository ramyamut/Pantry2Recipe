import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Recipe {
	private List<String> ingred;
	private String link = "";
	
	public Recipe(List<String> i, String l) {
		this.ingred = i;
		this.link = l;
	}
	
	//calculate how many ingredients the pantry has
	public double[] ingredMatch(List<String> pantry) {
		double[] stats = new double[2];
		double match = 0; //num of matching ingredients
		
		Iterator<String> it = ingred.iterator();
		while (it.hasNext()) {
			if (pantry.contains(it.next())){
				match++;
			}
		}
		
		double frac = match/(ingred.size()); 
		
		stats[0] = match;//number of pantry items used
		stats[1] = frac; //fraction of ingredients in pantry
		
		return stats;
	}
	
	public List<String> getIngredients() {
		return ingred;
	}
	
	public String getLink() {
		return link;
	}
}
