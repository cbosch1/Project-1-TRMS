package TRMS.controllers;

import java.time.LocalDateTime;
import java.util.List;

import TRMS.pojos.InfoRequest;
import io.javalin.http.Context;

public interface InfoRequestControl {

    public void createInfoRequest(Context ctx);

    public void readInfoRequest(Context ctx);

    public void readAllInfoFor(Context ctx);

    public void readAllInfoReq(Context ctx);

    public void updateInfoRequest(Context ctx);

    public void deleteInfoRequest(Context ctx);
}
