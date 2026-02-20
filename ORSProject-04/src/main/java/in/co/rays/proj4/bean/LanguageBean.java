package in.co.rays.proj4.bean;

public class LanguageBean extends BaseBean {

	private String languageCode;
	private String languageName;
	private String direction;
	private String languageStatus;

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLanguageStatus() {
		return languageStatus;
	}

	public void setLanguageStatus(String languageStatus) {
		this.languageStatus = languageStatus;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return languageName + " (" + languageCode + ")";
	}
}