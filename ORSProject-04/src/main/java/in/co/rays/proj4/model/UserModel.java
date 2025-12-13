package in.co.rays.proj4.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.EmailBuilder;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * UserModel handles all database operations related to User entities.
 * It provides functionality for adding, updating, deleting, searching,
 * authenticating, registering, and managing passwords of users.
 *
 * This model uses JDBC for communication with the st_user table and
 * maps data to/from UserBean objects.
 *
 * @author  Rishabh Shrivastava
 * @version 1.0
 */
public class UserModel {

    /**
     * Returns next primary key for st_user table.
     *
     * @return next available primary key
     * @throws DatabaseException if any database error occurs
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_user");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception: Exception in getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new user record.
     *
     * @param bean UserBean containing user details
     * @return generated primary key
     * @throws ApplicationException if application-level error occurs
     * @throws DuplicateRecordException if login ID already exists
     */
    public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        UserBean existBean = findByLogin(bean.getLogin());
        if (existBean != null) {
            throw new DuplicateRecordException("User already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn
                    .prepareStatement("insert into st_user values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getFirstName());
            pstmt.setString(3, bean.getLastName());
            pstmt.setString(4, bean.getLogin());
            pstmt.setString(5, bean.getPassword());
            pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(7, bean.getMobileNo());
            pstmt.setLong(8, bean.getRoleId());
            pstmt.setString(9, bean.getGender());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDatetime());
            pstmt.setTimestamp(13, bean.getModifiedDatetime());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add User");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Deletes a user record.
     *
     * @param bean UserBean containing user ID
     * @throws ApplicationException if delete fails
     */
    public void delete(UserBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete User");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Updates a user record.
     *
     * @param bean UserBean containing updated details
     * @throws DuplicateRecordException if login ID already exists
     * @throws ApplicationException if update fails
     */
    public void update(UserBean bean)
            throws DuplicateRecordException, ApplicationException {

        Connection conn = null;

        UserBean existBean = findByLogin(bean.getLogin());
        if (existBean != null && (existBean.getId() != bean.getId())) {
            throw new DuplicateRecordException("Login Id is already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_user set first_name = ?, last_name = ?, login = ?, password = ?, dob = ?, mobile_no = ?, "
                            + "role_id = ?, gender = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, bean.getFirstName());
            pstmt.setString(2, bean.getLastName());
            pstmt.setString(3, bean.getLogin());
            pstmt.setString(4, bean.getPassword());
            pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setLong(7, bean.getRoleId());
            pstmt.setString(8, bean.getGender());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());
            pstmt.setLong(13, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating User ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a user by primary key.
     *
     * @param pk primary key
     * @return UserBean if found, otherwise null
     * @throws ApplicationException if error occurs
     */
    public UserBean findByPk(long pk) throws ApplicationException {

        UserBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id = ?");
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds a user by login ID.
     *
     * @param login user login ID
     * @return UserBean if found, else null
     * @throws ApplicationException if database error occurs
     */
    public UserBean findByLogin(String login) throws ApplicationException {

        UserBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ?");
            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting User by login");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Lists all users.
     *
     * @return list of all users
     * @throws ApplicationException if error occurs
     */
    public List<UserBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches users based on criteria.
     *
     * @param bean search criteria
     * @param pageNo page number
     * @param pageSize number of records per page
     * @return list of matched users
     * @throws ApplicationException if search fails
     */
    public List<UserBean> search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        ArrayList<UserBean> list = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0)
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");
            if (bean.getLastName() != null && bean.getLastName().length() > 0)
                sql.append(" and last_name like '" + bean.getLastName() + "%'");
            if (bean.getLogin() != null && bean.getLogin().length() > 0)
                sql.append(" and login like '" + bean.getLogin() + "%'");
            if (bean.getPassword() != null && bean.getPassword().length() > 0)
                sql.append(" and password like '" + bean.getPassword() + "%'");
            if (bean.getDob() != null && bean.getDob().getTime() > 0)
                sql.append(" and dob like '" +  new java.sql.Date(bean.getDob().getTime()) + "%'");
            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0)
                sql.append(" and mobile_no like '" + bean.getMobileNo() + "%'");
            if (bean.getRoleId() > 0)
                sql.append(" and role_id = " + bean.getRoleId());
            if (bean.getGender() != null && bean.getGender().length() > 0)
                sql.append(" and gender like '" + bean.getGender() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search user");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Authenticates user credentials.
     *
     * @param login login ID
     * @param password password
     * @return UserBean if valid, else null
     * @throws ApplicationException if authentication fails
     */
    public UserBean authenticate(String login, String password) throws ApplicationException {

        UserBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ? and password = ?");
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new UserBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setLogin(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setRoleId(rs.getLong(8));
                bean.setGender(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in authentication");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Changes the user's password.
     *
     * @param id user ID
     * @param oldPassword old password
     * @param newPassword new password
     * @return true if update successful
     * @throws RecordNotFoundException if old password is wrong
     * @throws ApplicationException if database error occurs
     */
    public boolean changePassword(Long id, String oldPassword, String newPassword)
            throws RecordNotFoundException, ApplicationException {

        boolean flag = false;

        UserBean beanExist = findByPk(id);

        if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
            beanExist.setPassword(newPassword);

            try {
                update(beanExist);
                flag = true;

            } catch (DuplicateRecordException e) {
                throw new ApplicationException("Login Id already exist");
            }

        } else {
            throw new RecordNotFoundException("Old Password is Invalid");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("login", beanExist.getLogin());
        map.put("password", beanExist.getPassword());
        map.put("firstName", beanExist.getFirstName());
        map.put("lastName", beanExist.getLastName());

        String message = EmailBuilder.getChangePasswordMessage(map);

        EmailMessage msg = new EmailMessage();
        msg.setTo(beanExist.getLogin());
        msg.setSubject("ORSProject-04 Password has been changed Successfully.");
        msg.setMessage(message);
        msg.setMessageType(EmailMessage.HTML_MSG);

        EmailUtility.sendMail(msg);

        return flag;
    }

    /**
     * Sends the user's password to their email.
     *
     * @param login user's login ID
     * @return true if email successfully sent
     * @throws RecordNotFoundException if login does not exist
     * @throws ApplicationException if mail or DB error occurs
     */
    public boolean forgetPassword(String login)
            throws RecordNotFoundException, ApplicationException {

        UserBean userData = findByLogin(login);
        boolean flag = false;

        if (userData == null) {
            throw new RecordNotFoundException("Email ID does not exists..!!");
        }

        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("login", userData.getLogin());
            map.put("password", userData.getPassword());
            map.put("firstName", userData.getFirstName());
            map.put("lastName", userData.getLastName());

            String message = EmailBuilder.getForgetPasswordMessage(map);

            EmailMessage msg = new EmailMessage();
            msg.setTo(login);
            msg.setSubject("ORSProject-04 Password Reset");
            msg.setMessage(message);
            msg.setMessageType(EmailMessage.HTML_MSG);

            EmailUtility.sendMail(msg);
            flag = true;

        } catch (Exception e) {
            throw new ApplicationException("Please check your internet connection..!!");
        }
        return flag;
    }

    /**
     * Registers a new user and sends a confirmation email.
     *
     * @param bean UserBean containing registration details
     * @return primary key of newly registered user
     * @throws DuplicateRecordException if login already exists
     * @throws ApplicationException if registration fails
     */
    public long registerUser(UserBean bean)
            throws DuplicateRecordException, ApplicationException {

        long pk = add(bean);

        HashMap<String, String> map = new HashMap<>();
        map.put("login", bean.getLogin());
        map.put("password", bean.getPassword());

        String message = EmailBuilder.getUserRegistrationMessage(map);

        EmailMessage msg = new EmailMessage();
        msg.setTo(bean.getLogin());
        msg.setSubject("Registration is successful for ORSProject-04");
        msg.setMessage(message);
        msg.setMessageType(EmailMessage.HTML_MSG);

        EmailUtility.sendMail(msg);

        return pk;
    }
}