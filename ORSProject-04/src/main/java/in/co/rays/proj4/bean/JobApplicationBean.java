package in.co.rays.proj4.bean;

import java.util.Date;

public class JobApplicationBean extends BaseBean {

	private String applicantName;
	private String companyName;
	private String position;
	private Date applicationDate;

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return applicantName + " - " + companyName;
	}
}