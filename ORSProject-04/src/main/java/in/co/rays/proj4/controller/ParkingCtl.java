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
import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ParkingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ParkingCtl", urlPatterns = { "/ctl/ParkingCtl" })
public class ParkingCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> parkingStatusMap = new HashMap<>();

        parkingStatusMap.put("Available", "Available");
        parkingStatusMap.put("Occupied", "Occupied");
        parkingStatusMap.put("Reserved", "Reserved");
        parkingStatusMap.put("Maintenance", "Maintenance");

        request.setAttribute("parkingStatusMap", parkingStatusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("parkingCode"))) {
            request.setAttribute("parkingCode", "Parking Code is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("location"))) {
            request.setAttribute("location", "Location is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("slotNumber"))) {
            request.setAttribute("slotNumber", "Slot Number is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("parkingStatus"))) {
            request.setAttribute("parkingStatus", "Parking Status is required");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ParkingBean bean = new ParkingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setParkingCode(DataUtility.getString(request.getParameter("parkingCode")));
        bean.setLocation(DataUtility.getString(request.getParameter("location")));
        bean.setSlotNumber(DataUtility.getInt(request.getParameter("slotNumber")));
        bean.setParkingStatus(DataUtility.getString(request.getParameter("parkingStatus")));

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
        ParkingModel model = new ParkingModel();

        if (id > 0) {
            try {
                ParkingBean bean = model.findByPk(id);
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
        ParkingModel model = new ParkingModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ParkingBean bean = (ParkingBean) populateBean(request);

            try {

                model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Parking Added Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Parking Code already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ParkingBean bean = (ParkingBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setSuccessMessage("Parking Updated Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setErrorMessage("Parking Code already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PARKING_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PARKING_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PARKING_VIEW;
    }
}