package logic;

public class Employer {
	private String businessName;
	private boolean isApproved;
	private String hrName;
	private String hrUserName;
	private int branchManagerID;
	
	public Employer(String businessName, boolean isApproved, String hrName, String hrUserName, int branchManagerID) {
		this.businessName = businessName;
		this.isApproved = isApproved;
		this.hrName = hrName;
		this.hrUserName = hrUserName;
		this.branchManagerID = branchManagerID;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getHrName() {
		return hrName;
	}
	public void setHrName(String hrName) {
		this.hrName = hrName;
	}
	public String getHrUserName() {
		return hrUserName;
	}
	public void setHrUserName(String hrUserName) {
		this.hrUserName = hrUserName;
	}
	public int getBranchManagerID() {
		return branchManagerID;
	}
	public void setBranchManagerID(int branchManagerID) {
		this.branchManagerID = branchManagerID;
	}
}