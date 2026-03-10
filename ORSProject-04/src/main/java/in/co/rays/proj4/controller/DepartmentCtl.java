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
import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DepartmentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DepartmentCtl", urlPatterns = { "/ctl/DepartmentCtl" })
public class DepartmentCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");

		request.setAttribute("statusMap", statusMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("departmentCode"))) {
			request.setAttribute("departmentCode", "Department Code is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("departmentName"))) {
			request.setAttribute("departmentName", "Department Name is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("departmentHead"))) {
			request.setAttribute("departmentHead", "Department Head is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("departmentStatus"))) {
			request.setAttribute("departmentStatus", "Department Status is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		DepartmentBean bean = new DepartmentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDepartmentCode(DataUtility.getString(request.getParameter("departmentCode")));
		bean.setDepartmentName(DataUtility.getString(request.getParameter("departmentName")));
		bean.setDepartmentHead(DataUtility.getString(request.getParameter("departmentHead")));
		bean.setDepartmentStatus(DataUtility.getString(request.getParameter("departmentStatus")));

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
		DepartmentModel model = new DepartmentModel();

		if (id > 0) {
			try {
				DepartmentBean bean = model.findByPk(id);
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
		DepartmentModel model = new DepartmentModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			DepartmentBean bean = (DepartmentBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Department Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Department Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			DepartmentBean bean = (DepartmentBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Department Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Department Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DEPARTMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.DEPARTMENT_VIEW;
	}
}