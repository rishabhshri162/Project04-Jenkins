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
import in.co.rays.proj4.bean.BoardingPassBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BoardingPassModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BoardingPassCtl", urlPatterns = { "/ctl/BoardingPassCtl" })
public class BoardingPassCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> gateMap = new HashMap<>();

        gateMap.put("A1", "A1");
        gateMap.put("A2", "A2");
        gateMap.put("B1", "B1");
        gateMap.put("B2", "B2");

        request.setAttribute("gateMap", gateMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("seatNumber"))) {
            request.setAttribute("seatNumber", "Seat Number is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("gate"))) {
            request.setAttribute("gate", "Gate is required");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("boardingTime"))) {
            request.setAttribute("boardingTime", "Boarding Time is required");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        BoardingPassBean bean = new BoardingPassBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSeatNumber(DataUtility.getString(request.getParameter("seatNumber")));
        bean.setGate(DataUtility.getString(request.getParameter("gate")));
        bean.setBoardingTime(DataUtility.getDate(request.getParameter("boardingTime")));

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
        BoardingPassModel model = new BoardingPassModel();

        if (id > 0) {
            try {
                BoardingPassBean bean = model.findByPk(id);
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
        BoardingPassModel model = new BoardingPassModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            BoardingPassBean bean = (BoardingPassBean) populateBean(request);

            try {

                model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Boarding Pass Added Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Seat Number already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            BoardingPassBean bean = (BoardingPassBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setSuccessMessage("Boarding Pass Updated Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setErrorMessage("Seat Number already exists", request);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.BOARDING_PASS_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.BOARDING_PASS_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.BOARDING_PASS_VIEW;
    }
}