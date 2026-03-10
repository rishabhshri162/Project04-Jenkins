package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class DepartmentModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_department");
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

	public long add(DepartmentBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		DepartmentBean existBean = findByCode(bean.getDepartmentCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Department Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_department values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getDepartmentCode());
			pstmt.setString(3, bean.getDepartmentName());
			pstmt.setString(4, bean.getDepartmentHead());
			pstmt.setString(5, bean.getDepartmentStatus());
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

			throw new ApplicationException("Exception in adding Department");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(DepartmentBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		DepartmentBean existBean = findByCode(bean.getDepartmentCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Department Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_department set department_code=?, department_name=?, department_head=?, department_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getDepartmentCode());
			pstmt.setString(2, bean.getDepartmentName());
			pstmt.setString(3, bean.getDepartmentHead());
			pstmt.setString(4, bean.getDepartmentStatus());
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

			throw new ApplicationException("Exception in updating Department");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(DepartmentBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_department where id=?");

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

			throw new ApplicationException("Exception in deleting Department");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public DepartmentBean findByPk(long pk) throws ApplicationException {

		DepartmentBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_department where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DepartmentBean();

				bean.setId(rs.getLong(1));
				bean.setDepartmentCode(rs.getString(2));
				bean.setDepartmentName(rs.getString(3));
				bean.setDepartmentHead(rs.getString(4));
				bean.setDepartmentStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Department by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public DepartmentBean findByCode(String code) throws ApplicationException {

		DepartmentBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_department where department_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DepartmentBean();

				bean.setId(rs.getLong(1));
				bean.setDepartmentCode(rs.getString(2));
				bean.setDepartmentName(rs.getString(3));
				bean.setDepartmentHead(rs.getString(4));
				bean.setDepartmentStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Department by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<DepartmentBean> search(DepartmentBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<DepartmentBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_department where 1=1");

		if (bean != null) {

			if (bean.getDepartmentCode() != null && bean.getDepartmentCode().length() > 0) {
				sql.append(" and department_code like '" + bean.getDepartmentCode() + "%'");
			}

			if (bean.getDepartmentName() != null && bean.getDepartmentName().length() > 0) {
				sql.append(" and department_name like '" + bean.getDepartmentName() + "%'");
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

				DepartmentBean bean1 = new DepartmentBean();

				bean1.setId(rs.getLong(1));
				bean1.setDepartmentCode(rs.getString(2));
				bean1.setDepartmentName(rs.getString(3));
				bean1.setDepartmentHead(rs.getString(4));
				bean1.setDepartmentStatus(rs.getString(5));
				bean1.setCreatedBy(rs.getString(6));
				bean1.setModifiedBy(rs.getString(7));
				bean1.setCreatedDatetime(rs.getTimestamp(8));
				bean1.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Department");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}