package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.HostelRoomBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * HostelRoomModel handles database operations for Hostel Room. Table Name :
 * st_hostel
 *
 * @author Rishabh Shrivastava
 * @version 1.0
 */
public class HostelRoomModel {

	/**
	 * Get next primary key
	 */
	public int nextPk() throws DatabaseException {
		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_hostel");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Add Hostel Room
	 */
	public long add(HostelRoomBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		HostelRoomBean existBean = findByRoomNumber(bean.getRoomNumber());
		if (existBean != null) {
			throw new DuplicateRecordException("Room already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"insert into st_hostel (id, room_number, room_type, capacity, rent, status, created_by, modified_by, created_datetime) "
							+ "values (?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getRoomNumber());
			ps.setString(3, bean.getRoomType());
			ps.setString(4, bean.getCapacity());
			ps.setString(5, bean.getRent());
			ps.setString(6, bean.getStatus());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in add Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Update Hostel Room
	 */
	public void update(HostelRoomBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		HostelRoomBean existBean = findByRoomNumber(bean.getRoomNumber());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Room number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("update st_hostel set room_number=?, room_type=?, capacity=?, rent=?, status=?, "
							+ "created_by=?, modified_by=?, modified_datetime=? where id=?");

			ps.setString(1, bean.getRoomNumber());
			ps.setString(2, bean.getRoomType());
			ps.setString(3, bean.getCapacity());
			ps.setString(4, bean.getRent());
			ps.setString(5, bean.getStatus());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getModifiedDatetime());
			ps.setLong(9, bean.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in update Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Delete Hostel Room
	 */
	public void delete(HostelRoomBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("delete from st_hostel where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in delete Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find by Primary Key
	 */
	public HostelRoomBean findByPk(long pk) throws ApplicationException {

		HostelRoomBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from st_hostel where id=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new HostelRoomBean();
				bean.setId(rs.getLong(1));
				bean.setRoomNumber(rs.getString(2));
				bean.setRoomType(rs.getString(3));
				bean.setCapacity(rs.getString(4));
				bean.setRent(rs.getString(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Find by Room Number
	 */
	public HostelRoomBean findByRoomNumber(String roomNumber) throws ApplicationException {

		HostelRoomBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from st_hostel where room_number=?");
			ps.setString(1, roomNumber);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new HostelRoomBean();
				bean.setId(rs.getLong(1));
				bean.setRoomNumber(rs.getString(2));
				bean.setRoomType(rs.getString(3));
				bean.setCapacity(rs.getString(4));
				bean.setRent(rs.getString(5));
				bean.setStatus(rs.getString(6));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find room by number");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Search Hostel Rooms
	 */
	public List<HostelRoomBean> search(HostelRoomBean bean, int pageNo, int pageSize) throws ApplicationException {

		ArrayList<HostelRoomBean> list = new ArrayList<>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_hostel where 1=1");

		if (bean != null) {
			if (bean.getRoomNumber() != null && bean.getRoomNumber().trim().length() > 0) {
			    sql.append(" and room_number = '" + bean.getRoomNumber() + "'");
			}

			if (bean.getRoomType() != null && bean.getRoomType().length() > 0)
				sql.append(" and room_type like '" + bean.getRoomType() + "%'");
			if (bean.getStatus() != null && bean.getStatus().length() > 0)
				sql.append(" and status like '" + bean.getStatus() + "%'");
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new HostelRoomBean();
				bean.setId(rs.getLong(1));
				bean.setRoomNumber(rs.getString(2));
				bean.setRoomType(rs.getString(3));
				bean.setCapacity(rs.getString(4));
				bean.setRent(rs.getString(5));
				bean.setStatus(rs.getString(6));
				list.add(bean);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * List all Hostel Rooms
	 */
	public List<HostelRoomBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
