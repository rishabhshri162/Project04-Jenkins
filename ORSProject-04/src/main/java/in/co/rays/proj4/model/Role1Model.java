
package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.Role1Bean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class Role1Model {

	// ================= NEXT PK =================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role1");

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

	public long add(Role1Bean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		Role1Bean existBean = findByCode(bean.getRoleCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Role Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_role1 values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRoleCode());
			pstmt.setString(3, bean.getRoleName());
			pstmt.setString(4, bean.getRoleDescription());
			pstmt.setString(5, bean.getStatus());
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

			throw new ApplicationException("Exception in adding Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ================= UPDATE =================

	public void update(Role1Bean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		Role1Bean existBean = findByCode(bean.getRoleCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_role1 set role_code=?, role_name=?, role_description=?, status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getRoleCode());
			pstmt.setString(2, bean.getRoleName());
			pstmt.setString(3, bean.getRoleDescription());
			pstmt.setString(4, bean.getStatus());
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

			throw new ApplicationException("Exception in updating Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================

	public void delete(Role1Bean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_role1 where id=?");

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

			throw new ApplicationException("Exception in deleting Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================

	public Role1Bean findByPk(long pk) throws ApplicationException {

		Role1Bean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_role1 where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new Role1Bean();

				bean.setId(rs.getLong(1));
				bean.setRoleCode(rs.getString(2));
				bean.setRoleName(rs.getString(3));
				bean.setRoleDescription(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Role by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY CODE =================

	public Role1Bean findByCode(String code) throws ApplicationException {

		Role1Bean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_role1 where role_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new Role1Bean();

				bean.setId(rs.getLong(1));
				bean.setRoleCode(rs.getString(2));
				bean.setRoleName(rs.getString(3));
				bean.setRoleDescription(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Role by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= SEARCH =================

	public List<Role1Bean> search(Role1Bean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<Role1Bean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_role1 where 1=1");

		if (bean != null) {

			if (bean.getRoleCode() != null && bean.getRoleCode().length() > 0) {
				sql.append(" and role_code like '" + bean.getRoleCode() + "%'");
			}

			if (bean.getRoleName() != null && bean.getRoleName().length() > 0) {
				sql.append(" and role_name like '" + bean.getRoleName() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
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

				Role1Bean r = new Role1Bean();

				r.setId(rs.getLong(1));
				r.setRoleCode(rs.getString(2));
				r.setRoleName(rs.getString(3));
				r.setRoleDescription(rs.getString(4));
				r.setStatus(rs.getString(5));
				r.setCreatedBy(rs.getString(6));
				r.setModifiedBy(rs.getString(7));
				r.setCreatedDatetime(rs.getTimestamp(8));
				r.setModifiedDatetime(rs.getTimestamp(9));

				list.add(r);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
