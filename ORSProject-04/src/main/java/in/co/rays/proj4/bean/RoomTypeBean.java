package in.co.rays.proj4.bean;

public class RoomTypeBean extends BaseBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String getValue() {
        return name;
    }
}
