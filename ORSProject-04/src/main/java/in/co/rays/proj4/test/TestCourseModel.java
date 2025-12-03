package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.MarksheetModel;

public class TestCourseModel {
	
	public static void main(String[] args) throws DatabaseException {
		
//		CourseModel model = new CourseModel();
//		System.out.println(model.nextPk());
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByRollNo();
//		testSearch();
//		testDelete();
		
	}
	
	public static void testAdd() {

		CourseBean bean = new CourseBean();

		bean.setName("BCA");
		bean.setDuration("2 YEAR");
		bean.setDescription("BACHELORS OF COMPUTER APPLICATION");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		CourseModel model = new CourseModel();

		try {
			model.add(bean);
			System.out.println("Course Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testUpdate() {

		CourseBean bean = new CourseBean();
		bean.setId(1);
		bean.setName("MCA");
		bean.setDuration("2 YEAR");
		bean.setDescription("BACHELORS OF COMPUTER APPLICATION");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		CourseModel model = new CourseModel();
		try {
			model.update(bean);
			System.out.println("Course update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {

		CourseModel model = new CourseModel();
		try {
			CourseBean bean = model.findByPk(2);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByRollNo() {

		CourseModel model = new CourseModel();
		try {
			CourseBean bean = model.findByName("MCA");

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getDuration());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		try {
			CourseModel model = new CourseModel();
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			bean.setDuration("2 year");;
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		CourseBean bean = new CourseBean();
		bean.setId(2);

		CourseModel model = new CourseModel();

		try {
			model.delete(bean);
			System.out.println("Course deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
