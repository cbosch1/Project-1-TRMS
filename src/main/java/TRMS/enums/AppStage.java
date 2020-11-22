package TRMS.enums;

/**
 * This enum represents the different stages of where a
 * reimbursement request can be along it's lifetime.
 * <ul><li>Upload - the application is in the process of being
 * uploaded, maybe all the requirements haven't been met. Once
 * the request's information is complete it moves on</li>
 * <li>Supervisor - The Requester's direct supervisor is 
 * reviewing their application and has not approved it yet.</li>
 * <li>Dept_Head - The Requester's department head is 
 * reviewing their application and has not approved it yet.</li>
 * <li>Benco - The Benefits Coordinator who has been assigned this
 * request is still reviewing this application and has not approved it yet.</li>
 * <li>Event - The application has been approved by the previous 
 * parties but the event which this request is for has not been completed yet.</li>
 * <li>End - The end of the application life-cycle, either this request has been
 * approved, the event has been completed, and funds will be provided, or
 * the request has been denied or cancelled</li></ul>
 */
public enum AppStage {
    UPLOAD,
    SUPERVISOR,
    DEPT_HEAD,
    BENCO,
    EVENT,
    END
}
