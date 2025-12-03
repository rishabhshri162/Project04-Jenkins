package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

/**
 * BaseCtl is the abstract parent controller for all controllers. It provides
 * common constants, validation hooks, preload support, DTO population logic,
 * and custom service handling.
 *
 * Author: Rishabh Shrivastava
 */
public abstract class BaseCtl extends HttpServlet {

	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_RESET = "Reset";
	public static final String OP_LOG_OUT = "Logout";

	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";

	/**
	 * Validation hook for child controllers. Override when custom validation is
	 * required.
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Preload hook for dropdowns and reference data. Child controllers override as
	 * needed.
	 */
	protected void preload(HttpServletRequest request) {
	}

	/**
	 * Populates the bean from request parameters. Must be implemented in child
	 * controllers.
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	/**
	 * Populates audit fields (createdBy, modifiedBy, timestamps) in the DTO based
	 * on request and session user.
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			createdBy = "root";
			modifiedBy = "root";
		} else {
			modifiedBy = userbean.getLogin();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	/**
	 * Enhanced service method that: - Calls preload() - Manages validation flow -
	 * Forwards back to view on validation failure
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {

			if (!validate(request)) {
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		super.service(request, response);
	}

	/**
	 * Returns the JSP view path. Must be implemented by every controller.
	 */
	protected abstract String getView();
}
