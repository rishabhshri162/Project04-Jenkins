package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LanguageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class LanguageModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement("select max(id) from st_language");

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

	public long add(LanguageBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		LanguageBean existBean = findByCode(bean.getLanguageCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Language Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_language values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getLanguageCode());
			pstmt.setString(3, bean.getLanguageName());
			pstmt.setString(4, bean.getDirection());
			pstmt.setString(5, bean.getLanguageStatus());
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

			throw new ApplicationException("Exception in adding Language");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(LanguageBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		LanguageBean existBean = findByCode(bean.getLanguageCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Language Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_language set language_code=?, language_name=?, direction=?, language_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getLanguageCode());
			pstmt.setString(2, bean.getLanguageName());
			pstmt.setString(3, bean.getDirection());
			pstmt.setString(4, bean.getLanguageStatus());
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

			throw new ApplicationException("Exception in updating Language");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(LanguageBean bean)
			throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt =
					conn.prepareStatement("delete from st_language where id=?");

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

			throw new ApplicationException("Exception in deleting Language");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public LanguageBean findByPk(long pk)
			throws ApplicationException {

		LanguageBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement("select * from st_language where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new LanguageBean();

				bean.setId(rs.getLong(1));
				bean.setLanguageCode(rs.getString(2));
				bean.setLanguageName(rs.getString(3));
				bean.setDirection(rs.getString(4));
				bean.setLanguageStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Language by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public LanguageBean findByCode(String code)
			throws ApplicationException {

		LanguageBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement("select * from st_language where language_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new LanguageBean();

				bean.setId(rs.getLong(1));
				bean.setLanguageCode(rs.getString(2));
				bean.setLanguageName(rs.getString(3));
				bean.setDirection(rs.getString(4));
				bean.setLanguageStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Language by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<LanguageBean> search(LanguageBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		ArrayList<LanguageBean> list = new ArrayList<>();

		StringBuffer sql =
				new StringBuffer("select * from st_language where 1=1");

		if (bean != null) {

			if (bean.getLanguageCode() != null && bean.getLanguageCode().length() > 0) {
				sql.append(" and language_code like '" + bean.getLanguageCode() + "%'");
			}

			if (bean.getLanguageName() != null && bean.getLanguageName().length() > 0) {
				sql.append(" and language_name like '" + bean.getLanguageName() + "%'");
			}

			if (bean.getLanguageStatus() != null && bean.getLanguageStatus().length() > 0) {
				sql.append(" and language_status like '" + bean.getLanguageStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new LanguageBean();

				bean.setId(rs.getLong(1));
				bean.setLanguageCode(rs.getString(2));
				bean.setLanguageName(rs.getString(3));
				bean.setDirection(rs.getString(4));
				bean.setLanguageStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Language");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}