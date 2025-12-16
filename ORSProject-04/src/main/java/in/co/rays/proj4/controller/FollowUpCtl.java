package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.FollowUpBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.FollowUpModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * FollowUpCtl handles add, update, delete operations for FollowUp.
 *
 * author  Rishabh Shrivastava
 * version 1.0
 */
@WebServlet(name = "FollowUpCtl", urlPatterns = { "/ctl/FollowUpCtl" })
public class FollowUpCtl extends BaseCtl {

    /**
     * Validates input data.
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("patientId"))) {
            request.setAttribute("patientId",
                    PropertyReader.getValue("error.require", "Patient"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("doctorId"))) {
            request.setAttribute("doctorId",
                    PropertyReader.getValue("error.require", "Doctor"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("visitDate"))) {
            request.setAttribute("visitDate",
                    PropertyReader.getValue("error.require", "Visit Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("fees"))) {
            request.setAttribute("fees",
                    PropertyReader.getValue("error.require", "Fees"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates FollowUpBean from request.
     */
    @Override
    protected FollowUpBean populateBean(HttpServletRequest request) {

        FollowUpBean bean = new FollowUpBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setPatientId(DataUtility.getLong(request.getParameter("patientId")));
        bean.setPatientName(request.getParameter("patientName"));
        bean.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
        bean.setDoctorName(request.getParameter("doctorName"));
        bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
        bean.setFees(DataUtility.getLong(request.getParameter("fees")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        FollowUpModel model = new FollowUpModel();

        if (id > 0) {
            try {
                FollowUpBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
//                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        FollowUpModel model = new FollowUpModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            FollowUpBean bean = populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setSuccessMessage(
                            "FollowUp successfully updated", request);
                } else {
                    model.add(bean);
                    ServletUtility.setSuccessMessage(
                            "FollowUp successfully added", request);
                }
                ServletUtility.forward(getView(), request, response);

            } catch (Exception e) {
//                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        if (OP_CANCEL.equalsIgnoreCase(op)) {
//            ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
            return;
        }

        if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
            return;
        }
    }

    /**
     * Returns view page.
     */
    @Override
    protected String getView() {
        return ORSView.FOLLOWUP_VIEW;
    }
}
