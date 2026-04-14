package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import java.time.LocalDate;

public class TestRunner {
    public static void main(String[] args) {
        HRMService service = new HRMService(new EmployeeRepository());

        // Тест 1: Добавление
        Employee e = service.addEmployee(new Employee("Иван", "Dev", 1000, LocalDate.now()));
        System.out.println("Добавлен ID: " + e.getId());

        // Тест 2: Средняя зарплата
        service.addEmployee(new Employee("Петр", "Dev", 2000, LocalDate.now()));
        service.addEmployee(new Employee("Сидр", "Dev", 3000, LocalDate.now()));
        System.out.println("Средняя зарплата: " + service.getAverageSalary());

        // Тест 3: Самый высокооплачиваемый
        System.out.println("Лучший: " + service.getHighestPaidEmployee().getName());

        // Тест 4: Удаление
        service.deleteEmployee(e.getId());
        System.out.println("Удалён ID " + e.getId() + ", найден: " + service.findEmployeeById(e.getId()));

        // Тест 5: Фильтр по должности
        System.out.println("Разработчиков: " + service.getEmployeesByPosition("Dev").size());
    }
}