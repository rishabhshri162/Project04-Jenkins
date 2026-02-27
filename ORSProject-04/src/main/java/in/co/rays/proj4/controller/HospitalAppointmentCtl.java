package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.HospitalAppointmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.HospitalAppointmentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "HospitalAppointmentCtl", urlPatterns = { "/ctl/HospitalAppointmentCtl" })
public class HospitalAppointmentCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Pending", "Pending");
		statusMap.put("Confirmed", "Confirmed");
		statusMap.put("Completed", "Completed");
		statusMap.put("Cancelled", "Cancelled");

		request.setAttribute("statusMap", statusMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patientName"))) {
			request.setAttribute("patientName", "Patient Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("patientName"))) {
			request.setAttribute("patientName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doctorName"))) {
			request.setAttribute("doctorName", "Doctor Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("doctorName"))) {
			request.setAttribute("doctorName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", "Appointment Date is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("appointmentTime"))) {
			request.setAttribute("appointmentTime", "Appointment Time is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("consultationFee"))) {
			request.setAttribute("consultationFee", "Consultation Fee is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", "Status is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		HospitalAppointmentBean bean = new HospitalAppointmentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));
		bean.setAppointmentDate(DataUtility.getDate(request.getParameter("appointmentDate")));
		bean.setAppointmentTime(DataUtility.getString(request.getParameter("appointmentTime")));
		bean.setSymptoms(DataUtility.getString(request.getParameter("symptoms")));
		bean.setConsultationFee(DataUtility.getString(request.getParameter("consultationFee")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		HospitalAppointmentModel model = new HospitalAppointmentModel();

		if (id > 0) {
			try {
				HospitalAppointmentBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		HospitalAppointmentModel model = new HospitalAppointmentModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			HospitalAppointmentBean bean = (HospitalAppointmentBean) populateBean(request);

			try {

				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Appointment Added Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			HospitalAppointmentBean bean = (HospitalAppointmentBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Appointment Updated Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSPITAL_APPOINTMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSPITAL_APPOINTMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.HOSPITAL_APPOINTMENT_VIEW;
	}
}