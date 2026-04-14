package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HRMServiceTest {
    private HRMService service;

    @BeforeEach
    void setUp() {
        service = new HRMService(new EmployeeRepository());
    }

    @Test
    void testAverageSalary() {
        service.addEmployee(new Employee("Иван", "Dev", 1000, LocalDate.now()));
        service.addEmployee(new Employee("Петр", "Dev", 2000, LocalDate.now()));
        service.addEmployee(new Employee("Сидр", "Dev", 3000, LocalDate.now()));

        assertEquals(2000.0, service.getAverageSalary(), 0.01);
    }

    @Test
    void testHighestPaid() {
        service.addEmployee(new Employee("Иван", "Dev", 1000, LocalDate.now()));
        service.addEmployee(new Employee("Петр", "Manager", 5000, LocalDate.now()));

        Employee highest = service.getHighestPaidEmployee();
        assertEquals("Петр", highest.getName());
    }

    @Test
    void testFilterByPosition() {
        service.addEmployee(new Employee("Иван", "Dev", 1000, LocalDate.now()));
        service.addEmployee(new Employee("Петр", "QA", 2000, LocalDate.now()));

        List<Employee> devs = service.getEmployeesByPosition("Dev");
        assertEquals(1, devs.size());
    }

    @Test
    void testDeleteEmployee() {
        Employee e = service.addEmployee(new Employee("Тест", "Test", 1000, LocalDate.now()));
        assertTrue(service.deleteEmployee(e.getId()));
        assertNull(service.findEmployeeById(e.getId()));
    }
}