package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HRMService {
    private EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Добавить сотрудника
    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    // Удалить сотрудника
    public boolean deleteEmployee(Long id) {
        return repository.delete(id);
    }

    // Найти сотрудника по ID
    public Employee findEmployeeById(Long id) {
        return repository.findById(id);
    }

    // Получить всех сотрудников
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Средняя зарплата
    public double getAverageSalary() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return 0;
        }
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    // Самый высокооплачиваемый сотрудник
    public Employee getHighestPaidEmployee() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return null;
        }
        return employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElse(null);
    }

    // Фильтрация по должности
    public List<Employee> getEmployeesByPosition(String position) {
        return repository.findAll().stream()
                .filter(e -> e.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }

    // Проверка, есть ли сотрудники
    public boolean isEmpty() {
        return repository.findAll().isEmpty();
    }
}

