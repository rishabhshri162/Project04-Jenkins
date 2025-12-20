package in.co.rays.proj4.bean;

/**
 * HostelRoomBean represents a hostel room entity.
 * It stores room-related information like room number,
 * type, capacity, rent, and availability status.
 * 
 * @author
 *         Rishabh Shrivastava
 * @version 1.0
 */
public class HostelRoomBean extends BaseBean {

    /** Room Number */
    private String roomNumber;

    /** Room Type (Single, Double, Triple) */
    private String roomType;

    /** Maximum capacity of the room */
    private int capacity;

    /** Monthly rent of the room */
    private int rent;

    /** Room status (Available / Occupied) */
    private String status;

    /** Returns room number */
    public String getRoomNumber() {
        return roomNumber;
    }

    /** Sets room number */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /** Returns room type */
    public String getRoomType() {
        return roomType;
    }

    /** Sets room type */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /** Returns room capacity */
    public int getCapacity() {
        return capacity;
    }

    /** Sets room capacity */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** Returns room rent */
    public double getRent() {
        return rent;
    }

    /** Sets room rent */
    public void setRent(int rent) {
        this.rent = rent;
    }

    /** Returns room status */
    public String getStatus() {
        return status;
    }

    /** Sets room status */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Dropdown display value
     */
    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return roomNumber;
    }
}
