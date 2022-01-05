package logic;

public class Employee {
	private String businessName;
	private boolean isApproved;
	private String hrName;
	private String hrUserName;
	private String branchManagerUserName;

	public Employee(String businessName, boolean isApproved, String hrName, String hrUserName,
			String branchManagerUserName) {
		this.businessName = businessName;
		this.isApproved = isApproved;
		this.hrName = hrName;
		this.hrUserName = hrUserName;
		this.branchManagerUserName = branchManagerUserName;
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

	public String getBranchManagerUserName() {
		return branchManagerUserName;
	}

	public void setBranchManagerUserName(String branchManagerUserName) {
		this.branchManagerUserName = branchManagerUserName;
	}

}
