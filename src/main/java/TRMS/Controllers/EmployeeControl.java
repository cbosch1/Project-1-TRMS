package TRMS.controllers;

import io.javalin.http.Context;

public interface EmployeeControl {

    public void createEmployee(Context ctx);

    public void readEmployee(Context ctx);

    public void readAllEmployees(Context ctx);

    public void updateEmployee(Context ctx);

    public void deleteEmployee(Context ctx);
}
