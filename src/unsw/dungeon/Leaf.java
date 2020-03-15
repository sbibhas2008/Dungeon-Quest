package unsw.dungeon;

import java.util.List;

public class Leaf implements Component{
	
	String name;
	
	public Leaf(String name) {
		this.name = name;
	}

	@Override
	public boolean add(Component child) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Component child) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public List<Component> get_children() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_name() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
