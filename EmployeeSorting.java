package assessment2;

import java.util.*;

class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public int compareTo(Employee e) {
        int deptCompare = this.department.compareTo(e.department);
        if (deptCompare != 0) return deptCompare;

        int nameCompare = this.name.compareTo(e.name);
        if (nameCompare != 0) return nameCompare;

        return Double.compare(this.salary, e.salary); // ascending
    }

    @Override
    public String toString() {
        return id + "  " + name + "  " + department + "  " + salary;
    }
}

class SalaryDescComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Double.compare(e2.getSalary(), e1.getSalary()); // descending
    }
}

public class EmployeeSorting {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Anushka", "HR", 45000));
        employees.add(new Employee(2, "Ravi", "IT", 60000));
        employees.add(new Employee(3, "Meena", "Finance", 55000));
        employees.add(new Employee(4, "Amit", "IT", 60000));
        employees.add(new Employee(5, "Priya", "HR", 50000));

        System.out.println("Original List:");
        for (Employee e : employees) {
            System.out.println(e);
        }

        Collections.sort(employees);
        System.out.println("\nSorted by Department → Name → Salary (Comparable):");
        Iterator<Employee> it1 = employees.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }

        Collections.sort(employees, new SalaryDescComparator());
        System.out.println("\nSorted by Salary Descending (Comparator):");
        Iterator<Employee> it2 = employees.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
    }
}
