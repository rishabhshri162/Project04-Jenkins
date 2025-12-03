package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {

	public static void main(String[] args) throws DatabaseException, ParseException {

//		TimetableModel model =  new TimetableModel();
//		System.out.println(model.nextPk());
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testcheckByCourseName();
//		testcheckBySubjectName();
//		testcheckBySemester();
////		testcheckByExamTime();
//		testSearch();
		testDelete();
	}

	public static void testAdd() {

		TimetableBean bean = new TimetableBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setSemester("First");
		bean.setDescription("Data Science");
		try {
			bean.setExamDate(sdf.parse("2025-10-15"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		bean.setExamTime("10 am");
		bean.setCourseId(1);
		bean.setSubjectId(1);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		TimetableModel model = new TimetableModel();

		try {
			model.add(bean);
			System.out.println("Timetable Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		TimetableBean bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(2);
		bean.setSemester("First");
		bean.setDescription("Data Science");
		try {
			bean.setExamDate(sdf.parse("2025-10-15"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		bean.setExamTime("10 am");
		bean.setCourseId(1);
		bean.setSubjectId(2);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		TimetableModel model = new TimetableModel();
		try {
			model.update(bean);
			System.out.println("Timetable update successfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		TimetableModel model = new TimetableModel();
		try {
			TimetableBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testcheckByCourseName() throws ParseException {

		TimetableModel model = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse("2025-10-15");
			TimetableBean bean = model.checkByCourseName(1L, date);

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println("ID: " + bean.getId());
			System.out.println("Semester: " + bean.getSemester());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Exam Date: " + bean.getExamDate());
			System.out.println("Course Name: " + bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testcheckBySubjectName() throws ParseException {

		TimetableModel model = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse("2025-10-10");
			TimetableBean bean = model.checkBySubjectName(1L, 2L, date);

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println("ID: " + bean.getId());
			System.out.println("Semester: " + bean.getSemester());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Exam Date: " + bean.getExamDate());
			System.out.println("Course Name: " + bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testcheckBySemester() throws ParseException {

		TimetableModel model = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse("2025-10-10");
			TimetableBean bean = model.checkBySemester(1L, 2L, "First", date);

			if (bean == null) {
				System.out.println("Test Find by semester fail");

			}
			System.out.println("ID: " + bean.getId());
			System.out.println("Semester: " + bean.getSemester());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Exam Date: " + bean.getExamDate());
			System.out.println("Course Name: " + bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testcheckByExamTime() throws ParseException {

		TimetableModel model = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse("2025-10-15");
			TimetableBean bean = model.checkByExamTime(1L, 1L, "First", date, "10 am", "Data Science");

			if (bean == null) {
				System.out.println("Test Find by rollNo fail");

			}
			System.out.println("ID: " + bean.getId());
			System.out.println("Semester: " + bean.getSemester());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Exam Date: " + bean.getExamDate());
			System.out.println("Course Name: " + bean.getCourseName());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			TimetableModel model = new TimetableModel();
			TimetableBean bean = new TimetableBean();
			List list = new ArrayList();
			bean.setSubjectId(1L);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimetableBean) it.next();
				System.out.println(bean.getExamTime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		TimetableBean bean = new TimetableBean();
		bean.setId(3);

		TimetableModel model = new TimetableModel();

		try {
			model.delete(bean);
			System.out.println("Timetable deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
