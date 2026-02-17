package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrainTicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TrainTicketModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TrainTicketCtl", urlPatterns = { "/ctl/TrainTicketCtl" })
public class TrainTicketCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> classMap = new HashMap<String, String>();

		classMap.put("Sleeper", "Sleeper");
		classMap.put("AC 3 Tier", "AC 3 Tier");
		classMap.put("AC 2 Tier", "AC 2 Tier");
		classMap.put("AC 1 Tier", "AC 1 Tier");
		classMap.put("General", "General");

		request.setAttribute("classMap", classMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("passengerName"))) {
			request.setAttribute("passengerName", "Passenger Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("passengerName"))) {

			request.setAttribute("passengerName", "Only in alphabetical");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainName"))) {
			request.setAttribute("trainName", "Train Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("trainName"))) {

			request.setAttribute("trainName", "Only in alphabetical");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainNumber"))) {
			request.setAttribute("trainNumber", "Train Number is required");
			pass = false;
			
		} else if (!DataValidator.isInteger(request.getParameter("trainNumber"))) {

			request.setAttribute("trainNumber", "please only in numeric");

			pass = false;

		} else if (!DataValidator.isTrainNumber(request.getParameter("trainNumber"))) {

			request.setAttribute("trainNumber", "Train number should be only 5 digit");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("sourceStation"))) {
			request.setAttribute("sourceStation", "Source Station is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("destinationStation"))) {
			request.setAttribute("destinationStation", "Destination Station is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("journeyDate"))) {
			request.setAttribute("journeyDate", "Journey Date is required");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("seatNumber"))) {
			request.setAttribute("seatNumber", "Seat Number is required");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("ticketClass"))) {
			request.setAttribute("ticketClass", "Ticket Class is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TrainTicketBean bean = new TrainTicketBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPassengerName(DataUtility.getString(request.getParameter("passengerName")));
		bean.setTrainNumber(DataUtility.getString(request.getParameter("trainNumber")));
		bean.setTrainName(DataUtility.getString(request.getParameter("trainName")));
		bean.setSourceStation(DataUtility.getString(request.getParameter("sourceStation")));
		bean.setDestinationStation(DataUtility.getString(request.getParameter("destinationStation")));
		bean.setJourneyDate(DataUtility.getString(request.getParameter("journeyDate")));
		bean.setSeatNumber(DataUtility.getString(request.getParameter("seatNumber")));
		bean.setTicketClass(DataUtility.getString(request.getParameter("ticketClass")));

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TrainTicketModel model = new TrainTicketModel();

		if (id > 0) {
			try {
				TrainTicketBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		TrainTicketModel model = new TrainTicketModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TrainTicketBean bean = (TrainTicketBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Ticket Added Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TrainTicketBean bean = (TrainTicketBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setSuccessMessage("Ticket Updated Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TRAIN_TICKET_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TRAIN_TICKET_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TRAIN_TICKET_VIEW;
	}
}
