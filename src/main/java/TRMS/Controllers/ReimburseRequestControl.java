package TRMS.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import TRMS.Pojos.ReimburseRequest;
import TRMS.Pojos.Enums.*;
import io.javalin.http.Context;

public interface ReimburseRequestControl {

    public void createRequest(Context ctx);

    public void readRequest(Context ctx);

    public void readAllRequestsFor(Context ctx);

    public void readAllRequests(Context ctx);

    public void updateRequest(Context ctx);

    public void deleteRequest(Context ctx);
}
