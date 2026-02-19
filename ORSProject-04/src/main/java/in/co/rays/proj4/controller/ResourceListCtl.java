package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ResourceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ResourceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ResourceListCtl", urlPatterns = { "/ctl/ResourceListCtl" })
public class ResourceListCtl extends BaseCtl {

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("Active", "Active");
        statusMap.put("Inactive", "Inactive");
        statusMap.put("Blocked", "Blocked");

        request.setAttribute("statusMap", statusMap);
    }

    // ================= POPULATE =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ResourceBean bean = new ResourceBean();

        bean.setResourceCode(
                DataUtility.getString(request.getParameter("resourceCode")));

        bean.setResourceName(
                DataUtility.getString(request.getParameter("resourceName")));

        bean.setResourceStatus(
                DataUtility.getString(request.getParameter("resourceStatus")));

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

        ResourceBean bean = (ResourceBean) populateBean(request);
        ResourceModel model = new ResourceModel();

        try {

            List<ResourceBean> list =
                    model.search(bean, pageNo, pageSize);

            List<ResourceBean> next =
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

        ResourceBean bean =
                (ResourceBean) populateBean(request);

        ResourceModel model = new ResourceModel();

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
                        ORSView.RESOURCE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    ResourceBean deleteBean = new ResourceBean();

                    for (String id : ids) {
                        deleteBean.setId(
                                DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Resource deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.RESOURCE_LIST_CTL, request, response);
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
        return ORSView.RESOURCE_LIST_VIEW;
    }
}
