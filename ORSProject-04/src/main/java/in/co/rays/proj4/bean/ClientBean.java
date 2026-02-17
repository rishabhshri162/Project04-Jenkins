package in.co.rays.proj4.bean;

public class ClientBean extends BaseBean {

	private String clientName;
	private String email;
	private String phone;
	private String priority;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return clientName + " - " + email;
	}
}
