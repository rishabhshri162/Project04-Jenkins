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
import in.co.rays.proj4.bean.LanguageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.LanguageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "LanguageCtl", urlPatterns = { "/ctl/LanguageCtl" })
public class LanguageCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();
		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");

		HashMap<String, String> directionMap = new HashMap<>();
		directionMap.put("LTR", "LTR");
		directionMap.put("RTL", "RTL");

		request.setAttribute("statusMap", statusMap);
		request.setAttribute("directionMap", directionMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("languageCode"))) {
			request.setAttribute("languageCode", "Language Code is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("languageName"))) {
			request.setAttribute("languageName", "Language Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("languageName"))) {
			request.setAttribute("languageName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("direction"))) {
			request.setAttribute("direction", "Direction is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("languageStatus"))) {
			request.setAttribute("languageStatus", "Status is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		LanguageBean bean = new LanguageBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLanguageCode(DataUtility.getString(request.getParameter("languageCode")));
		bean.setLanguageName(DataUtility.getString(request.getParameter("languageName")));
		bean.setDirection(DataUtility.getString(request.getParameter("direction")));
		bean.setLanguageStatus(DataUtility.getString(request.getParameter("languageStatus")));

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
		LanguageModel model = new LanguageModel();

		if (id > 0) {
			try {
				LanguageBean bean = model.findByPk(id);
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
		LanguageModel model = new LanguageModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			LanguageBean bean = (LanguageBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Language Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Language Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			LanguageBean bean = (LanguageBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Language Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Language Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LANGUAGE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LANGUAGE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.LANGUAGE_VIEW;
	}
}