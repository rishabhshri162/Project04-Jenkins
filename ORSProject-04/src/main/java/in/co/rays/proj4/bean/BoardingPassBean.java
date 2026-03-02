package in.co.rays.proj4.bean;

import java.util.Date;

public class BoardingPassBean extends BaseBean {

    private Long boardingPassId;
    private String seatNumber;
    private String gate;
    private Date boardingTime;

    public Long getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(Long boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public Date getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Date boardingTime) {
        this.boardingTime = boardingTime;
    }

    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return seatNumber + " - " + gate;
    }
}