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
import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.InventoryModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InventoryCtl", urlPatterns = { "/ctl/InventoryCtl" })
public class InventoryCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> stockMap = new HashMap<String, String>();

		stockMap.put("In Stock", "In Stock");
		stockMap.put("Low Stock", "Low Stock");
		stockMap.put("Out of Stock", "Out of Stock");

		request.setAttribute("stockMap", stockMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("itemName"))) {
			request.setAttribute("itemName", "Item Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("itemName"))) {
			request.setAttribute("itemName", "Only alphabets allowed");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("stock"))) {
			request.setAttribute("stock", "Stock is required");
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("stock"))) {
			request.setAttribute("stock", "Stock must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", "Price is required");
			pass = false;

		} else if (!DataValidator.isDouble(request.getParameter("price"))) {
			request.setAttribute("price", "Enter valid price");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", "Quantity is required");
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("quantity"))) {
			request.setAttribute("quantity", "Quantity must be numeric");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InventoryBean bean = new InventoryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setItemName(DataUtility.getString(request.getParameter("itemName")));
		bean.setStock(DataUtility.getString(request.getParameter("stock")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));
		bean.setQuantity(DataUtility.getString(request.getParameter("quantity")));

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

		InventoryModel model = new InventoryModel();

		if (id > 0) {
			try {
				InventoryBean bean = model.findByPk(id);
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
		InventoryModel model = new InventoryModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InventoryBean bean = (InventoryBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Inventory Added Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InventoryBean bean = (InventoryBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setSuccessMessage("Inventory Updated Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.INVENTORY_VIEW;
	}
}
