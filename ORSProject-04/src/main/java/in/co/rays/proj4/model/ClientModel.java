package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ClientModel {

    // ================== NEXT PK ==================
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_client");

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

    // ================== ADD ==================
    public long add(ClientBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_client values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getClientName());
            pstmt.setString(3, bean.getEmail());
            pstmt.setString(4, bean.getPhone());
            pstmt.setString(5, bean.getPriority());
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
            throw new ApplicationException("Exception in adding Client");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ================== UPDATE ==================
    public void update(ClientBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_client set client_name=?, email=?, phone=?, "
                    + "priority=?, created_by=?, modified_by=?, "
                    + "created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getClientName());
            pstmt.setString(2, bean.getEmail());
            pstmt.setString(3, bean.getPhone());
            pstmt.setString(4, bean.getPriority());
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
            throw new ApplicationException("Exception in updating Client");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================== DELETE ==================
    public void delete(ClientBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_client where id=?");

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
            throw new ApplicationException("Exception in deleting Client");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================== FIND BY PK ==================
    public ClientBean findByPk(long pk)
            throws ApplicationException {

        ClientBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_client where id=?");

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ClientBean();

                bean.setId(rs.getLong(1));
                bean.setClientName(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setPhone(rs.getString(4));
                bean.setPriority(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Client by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================== SEARCH ==================
    public List<ClientBean> search(ClientBean bean,
                                   int pageNo,
                                   int pageSize)
            throws ApplicationException {

        Connection conn = null;
        ArrayList<ClientBean> list = new ArrayList<>();

        StringBuffer sql =
                new StringBuffer("select * from st_client where 1=1");

        if (bean != null) {

            if (bean.getClientName() != null &&
                bean.getClientName().length() > 0) {

                sql.append(" and client_name like '"
                        + bean.getClientName() + "%'");
            }

            if (bean.getEmail() != null &&
                bean.getEmail().length() > 0) {

                sql.append(" and email like '"
                        + bean.getEmail() + "%'");
            }

            if (bean.getPriority() != null &&
                bean.getPriority().length() > 0) {

                sql.append(" and priority like '"
                        + bean.getPriority() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ClientBean();

                bean.setId(rs.getLong(1));
                bean.setClientName(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setPhone(rs.getString(4));
                bean.setPriority(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Client");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    public List<ClientBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }
}
