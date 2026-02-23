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
import in.co.rays.proj4.bean.VendorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.VendorModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VendorCtl", urlPatterns = { "/ctl/VendorCtl" })
public class VendorCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> serviceMap = new HashMap<>();

		serviceMap.put("Catering", "Catering");
		serviceMap.put("IT Services", "IT Services");
		serviceMap.put("Transport", "Transport");
		serviceMap.put("Security", "Security");

		request.setAttribute("serviceMap", serviceMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vendorCode"))) {
			request.setAttribute("vendorCode", "Vendor Code is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("vendorName"))) {
			request.setAttribute("vendorName", "Vendor Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("vendorName"))) {
			request.setAttribute("vendorName", "Only alphabetical characters allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("serviceType"))) {
			request.setAttribute("serviceType", "Service Type is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", "Contact Number is required");
			pass = false;

		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", "Invalid Contact Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", "Address is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		VendorBean bean = new VendorBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setVendorCode(DataUtility.getString(request.getParameter("vendorCode")));
		bean.setVendorName(DataUtility.getString(request.getParameter("vendorName")));
		bean.setServiceType(DataUtility.getString(request.getParameter("serviceType")));
		bean.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));

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
		VendorModel model = new VendorModel();

		if (id > 0) {
			try {
				VendorBean bean = model.findByPk(id);
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
		VendorModel model = new VendorModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			VendorBean bean = (VendorBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Vendor Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Vendor Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			VendorBean bean = (VendorBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Vendor Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Vendor Code already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VENDOR_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VENDOR_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.VENDOR_VIEW;
	}
}