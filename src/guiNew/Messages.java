package guiNew;


import logic.Employer;

public class Messages {
	private String from;
	private String topic;
	private String status;
	private Employer employee;

	public Messages(String from, String status, Employer employee) {
		super();
		this.from = from;
		this.status = status;
		topic = "Registration of the employer as licensed";
		this.employee = employee;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employer getEmployee() {
		return employee;
	}

	public void setEmployee(Employer employee) {
		this.employee = employee;
	}

}
