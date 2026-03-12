package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PetAdoptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PetAdoptionModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_pet_adoption");

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

	public long add(PetAdoptionBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_pet_adoption values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPetName());
			pstmt.setString(3, bean.getAnimalType());
			pstmt.setInt(4, bean.getAge());
			pstmt.setString(5, bean.getAdoptionStatus());
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

			throw new ApplicationException("Exception in adding Pet Adoption");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(PetAdoptionBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_pet_adoption set pet_name=?, animal_type=?, age=?, adoption_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPetName());
			pstmt.setString(2, bean.getAnimalType());
			pstmt.setInt(3, bean.getAge());
			pstmt.setString(4, bean.getAdoptionStatus());
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

			throw new ApplicationException("Exception in updating Pet Adoption");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PetAdoptionBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_pet_adoption where id=?");

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

			throw new ApplicationException("Exception in deleting Pet Adoption");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PetAdoptionBean findByPk(long pk) throws ApplicationException {

		PetAdoptionBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_pet_adoption where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new PetAdoptionBean();

				bean.setId(rs.getLong(1));
				bean.setPetName(rs.getString(2));
				bean.setAnimalType(rs.getString(3));
				bean.setAge(rs.getInt(4));
				bean.setAdoptionStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Pet by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<PetAdoptionBean> search(PetAdoptionBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<PetAdoptionBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_pet_adoption where 1=1");

		if (bean != null) {

			if (bean.getPetName() != null && bean.getPetName().length() > 0) {
				sql.append(" and pet_name like '" + bean.getPetName() + "%'");
			}

			if (bean.getAnimalType() != null && bean.getAnimalType().length() > 0) {
				sql.append(" and animal_type like '" + bean.getAnimalType() + "%'");
			}

			if (bean.getAdoptionStatus() != null && bean.getAdoptionStatus().length() > 0) {
				sql.append(" and adoption_status like '" + bean.getAdoptionStatus() + "%'");
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

				PetAdoptionBean bean1 = new PetAdoptionBean();

				bean1.setId(rs.getLong(1));
				bean1.setPetName(rs.getString(2));
				bean1.setAnimalType(rs.getString(3));
				bean1.setAge(rs.getInt(4));
				bean1.setAdoptionStatus(rs.getString(5));
				bean1.setCreatedBy(rs.getString(6));
				bean1.setModifiedBy(rs.getString(7));
				bean1.setCreatedDatetime(rs.getTimestamp(8));
				bean1.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Pet Adoption");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}