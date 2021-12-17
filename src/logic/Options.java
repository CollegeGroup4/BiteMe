package logic;

public class Options {
	private String option_category;
	private String specify_option;
	private int itemID;
	public Options(String option_category, String specify_option, int itemID) {
		super();
		this.option_category = option_category;
		this.specify_option = specify_option;
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
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	
	
}
