package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component{
	
	List<Component> children = new ArrayList<Component>();
	String name;
	
	public Composite(String name) {
		this.name = name;
	}

	@Override
	public boolean add(Component child) {
		// TODO Auto-generated method stub
		children.add(child);
		return true;	
	}

	@Override
	public boolean remove(Component child) {
		// TODO Auto-generated method stub
		children.remove(child);
		return false;
	}

	@Override
	public List<Component> get_children() {
		// TODO Auto-generated method stub
		return this.children;
	}

	@Override
	public String get_name() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
