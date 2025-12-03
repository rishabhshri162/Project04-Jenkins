package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.TicketModel;

public class TestTicketModel {
	
	
	
	
	public static void main(String[] args) throws Exception {
		TicketModel model = new TicketModel();
//		System.out.println(model.nextPk());
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
		testFindByUserId();
		
		
	}
	
	public static void testAdd() throws ParseException {

		TicketBean bean = new TicketBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setUserId("user@gmail.com");
		bean.setBookingDate(sdf.parse("2025-12-26"));;
		bean.setSeatNo("10");
		bean.setQuantity(2);
		bean.setAmount(1200);
		bean.setPaymentMode("Cash");
		bean.setBookingStatus("Confirm");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		TicketModel model = new TicketModel();

		try {
			model.add(bean);
			System.out.println("Booking Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		TicketBean bean = new TicketBean();
		bean.setId(2);

		TicketModel model = new TicketModel();

		try {
			model.delete(bean);
			System.out.println("Booking deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testUpdate() throws ParseException {

		TicketBean bean = new TicketBean();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(4);
		bean.setUserId("udit@gmail.com");
		bean.setBookingDate(sdf.parse("2025-12-26"));;
		bean.setSeatNo("04");
		bean.setQuantity(2);
		bean.setAmount(1200);
		bean.setPaymentMode("Cash");
		bean.setBookingStatus("Confirm");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		TicketModel model = new TicketModel();
		try {
			model.update(bean);
			System.out.println("Booking update successfully");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {

		TicketModel model = new TicketModel();
		try {
			TicketBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getUserId());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public static void testFindByUserId() {

		TicketModel model = new TicketModel();
		try {
			TicketBean bean = model.findByUserId("ajay@gmail.com");

			if (bean == null) {
				System.out.println("Test Find by pk fail");

			}
			System.out.println(bean.getBookingDate());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	

}
