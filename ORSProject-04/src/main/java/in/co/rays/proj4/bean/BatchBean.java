package in.co.rays.proj4.bean;

public class BatchBean extends BaseBean {

	private String batchCode;
	private String batchName;
	private String trainerName;
	private String batchTiming;

	// Getter and Setter

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getBatchTiming() {
		return batchTiming;
	}

	public void setBatchTiming(String batchTiming) {
		this.batchTiming = batchTiming;
	}

	// For dropdown display

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return batchName;
	}
}