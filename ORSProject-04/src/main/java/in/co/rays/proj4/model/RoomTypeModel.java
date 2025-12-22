package in.co.rays.proj4.model;

import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RoomTypeBean;

public class RoomTypeModel {

    public List<RoomTypeBean> list() {
        List<RoomTypeBean> list = new ArrayList<>();

        RoomTypeBean b1 = new RoomTypeBean();
        b1.setName("Single");
        list.add(b1);

        RoomTypeBean b2 = new RoomTypeBean();
        b2.setName("Double");
        list.add(b2);

        RoomTypeBean b3 = new RoomTypeBean();
        b3.setName("Triple");
        list.add(b3);

        return list;
    }
}
