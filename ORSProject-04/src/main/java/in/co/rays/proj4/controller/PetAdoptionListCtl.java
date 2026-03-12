package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PetAdoptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.PetAdoptionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PetAdoptionListCtl", urlPatterns = { "/ctl/PetAdoptionListCtl" })
public class PetAdoptionListCtl extends BaseCtl {

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("Available", "Available");
        statusMap.put("Adopted", "Adopted");
        statusMap.put("Pending", "Pending");

        request.setAttribute("statusMap", statusMap);
    }

    // ================= POPULATE =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        PetAdoptionBean bean = new PetAdoptionBean();

        bean.setPetName(
                DataUtility.getString(request.getParameter("petName")));

        bean.setAnimalType(
                DataUtility.getString(request.getParameter("animalType")));

        bean.setAdoptionStatus(
                DataUtility.getString(request.getParameter("adoptionStatus")));

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

        PetAdoptionBean bean = (PetAdoptionBean) populateBean(request);
        PetAdoptionModel model = new PetAdoptionModel();

        try {

            List<PetAdoptionBean> list =
                    model.search(bean, pageNo, pageSize);

            List<PetAdoptionBean> next =
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

        PetAdoptionBean bean =
                (PetAdoptionBean) populateBean(request);

        PetAdoptionModel model = new PetAdoptionModel();

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
                        ORSView.PET_ADOPTION_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    PetAdoptionBean deleteBean = new PetAdoptionBean();

                    for (String id : ids) {
                        deleteBean.setId(
                                DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Pet record deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.PET_ADOPTION_LIST_CTL, request, response);
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
        return ORSView.PET_ADOPTION_LIST_VIEW;
    }
}