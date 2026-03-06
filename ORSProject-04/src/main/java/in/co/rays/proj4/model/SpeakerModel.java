package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SpeakerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SpeakerModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_speaker");

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

	public long add(SpeakerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		SpeakerBean existBean = findBySpeakerName(bean.getSpeakerName());

		if (existBean != null) {
			throw new DuplicateRecordException("Speaker already exists");
		}

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_speaker values(?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getSpeakerName());
			pstmt.setString(3, bean.getTopic());
			pstmt.setString(4, bean.getOrganization());
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

			throw new ApplicationException("Exception in adding Speaker");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(SpeakerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		SpeakerBean existBean = findBySpeakerName(bean.getSpeakerName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Speaker already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_speaker set speaker_name=?, topic=?, organization=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getSpeakerName());
			pstmt.setString(2, bean.getTopic());
			pstmt.setString(3, bean.getOrganization());
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

			throw new ApplicationException("Exception in updating Speaker");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(SpeakerBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_speaker where id=?");

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

			throw new ApplicationException("Exception in deleting Speaker");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public SpeakerBean findByPk(long pk) throws ApplicationException {

		SpeakerBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_speaker where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SpeakerBean();

				bean.setId(rs.getLong(1));
				bean.setSpeakerName(rs.getString(2));
				bean.setTopic(rs.getString(3));
				bean.setOrganization(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Speaker by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public SpeakerBean findBySpeakerName(String name) throws ApplicationException {

		SpeakerBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_speaker where speaker_name=?");

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SpeakerBean();

				bean.setId(rs.getLong(1));
				bean.setSpeakerName(rs.getString(2));
				bean.setTopic(rs.getString(3));
				bean.setOrganization(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Speaker");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<SpeakerBean> search(SpeakerBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<SpeakerBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_speaker where 1=1");

		if (bean != null) {

			if (bean.getSpeakerName() != null && bean.getSpeakerName().length() > 0) {
				sql.append(" and speaker_name like '" + bean.getSpeakerName() + "%'");
			}

			if (bean.getTopic() != null && bean.getTopic().length() > 0) {
				sql.append(" and topic like '" + bean.getTopic() + "%'");
			}
			if (bean.getOrganization() != null && bean.getOrganization().length() > 0) {
				sql.append(" and organization like '" + bean.getOrganization() + "%'");
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

				SpeakerBean bean1 = new SpeakerBean();

				bean1.setId(rs.getLong(1));
				bean1.setSpeakerName(rs.getString(2));
				bean1.setTopic(rs.getString(3));
				bean1.setOrganization(rs.getString(4));
				bean1.setCreatedBy(rs.getString(5));
				bean1.setModifiedBy(rs.getString(6));
				bean1.setCreatedDatetime(rs.getTimestamp(7));
				bean1.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Speaker");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}