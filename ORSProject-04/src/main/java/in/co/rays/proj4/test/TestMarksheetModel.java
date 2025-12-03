package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;

public class TestMarksheetModel {
	public static void main(String[] args) throws DatabaseException {

//		MarksheetModel model = new MarksheetModel();
//		System.out.println(model.nextPk());
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByRollNo();
//		testSearch();
//		testDelete();

	}

	public static void testAdd() {

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo("123456");
		bean.setStudentId(1);
		bean.setPhysics(50);
		bean.setChemistry(50);
		bean.setMaths(60);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		MarksheetModel model = new MarksheetModel();

		try {
			model.add(bean);
			System.out.println("Marksheet Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		MarksheetBean bean = new MarksheetBean();
		bean.setId(1);
		bean.setRollNo("113456");
		bean.setStudentId(1);
		bean.setPhysics(50);
		bean.setChemistry(50);
		bean.setMaths(60);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		MarksheetModel model = new MarksheetModel();
		try {
			model.update(bean);
			System.out.println("Marksheet update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {

		MarksheetModel model = new MarksheetModel();
		try {
			MarksheetBean bean = model.findByPk(2);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	
	public static void testFindByRollNo() {

		MarksheetModel model = new MarksheetModel();
		try {
			MarksheetBean bean = model.findByRollNo("113456");

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		try {
			MarksheetModel model = new MarksheetModel();
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setName("Rahul");;
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		MarksheetBean bean = new MarksheetBean();
		bean.setId(2);

		MarksheetModel model = new MarksheetModel();

		try {
			model.delete(bean);
			System.out.println("marksheet deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
