package ru.ranepa;

import ru.ranepa.presentation.Menu;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;

public class HrmApplication {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        HRMService service = new HRMService(repository);
        Menu menu = new Menu(service);

        menu.start();
    }
}
