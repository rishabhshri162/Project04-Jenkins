package in.co.rays.proj4.bean;

import java.util.Date;

public class HospitalAppointmentBean extends BaseBean {

	private String patientName;
	private String doctorName;
	private Date appointmentDate;
	private String appointmentTime;
	private String symptoms;
	private String consultationFee;
	private String status;


	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(String consultationFee) {
		this.consultationFee = consultationFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// For dropdown display

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return patientName;
	}
}