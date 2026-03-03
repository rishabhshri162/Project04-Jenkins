package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ClaimBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ClaimModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_claim");
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

    public long add(ClaimBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        ClaimBean existBean =
                findByClaimNumber(bean.getClaimNumber());

        if (existBean != null) {
            throw new DuplicateRecordException("Claim Number already exists");
        }

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_claim values(?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getClaimNumber());
            pstmt.setDouble(3, bean.getClaimAmount());
            pstmt.setString(4, bean.getStatus());
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

            throw new ApplicationException("Exception in adding Claim");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(ClaimBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        ClaimBean existBean =
                findByClaimNumber(bean.getClaimNumber());

        if (existBean != null &&
                existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Claim Number already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_claim set claim_number=?, claim_amount=?, status=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getClaimNumber());
            pstmt.setDouble(2, bean.getClaimAmount());
            pstmt.setString(3, bean.getStatus());
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

            throw new ApplicationException("Exception in updating Claim");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(ClaimBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_claim where id=?");

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

            throw new ApplicationException("Exception in deleting Claim");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public ClaimBean findByPk(long pk)
            throws ApplicationException {

        ClaimBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_claim where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ClaimBean();

                bean.setId(rs.getLong(1));
                bean.setClaimNumber(rs.getString(2));
                bean.setClaimAmount(rs.getDouble(3));
                bean.setStatus(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Claim by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public ClaimBean findByClaimNumber(String claimNumber)
            throws ApplicationException {

        ClaimBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_claim where claim_number=?");

            pstmt.setString(1, claimNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ClaimBean();

                bean.setId(rs.getLong(1));
                bean.setClaimNumber(rs.getString(2));
                bean.setClaimAmount(rs.getDouble(3));
                bean.setStatus(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Claim by Claim Number");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<ClaimBean> search(ClaimBean bean,
            int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        ArrayList<ClaimBean> list = new ArrayList<>();

        StringBuffer sql =
                new StringBuffer("select * from st_claim where 1=1");

        if (bean != null) {

            if (bean.getClaimNumber() != null
                    && bean.getClaimNumber().length() > 0) {
                sql.append(" and claim_number like '"
                        + bean.getClaimNumber() + "%'");
            }

            if (bean.getStatus() != null
                    && bean.getStatus().length() > 0) {
                sql.append(" and status like '"
                        + bean.getStatus() + "%'");
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

                ClaimBean bean1 = new ClaimBean();

                bean1.setId(rs.getLong(1));
                bean1.setClaimNumber(rs.getString(2));
                bean1.setClaimAmount(rs.getDouble(3));
                bean1.setStatus(rs.getString(4));
                bean1.setCreatedBy(rs.getString(5));
                bean1.setModifiedBy(rs.getString(6));
                bean1.setCreatedDatetime(rs.getTimestamp(7));
                bean1.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Claim");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}