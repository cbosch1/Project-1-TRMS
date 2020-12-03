package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.pojos.Employee;
import TRMS.services.EmployeeService;
import io.javalin.http.Context;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControlTest {

	@Mock
	private EmployeeService mockService;
	@Mock
	private Context mockCtx;

	private EmployeeControl controlToTest;
	private Employee employee;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new EmployeeControl(mockService, null);
		employee = new Employee(2010, "Billy Bob", "Highest of the Hunters", 1, "Hunting", false);

		when(mockCtx.formParam("employeeId")).thenReturn(Integer.toString(employee.getEmployeeId()));
		when(mockCtx.formParam("name")).thenReturn(employee.getName());
		when(mockCtx.formParam("title")).thenReturn(employee.getTitle());
		when(mockCtx.formParam("supervisor")).thenReturn(Integer.toString(employee.getSupervisor()));
		when(mockCtx.formParam("department")).thenReturn(employee.getDepartment());
		when(mockCtx.formParam("deptHead")).thenReturn(Boolean.toString(employee.getDeptHead()));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createEmployeeTest() {
		try {
			controlToTest.createEmployee(mockCtx);

			verify(mockCtx).formParam("name");
			verify(mockCtx).formParam("title");
			verify(mockCtx).formParam("supervisor");
			verify(mockCtx).formParam("department");
			verify(mockCtx).formParam("deptHead");

			verify(mockService).createEmployee(employee.getName(), employee.getTitle(), employee.getSupervisor(),
												employee.getDepartment(), employee.getDeptHead());
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void readEmployeeTest() {
		try {
			controlToTest.readEmployee(mockCtx);

			verify(mockCtx).formParam("employeeId");
			verify(mockService).readEmployee(employee.getEmployeeId());

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void readAllEmployeesTest() {
		try {
			List<Employee> employees = new ArrayList<>();
			employees.add(employee);

			when(mockService.readAllEmployees()).thenReturn(employees);

			controlToTest.readAllEmployees(mockCtx);
			verify(mockService).readAllEmployees();

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read all test: " + e);
		}
	}

	@Test
	public void updateEmployeeTest() {
		try {
			when(mockService.updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getTitle(), 
											employee.getSupervisor(), employee.getDepartment(), 
											employee.getDeptHead())).thenReturn(true);

			controlToTest.updateEmployee(mockCtx);
	
			verify(mockCtx).formParam("employeeId");
			verify(mockCtx).formParam("name");
			verify(mockCtx).formParam("title");
			verify(mockCtx).formParam("supervisor");
			verify(mockCtx).formParam("department");
			verify(mockCtx).formParam("deptHead");
	
			verify(mockService).updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getTitle(), 
												employee.getSupervisor(), employee.getDepartment(), employee.getDeptHead());

			verify(mockCtx).status(200);
	
		} catch (Exception e) {
			fail("Exception thrown during update test: " + e);
		}
	}

	@Test
	public void deleteEmployeeTest() {
		try {
			when(mockService.deleteEmployee(employee.getEmployeeId())).thenReturn(true);

			controlToTest.deleteEmployee(mockCtx);

			verify(mockCtx).formParam("employeeId");
			verify(mockService).deleteEmployee(employee.getEmployeeId());

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
