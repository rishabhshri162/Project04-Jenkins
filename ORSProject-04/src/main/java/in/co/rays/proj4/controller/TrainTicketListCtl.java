package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrainTicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TrainTicketModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TrainTicketListCtl", urlPatterns = { "/ctl/TrainTicketListCtl" })
public class TrainTicketListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		// Ticket Class Static List
		HashMap<String, String> classMap = new HashMap<String, String>();

		classMap.put("Sleeper", "Sleeper");
		classMap.put("AC 3 Tier", "AC 3 Tier");
		classMap.put("AC 2 Tier", "AC 2 Tier");
		classMap.put("AC 1 Tier", "AC 1 Tier");
		classMap.put("General", "General");

		request.setAttribute("classMap", classMap);

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TrainTicketBean bean = new TrainTicketBean();

		bean.setPassengerName(DataUtility.getString(request.getParameter("passengerName")));
		bean.setTrainNumber(DataUtility.getString(request.getParameter("trainNumber")));
		bean.setSourceStation(DataUtility.getString(request.getParameter("sourceStation")));
		bean.setDestinationStation(DataUtility.getString(request.getParameter("destinationStation")));
		bean.setTicketClass(DataUtility.getString(request.getParameter("ticketClass")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TrainTicketBean bean = (TrainTicketBean) populateBean(request);
		TrainTicketModel model = new TrainTicketModel();

		try {

			List<TrainTicketBean> list = model.search(bean, pageNo, pageSize);
			List<TrainTicketBean> next = model.search(bean, pageNo + 1, pageSize);

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TrainTicketBean bean = (TrainTicketBean) populateBean(request);
		TrainTicketModel model = new TrainTicketModel();

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
				ServletUtility.redirect(ORSView.TRAIN_TICKET_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					TrainTicketBean deleteBean = new TrainTicketBean();

					for (String id : ids) {
						deleteBean.setId(DataUtility.getLong(id));
						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Ticket deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TRAIN_TICKET_LIST_CTL, request, response);
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
		return ORSView.TRAIN_TICKET_LIST_VIEW;
	}
}
