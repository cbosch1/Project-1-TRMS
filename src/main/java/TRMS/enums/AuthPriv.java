package TRMS.enums;

/**
 * The level of access privilege a user has regarding their
 * use of this application. Each privilege gives unique access
 * to certain elements as needed by that user's position within
 * the company.
 * <ul><li>Employee</li>
 * <li>Supervisor</li>
 * <li>Dept_Head</li>
 * <li>Benco</li>
 * <li>Admin</li></ul> 
 */
public enum AuthPriv {
    EMPLOYEE,
    SUPERVISOR,
    DEPT_HEAD,
    BENCO,
    ADMIN
}
