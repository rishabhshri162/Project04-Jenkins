package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ResourceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ResourceModel {


    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_resource");
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

    public long add(ResourceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        // UNIQUE check
        ResourceBean existBean = findByCode(bean.getResourceCode());
        if (existBean != null) {
            throw new DuplicateRecordException("Resource Code already exists");
        }

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_resource values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getResourceCode());
            pstmt.setString(3, bean.getResourceName());
            pstmt.setString(4, bean.getResourceType());
            pstmt.setString(5, bean.getResourceStatus());
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

            throw new ApplicationException("Exception in adding Resource");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }


    public void update(ResourceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        ResourceBean existBean = findByCode(bean.getResourceCode());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Resource Code already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_resource set resource_code=?, resource_name=?, resource_type=?, resource_status=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getResourceCode());
            pstmt.setString(2, bean.getResourceName());
            pstmt.setString(3, bean.getResourceType());
            pstmt.setString(4, bean.getResourceStatus());
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

            throw new ApplicationException("Exception in updating Resource");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }


    public void delete(ResourceBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_resource where id=?");

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

            throw new ApplicationException("Exception in deleting Resource");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }


    public ResourceBean findByPk(long pk)
            throws ApplicationException {

        ResourceBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_resource where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ResourceBean();

                bean.setId(rs.getLong(1));
                bean.setResourceCode(rs.getString(2));
                bean.setResourceName(rs.getString(3));
                bean.setResourceType(rs.getString(4));
                bean.setResourceStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Resource by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }


    public ResourceBean findByCode(String code)
            throws ApplicationException {

        ResourceBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_resource where resource_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ResourceBean();

                bean.setId(rs.getLong(1));
                bean.setResourceCode(rs.getString(2));
                bean.setResourceName(rs.getString(3));
                bean.setResourceType(rs.getString(4));
                bean.setResourceStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Resource by Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }


    public List<ResourceBean> search(ResourceBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        ArrayList<ResourceBean> list = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from st_resource where 1=1");

        if (bean != null) {

            if (bean.getResourceCode() != null && bean.getResourceCode().length() > 0) {
                sql.append(" and resource_code like '" + bean.getResourceCode() + "%'");
            }

            if (bean.getResourceName() != null && bean.getResourceName().length() > 0) {
                sql.append(" and resource_name like '" + bean.getResourceName() + "%'");
            }

            if (bean.getResourceStatus() != null && bean.getResourceStatus().length() > 0) {
                sql.append(" and resource_status like '" + bean.getResourceStatus() + "%'");
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

                bean = new ResourceBean();

                bean.setId(rs.getLong(1));
                bean.setResourceCode(rs.getString(2));
                bean.setResourceName(rs.getString(3));
                bean.setResourceType(rs.getString(4));
                bean.setResourceStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Resource");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
