package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.HospitalAppointmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class HospitalAppointmentModel {

	// ================= NEXT PK =================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select max(id) from st_hospital_appointment");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ================= ADD =================

	public long add(HospitalAppointmentBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		HospitalAppointmentBean existBean =
				findByPatientName(bean.getPatientName());

		if (existBean != null) {
			throw new DuplicateRecordException("Appointment already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_hospital_appointment values(?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPatientName());
			pstmt.setString(3, bean.getDoctorName());
			pstmt.setDate(4, new java.sql.Date(bean.getAppointmentDate().getTime()));
			pstmt.setString(5, bean.getAppointmentTime());
			pstmt.setString(6, bean.getSymptoms());
			pstmt.setString(7, bean.getConsultationFee());
			pstmt.setString(8, bean.getStatus());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in adding Appointment");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ================= UPDATE =================

	public void update(HospitalAppointmentBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		HospitalAppointmentBean existBean =
				findByPatientName(bean.getPatientName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Appointment already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_hospital_appointment set patient_name=?, doctor_name=?, "
					+ "appointment_date=?, appointment_time=?, symptoms=?, "
					+ "consultation_fee=?, status=?, created_by=?, modified_by=?, "
					+ "created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPatientName());
			pstmt.setString(2, bean.getDoctorName());
			pstmt.setDate(3, new java.sql.Date(bean.getAppointmentDate().getTime()));
			pstmt.setString(4, bean.getAppointmentTime());
			pstmt.setString(5, bean.getSymptoms());
			pstmt.setString(6, bean.getConsultationFee());
			pstmt.setString(7, bean.getStatus());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in updating Appointment");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================

	public void delete(HospitalAppointmentBean bean)
			throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("delete from st_hospital_appointment where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception");
			}

			throw new ApplicationException("Exception in deleting Appointment");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================

	public HospitalAppointmentBean findByPk(long pk)
			throws ApplicationException {

		HospitalAppointmentBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_hospital_appointment where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new HospitalAppointmentBean();

				bean.setId(rs.getLong(1));
				bean.setPatientName(rs.getString(2));
				bean.setDoctorName(rs.getString(3));
				bean.setAppointmentDate(rs.getDate(4));
				bean.setAppointmentTime(rs.getString(5));
				bean.setSymptoms(rs.getString(6));
				bean.setConsultationFee(rs.getString(7));
				bean.setStatus(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Appointment by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY PATIENT NAME =================

	public HospitalAppointmentBean findByPatientName(String name)
			throws ApplicationException {

		HospitalAppointmentBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_hospital_appointment where patient_name=?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new HospitalAppointmentBean();
				bean.setId(rs.getLong(1));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Appointment by Patient Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= SEARCH =================

	public List<HospitalAppointmentBean> search(
			HospitalAppointmentBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		ArrayList<HospitalAppointmentBean> list = new ArrayList<>();

		StringBuffer sql =
				new StringBuffer("select * from st_hospital_appointment where 1=1");

		if (bean != null) {

			if (bean.getPatientName() != null && bean.getPatientName().length() > 0) {
				sql.append(" and patient_name like '" + bean.getPatientName() + "%'");
			}

			if (bean.getDoctorName() != null && bean.getDoctorName().length() > 0) {
				sql.append(" and doctor_name like '" + bean.getDoctorName() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status = '" + bean.getStatus() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				HospitalAppointmentBean b = new HospitalAppointmentBean();

				b.setId(rs.getLong(1));
				b.setPatientName(rs.getString(2));
				b.setDoctorName(rs.getString(3));
				b.setAppointmentDate(rs.getDate(4));
				b.setAppointmentTime(rs.getString(5));
				b.setSymptoms(rs.getString(6));
				b.setConsultationFee(rs.getString(7));
				b.setStatus(rs.getString(8));
				b.setCreatedBy(rs.getString(9));
				b.setModifiedBy(rs.getString(10));
				b.setCreatedDatetime(rs.getTimestamp(11));
				b.setModifiedDatetime(rs.getTimestamp(12));

				list.add(b);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Appointment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}