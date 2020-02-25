import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final Scanner scanner;

    Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        //  Enter Task number -> First run, next runs Ctrl+F5 //
        int currentTask = 2
                ;

        switch (currentTask){
            case 2 :
                System.out.println("2. Remove Objects");
                this.removeObjectsEx(); break;
            case 3 :
                System.out.println("3. Contains Employee");
                this.containsEmployeeEx(); break;
            case 4 :
                System.out.println("4. Employees with Salary Over 50 000");
                this.employeesWithSalaryOver50000Ex(); break;
            case 5 :
                System.out.println("5. Employees from Department");
                this.employeesFromDepartmentEx(); break;
            case 6 :
                System.out.println("6. Adding a New Address and Updating Employee");
                this.addingNewAddressAndUpdatingEmployeeEx(); break;
            case 7 :
                System.out.println("7. Addresses with Employee Count");
                this.addressesWithEmployeeCountEx(); break;
            case 8 :
                System.out.println("8. Get Employee with Project");
                this.getEmployeeWithProjectEx(); break;
            case 9 :
                System.out.println("9. Find Latest 10 Projects");
                this.findLatest10ProjectsEx(); break;
            case 10 :
                System.out.println("10. Increase Salaries");
                this.increaseSalariesEx(); break;
            case 11 :
                System.out.println("11. Remove Towns");
                this.removeTownsEx(); break;
            case 12 :
                System.out.println("12. Find Employees by First Name");
                this.findEmployeesByFirstNameEx(); break;
            case 13 :
                System.out.println("13. Employees Maximum Salaries");
                this.employeesMaximumSalariesEx(); break;
        }
    }

    private String getInput(String print, String defaultValue){
        if (!print.isBlank()){
            System.out.printf("%s%s",
                    print,
                    defaultValue == null ? "" : String.format(" <%s> :", defaultValue));
        }
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue == null ? "" : defaultValue : input;
    }

    private void removeObjectsEx(){
        List<Town> townsDetach = entityManager
                .createQuery("SELECT t FROM Town t WHERE length(t.name) > 5", Town.class)
                .getResultList();

        entityManager.getTransaction().begin();

        townsDetach.forEach(this.entityManager::detach);

        entityManager
                .createQuery("SELECT t FROM Town t", Town.class)
                .getResultList()
                .forEach(town -> town.setName(town.getName().toLowerCase()));

        townsDetach.forEach(this.entityManager::merge);

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private void containsEmployeeEx(){
        String fullName = getInput("Enter employee full name", "Svetlin Nakov");

        try {
            this.entityManager
                    .createQuery("SELECT e FROM Employee e " +
                            "WHERE concat(e.firstName, ' ', e.lastName) =:name ", Employee.class)
                    .setParameter("name", fullName)
                    .getSingleResult();

            System.out.println("Yes");

        } catch (NoResultException e){
            System.out.println("No");
        }
    }

    private void employeesWithSalaryOver50000Ex(){
        entityManager
                .createQuery("SELECT e FROM Employee  e WHERE e.salary>50000", Employee.class)
                .getResultList()
                .forEach(employee -> System.out.println(employee.getFirstName()));
    }

    private void employeesFromDepartmentEx(){
        Department department = entityManager
                .createQuery("select d from Department d where d.name=:name", Department.class)
                .setParameter("name", "Research and Development")
                .getSingleResult();

        entityManager
                .createQuery("select e from Employee e where e.department="
                        + department.getId()+
                        " order by e.salary, e.id", Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s from %s - $%.2f%n",
                        e.getFirstName() + " " + e.getLastName(),
                        department.getName(),
                        e.getSalary())
                );
    }

    private void addingNewAddressAndUpdatingEmployeeEx(){
        String lastName = getInput("Enter last name", "Tamburello");
        String ADDRESS_TEXT = "Vitoshka 15";
        String TOWN_NAME = "Sofia";

        Town town;
        try {
            town = entityManager
                    .createQuery("select t from Town t where t.name like :townName", Town.class)
                    .setParameter("townName", TOWN_NAME)
                    .getSingleResult();

        } catch (NoResultException ex){
            town = new Town();
            town.setName(TOWN_NAME);
            entityManager.getTransaction().begin();
            entityManager.persist(town);
            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Added new town : " + TOWN_NAME);
        }

        Address address;
        try {
            address = entityManager
                    .createQuery("select a from Address  a where a.text = :address", Address.class)
                    .setParameter("address", ADDRESS_TEXT)
                    .getSingleResult();

        } catch (NoResultException ex){
            entityManager.getTransaction().begin();
            address = new Address();
            address.setText(ADDRESS_TEXT);
            address.setTown(town);
            entityManager.persist(address);
            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Added new address : " + ADDRESS_TEXT);
        }

        try {
            Employee employee = entityManager
                    .createQuery("select e from Employee  e where e.lastName=:lastName", Employee.class)
                    .setParameter("lastName", lastName)
                    .getSingleResult();

            entityManager.getTransaction().begin();
            employee.setAddress(address);
            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.printf("%s %s has a new address.", employee.getFirstName(), employee.getLastName());

        } catch (NoResultException ex){
            System.out.println("Not found employee with last name: " + lastName);
        }
    }

    private void addressesWithEmployeeCountEx(){
        try {
            entityManager
                    .createQuery("select a from Address a order by a.employees.size desc", Address.class)
                    .setMaxResults(10)
                    .getResultList()
                    .forEach(address -> System.out.printf("%n%s, %s - %d employees",
                            address.getText(),
                            address.getTown().getName(),
                            address.getEmployees().size())
                    );
        } catch (NoResultException ex){
            System.out.println("Please reload the database.");
        }
    }

    private void getEmployeeWithProjectEx(){

        String input = getInput("Enter employee id", "147");
        int employeeId = Integer.parseInt(input);

        try {
            Employee employee = entityManager.find(Employee.class, employeeId);
            System.out.printf("%n%s %s - %s",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle());

            employee
                    .getProjects()
                    .stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(p -> System.out.printf("%n\t\t%s", p.getName()));

        } catch (NoResultException ex){
            System.out.println("Please reload the database.");
        }
    }

    private void findLatest10ProjectsEx(){
        try {
            entityManager
                    .createQuery("select p from Project p where p.endDate is null order by p.startDate desc", Project.class)
                    .setMaxResults(10)
                    .getResultList()
                    .stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(project ->
                            System.out.printf(
                                    "Project name: %s%n"
                                            +"\tProject Description: %s%n"
                                            +"\tProject Start Date:%s%n"
                                            +"\tProject End Date: %s%n",
                                    project.getName(),
                                    project.getDescription(),
                                    project.getStartDate(),
                                    project.getEndDate())
                    );

        }catch (NoResultException ex){
            System.out.println("Not found projects.");
        }
    }

    private void increaseSalariesEx(){
        List<String> DEPARTMENTS =
                Arrays.asList("Engineering", "Tool Design", "Marketing or Information Services");

        BigDecimal MULTIPLICAND = new BigDecimal("1.12");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            List<Employee> employees = entityManager
                    .createQuery("select e from Employee e where e.department.name in(:departments)", Employee.class)
                    .setParameter("departments", DEPARTMENTS)
                    .getResultList();

            entityManager.getTransaction().begin();
            employees.forEach(e -> e.setSalary(e.getSalary().multiply(MULTIPLICAND)));
            entityManager.flush();
            entityManager.getTransaction().commit();

            employees.forEach(employee ->
                    System.out.printf("%n%s %s ($%s)",
                            employee.getFirstName(),
                            employee.getLastName(),
                            decimalFormat.format(employee.getSalary())
                    )
            );

        } catch (NoResultException ex){
            System.out.println("Please reload the database.");
        }
    }

    private void removeTownsEx(){
        String townName = getInput("Enter town name", "Sofia");

        Town town;
        try {
            town = entityManager
                    .createQuery("select t from Town  t where t.name like :name", Town.class)
                    .setParameter("name", townName)
                    .getSingleResult();

        } catch (NoResultException ex){
            System.out.println("Please reload the database.");
            return;
        }

        try {
            List<Address> addresses = entityManager
                    .createQuery("select a from Address a where a.town=:town", Address.class)
                    .setParameter("town", town)
                    .getResultList();

            List<Employee> employees = entityManager
                    .createQuery("select e from Employee e where e.address in(:addresses)", Employee.class)
                    .setParameter("addresses", addresses)
                    .getResultList();

            entityManager.getTransaction().begin();

            employees.forEach(employee -> employee.setAddress(null));
            addresses.forEach(entityManager::remove);
            entityManager.remove(town);

            entityManager.flush();
            entityManager.getTransaction().commit();

            System.out.printf("%n%d "+(addresses.size()==1 ? "address" : "addresses")+" in %s deleted",
                    addresses.size(),
                    town.getName()
            );

        }catch (NoResultException ex){
            System.out.println("Please reload the database.");
        }
    }

    private void findEmployeesByFirstNameEx(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String pattern = getInput("Enter start pattern", "SA");

        try {
            entityManager
                    .createQuery("select e from Employee e where e.firstName like :pattern", Employee.class)
                    .setParameter("pattern", pattern + "%")
                    .getResultList()
                    .forEach(employee -> System.out.printf("%n%s %s - %s - ($%s)",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getJobTitle(),
                            decimalFormat.format(employee.getSalary()))
                    );
        }catch (NoResultException ex){
            System.out.println("Not found results.");
        }
    }

    private void employeesMaximumSalariesEx(){
        BigDecimal FROM_SALARY = new BigDecimal(30000);
        BigDecimal TO_SALARY = new BigDecimal(70000);

        try {
            TypedQuery<Object[]> query = entityManager
                    .createQuery("select e, max(e.salary) from Employee e "+
                                    "join Department d on e.department = d "+
                                    "group by d "+
                                    "having max(e.salary) not between :min and :max",
                            Object[].class)
                    .setParameter("min", FROM_SALARY)
                    .setParameter("max", TO_SALARY);

            List<Object[]> resultList = query.getResultList();

            resultList.forEach(result -> {
                Employee employee = (Employee) result[0];
                BigDecimal maxSalary = (BigDecimal) result[1];

                System.out.printf("%n%s %s",
                        employee.getDepartment().getName(),
                        maxSalary
                );
            });

        }catch (NoResultException ex){
            System.out.println("Not found results.");
        }
    }
}
