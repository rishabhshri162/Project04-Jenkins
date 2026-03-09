package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.JobApplicationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class JobApplicationModel {

	// ================= NEXT PK =================
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_job_application");

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
	public long add(JobApplicationBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_job_application values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getApplicantName());
			pstmt.setString(3, bean.getCompanyName());
			pstmt.setString(4, bean.getPosition());
			pstmt.setDate(5, new java.sql.Date(bean.getApplicationDate().getTime()));
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in adding Job Application");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ================= UPDATE =================
	public void update(JobApplicationBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_job_application set applicant_name=?, company_name=?, position=?, application_date=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getApplicantName());
			pstmt.setString(2, bean.getCompanyName());
			pstmt.setString(3, bean.getPosition());
			pstmt.setDate(4, new java.sql.Date(bean.getApplicationDate().getTime()));
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in updating Job Application");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================
	public void delete(JobApplicationBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_job_application where id=?");

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

			throw new ApplicationException("Exception in deleting Job Application");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public JobApplicationBean findByPk(long pk) throws ApplicationException {

		JobApplicationBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_job_application where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new JobApplicationBean();

				bean.setId(rs.getLong(1));
				bean.setApplicantName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPosition(rs.getString(4));
				bean.setApplicationDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Job Application by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= SEARCH =================
	public List<JobApplicationBean> search(JobApplicationBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		ArrayList<JobApplicationBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_job_application where 1=1");

		if (bean != null) {

			if (bean.getApplicantName() != null && bean.getApplicantName().length() > 0) {

				sql.append(" and applicant_name like '" + bean.getApplicantName() + "%'");
			}

			if (bean.getCompanyName() != null && bean.getCompanyName().length() > 0) {

				sql.append(" and company_name like '" + bean.getCompanyName() + "%'");
			}

			if (bean.getPosition() != null && bean.getPosition().length() > 0) {

				sql.append(" and position like '" + bean.getPosition() + "%'");
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

				bean = new JobApplicationBean();

				bean.setId(rs.getLong(1));
				bean.setApplicantName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPosition(rs.getString(4));
				bean.setApplicationDate(rs.getDate(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Job Application");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List<JobApplicationBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
}