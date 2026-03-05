package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SubscriptionPlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SubscriptionPlanModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_subscription_plan");

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

    public long add(SubscriptionPlanBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        SubscriptionPlanBean existBean =
                findByPlanName(bean.getPlanName());

        if (existBean != null) {
            throw new DuplicateRecordException("Plan Name already exists");
        }

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_subscription_plan values(?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getPlanName());
            pstmt.setDouble(3, bean.getPrice());
            pstmt.setInt(4, bean.getValidityDays());
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

            throw new ApplicationException("Exception in adding Subscription Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(SubscriptionPlanBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        SubscriptionPlanBean existBean =
                findByPlanName(bean.getPlanName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Plan Name already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_subscription_plan set plan_name=?, price=?, validity_days=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getPlanName());
            pstmt.setDouble(2, bean.getPrice());
            pstmt.setInt(3, bean.getValidityDays());
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

            throw new ApplicationException("Exception in updating Subscription Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(SubscriptionPlanBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_subscription_plan where id=?");

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

            throw new ApplicationException("Exception in deleting Subscription Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public SubscriptionPlanBean findByPk(long pk)
            throws ApplicationException {

        SubscriptionPlanBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_subscription_plan where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new SubscriptionPlanBean();

                bean.setId(rs.getLong(1));
                bean.setPlanName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setValidityDays(rs.getInt(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Plan by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public SubscriptionPlanBean findByPlanName(String planName)
            throws ApplicationException {

        SubscriptionPlanBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_subscription_plan where plan_name=?");

            pstmt.setString(1, planName);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new SubscriptionPlanBean();

                bean.setId(rs.getLong(1));
                bean.setPlanName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setValidityDays(rs.getInt(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Plan by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<SubscriptionPlanBean> search(SubscriptionPlanBean bean,
            int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        ArrayList<SubscriptionPlanBean> list = new ArrayList<>();

        StringBuffer sql =
                new StringBuffer("select * from st_subscription_plan where 1=1");

        if (bean != null) {

            if (bean.getPlanName() != null
                    && bean.getPlanName().length() > 0) {
                sql.append(" and plan_name like '" + bean.getPlanName() + "%'");
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

                SubscriptionPlanBean bean1 = new SubscriptionPlanBean();

                bean1.setId(rs.getLong(1));
                bean1.setPlanName(rs.getString(2));
                bean1.setPrice(rs.getDouble(3));
                bean1.setValidityDays(rs.getInt(4));
                bean1.setCreatedBy(rs.getString(5));
                bean1.setModifiedBy(rs.getString(6));
                bean1.setCreatedDatetime(rs.getTimestamp(7));
                bean1.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Plan");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}