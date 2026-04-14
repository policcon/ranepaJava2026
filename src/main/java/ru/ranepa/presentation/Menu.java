package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.service.HRMService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final HRMService service;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Menu(HRMService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            showMainMenu();
            int choice = readIntInput("Выберите опцию: ");

            switch (choice) {
                case 1:
                    showAllEmployees();
                    break;
                case 2:
                    addNewEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    findEmployeeById();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Exit");
    }

    private void showAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Нет сотрудников в базе.");
        } else {
            System.out.println("\n=== All Employees ===");
            employees.forEach(System.out::println);
        }
    }

    private void addNewEmployee() {
        System.out.println("\n=== Add New Employee ===");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        double salary = readDoubleInput("Enter salary: ");

        LocalDate hireDate = readDateInput("Enter hire date (yyyy-MM-dd): ");

        Employee employee = new Employee(name, position, salary, hireDate);
        Employee saved = service.addEmployee(employee);

        System.out.println("Employee added successfully with ID: " + saved.getId());
    }

    private void deleteEmployee() {
        if (service.isEmpty()) {
            System.out.println("Нет сотрудников для удаления.");
            return;
        }

        showAllEmployees();
        Long id = readLongInput("Enter employee ID to delete: ");

        if (service.deleteEmployee(id)) {
            System.out.println("Employee with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void findEmployeeById() {
        Long id = readLongInput("Enter employee ID: ");
        Employee employee = service.findEmployeeById(id);

        if (employee != null) {
            System.out.println("\n=== Employee Found ===");
            System.out.println(employee);
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void showStatistics() {
        if (service.isEmpty()) {
            System.out.println("Нет данных для статистики.");
            return;
        }

        System.out.println("\n=== Statistics ===");
        System.out.printf("Average salary: %.2f%n", service.getAverageSalary());

        Employee highest = service.getHighestPaidEmployee();
        if (highest != null) {
            System.out.println("Highest paid employee: " + highest.getName() +
                    " (Salary: " + highest.getSalary() + ")");
        }
    }

    // Вспомогательные методы для безопасного ввода
    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число.");
            }
        }
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число (например, 1500.50).");
            }
        }
    }

    private Long readLongInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число.");
            }
        }
    }

    private LocalDate readDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Пожалуйста, введите дату в формате yyyy-MM-dd (например, 2024-01-15).");
            }
        }
    }
}