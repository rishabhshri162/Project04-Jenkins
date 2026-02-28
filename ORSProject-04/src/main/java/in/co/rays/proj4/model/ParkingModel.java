package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ParkingModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_parking");
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

	public long add(ParkingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		ParkingBean existBean = findByCode(bean.getParkingCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Parking Code already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_parking values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getParkingCode());
			pstmt.setString(3, bean.getLocation());
			pstmt.setInt(4, bean.getSlotNumber());
			pstmt.setString(5, bean.getParkingStatus());
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

			throw new ApplicationException("Exception in adding Parking");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(ParkingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ParkingBean existBean = findByCode(bean.getParkingCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Parking Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_parking set parking_code=?, location=?, slot_number=?, parking_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getParkingCode());
			pstmt.setString(2, bean.getLocation());
			pstmt.setInt(3, bean.getSlotNumber());
			pstmt.setString(4, bean.getParkingStatus());
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

			throw new ApplicationException("Exception in updating Parking");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(ParkingBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_parking where id=?");

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

			throw new ApplicationException("Exception in deleting Parking");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ParkingBean findByPk(long pk) throws ApplicationException {

		ParkingBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_parking where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new ParkingBean();

				bean.setId(rs.getLong(1));
				bean.setParkingCode(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSlotNumber(rs.getInt(4));
				bean.setParkingStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Parking by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public ParkingBean findByCode(String code) throws ApplicationException {

		ParkingBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_parking where parking_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new ParkingBean();

				bean.setId(rs.getLong(1));
				bean.setParkingCode(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSlotNumber(rs.getInt(4));
				bean.setParkingStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Parking by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<ParkingBean> search(ParkingBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<ParkingBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_parking where 1=1");

		if (bean != null) {

			if (bean.getParkingCode() != null && bean.getParkingCode().length() > 0) {
				sql.append(" and parking_code like '" + bean.getParkingCode() + "%'");
			}

			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" and location like '" + bean.getLocation() + "%'");
			}

			if (bean.getParkingStatus() != null && bean.getParkingStatus().length() > 0) {
				sql.append(" and parking_status like '" + bean.getParkingStatus() + "%'");
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

				ParkingBean bean1 = new ParkingBean();

				bean1.setId(rs.getLong(1));
				bean1.setParkingCode(rs.getString(2));
				bean1.setLocation(rs.getString(3));
				bean1.setSlotNumber(rs.getInt(4));
				bean1.setParkingStatus(rs.getString(5));
				bean1.setCreatedBy(rs.getString(6));
				bean1.setModifiedBy(rs.getString(7));
				bean1.setCreatedDatetime(rs.getTimestamp(8));
				bean1.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Parking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}