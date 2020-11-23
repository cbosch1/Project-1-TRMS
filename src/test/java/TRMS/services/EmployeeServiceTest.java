package TRMS.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
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

import TRMS.daos.EmployeeDao;
import TRMS.pojos.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeDao mockDao;

	private EmployeeService serviceToTest;
	private Employee employee;
        
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		serviceToTest = new EmployeeServiceImpl(mockDao);
		employee = new Employee(2010, "Kent Mansley", "Abnormal Investigator", 0, "Governmental", false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createEmployeeTest() {
		try {
			employee.setEmployeeId(0);
			when(mockDao.createEmployee(employee)).thenReturn(employee.getEmployeeId());

			assertTrue("createEmployee returned false", 
				serviceToTest.createEmployee(employee.getName(), employee.getTitle(), employee.getSupervisor(),
								employee.getDepartment(), employee.getDeptHead()) == employee.getEmployeeId());

			verify(mockDao).createEmployee(employee);

		} catch (SQLException e) {
			fail("SQLException thrown by create method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by create method: " + e);
		}
	}

	@Test
	public void readEmployeeTest() {
		try {
			when(mockDao.readEmployee(employee.getEmployeeId())).thenReturn(employee);

			Employee returned = serviceToTest.readEmployee(employee.getEmployeeId());

			verify(mockDao).readEmployee(employee.getEmployeeId());

			assertTrue("Object read does not match expected", employee.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read method: " + e);
		}
	}

	@Test
	public void readAllEmployeesTest() {
		try {
			List<Employee> employees = new ArrayList<>();
			employees.add(employee);

			when(mockDao.readAllEmployees()).thenReturn(employees);

			List<Employee> returned = serviceToTest.readAllEmployees();

			verify(mockDao).readAllEmployees();

			assertTrue("Objects read do not match expected", employees.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read all method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read all method: " + e);
		}
	}

	@Test
	public void updateEmployeeTest() {
		try {
			when(mockDao.updateEmployee(employee)).thenReturn(true);

			assertTrue("updateEmployee returned false", 
				serviceToTest.updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getTitle(), 
											employee.getSupervisor(), employee.getDepartment(), employee.getDeptHead()));

			verify(mockDao).updateEmployee(employee);

		} catch (SQLException e) {
			fail("SQLException thrown by update method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by update method: " + e);
		}
	}

	@Test
	public void deleteEmployeeTest() {
		try {
			when(mockDao.deleteEmployee(employee.getEmployeeId())).thenReturn(true);

			assertTrue("deleteEmployee returned false", serviceToTest.deleteEmployee(employee.getEmployeeId()));

			verify(mockDao).deleteEmployee(employee.getEmployeeId());

		} catch (SQLException e) {
			fail("SQLException thrown by delete method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by delete method: " + e);
		}
	}
}
