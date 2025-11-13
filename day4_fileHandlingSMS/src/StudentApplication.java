package assessment2.day4_fileHandlingSMS.src;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

// ----- Custom Exceptions -----
class InvalidAgeException extends Exception {
    public InvalidAgeException(String msg) {
        super(msg);
    }
}


class InvalidMarksException extends Exception{
    public InvalidMarksException(String msg){
        super(msg);
    }
}
// -----------------------------


class Student{
    int id;
    String name;
    int class_id;
    int marks;
    char gender;
    int age;

    public Student(int id, String name, int class_id, int marks, char gender, int age) {
        this.id = id;
        this.name = name;
        this.class_id = class_id;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return " id - " + id + " name- " + name + " class_id- " + class_id +
                " Marks- " + marks + " gender- " + gender + " age- " + age;
    }
}

class Class{
    int id;
    String name;

    public Class(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id- " + id + " Name- "+ name;
    }
}

class Address{
    int id;
    int pin_code;
    String city;
    int student_id;

    public Address(int id, int pin_code, String city, int student_id) {
        this.id = id;
        this.pin_code = pin_code;
        this.city = city;
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Id- " + id + " Pin_code- "+ pin_code +" City- "+ city + " Student id- "+ student_id;
    }
}

public class StudentApplication {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Class> classList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Address> addressList = new ArrayList<>();

        char option;
        do {
            System.out.println("a. Insert New Class Details ");
            System.out.println("b. Insert Student Details ");
            System.out.println("c. Insert Address Details ");
            System.out.println("d. Show All Student records ");
            System.out.println("e. Passing Status ");
            System.out.println("f. Overall Ranking ");
            System.out.println("g. Find Students by Pin code ");
            System.out.println("h. Find Students by City ");
            System.out.println("i. Find Students by Class ");
            System.out.println("j. Get Passed Students ");
            System.out.println("k. Get Failed Students ");
            System.out.println("l. Delete Student ");
            System.out.println("m. Paginated Read ");
            System.out.println("n. Load Students from File");
            System.out.println("o. Load Classes from File");
            System.out.println("p. Load Addresses from File");
            System.out.println("q. Exit ");

            option = sc.next().charAt(0);

            if(option == 'a') {
                System.out.println("Enter id: ");
                int classid = sc.nextInt();

                System.out.println("Enter Name: ");
                String className = sc.next();

                Class c = new Class(classid,className);
                classList.add(c);

                for(Class i : classList){
                    System.out.println(i);
                }

                saveClasses(classList);
            }
            else if(option == 'b') {
                System.out.println("Enter Student details: ");
                System.out.println("Enter Id: ");
                int id = sc.nextInt();

                System.out.println("Enter Name: ");
                String name = sc.next();

                System.out.println("Enter Class_id: ");
                int class_id = sc.nextInt();

                int marks = 0;
                try{
                    marks = marksValidation();
                } catch (InvalidMarksException e) {
                    System.out.println("Error: " + e.getMessage());
                    continue;
                }

                System.out.println("Enter gender: ");
                char gender = sc.next().charAt(0);

                //-----------------------
                int age = 0;
                try {
                    age = ageValidation();
                } catch (InvalidAgeException e) {
                    System.out.println("Error: " + e.getMessage());
                    continue; // go back to main menu or retry input
                }
                //-----------------------

                Student s = new Student(id,name,class_id,marks,gender,age);
                studentList.add(s);

                for(Student i: studentList){
                    System.out.println(i);
                }

                saveStudents(studentList);
            }
            else if(option == 'c') {
                System.out.println("Enter id: ");
                int addressId = sc.nextInt();

                System.out.println("Enter Pin code: ");
                int addressPincode = sc.nextInt();

                System.out.println("Enter City: ");
                String addressCity = sc.next();

                System.out.println("Enter Student id: ");
                int addressStudentId = sc.nextInt();

                Address a = new Address(addressId,addressPincode,addressCity,addressStudentId);
                addressList.add(a);

                for(Address i: addressList){
                    System.out.println(i);
                }
                saveAddresses(addressList);
            }
            else if(option == 'd') {
                showAll(studentList);
            }
            else if(option == 'e') {
                MarksEvaluation(studentList);
            }
            else if(option == 'f') {
                Ranking(studentList);
            }
            else if(option == 'g') {
                findByPincode(studentList, addressList);
            }
            else if(option == 'h') {
                findByCity(studentList, addressList);
            }
            else if(option == 'i') {
                findByClass(studentList, classList);
            }
            else if(option == 'j') {
                getPassedStudents(studentList);
            }
            else if(option == 'k') {
                getFailedStudents(studentList);
            }
            else if(option == 'l') {
                deleteStudent(studentList, addressList, classList);
            }
            else if(option == 'm') {
                paginateStudents(studentList);
            }
            else if(option == 'n') {
                studentList = readStudents();
                System.out.println("Students loaded from students.csv");
                showAll(studentList);
            }
            else if(option == 'o') {
                classList = readClass();
                System.out.println("Classes loaded from classes.csv");
                showAll(classList);
            }
            else if(option == 'p') {
                addressList = readAddress();
                System.out.println("Address loaded from address.csv");
                showAll(addressList);
            }
            else if(option == 'q') {
                readRanking();
            }
        } while(option != 'r');
    }

    //-------------------
    static int ageValidation() throws InvalidAgeException {
        int age;
        while (true) {
            try {
                System.out.println("Enter Age: ");
                age = sc.nextInt();
                if (age < 0 || age > 20) {
                    throw new InvalidAgeException("Age must be between 0 and 20");
                }
                return age; // valid age
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // clear the invalid input
            }
        }
    }
    //-----------------------

    static int marksValidation() throws InvalidMarksException{
        int marks;
        while(true){
        try{
            System.out.println("Enter Marks: ");
            marks = sc.nextInt();
            if (marks < 0 || marks > 100){
                throw new InvalidMarksException("Grade between 0 - 100");
            }
            return marks;
        }catch(InputMismatchException e){
            System.out.println("Invalid input ! Please enter a number: ");
            sc.nextLine();
        }
    }
        }



    static <T> void showAll(List<T> list){
        for(T obj: list){
            System.out.println(obj);
        }
    }

    static void MarksEvaluation(List<Student> list){
        for(Student obj : list){
            if(obj.marks < 50){
                System.out.println("Marks: "+ obj.marks + " Student Name: "+ obj.name +" Passing Status: Failed");
            } else {
                System.out.println("Marks: "+ obj.marks + " Student Name: "+ obj.name +" Passing Status: Passed");
            }
        }
    }

    static void Ranking(List<Student> list){
        list.sort(Comparator.comparingInt((Student s)-> s.marks).reversed());

        try (PrintWriter pw = new PrintWriter(new FileWriter("ranking.csv"))) {
            pw.println("Rank,ID,Name,Marks,ClassId");

            int rank =1;
            int previousMark = -1;
            int count = 0;
            for(Student s : list) {
                if (s.marks != previousMark) {
                    count++;
                    rank = count;
                }
                System.out.println("Rank " + rank + " Student Name " + s.name + " Marks: " + s.marks);

                if (count <= 5) {
                    pw.println(rank + "," + s.id + "," + s.name + "," + s.marks + "," + s.class_id);
                }

                previousMark = s.marks;
            }
            System.out.println("Top 5 ranked students saved in ranking.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void findByPincode(List<Student> students, List<Address> addresses) {
        System.out.println("Enter Pincode: ");
        int pin = sc.nextInt();

        List<Integer> studentIds = addresses.stream()
                .filter(a -> a.pin_code == pin)
                .map(a -> a.student_id)
                .collect(Collectors.toList());

        students.stream()
                .filter(s -> studentIds.contains(s.id))
                .forEach(System.out::println);
    }

    static void findByCity(List<Student> students, List<Address> addresses) {
        System.out.println("Enter City: ");
        String city = sc.next();

        List<Integer> studentIds = addresses.stream()
                .filter(a -> a.city.equalsIgnoreCase(city))
                .map(a -> a.student_id)
                .collect(Collectors.toList());

        students.stream()
                .filter(s -> studentIds.contains(s.id))
                .forEach(System.out::println);
    }

    static void findByClass(List<Student> students, List<Class> classes) {
        System.out.println("Enter Class Id: ");
        int cid = sc.nextInt();

        students.stream()
                .filter(s -> s.class_id == cid)
                .forEach(System.out::println);
    }

    static void getPassedStudents(List<Student> students) {
        students.stream()
                .filter(s -> s.marks >= 50)
                .forEach(System.out::println);
    }

    static void getFailedStudents(List<Student> students) {
        students.stream()
                .filter(s -> s.marks < 50)
                .forEach(System.out::println);
    }

    static void deleteStudent(List<Student> students, List<Address> addresses, List<Class> classes) {
        System.out.println("Enter Student ID to delete: ");
        int sid = sc.nextInt();

        students.removeIf(s -> s.id == sid);
        addresses.removeIf(a -> a.student_id == sid);

        Set<Integer> remainingClassIds = students.stream()
                .map(s -> s.class_id)
                .collect(Collectors.toSet());

        classes.removeIf(c -> !remainingClassIds.contains(c.id));

        System.out.println("Student and related records deleted successfully.");
    }

    static void paginateStudents(List<Student> students) {
        System.out.println("Enter start index: ");
        int start = sc.nextInt();
        System.out.println("Enter end index: ");
        int end = sc.nextInt();

        System.out.println("Order by (name/marks/none): ");
        String order = sc.next();

        List<Student> sorted = new ArrayList<>(students);
        if (order.equalsIgnoreCase("name")) {
            sorted.sort(Comparator.comparing(s -> s.name));
        } else if (order.equalsIgnoreCase("marks")) {
            sorted.sort(Comparator.comparingInt(s -> s.marks));
        }

        sorted.stream()
                .skip(start - 1)
                .limit(end - start + 1)
                .forEach(System.out::println);
    }

    static void saveStudents(List<Student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("students.csv"))) {
            for (Student s : students) {
                pw.println(s.id + "," + s.name + "," + s.class_id + "," +
                        s.marks + "," + s.gender + "," + s.age);
            }
            System.out.println("Students saved to students.csv");
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    static void saveClasses(List<Class> classes){
        try(PrintWriter pw = new PrintWriter(new FileWriter("Classes.csv"))){
            for(Class c:classes){
                pw.println(c.id + "," + c.name);
            }
            System.out.println("Class saved to classes.csv");
        } catch (IOException e) {
            System.out.println("Error saving classes: " + e.getMessage());
        }
    }

    static void saveAddresses(List<Address> addresses){
        try(PrintWriter pw = new PrintWriter(new FileWriter("addresses.csv"))){
            for(Address a : addresses){
                pw.println(a.id + ","+ a.pin_code + "," +a.city + ","+ a.student_id);
            }
            System.out.println("Address saved successfully to addresses.csv");
        } catch (IOException e) {
            System.out.println("Error saving addresses: " + e.getMessage());
        }
    }

    static ArrayList<Student> readStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Student s = new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        parts[4].charAt(0),
                        Integer.parseInt(parts[5])
                );
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    static ArrayList<Class> readClass(){
        ArrayList<Class> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("Classes.csv"))){
            String line;
            while((line = br.readLine())!=null){
                String[] parts = line.split(",");
                Class c = new Class(Integer.parseInt(parts[0]),parts[1]);
                list.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    static ArrayList<Address> readAddress() {
        ArrayList<Address> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("addresses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Address a = new Address(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        Integer.parseInt(parts[3])
                );
                list.add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void readRanking() {
        try (BufferedReader br = new BufferedReader(new FileReader("ranking.csv"))) {
            String line;
            boolean firstLine = true;

            System.out.println("Top 5 Ranked Students");
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }
                String[] parts = line.split(",");
                System.out.println("Rank " + parts[0].trim() + " ID: " + parts[1].trim() +
                        " Name: " + parts[2].trim() + " Marks: " + parts[3].trim() + " ClassId: " + parts[4].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
