package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.LanguageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.LanguageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "LanguageListCtl", urlPatterns = { "/ctl/LanguageListCtl" })
public class LanguageListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("Active", "Active");
        statusMap.put("Inactive", "Inactive");

        request.setAttribute("statusMap", statusMap);
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        LanguageBean bean = new LanguageBean();

        bean.setLanguageCode(
                DataUtility.getString(request.getParameter("languageCode")));

        bean.setLanguageName(
                DataUtility.getString(request.getParameter("languageName")));

        bean.setLanguageStatus(
                DataUtility.getString(request.getParameter("languageStatus")));

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(
                PropertyReader.getValue("page.size"));

        LanguageBean bean = (LanguageBean) populateBean(request);
        LanguageModel model = new LanguageModel();

        try {

            List<LanguageBean> list =
                    model.search(bean, pageNo, pageSize);

            List<LanguageBean> next =
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

        LanguageBean bean =
                (LanguageBean) populateBean(request);

        LanguageModel model = new LanguageModel();

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
                        ORSView.LANGUAGE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    LanguageBean deleteBean = new LanguageBean();

                    for (String id : ids) {
                        deleteBean.setId(
                                DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Language deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.LANGUAGE_LIST_CTL, request, response);
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
        return ORSView.LANGUAGE_LIST_VIEW;
    }
}