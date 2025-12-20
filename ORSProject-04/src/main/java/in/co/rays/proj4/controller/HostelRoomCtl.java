package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.HostelRoomBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.HostelRoomModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "HostelRoomCtl", urlPatterns = { "/ctl/HostelRoomCtl" })
public class HostelRoomCtl extends BaseCtl {

    /**
     * Preload Status dropdown
     */
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("Available", "Available");
        statusMap.put("Occupied", "Occupied");

        request.setAttribute("statusMap", statusMap);
    }

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("roomNumber"))) {
            request.setAttribute("roomNumber",
                    PropertyReader.getValue("error.require", "Room Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("roomType"))) {
            request.setAttribute("roomType",
                    PropertyReader.getValue("error.require", "Room Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("capacity"))) {
            request.setAttribute("capacity",
                    PropertyReader.getValue("error.require", "Capacity"));
            pass = false;
        } else if (!DataValidator.isInteger(request.getParameter("capacity"))) {
            request.setAttribute("capacity", "Capacity must be numeric");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("rent"))) {
            request.setAttribute("rent",
                    PropertyReader.getValue("error.require", "Rent"));
            pass = false;
        } else if (!DataValidator.isInteger(request.getParameter("rent"))) {
            request.setAttribute("rent", "Rent must be numeric");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        HostelRoomBean bean = new HostelRoomBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setRoomNumber(DataUtility.getString(request.getParameter("roomNumber")));
        bean.setRoomType(DataUtility.getString(request.getParameter("roomType")));
        bean.setCapacity(DataUtility.getInt(request.getParameter("capacity")));
        bean.setRent(DataUtility.getInt(request.getParameter("rent")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * GET
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        HostelRoomModel model = new HostelRoomModel();

        if (id > 0) {
            try {
                HostelRoomBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * POST
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        HostelRoomModel model = new HostelRoomModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            HostelRoomBean bean = (HostelRoomBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Hostel Room added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Room Number already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            HostelRoomBean bean = (HostelRoomBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Hostel Room updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Room Number already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
//            ServletUtility.redirect(ORSView.HOSTEL_ROOM_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
//            ServletUtility.redirect(ORSView.HOSTEL_ROOM_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.HOSTEL_ROOM_VIEW;
    }
}
