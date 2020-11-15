package TRMS.controllers;

import TRMS.pojos.User;
import TRMS.enums.AuthPriv;
import io.javalin.http.Context;

public interface UserControl {
    
    public void createUser(Context ctx);

    public void readUser(Context ctx);

    public void updateUser(Context ctx);

    public void deleteUser(Context ctx);
}