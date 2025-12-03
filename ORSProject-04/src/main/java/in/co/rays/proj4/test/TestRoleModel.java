package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	public static void main(String[] args) {

//		RoleModel model = new RoleModel();
//		System.out.println(model.nextPk());
		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
		testSearch();

	}

	public static void testAdd() {

		RoleBean bean = new RoleBean();
		bean.setName("kiosk");
		bean.setDescription("kiosk");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		RoleModel model = new RoleModel();

		try {
			model.add(bean);
			System.out.println("Role Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		RoleBean bean = new RoleBean();
		bean.setId(1);

		RoleModel model = new RoleModel();

		try {
			model.delete(bean);
			System.out.println("Role deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		RoleBean bean = new RoleBean();
		bean.setName("Admin");
		bean.setDescription("Admin");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(2);

		RoleModel model = new RoleModel();
		try {
			model.update(bean);
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
		System.out.println("Record update successfully");
	}

	public static void testFindByPk() {

		RoleModel model = new RoleModel();
		try {
			RoleBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByName() {

		RoleModel model = new RoleModel();
		try {
			RoleBean bean = model.findByName("account");

			if (bean == null) {
				System.out.println("Test Find by name fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			RoleModel model = new RoleModel();
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("developer");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
