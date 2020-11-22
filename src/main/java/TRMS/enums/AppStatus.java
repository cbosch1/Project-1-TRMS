package TRMS.enums;

/**
 * This enum represents the different status a
 * reimbursement request can hold along it's lifetime.
 * <ul><li>Pending - The application is in the process of being
 * approved by the various points of approval. Once
 * all parties have approved, the application becomes approved</li>
 * <li>Approved - The application has been approved, the request will
 * stay in this status for the rest of the time unless approval is revoked.
 * Funds will be dispersed once the event is completed.</li>
 * <li>Denied - The application has been denied and funds will not be dispersed.
 * The request will stay in this status for the rest of the time unless
 * this request is reviewed and moved to a different status.</li>
 * <li>Cancelled - The application has been cancelled by the requester,
 * funds will not be dispersed. The request will stay in this status for 
 * the rest of the time. An addition request will need to be made for another
 * attempt and reimbursement.</li></ul>
 */
public enum AppStatus {
    PENDING,
    APPROVED,
    DENIED,
    CANCELLED
}
