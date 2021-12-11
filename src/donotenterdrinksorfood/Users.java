package donotenterdrinksorfood;

public class Users {
	private String username, id, name;
	private int role;

	public Users(String name, String id, String username, int role) {
		this.name = name;
		this.username = username;
		this.id = id;
		this.role=role;
	}

	public String getId() {
		return id;
	}
	public String getName() {
		System.out.println("name: "+name);
		return name;
	}

	public String getUsername() {
		return username;
	}
	public int getRole() {
		return role;
	}
}
