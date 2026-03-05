package in.co.rays.proj4.bean;

public class SubscriptionPlanBean extends BaseBean {

    private Long planId;
    private String planName;
    private Double price;
    private Integer validityDays;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
    }

    @Override
    public String getKey() {
        return String.valueOf(planId);
    }

    @Override
    public String getValue() {
        return planName + " - " + price;
    }
}