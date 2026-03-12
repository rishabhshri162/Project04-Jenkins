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
import in.co.rays.proj4.bean.PetAdoptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.PetAdoptionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PetAdoptionCtl", urlPatterns = { "/ctl/PetAdoptionCtl" })
public class PetAdoptionCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("Available", "Available");
        statusMap.put("Adopted", "Adopted");
        statusMap.put("Pending", "Pending");

        request.setAttribute("statusMap", statusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("petName"))) {
            request.setAttribute("petName", "Pet Name is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("animalType"))) {
            request.setAttribute("animalType", "Animal Type is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("age"))) {
            request.setAttribute("age", "Age is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("adoptionStatus"))) {
            request.setAttribute("adoptionStatus", "Adoption Status is required");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        PetAdoptionBean bean = new PetAdoptionBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setPetName(DataUtility.getString(request.getParameter("petName")));
        bean.setAnimalType(DataUtility.getString(request.getParameter("animalType")));
        bean.setAge(DataUtility.getInt(request.getParameter("age")));
        bean.setAdoptionStatus(DataUtility.getString(request.getParameter("adoptionStatus")));

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
        PetAdoptionModel model = new PetAdoptionModel();

        if (id > 0) {

            try {

                PetAdoptionBean bean = model.findByPk(id);
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

        PetAdoptionModel model = new PetAdoptionModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            PetAdoptionBean bean = (PetAdoptionBean) populateBean(request);

            try {

                model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Pet Added Successfully", request);

            } catch (Exception e) {

                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            PetAdoptionBean bean = (PetAdoptionBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setSuccessMessage("Pet Updated Successfully", request);

            } catch (Exception e) {

                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PET_ADOPTION_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PET_ADOPTION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PET_ADOPTION_VIEW;
    }
}