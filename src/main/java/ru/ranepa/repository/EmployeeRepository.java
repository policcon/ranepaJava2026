package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    private Map<Long, Employee> employees = new HashMap<>();
    private long nextId = 1;

    // Сохранение нового сотрудника (автоматически генерирует ID)
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId++);
        }
        employees.put(employee.getId(), employee);
        return employee;
    }

    // Получить всех сотрудников
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    // Поиск по ID
    public Employee findById(Long id) {
        return employees.get(id);
    }

    // Удаление по ID
    public boolean delete(Long id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            return true;
        }
        return false;
    }

    // Проверка существования сотрудника
    public boolean existsById(Long id) {
        return employees.containsKey(id);
    }
}