package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents a user of the application.
 * It holds personal details, login credentials and role information.
 *
 * Author: Rishabh Shrivastava
 */
public class UserBean extends BaseBean {

    /** First name of the user */
    private String firstName;

    /** Last name of the user */
    private String lastName;

    /** Login ID / Email ID of the user */
    private String login;

    /** Password for login */
    private String password;

    /** Confirm password (used during registration) */
    private String confirmPassword;

    /** Date of birth */
    private Date dob;

    /** Mobile number */
    private String mobileNo;

    /** Role ID (FK) */
    private long roleId;

    /** Gender */
    private String gender;

    /** Returns first name */
    public String getFirstName() {
        return firstName;
    }

    /** Sets first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Returns last name */
    public String getLastName() {
        return lastName;
    }

    /** Sets last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Returns login ID */
    public String getLogin() {
        return login;
    }

    /** Sets login ID */
    public void setLogin(String login) {
        this.login = login;
    }

    /** Returns password */
    public String getPassword() {
        return password;
    }

    /** Sets password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Returns confirm password */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /** Sets confirm password */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /** Returns date of birth */
    public Date getDob() {
        return dob;
    }

    /** Sets date of birth */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /** Returns mobile number */
    public String getMobileNo() {
        return mobileNo;
    }

    /** Sets mobile number */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /** Returns role ID */
    public long getRoleId() {
        return roleId;
    }

    /** Sets role ID */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /** Returns gender */
    public String getGender() {
        return gender;
    }

    /** Sets gender */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /** Dropdown key */
    @Override
    public String getKey() {
        return id + "";
    }

    /** Dropdown value */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}
