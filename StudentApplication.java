package assessment3;

import java.util.*;
import java.util.stream.Collectors;

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
        this.age = age;;
    }

    //overriding toString to display your Student obj
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
        return "Id- " + id + "Pin_code- "+ pin_code +"City- "+ city + "Student id- "+ student_id;
    }
}

public class StudentApplication {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Class> classList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Address> addressList = new ArrayList<>();

        char option;
        do{
            System.out.println("a. Insert New Class Details ");
            System.out.println("b. Insert Student Details ");
            System.out.println("c. Insert Address Details ");
            System.out.println("d. Show All Student records ");
            System.out.println("e. Passing Status ");
            System.out.println("f. Overall Ranking ");
            System.out.println("g. Find Students by Pincode ");
            System.out.println("h. Find Students by City ");
            System.out.println("i. Find Students by Class ");
            System.out.println("j. Get Passed Students ");
            System.out.println("k. Get Failed Students ");
            System.out.println("l. Delete Student ");
            System.out.println("m. Paginated Read ");
            System.out.println("n. Exit ");

            option = sc.next().charAt(0);
            if(option == 'a'){
                System.out.println("Enter id: ");
                int classid = sc.nextInt();

                System.out.println("Enter Name: ");
                String className = sc.next();

                Class c = new Class(classid,className);
                classList.add(c);

                for(Class i : classList){
                    System.out.println(i);
                }
            }
            else if(option == 'b'){
                System.out.println("Enter Student details: ");
                System.out.println("Enter Id: ");
                int id = sc.nextInt();

                System.out.println("Enter Name: ");
                String name = sc.next();

                System.out.println("Enter Class_id: ");
                int class_id = sc.nextInt();

                System.out.println("Enter Marks: ");
                int marks = sc.nextInt();

                System.out.println("Enter gender: ");
                char gender = sc.next().charAt(0);

                int age = ageValidation();

                Student s = new Student(id,name,class_id,marks,gender,age);
                studentList.add(s);

                //printing student obj from arraylist
                for(Student i: studentList){
                    System.out.println(i);
                }
            }
            else if(option == 'c'){
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
            }
                else if(option == 'd'){
                    showAll(studentList);
            }
                else if(option == 'e'){
                MarksEvaluation(studentList);
            }
                else if(option == 'f'){
                Ranking(studentList);
            }
                else if (option == 'g') {
                findByPincode(studentList, addressList);
            }
                else if (option == 'h') {
                findByCity(studentList, addressList);
            }
                else if (option == 'i') {
                findByClass(studentList, classList);
            }
                else if (option == 'j') {
                getPassedStudents(studentList);
            }
                else if (option == 'k') {
                getFailedStudents(studentList);
            }
                else if (option == 'l') {
                deleteStudent(studentList, addressList, classList);
            }
                else if (option == 'm') {
                paginateStudents(studentList);
            }
        }while(option!='n');
    }

    static int ageValidation() {
        int age1;
        boolean isAgeValid = false;
        do {
            System.out.println("Enter Age: ");
            age1 = sc.nextInt();
            if(age1>=0 && age1<=20){
                isAgeValid = true;
            }
            else{
                System.out.println("Enter valid age between 0-20");
            }
        }while(!isAgeValid);
        return age1;
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
            }
            else{
                System.out.println("Marks: "+ obj.marks + " Student Name: "+ obj.name +" Passing Status: Passed");
            }
        }
    }
    static void Ranking(List<Student> list){
        list.sort(Comparator.comparingInt((Student s)-> s.marks).reversed());

        int rank =1;
        int previousMark = -1;
        int count = 0;
        for(Student s : list){
            count++;
            if(s.marks != previousMark){
                rank = count;
            }
            System.out.println("Rank " + rank +" Student Name "+ s.name + " Marks: " + s.marks);
            previousMark = s.marks;
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

        // If no students left in class → delete class
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
}
