package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FollowUpBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * FollowUpModel handles CRUD operations for st_followup table.
 * It supports add, update, delete, find, search and list operations.
 *
 * author  Rishabh Shrivastava
 * version 1.0
 */
public class FollowUpModel {

    /**
     * Generates next primary key.
     *
     * @return next primary key
     * @throws DatabaseException
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_followup");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Adds a new FollowUp record.
     *
     * @param bean FollowUpBean
     * @return generated primary key
     * @throws ApplicationException
     */
    public long add(FollowUpBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "insert into st_followup values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            pstmt.setInt(1, pk);
            pstmt.setLong(2, bean.getPatientId());
            pstmt.setString(3, bean.getPatientName());
            pstmt.setLong(4, bean.getDoctorId());
            pstmt.setString(5, bean.getDoctorName());
            pstmt.setDate(6, new java.sql.Date(bean.getVisitDate().getTime()));
            pstmt.setDouble(7, bean.getFees());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDatetime());
            pstmt.setTimestamp(11, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add FollowUp");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Updates FollowUp record.
     *
     * @param bean FollowUpBean
     * @throws ApplicationException
     */
    public void update(FollowUpBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "update st_followup set patient_id=?, patient_name=?, doctor_id=?, doctor_name=?, "
              + "visit_date=?, fees=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? "
              + "where id=?"
            );

            pstmt.setLong(1, bean.getPatientId());
            pstmt.setString(2, bean.getPatientName());
            pstmt.setLong(3, bean.getDoctorId());
            pstmt.setString(4, bean.getDoctorName());
            pstmt.setDate(5, new java.sql.Date(bean.getVisitDate().getTime()));
            pstmt.setDouble(6, bean.getFees());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());
            pstmt.setLong(11, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in update FollowUp");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes FollowUp record.
     *
     * @param bean FollowUpBean
     * @throws ApplicationException
     */
    public void delete(FollowUpBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_followup where id=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in delete FollowUp");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds FollowUp by primary key.
     *
     * @param pk primary key
     * @return FollowUpBean
     * @throws ApplicationException
     */
    public FollowUpBean findByPk(long pk) throws ApplicationException {

        FollowUpBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_followup where id=?");
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new FollowUpBean();
                bean.setId(rs.getLong(1));
                bean.setPatientId(rs.getLong(2));
                bean.setPatientName(rs.getString(3));
                bean.setDoctorId(rs.getLong(4));
                bean.setDoctorName(rs.getString(5));
                bean.setVisitDate(rs.getDate(6));
                bean.setFees(rs.getLong(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting FollowUp by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Returns list of FollowUp records.
     *
     * @return list
     * @throws ApplicationException
     */
    public List<FollowUpBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches FollowUp records.
     *
     * @param bean filter
     * @param pageNo page number
     * @param pageSize page size
     * @return list
     * @throws ApplicationException
     */
    public List<FollowUpBean> search(FollowUpBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_followup where 1=1");

        if (bean != null) {

            if (bean.getPatientId() != null && bean.getPatientId() > 0)
                sql.append(" and patient_id = " + bean.getPatientId());

            if (bean.getDoctorId() != null && bean.getDoctorId() > 0)
                sql.append(" and doctor_id = " + bean.getDoctorId());

            if (bean.getVisitDate() != null)
                sql.append(" and visit_date = '" +
                        new java.sql.Date(bean.getVisitDate().getTime()) + "'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<FollowUpBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new FollowUpBean();
                bean.setId(rs.getLong(1));
                bean.setPatientId(rs.getLong(2));
                bean.setPatientName(rs.getString(3));
                bean.setDoctorId(rs.getLong(4));
                bean.setDoctorName(rs.getString(5));
                bean.setVisitDate(rs.getDate(6));
                bean.setFees(rs.getLong(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search FollowUp");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
