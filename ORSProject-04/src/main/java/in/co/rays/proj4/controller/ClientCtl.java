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
import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ClientModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ClientCtl", urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> priorityMap = new HashMap<>();

        priorityMap.put("High", "High");
        priorityMap.put("Medium", "Medium");
        priorityMap.put("Low", "Low");

        request.setAttribute("priorityMap", priorityMap);
    }

    // ================= VALIDATE =================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("clientName"))) {
            request.setAttribute("clientName", "Client Name is required");
            pass = false;

        } else if (!DataValidator.isName(request.getParameter("clientName"))) {
            request.setAttribute("clientName", "Only alphabetical characters allowed");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", "Email is required");
            pass = false;

        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email", "Invalid Email format");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("phone"))) {
            request.setAttribute("phone", "Phone number is required");
            pass = false;

        } else if (!DataValidator.isPhoneNo(request.getParameter("phone"))) {
            request.setAttribute("phone", "Invalid Phone number");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("priority"))) {
            request.setAttribute("priority", "Priority is required");
            pass = false;
        }

        return pass;
    }

    // ================= POPULATE =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ClientBean bean = new ClientBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setClientName(DataUtility.getString(request.getParameter("clientName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setPhone(DataUtility.getString(request.getParameter("phone")));
        bean.setPriority(DataUtility.getString(request.getParameter("priority")));

        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
        bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
        bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

        populateDTO(bean, request);

        return bean;
    }

    // ================= DO GET =================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        ClientModel model = new ClientModel();

        if (id > 0) {
            try {
                ClientBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ================= DO POST =================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        ClientModel model = new ClientModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ClientBean bean = (ClientBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setSuccessMessage("Client Added Successfully", request);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ClientBean bean = (ClientBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setSuccessMessage("Client Updated Successfully", request);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.CLIENT_VIEW;
    }
}
