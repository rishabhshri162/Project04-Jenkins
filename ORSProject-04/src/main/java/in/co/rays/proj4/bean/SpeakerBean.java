package in.co.rays.proj4.bean;

public class SpeakerBean extends BaseBean {

    private Long speakerId;
    private String speakerName;
    private String topic;
    private String organization;

    public Long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String getKey() {
        return String.valueOf(speakerId);
    }

    @Override
    public String getValue() {
        return speakerName + " - " + topic;
    }
}