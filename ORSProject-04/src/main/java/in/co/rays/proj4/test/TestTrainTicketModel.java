package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TrainTicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TrainTicketModel;

public class TestTrainTicketModel {

	public static void main(String[] args) throws DatabaseException {

//		testAdd();
//      testDelete();
//      testUpdate();
//      testFindByPk();
//      testSearch();
		testFindByPassengerName();

	}

	public static void testAdd() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TrainTicketBean bean = new TrainTicketBean();

		bean.setPassengerName("Rishabh Shrivastava");
		bean.setTrainNumber("12951");
		bean.setTrainName("Mumbai Rajdhani");
		bean.setSourceStation("Delhi");
		bean.setDestinationStation("Mumbai");
		bean.setJourneyDate("2026-10-15");
		bean.setSeatNumber("B1-45");
		bean.setTicketClass("AC 2 Tier");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		TrainTicketModel model = new TrainTicketModel();

		try {
			model.add(bean);
			System.out.println("Train Ticket Added Successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		TrainTicketBean bean = new TrainTicketBean();
		bean.setId(1);

		TrainTicketModel model = new TrainTicketModel();

		try {
			model.delete(bean);
			System.out.println("Train Ticket Deleted Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TrainTicketBean bean = new TrainTicketBean();
		bean.setId(1);

		bean.setPassengerName("Updated Name");
		bean.setTrainNumber("12345");
		bean.setTrainName("Updated Express");
		bean.setSourceStation("Agra");
		bean.setDestinationStation("Delhi");
		bean.setJourneyDate("2026-10-15");
		bean.setSeatNumber("S2-20");
		bean.setTicketClass("Sleeper");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		TrainTicketModel model = new TrainTicketModel();

		try {
			model.update(bean);
			System.out.println("Train Ticket Updated Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		TrainTicketModel model = new TrainTicketModel();

		try {
			TrainTicketBean bean = model.findByPk(1);

			if (bean == null) {
				System.out.println("Record Not Found");
			} else {
				System.out.println("Passenger Name: " + bean.getPassengerName());
				System.out.println("Train Name: " + bean.getTrainName());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {

			TrainTicketModel model = new TrainTicketModel();
			TrainTicketBean bean = new TrainTicketBean();

			List list = new ArrayList();

			bean.setPassengerName("Rishabh");

			list = model.search(bean, 0, 0);

			if (list.size() <= 0) {
				System.out.println("No Records Found");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TrainTicketBean) it.next();

				System.out.println("ID: " + bean.getId());
				System.out.println("Passenger: " + bean.getPassengerName());
				System.out.println("Train: " + bean.getTrainName());
				System.out.println("Seat: " + bean.getSeatNumber());
				System.out.println("---------------------------");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testFindByPassengerName() {

	    TrainTicketModel model = new TrainTicketModel();

	    try {

	        TrainTicketBean bean =
	                model.findByPassengerName("Updated Name");

	        if (bean != null) {
	            System.out.println("Record Found:");
	            System.out.println(bean.getTrainName());
	            System.out.println(bean.getSeatNumber());
	        } else {
	            System.out.println("Record Not Found");
	        }

	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
	}

}
