package logic;

public class Options {
	private String option_category;
	private String specify_option;
	private double price;
	private int itemID;
	
	public Options(String option_category, String specify_option, double price, int itemID) {
		this.option_category = option_category;
		this.specify_option = specify_option;
		this.price = price;
		this.itemID = itemID;
	}
	
	public String getOption_category() {
		return option_category;
	}
	public void setOption_category(String option_category) {
		this.option_category = option_category;
	}
	public String getSpecify_option() {
		return specify_option;
	}
	public void setSpecify_option(String specify_option) {
		this.specify_option = specify_option;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
}
