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
import in.co.rays.proj4.bean.SpeakerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SpeakerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SpeakerCtl", urlPatterns = { "/ctl/SpeakerCtl" })
public class SpeakerCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> orgMap = new HashMap<>();

		orgMap.put("Google", "Google");
		orgMap.put("Microsoft", "Microsoft");
		orgMap.put("Amazon", "Amazon");
		orgMap.put("Infosys", "Infosys");

		request.setAttribute("organizationMap", orgMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("speakerName"))) {
			request.setAttribute("speakerName", "Speaker Name is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("topic"))) {
			request.setAttribute("topic", "Topic is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("organization"))) {
			request.setAttribute("organization", "Organization is required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SpeakerBean bean = new SpeakerBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSpeakerName(DataUtility.getString(request.getParameter("speakerName")));
		bean.setTopic(DataUtility.getString(request.getParameter("topic")));
		bean.setOrganization(DataUtility.getString(request.getParameter("organization")));

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
		SpeakerModel model = new SpeakerModel();

		if (id > 0) {
			try {

				SpeakerBean bean = model.findByPk(id);
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
		SpeakerModel model = new SpeakerModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SpeakerBean bean = (SpeakerBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Speaker Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Speaker already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SpeakerBean bean = (SpeakerBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Speaker Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Speaker already exists", request);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SPEAKER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SPEAKER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SPEAKER_VIEW;
	}
}