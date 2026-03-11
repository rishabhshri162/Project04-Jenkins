package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PolicyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PolicyModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_policy");
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

	public long add(PolicyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		PolicyBean existBean = findByName(bean.getPolicyName());
		if (existBean != null) {
			throw new DuplicateRecordException("Policy Name already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_policy values(?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPolicyName());
			pstmt.setDouble(3, bean.getPremiumAmount());
			pstmt.setDate(4, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in adding Policy");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(PolicyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		PolicyBean existBean = findByName(bean.getPolicyName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Policy Name already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_policy set policy_name=?, premium_amount=?, start_date=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPolicyName());
			pstmt.setDouble(2, bean.getPremiumAmount());
			pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in updating Policy");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PolicyBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_policy where id=?");

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

			throw new ApplicationException("Exception in deleting Policy");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PolicyBean findByPk(long pk) throws ApplicationException {

		PolicyBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_policy where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new PolicyBean();

				bean.setId(rs.getLong(1));
				bean.setPolicyName(rs.getString(2));
				bean.setPremiumAmount(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Policy by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public PolicyBean findByName(String name) throws ApplicationException {

		PolicyBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_policy where policy_name=?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new PolicyBean();

				bean.setId(rs.getLong(1));
				bean.setPolicyName(rs.getString(2));
				bean.setPremiumAmount(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Policy by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<PolicyBean> search(PolicyBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<PolicyBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_policy where 1=1");

		if (bean != null) {

			if (bean.getPolicyName() != null && bean.getPolicyName().length() > 0) {
				sql.append(" and policy_name like '" + bean.getPolicyName() + "%'");
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

				PolicyBean bean1 = new PolicyBean();

				bean1.setId(rs.getLong(1));
				bean1.setPolicyName(rs.getString(2));
				bean1.setPremiumAmount(rs.getDouble(3));
				bean1.setStartDate(rs.getDate(4));
				bean1.setCreatedBy(rs.getString(5));
				bean1.setModifiedBy(rs.getString(6));
				bean1.setCreatedDatetime(rs.getTimestamp(7));
				bean1.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Policy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}