package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * BaseBean is the superclass for all beans in the project.
 * It provides common audit fields like id, createdBy, modifiedBy,
 * and timestamps for creation and modification.
 *
 * @author  
 *         Rishabh Shrivastava
 * @version 1.0
 */
public abstract class BaseBean implements DropdownListBean {

    /** Primary Key */
    protected long id;

    /** Created By User */
    protected String createdBy;

    /** Modified By User */
    protected String modifiedBy;

    /** Timestamp when record was created */
    protected Timestamp createdDatetime;

    /** Timestamp when record was last modified */
    protected Timestamp modifiedDatetime;

    /** Returns primary key */
    public long getId() {
        return id;
    }

    /** Sets primary key */
    public void setId(long id) {
        this.id = id;
    }

    /** Returns created-by username */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets created-by username */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Returns modified-by username */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /** Sets modified-by username */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /** Returns creation timestamp */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /** Sets creation timestamp */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /** Returns last modified timestamp */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /** Sets last modified timestamp */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }
}
