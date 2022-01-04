package logic;

import java.util.Arrays;

import common.MyPhoto;

public class Item {
	private String category;
	private String subcategory;
	private int itemID;
	private int restaurantID;
	private String name;
	private float price;
	private String description;
	private String ingrediants;
	private Options[] options;
	private String photo;
	private int amount;
	private MyPhoto itemImage;
	
	public MyPhoto getItemImage() {
		return itemImage;
	}

	public void setItemImage(MyPhoto itemImage) {
		this.itemImage = itemImage;
	}

	public Item(String category, String subcategory, int itemID, int restaurantID, String name, float price,
			String description, String ingrediants, Options[] options, String photo, int amount) {
		this.category = category;
		this.subcategory = subcategory;
		this.itemID = itemID;
		this.restaurantID = restaurantID;
		this.name = name;
		this.price = price;
		this.description = description;
		this.ingrediants = ingrediants;
		this.options = options;
		this.photo = photo;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Item [category=" + category + ", subcategory=" + subcategory + ", itemID=" + itemID + ", restaurantID="
				+ restaurantID + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", ingrediants=" + ingrediants + ", options=" + Arrays.toString(options) + ", photo=" + photo
				+ ", amount=" + amount + "]";
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIngrediants() {
		return ingrediants;
	}
	public void setIngrediants(String ingrediants) {
		this.ingrediants = ingrediants;
	}
	public Options[] getOptions() {
		return options;
	}
	public void setOptions(Options[] options) {
		this.options = options;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}


//private void setTempDatabase() {
//
//		Options op0[] = { new Options("Size", "Regular", 0, 0, true), new Options("Size", "Big", 15, 0, true),
//				new Options("Select", "Not tomatoes", 0, 0, false), new Options("Select", "No onions", 0, 0, false) };
//
//		Options op1[] = { new Options("Cook Size", "Medium", 0, 1, true),
//				new Options("Cook Size", "Medium Well", 0, 1, true),
//				new Options("Cook Size", "Well Done", 0, 1, true) };
////========Day menu =======//
//		Item item0 = new Item("Italiano", null, 0, 0, "Regular Pizza", 40,
//				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
//				"regular-pizza.jpg", 5);
//		Item item2 = new Item("Italiano", null, 2, 0, "Margherita Pizza", 47,
//				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
//				"margherita-pizza.jpg", 5);
//		Item item3 = new Item("Italiano", null, 3, 0, "Special Pizza", 52,
//				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
//				"agg-pizza.jpeg", 5);
//		Item item6 = new Item("Salads", null, 6, 0, "Greek Salad", 52,
//				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
//				"GreekSalad.jpg", 5);
////		Item item3 = new Item("Italiano", null, 3, 0, "Special Pizza", 52,
////				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
////				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\agg-pizza.jpeg",
////				5);
//		allItems.put(item0.getItemID(), item0);
//		allItems.put(item2.getItemID(), item2);
//		allItems.put(item3.getItemID(), item3);
//		allItems.put(item6.getItemID(), item6);
//
//		item_in_menu[] item_in_Daymenu = new item_in_menu[4];
//		item_in_Daymenu[0] = new item_in_menu(6, 0, "Day", "First Course");
//		item_in_Daymenu[1] = new item_in_menu(0, 0, "Day", "Second Course");
//		item_in_Daymenu[2] = new item_in_menu(2, 0, "Day", "Second Course");
//		item_in_Daymenu[3] = new item_in_menu(3, 0, "Day", "Second Course");
//
////		item_in_menu item_in_Daymenu= new item_in_menu(1, 0, "Day", "Second Course");
//
//		// ========Night menu =======//
//		Item item1 = new Item("Steaks", null, 1, 0, "Entrecote", 85, "A classic 300 gram slice of entrecote", null, op1,
//				"meat-bar.jpg", 3);
//		Item item4 = new Item("Steaks", null, 4, 0, "Hamburger", 60, "A classic 300 gram slice of entrecote", null, op1,
//				"hamburger.jpg", 3);
//		Item item5 = new Item("Steaks", null, 5, 0, "Fries", 15, "A classic 300 gram slice of entrecote", null, op1,
//				"fries.jpg", 3);
//		allItems.put(item1.getItemID(), item1);
//		allItems.put(item4.getItemID(), item4);
//		allItems.put(item5.getItemID(), item5);
//		item_in_menu[] item_in_Nightmenu = new item_in_menu[3];
//		item_in_Nightmenu[0] = new item_in_menu(1, 0, "Night", "Second Course");
//		item_in_Nightmenu[1] = new item_in_menu(4, 0, "Night", "Second Course");
//		item_in_Nightmenu[2] = new item_in_menu(0, 0, "Night", "Second Course");
//
//		// items_in_menuArr.add(item_in_menu0);
//		// items_in_menuArr.add(item_in_menu1);
//
//		Menu dayMenu = new Menu("Day", 0, item_in_Daymenu);
//		Menu nightMenu = new Menu("Night", 0, item_in_Nightmenu);
//		menusArr = new ArrayList<Menu>();
//		menusArr.add(dayMenu);
//		menusArr.add(nightMenu);
//	}







