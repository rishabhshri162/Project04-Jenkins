package in.co.rays.proj4.bean;

import java.util.Date;

public class FollowUpBean extends BaseBean {

    private Long patientId;     
    private String patientName; 
    private Long doctorId;      
    private String doctorName;  
    private Date visitDate;
    private long fees;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public long getFees() {
        return fees;
    }

    public void setFees(long l) {
        this.fees = l;
    }

    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return patientName + " - " + doctorName;
    }

    @Override
    public String toString() {
        return "FollowUpBean [patientId=" + patientId + ", patientName=" + patientName
                + ", doctorId=" + doctorId + ", doctorName=" + doctorName
                + ", visitDate=" + visitDate + ", fees=" + fees + "]";
    }
}
