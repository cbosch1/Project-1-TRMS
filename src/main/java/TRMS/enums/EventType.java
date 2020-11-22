package TRMS.enums;

/**
 * The type of event that a reimbursement request is for.
 * Depending on the event, the application will go through
 * a slightly different life cycle.
 * <ul><li>Uni_Course - A university level course</li>
 * <li>Seminar - A conference or other meeting for training</li>
 * <li>Cert_Prep_Class - A class for the purpose of preparing the
 * attendees to take a certification. </li>
 * <li>Certification - A class/test that provides an attendee with
 * official documentation proving their success in the related subject</li>
 * <li>Technical_Training - A specific training process in which the attendee
 * acquires technical skills that would otherwise be difficult to acquire</li>
 * <li>Other - Any other event that the requester feels like would be 
 * applicable in their job requirements.</li></ul>
 */
public enum EventType {
    UNI_COURSE,
    SEMINAR,
    CERT_PREP_CLASS,
    CERTIFICATION,
    TECHNICAL_TRAINING,
    OTHER
}
