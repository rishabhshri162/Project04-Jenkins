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
import in.co.rays.proj4.bean.BatchBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BatchModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BatchCtl", urlPatterns = { "/ctl/BatchCtl" })
public class BatchCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {


		HashMap<String, String> timingMap = new HashMap<>();
		timingMap.put("Morning", "Morning");
		timingMap.put("Afternoon", "Afternoon");
		timingMap.put("Evening", "Evening");

		request.setAttribute("timingMap", timingMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("batchCode"))) {
			request.setAttribute("batchCode", "Batch Code is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batchName"))) {
			request.setAttribute("batchName", "Batch Name is required");
			pass = false;
			
		} else if (!DataValidator.isName(request.getParameter("batchName"))) {
			request.setAttribute("batchName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName", "Trainer Name is required");
			pass = false;
			
		} else if (!DataValidator.isName(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batchTiming"))) {
			request.setAttribute("batchTiming", "Batch Timing is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BatchBean bean = new BatchBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setBatchCode(DataUtility.getString(request.getParameter("batchCode")));
		bean.setBatchName(DataUtility.getString(request.getParameter("batchName")));
		bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
		bean.setBatchTiming(DataUtility.getString(request.getParameter("batchTiming")));

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
		BatchModel model = new BatchModel();

		if (id > 0) {
			try {
				BatchBean bean = model.findByPk(id);
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
		BatchModel model = new BatchModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			BatchBean bean = (BatchBean) populateBean(request);

			try {

				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Batch Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Batch Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			BatchBean bean = (BatchBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Batch Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Batch Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BATCH_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BATCH_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BATCH_VIEW;
	}
}