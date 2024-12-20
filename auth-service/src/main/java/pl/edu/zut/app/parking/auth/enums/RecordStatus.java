package pl.edu.zut.app.parking.auth.enums;

/**
 * Defines the status of any given record.
 */
public enum RecordStatus {
    /**
     * The record is active and visible
     */
    ACTIVE,

    /**
     * The record is deleted and not visible
     */
    DELETED,

    /**
     * The record is hidden i.e., it's inactive but not deleted
     */
    HIDDEN
}
