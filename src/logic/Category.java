package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {
	private String category;
	private List<String> subCategory;
	
	public Category(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<String> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String[] subCategory) {
		this.subCategory = Arrays.asList(subCategory);
	}
	public void addSubCategory(String subCategory){ this.subCategory.add(subCategory);}
	
}
