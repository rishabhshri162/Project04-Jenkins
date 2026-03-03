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
import in.co.rays.proj4.bean.ClaimBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ClaimModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ClaimCtl", urlPatterns = { "/ctl/ClaimCtl" })
public class ClaimCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("Pending", "Pending");
        statusMap.put("Approved", "Approved");
        statusMap.put("Rejected", "Rejected");

        request.setAttribute("statusMap", statusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("claimNumber"))) {
            request.setAttribute("claimNumber", "Claim Number is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("claimAmount"))) {
            request.setAttribute("claimAmount", "Claim Amount is required");
            pass = false;
        }

        if (!DataValidator.isNull(request.getParameter("claimAmount"))
                && !DataValidator.isDouble(request.getParameter("claimAmount"))) {
            request.setAttribute("claimAmount", "Claim Amount must be numeric");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status", "Status is required");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ClaimBean bean = new ClaimBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setClaimNumber(DataUtility.getString(request.getParameter("claimNumber")));
        bean.setClaimAmount(DataUtility.getDouble(request.getParameter("claimAmount")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
        bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
        bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        ClaimModel model = new ClaimModel();

        if (id > 0) {
            try {
                ClaimBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        ClaimModel model = new ClaimModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ClaimBean bean = (ClaimBean) populateBean(request);

            try {

                model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Claim Added Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Claim Number already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ClaimBean bean = (ClaimBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setSuccessMessage("Claim Updated Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setErrorMessage("Claim Number already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CLAIM_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CLAIM_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.CLAIM_VIEW;
    }
}