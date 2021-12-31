package logic;

import java.util.Arrays;

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
	
//	@Override
//	public String toString() {
//		String temp = "";
//		StringBuilder invoice = new StringBuilder();
//		for (Options option : options) {
//			temp += option.getOption_category() +":"+option.getSpecify_option()+"\n\t\t    ";
//		}
//		invoice.append(name +price+"\t\t"+Integer.toString(amount)+"\t\t"+temp);
//		return invoice.toString();
//	}
	
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