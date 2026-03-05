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
import in.co.rays.proj4.bean.SubscriptionPlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubscriptionPlanModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SubscriptionPlanCtl", urlPatterns = { "/ctl/SubscriptionPlanCtl" })
public class SubscriptionPlanCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> daysMap = new HashMap<>();

		daysMap.put("90", "90");
		daysMap.put("45", "45");
		daysMap.put("30", "30");

		request.setAttribute("daysMap", daysMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("planName"))) {
			request.setAttribute("planName", "Plan Name is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", "Price is required");
			pass = false;
		}

		if (!DataValidator.isNull(request.getParameter("price"))
				&& !DataValidator.isDouble(request.getParameter("price"))) {
			request.setAttribute("price", "Price must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("validityDays"))) {
			request.setAttribute("validityDays", "Validity Days is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubscriptionPlanBean bean = new SubscriptionPlanBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPlanName(DataUtility.getString(request.getParameter("planName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setValidityDays(DataUtility.getInt(request.getParameter("validityDays")));
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
		SubscriptionPlanModel model = new SubscriptionPlanModel();

		if (id > 0) {

			try {

				SubscriptionPlanBean bean = model.findByPk(id);
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
		SubscriptionPlanModel model = new SubscriptionPlanModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subscription Plan Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Plan Name already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SubscriptionPlanBean bean = (SubscriptionPlanBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Subscription Plan Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Plan Name already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBSCRIPTION_PLAN_VIEW;
	}
}