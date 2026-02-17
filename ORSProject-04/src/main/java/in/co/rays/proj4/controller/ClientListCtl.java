package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ClientModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ClientListCtl", urlPatterns = { "/ctl/ClientListCtl" })
public class ClientListCtl extends BaseCtl {

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("High", "High");
        statusMap.put("Medium", "Medium");
        statusMap.put("Low", "Low");

        request.setAttribute("statusMap", statusMap);
    }

    // ================= POPULATE =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ClientBean bean = new ClientBean();

        bean.setClientName(
                DataUtility.getString(request.getParameter("clientName")));

        bean.setEmail(
                DataUtility.getString(request.getParameter("email")));

        bean.setPriority(
                DataUtility.getString(request.getParameter("priority")));

        return bean;
    }

    // ================= DO GET =================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(
                PropertyReader.getValue("page.size"));

        ClientBean bean = (ClientBean) populateBean(request);
        ClientModel model = new ClientModel();

        try {

            List<ClientBean> list =
                    model.search(bean, pageNo, pageSize);

            List<ClientBean> next =
                    model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage(
                        "No record found", request);
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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo =
                DataUtility.getInt(request.getParameter("pageNo"));

        int pageSize =
                DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        ClientBean bean =
                (ClientBean) populateBean(request);

        ClientModel model = new ClientModel();

        String op =
                DataUtility.getString(request.getParameter("operation"));

        String[] ids =
                request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)
                    || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;

                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;

                } else if (OP_PREVIOUS.equalsIgnoreCase(op)
                        && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.CLIENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    ClientBean deleteBean = new ClientBean();

                    for (String id : ids) {
                        deleteBean.setId(
                                DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Client deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.CLIENT_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage(
                        "No record found", request);
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
        return ORSView.CLIENT_LIST_VIEW;
    }
}
