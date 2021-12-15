package logic;

import java.util.ArrayList;

public class Category {
	private String category;
	private ArrayList<String> subCategory;
	
	public Category(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList<String> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(ArrayList<String> subCategory) {
		this.subCategory = subCategory;
	}
}
