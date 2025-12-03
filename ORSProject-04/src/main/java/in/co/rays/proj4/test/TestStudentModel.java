package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.model.UserModel;

public class TestStudentModel {
	public static void main(String[] args) throws DatabaseException {
		
//	
//	StudentModel model = new StudentModel();
//	System.out.println(model.nextPk());
//		testAdd();
		testUpdate();
//		testFindByPk();
//		testFindByEmailId();
//		testSearch();
//		testDelete();
	
	}
	
	
	public static void testAdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		StudentBean bean = new StudentBean();
		bean.setFirstName("Narendra");
		bean.setLastName("Modi");
		try {
			bean.setDob(sdf.parse("1970-06-16"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bean.setGender("male");
		bean.setMobileNo("7897897897");
		bean.setEmail("nn@gmail.com");
		bean.setCollegeId(1);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		StudentModel model = new StudentModel();

		try {
			model.add(bean);
			System.out.println("Student Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	
	public static void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		StudentBean bean = new StudentBean();
		bean.setId(2);
		bean.setFirstName("Rahul");
		bean.setLastName("Modi");
		try {
			bean.setDob(sdf.parse("1970-06-16"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bean.setGender("male");
		bean.setMobileNo("7897897897");
		bean.setEmail("nm@gmail.com");
		bean.setCollegeId(1);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

	


		StudentModel model = new StudentModel();
		try {
			model.update(bean);
			System.out.println("Student update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {

		StudentModel model = new StudentModel();
		try {
			StudentBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getFirstName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByEmailId() {

		StudentModel model = new StudentModel();
		try {
			StudentBean bean = model.findByEmailId("nm@gmail.com");

			if (bean == null) {
				System.out.println("Test Find by name fail");

			}
			System.out.println(bean.getId());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		try {
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			bean.setFirstName("Narendra");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getGender());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		StudentBean bean = new StudentBean();
		bean.setId(2);

		StudentModel model = new StudentModel();

		try {
			model.delete(bean);
			System.out.println("user deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}



}
