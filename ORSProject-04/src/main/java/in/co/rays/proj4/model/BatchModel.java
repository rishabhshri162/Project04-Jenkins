package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BatchBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BatchModel {

	// ================= NEXT PK =================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_batch");

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

	public long add(BatchBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		BatchBean existBean = findByCode(bean.getBatchCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Batch Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_batch values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getBatchCode());
			pstmt.setString(3, bean.getBatchName());
			pstmt.setString(4, bean.getTrainerName());
			pstmt.setString(5, bean.getBatchTiming());
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

			throw new ApplicationException("Exception in adding Batch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ================= UPDATE =================

	public void update(BatchBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		BatchBean existBean = findByCode(bean.getBatchCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Batch Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_batch set batch_code=?, batch_name=?, trainer_name=?, batch_timing=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getBatchCode());
			pstmt.setString(2, bean.getBatchName());
			pstmt.setString(3, bean.getTrainerName());
			pstmt.setString(4, bean.getBatchTiming());
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

			throw new ApplicationException("Exception in updating Batch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================

	public void delete(BatchBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("delete from st_batch where id=?");

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

			throw new ApplicationException("Exception in deleting Batch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================

	public BatchBean findByPk(long pk) throws ApplicationException {

		BatchBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_batch where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new BatchBean();

				bean.setId(rs.getLong(1));
				bean.setBatchCode(rs.getString(2));
				bean.setBatchName(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setBatchTiming(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Batch by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY CODE =================

	public BatchBean findByCode(String code) throws ApplicationException {

		BatchBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_batch where batch_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new BatchBean();

				bean.setId(rs.getLong(1));
				bean.setBatchCode(rs.getString(2));
				bean.setBatchName(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setBatchTiming(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Batch by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= SEARCH =================

	public List<BatchBean> search(BatchBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		ArrayList<BatchBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_batch where 1=1");

		if (bean != null) {

			if (bean.getBatchCode() != null && bean.getBatchCode().length() > 0) {
				sql.append(" and batch_code like '" + bean.getBatchCode() + "%'");
			}

			if (bean.getBatchName() != null && bean.getBatchName().length() > 0) {
				sql.append(" and batch_name like '" + bean.getBatchName() + "%'");
			}

			if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0) {
				sql.append(" and trainer_name like '" + bean.getTrainerName() + "%'");
			}
			
			if (bean.getBatchTiming() != null && bean.getBatchTiming().length() > 0) {
			    sql.append(" and batch_timing = '" + bean.getBatchTiming() + "'");
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

				BatchBean b = new BatchBean();

				b.setId(rs.getLong(1));
				b.setBatchCode(rs.getString(2));
				b.setBatchName(rs.getString(3));
				b.setTrainerName(rs.getString(4));
				b.setBatchTiming(rs.getString(5));
				b.setCreatedBy(rs.getString(6));
				b.setModifiedBy(rs.getString(7));
				b.setCreatedDatetime(rs.getTimestamp(8));
				b.setModifiedDatetime(rs.getTimestamp(9));

				list.add(b);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Batch");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}