package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.JobApplicationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.JobApplicationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobApplicationListCtl", urlPatterns = { "/ctl/JobApplicationListCtl" })
public class JobApplicationListCtl extends BaseCtl {

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

	// ================= POPULATE =================
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		JobApplicationBean bean = new JobApplicationBean();

		bean.setApplicantName(DataUtility.getString(request.getParameter("applicantName")));

		bean.setCompanyName(DataUtility.getString(request.getParameter("companyName")));

		bean.setPosition(DataUtility.getString(request.getParameter("position")));

		return bean;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		JobApplicationBean bean = (JobApplicationBean) populateBean(request);

		JobApplicationModel model = new JobApplicationModel();

		try {

			List<JobApplicationBean> list = model.search(bean, pageNo, pageSize);

			List<JobApplicationBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= DO POST =================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		JobApplicationBean bean = (JobApplicationBean) populateBean(request);

		JobApplicationModel model = new JobApplicationModel();

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.JOB_APPLICATION_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					JobApplicationBean deleteBean = new JobApplicationBean();

					for (String id : ids) {

						deleteBean.setId(DataUtility.getLong(id));

						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Record deleted successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.JOB_APPLICATION_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		return ORSView.JOB_APPLICATION_LIST_VIEW;
	}
}