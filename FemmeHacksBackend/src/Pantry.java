import java.util.List;

public class Pantry {
	private List<String> inventory;
	
	public Pantry(List<String> f) {
		inventory = f;
	}
	
	public List<String> getInventory() {
		return inventory;
	}
	
	public void removeGood(String s) {
		inventory.remove(s);
	}
	
	public void addGood(String s) {
		inventory.add(s);
	}
}
