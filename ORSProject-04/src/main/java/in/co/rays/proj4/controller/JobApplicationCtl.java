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
import in.co.rays.proj4.bean.JobApplicationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.JobApplicationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobApplicationCtl", urlPatterns = { "/ctl/JobApplicationCtl" })
public class JobApplicationCtl extends BaseCtl {

	// ================= PRELOAD =================
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> positionMap = new HashMap<>();

		positionMap.put("Developer", "Developer");
		positionMap.put("Tester", "Tester");
		positionMap.put("Manager", "Manager");
		positionMap.put("Intern", "Intern");

		request.setAttribute("positionMap", positionMap);
	}

	// ================= VALIDATE =================
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("applicantName"))) {
			request.setAttribute("applicantName", "Applicant Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("applicantName"))) {
			request.setAttribute("applicantName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("companyName"))) {
			request.setAttribute("companyName", "Company Name is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("position"))) {
			request.setAttribute("position", "Position is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("applicationDate"))) {
			request.setAttribute("applicationDate", "Application Date is required");
			pass = false;
		}

		return pass;
	}

	// ================= POPULATE =================
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		JobApplicationBean bean = new JobApplicationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setApplicantName(DataUtility.getString(request.getParameter("applicantName")));
		bean.setCompanyName(DataUtility.getString(request.getParameter("companyName")));
		bean.setPosition(DataUtility.getString(request.getParameter("position")));
		bean.setApplicationDate(DataUtility.getDate(request.getParameter("applicationDate")));

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		populateDTO(bean, request);

		return bean;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		JobApplicationModel model = new JobApplicationModel();

		if (id > 0) {
			try {
				JobApplicationBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ================= DO POST =================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		JobApplicationModel model = new JobApplicationModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			JobApplicationBean bean = (JobApplicationBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Application Added Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			JobApplicationBean bean = (JobApplicationBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setSuccessMessage("Application Updated Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_APPLICATION_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_APPLICATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.JOB_APPLICATION_VIEW;
	}
}