package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestCollegeModel {

	public static void main(String[] args) throws DatabaseException {

//		CollegeModel model = new CollegeModel();
//		System.out.println(model.nextPk());
//
//	testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
		testSearch();

	}

	public static void testAdd() {

		CollegeBean bean = new CollegeBean();
		bean.setName("Amity university");
		bean.setAddress("noida");
		bean.setState("Uttar Pradesh");
		bean.setCity("noida");
		bean.setPhoneNo("789789789");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		CollegeModel model = new CollegeModel();
		try {
			model.add(bean);
			System.out.println("College Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		CollegeBean bean = new CollegeBean();
		bean.setId(2);

		CollegeModel model = new CollegeModel();

		try {
			model.delete(bean);
			System.out.println("College deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		CollegeBean bean = new CollegeBean();
		bean.setId(2);
		bean.setName("Amity university");
		bean.setAddress("noida");
		bean.setState("Uttar Pradesh");
		bean.setCity("noida");
		bean.setPhoneNo("789789789");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		CollegeModel model = new CollegeModel();
		try {
			model.update(bean);
			System.out.println("College updated successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		CollegeModel model = new CollegeModel();
		try {
			CollegeBean bean = model.findByPk(2);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByName() {

		CollegeModel model = new CollegeModel();
		try {
			CollegeBean bean = model.findByName("Amity university");

			if (bean == null) {
				System.out.println("Test Find by name fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getCity());
			System.out.println(bean.getAddress());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			CollegeModel model = new CollegeModel();
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setCity("noida");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getState());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}


