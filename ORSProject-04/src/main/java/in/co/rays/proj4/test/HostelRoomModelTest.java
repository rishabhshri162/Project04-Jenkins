package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.HostelRoomBean;
import in.co.rays.proj4.model.HostelRoomModel;

/**
 * Test class for HostelRoomModel.
 * Used to test CRUD and search operations.
 * 
 * Run this as Java Application.
 * 
 * @author
 *         Rishabh Shrivastava
 */
public class HostelRoomModelTest {

    public static HostelRoomModel model = new HostelRoomModel();

    public static void main(String[] args) throws Exception {

        // Uncomment jo test chahiye
//        testAdd();
//         testUpdate();
        // testDelete();
//         testFindByPk();
        // testFindByRoomNumber();
        // testSearch();
        // testList();
    }

    /**
     * Test Add
     */
    public static void testAdd() throws Exception {

        HostelRoomBean bean = new HostelRoomBean();
        bean.setId(1);
        bean.setRoomNumber("A-101");
        bean.setRoomType("Double");
        bean.setCapacity(2);
        bean.setRent(6500);
        bean.setStatus("Available");

        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
        bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));

        long pk = model.add(bean);
        System.out.println("Hostel Room Added Successfully, PK = " + pk);
    }

    /**
     * Test Update
     */
    public static void testUpdate() throws Exception {

        HostelRoomBean bean = model.findByPk(1);

        if (bean != null) {
            bean.setRoomType("Single");
            bean.setCapacity(1);
            bean.setRent(8000);
            bean.setStatus("Occupied");
            bean.setModifiedBy("admin");
            bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

            model.update(bean);
            System.out.println("Hostel Room Updated Successfully");
        } else {
            System.out.println("Record not found");
        }
    }

    /**
     * Test Delete
     */
    public static void testDelete() throws Exception {

        HostelRoomBean bean = new HostelRoomBean();
        bean.setId(2);

        model.delete(bean);
        System.out.println("Hostel Room Deleted Successfully");
    }

    /**
     * Test Find By PK
     */
    public static void testFindByPk() throws Exception {

        HostelRoomBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println(bean.getId());
            System.out.println(bean.getRoomNumber());
            System.out.println(bean.getRoomType());
            System.out.println(bean.getCapacity());
            System.out.println(bean.getRent());
            System.out.println(bean.getStatus());
        } else {
            System.out.println("Record not found");
        }
    }

    /**
     * Test Find By Room Number
     */
    public static void testFindByRoomNumber() throws Exception {

        HostelRoomBean bean = model.findByRoomNumber("A-101");

        if (bean != null) {
            System.out.println(bean.getId());
            System.out.println(bean.getRoomNumber());
            System.out.println(bean.getRoomType());
            System.out.println(bean.getCapacity());
            System.out.println(bean.getRent());
            System.out.println(bean.getStatus());
        } else {
            System.out.println("Room not found");
        }
    }

    /**
     * Test Search
     */
    public static void testSearch() throws Exception {

        HostelRoomBean bean = new HostelRoomBean();
        bean.setStatus("Available");

        List<HostelRoomBean> list = model.search(bean, 1, 10);

        for (HostelRoomBean b : list) {
            System.out.print(b.getId());
            System.out.print("\t" + b.getRoomNumber());
            System.out.print("\t" + b.getRoomType());
            System.out.print("\t" + b.getCapacity());
            System.out.print("\t" + b.getRent());
            System.out.println("\t" + b.getStatus());
        }
    }

    /**
     * Test List
     */
    public static void testList() throws Exception {

        List<HostelRoomBean> list = model.list();

        for (HostelRoomBean b : list) {
            System.out.print(b.getId());
            System.out.print("\t" + b.getRoomNumber());
            System.out.print("\t" + b.getRoomType());
            System.out.print("\t" + b.getCapacity());
            System.out.print("\t" + b.getRent());
            System.out.println("\t" + b.getStatus());
        }
    }
}
