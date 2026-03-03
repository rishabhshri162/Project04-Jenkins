package in.co.rays.proj4.bean;

public class ClaimBean extends BaseBean {

    private Long claimId;
    private String claimNumber;
    private Double claimAmount;
    private String status;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getKey() {
        return String.valueOf(getClaimId());
    }

    @Override
    public String getValue() {
        return claimNumber + " - " + claimAmount;
    }
}