package in.co.rays.proj4.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.JDBCDataSource;

public class TestSubjectModel {
	public static void main(String[] args) {
		
//		SubjectModel model = new SubjectModel();
//		try {
//			System.out.println(model.nextPk());
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//		}
//		
//		
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
		testSearch();
		
		
	}
	
	public static void testAdd() {

		SubjectBean bean = new SubjectBean();

		bean.setName("Data Science");
		bean.setCourseId(1);
		bean.setDescription("Data Science");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		SubjectModel model = new SubjectModel();

		try {
			model.add(bean);
			System.out.println("Subject Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
	public static void testUpdate() {

		SubjectBean bean = new SubjectBean();
		
		bean.setId(2);
		bean.setName("Python");
		bean.setCourseId(1);
		bean.setDescription("Data Science");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		SubjectModel model = new SubjectModel();
		try {
			model.update(bean);
			System.out.println("Subject update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {

		SubjectModel model = new SubjectModel();
		try {
			SubjectBean bean = model.findByPk(2);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getName());
			System.out.println(bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByName() {

		SubjectModel model = new SubjectModel();
		try {
			SubjectBean bean = model.findByName("python");

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println(bean.getId());
		

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
	
	
	public static void testSearch() {
		try {
			SubjectModel model = new SubjectModel();
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			
			bean.setName("Web development");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getCourseName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
