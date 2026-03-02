package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BoardingPassBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BoardingPassModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_boarding_pass");
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

    public long add(BoardingPassBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        BoardingPassBean existBean = findBySeatNumber(bean.getSeatNumber());
        if (existBean != null) {
            throw new DuplicateRecordException("Seat Number already exists");
        }

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_boarding_pass values(?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getSeatNumber());
            pstmt.setString(3, bean.getGate());
            pstmt.setTimestamp(4, new java.sql.Timestamp(bean.getBoardingTime().getTime()));
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

            throw new ApplicationException("Exception in adding Boarding Pass");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(BoardingPassBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        BoardingPassBean existBean = findBySeatNumber(bean.getSeatNumber());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Seat Number already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_boarding_pass set seat_number=?, gate=?, boarding_time=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSeatNumber());
            pstmt.setString(2, bean.getGate());
            pstmt.setTimestamp(3, new java.sql.Timestamp(bean.getBoardingTime().getTime()));
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

            throw new ApplicationException("Exception in updating Boarding Pass");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(BoardingPassBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_boarding_pass where id=?");

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

            throw new ApplicationException("Exception in deleting Boarding Pass");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public BoardingPassBean findByPk(long pk) throws ApplicationException {

        BoardingPassBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_boarding_pass where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new BoardingPassBean();

                bean.setId(rs.getLong(1));
                bean.setSeatNumber(rs.getString(2));
                bean.setGate(rs.getString(3));
                bean.setBoardingTime(rs.getTimestamp(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Boarding Pass by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public BoardingPassBean findBySeatNumber(String seatNumber)
            throws ApplicationException {

        BoardingPassBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_boarding_pass where seat_number=?");

            pstmt.setString(1, seatNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new BoardingPassBean();

                bean.setId(rs.getLong(1));
                bean.setSeatNumber(rs.getString(2));
                bean.setGate(rs.getString(3));
                bean.setBoardingTime(rs.getTimestamp(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Boarding Pass by Seat Number");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<BoardingPassBean> search(BoardingPassBean bean,
            int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        ArrayList<BoardingPassBean> list = new ArrayList<>();

        StringBuffer sql =
                new StringBuffer("select * from st_boarding_pass where 1=1");

        if (bean != null) {

            if (bean.getSeatNumber() != null
                    && bean.getSeatNumber().length() > 0) {
                sql.append(" and seat_number like '"
                        + bean.getSeatNumber() + "%'");
            }

            if (bean.getGate() != null
                    && bean.getGate().length() > 0) {
                sql.append(" and gate like '"
                        + bean.getGate() + "%'");
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

                BoardingPassBean bean1 = new BoardingPassBean();

                bean1.setId(rs.getLong(1));
                bean1.setSeatNumber(rs.getString(2));
                bean1.setGate(rs.getString(3));
                bean1.setBoardingTime(rs.getTimestamp(4));
                bean1.setCreatedBy(rs.getString(5));
                bean1.setModifiedBy(rs.getString(6));
                bean1.setCreatedDatetime(rs.getTimestamp(7));
                bean1.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Boarding Pass");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}