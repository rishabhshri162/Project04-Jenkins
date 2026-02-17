package in.co.rays.proj4.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InventoryModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_inventory");
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

	public long add(InventoryBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_inventory values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getItemName());
			pstmt.setString(3, bean.getStock());
			pstmt.setString(4, bean.getPrice());
			pstmt.setString(5, bean.getQuantity());
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
			}
			throw new ApplicationException("Exception in adding Inventory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(InventoryBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_inventory set item_name=?, stock=?, price=?, quantity=?,created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getItemName());
			pstmt.setString(2, bean.getStock());
			pstmt.setString(3, bean.getPrice());
			pstmt.setString(4, bean.getQuantity());
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
			}
			throw new ApplicationException("Exception in updating Inventory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(InventoryBean bean) throws ApplicationException {

		try (Connection conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from st_inventory where id=?")) {

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in deleting Inventory");
		}
	}

	public InventoryBean findByPk(long pk) throws ApplicationException {

		InventoryBean bean = null;

		try (Connection conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select * from st_inventory where id=?")) {

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setItemName(rs.getString(2));
				bean.setStock(rs.getString(3));
				bean.setPrice(rs.getString(4));
				bean.setQuantity(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Inventory by PK");
		}

		return bean;
	}

	public List<InventoryBean> search(InventoryBean bean, int pageNo, int pageSize) throws ApplicationException {

		ArrayList<InventoryBean> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from st_inventory where 1=1");

		if (bean != null) {

			if (bean.getItemName() != null && bean.getItemName().length() > 0) {

				sql.append(" and item_name like '" + bean.getItemName() + "%'");
			}
			if (bean.getStock() != null && bean.getStock().length() > 0) {
				sql.append(" and stock like '" + bean.getStock() + "%'");
			}

			if (bean.getPrice() != null && bean.getPrice().length() > 0) {
				sql.append(" and price like '" + bean.getPrice() + "%'");
			}

			if (bean.getQuantity() != null && bean.getQuantity().length() > 0) {
				sql.append(" and quantity like '" + bean.getQuantity() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try (Connection conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				InventoryBean b = new InventoryBean();
				b.setId(rs.getLong(1));
				b.setItemName(rs.getString(2));
				b.setStock(rs.getString(3));
				b.setPrice(rs.getString(4));
				b.setQuantity(rs.getString(5));
				list.add(b);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Inventory");
		}

		return list;
	}

	public List<InventoryBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
