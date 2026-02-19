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
import in.co.rays.proj4.bean.ResourceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ResourceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ResourceCtl", urlPatterns = { "/ctl/ResourceCtl" })
public class ResourceCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");
		statusMap.put("Blocked", "Blocked");

		request.setAttribute("statusMap", statusMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("resourceCode"))) {
			request.setAttribute("resourceCode", "Resource Code is required");
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("resourceCode"))) {
			request.setAttribute("resourceCode", "Only numeric characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("resourceName"))) {
			request.setAttribute("resourceName", "Resource Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("resourceName"))) {
			request.setAttribute("resourceName", "Only alphabitcal character allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("resourceType"))) {
			request.setAttribute("resourceType", "Resource Type is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("resourceStatus"))) {
			request.setAttribute("resourceStatus", "Status is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ResourceBean bean = new ResourceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setResourceCode(DataUtility.getString(request.getParameter("resourceCode")));
		bean.setResourceName(DataUtility.getString(request.getParameter("resourceName")));
		bean.setResourceType(DataUtility.getString(request.getParameter("resourceType")));
		bean.setResourceStatus(DataUtility.getString(request.getParameter("resourceStatus")));

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
		ResourceModel model = new ResourceModel();

		if (id > 0) {
			try {
				ResourceBean bean = model.findByPk(id);
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
		ResourceModel model = new ResourceModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ResourceBean bean = (ResourceBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Resource Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Resource Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			ResourceBean bean = (ResourceBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setSuccessMessage("Resource Updated Successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("Resource Code already exists", request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESOURCE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESOURCE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.RESOURCE_VIEW;
	}
}
