package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.VendorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class VendorModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_vendor");

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

	public long add(VendorBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		VendorBean existBean = findByCode(bean.getVendorCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Vendor Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_vendor values(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getVendorCode());
			pstmt.setString(3, bean.getVendorName());
			pstmt.setString(4, bean.getServiceType());
			pstmt.setString(5, bean.getContactNumber());
			pstmt.setString(6, bean.getAddress());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in adding Vendor");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(VendorBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		VendorBean existBean = findByCode(bean.getVendorCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Vendor Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_vendor set vendor_code=?, vendor_name=?, service_type=?, contact_number=?, address=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getVendorCode());
			pstmt.setString(2, bean.getVendorName());
			pstmt.setString(3, bean.getServiceType());
			pstmt.setString(4, bean.getContactNumber());
			pstmt.setString(5, bean.getAddress());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in updating Vendor");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(VendorBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_vendor where id=?");

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

			throw new ApplicationException("Exception in deleting Vendor");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public VendorBean findByPk(long pk) throws ApplicationException {

		VendorBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_vendor where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new VendorBean();

				bean.setId(rs.getLong(1));
				bean.setVendorCode(rs.getString(2));
				bean.setVendorName(rs.getString(3));
				bean.setServiceType(rs.getString(4));
				bean.setContactNumber(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Vendor by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public VendorBean findByCode(String code) throws ApplicationException {

		VendorBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_vendor where vendor_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new VendorBean();

				bean.setId(rs.getLong(1));
				bean.setVendorCode(rs.getString(2));
				bean.setVendorName(rs.getString(3));
				bean.setServiceType(rs.getString(4));
				bean.setContactNumber(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Vendor by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<VendorBean> search(VendorBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<VendorBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_vendor where 1=1");

		if (bean != null) {

			if (bean.getVendorCode() != null && bean.getVendorCode().length() > 0) {
				sql.append(" and vendor_code like '" + bean.getVendorCode() + "%'");
			}

			if (bean.getVendorName() != null && bean.getVendorName().length() > 0) {
				sql.append(" and vendor_name like '" + bean.getVendorName() + "%'");
			}

			if (bean.getServiceType() != null && bean.getServiceType().length() > 0) {
				sql.append(" and service_type like '" + bean.getServiceType() + "%'");
			}
		}

		// Pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				VendorBean bean1 = new VendorBean();

				bean1.setId(rs.getLong(1));
				bean1.setVendorCode(rs.getString(2));
				bean1.setVendorName(rs.getString(3));
				bean1.setServiceType(rs.getString(4));
				bean1.setContactNumber(rs.getString(5));
				bean1.setAddress(rs.getString(6));
				bean1.setCreatedBy(rs.getString(7));
				bean1.setModifiedBy(rs.getString(8));
				bean1.setCreatedDatetime(rs.getTimestamp(9));
				bean1.setModifiedDatetime(rs.getTimestamp(10));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Vendor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}