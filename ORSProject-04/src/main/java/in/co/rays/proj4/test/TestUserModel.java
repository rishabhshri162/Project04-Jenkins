package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {
	public static void main(String[] args) throws DatabaseException, ParseException {

            testAdd();	
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByLogin();
//		testSearch();
//		testAuthenticate();

	}

	public static void testAdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		UserBean bean = new UserBean();

		bean.setFirstName("Rishabh");
		bean.setLastName("Shrivastava");
		bean.setLogin("rs@gmail.com");
		bean.setPassword("123");
		try {
			bean.setDob(sdf.parse("2002-06-01"));
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		bean.setMobileNo("7894561237");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		UserModel model = new UserModel();
		try {
			model.add(bean);
			System.out.println("User Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		UserBean bean = new UserBean();
		bean.setId(2);

		UserModel model = new UserModel();

		try {
			model.delete(bean);
			System.out.println("user deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		UserBean bean = new UserBean();
		bean.setId(1);
		bean.setFirstName("Ram");
		bean.setLastName("Shrivastava");
		bean.setLogin("hs@gmail.com");
		bean.setPassword("123");
		try {
			bean.setDob(sdf.parse("2002-06-01"));
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		bean.setMobileNo("7894561237");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		UserModel model = new UserModel();
		try {
			model.update(bean);
			System.out.println("Record update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		UserModel model = new UserModel();
		try {
			UserBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getFirstName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByLogin() {

		UserModel model = new UserModel();
		try {
			UserBean bean = model.findByLogin("rs@gmail.com");

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
			UserModel model = new UserModel();
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("Rishabh");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getLogin());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getGender());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testAuthenticate() {

		UserModel model = new UserModel();

		UserBean bean;
		try {
			bean = model.authenticate("rs@gmail.com", "123");
			if (bean != null) {
				System.out.println("User found:");
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
			} else {
				System.out.println("user not found");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

}
