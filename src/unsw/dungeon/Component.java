package unsw.dungeon;

import java.util.List;

public interface Component {
	public boolean add(Component child);
	public boolean remove(Component child);
	public List<Component> get_children();
	public String get_name();
}
