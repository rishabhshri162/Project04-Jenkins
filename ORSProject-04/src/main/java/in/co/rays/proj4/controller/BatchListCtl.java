package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BatchBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.BatchModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BatchListCtl", urlPatterns = { "/ctl/BatchListCtl" })
public class BatchListCtl extends BaseCtl {

	// ================= PRELOAD =================
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> timingMap = new HashMap<>();
		timingMap.put("Morning", "Morning");
		timingMap.put("Afternoon", "Afternoon");
		timingMap.put("Evening", "Evening");

		request.setAttribute("timingMap", timingMap);
	}

	// ================= POPULATE =================
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BatchBean bean = new BatchBean();

		bean.setBatchCode(DataUtility.getString(request.getParameter("batchCode")));

		bean.setBatchName(DataUtility.getString(request.getParameter("batchName")));

		bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));

		bean.setBatchTiming(DataUtility.getString(request.getParameter("batchTiming")));

		return bean;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		BatchBean bean = (BatchBean) populateBean(request);
		BatchModel model = new BatchModel();

		try {

			List<BatchBean> list = model.search(bean, pageNo, pageSize);

			List<BatchBean> next = model.search(bean, pageNo + 1, pageSize);

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

		BatchBean bean = (BatchBean) populateBean(request);

		BatchModel model = new BatchModel();

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

				ServletUtility.redirect(ORSView.BATCH_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					BatchBean deleteBean = new BatchBean();

					for (String id : ids) {
						deleteBean.setId(DataUtility.getLong(id));
						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Batch deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BATCH_LIST_CTL, request, response);
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
		return ORSView.BATCH_LIST_VIEW;
	}
}