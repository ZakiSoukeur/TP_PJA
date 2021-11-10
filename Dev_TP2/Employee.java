package Dev_TP2;

import java.io.Serializable;
import java.util.Scanner;

class Employee implements Serializable {
    public String name;
    public String address;
    public transient int SSN;
    public int number;
    static Scanner sc = new Scanner(System.in);  // we have to mark Scanner as static to be serializable


    public Employee(final Scanner sc) {
        System.out.println("Employee's name :");
        this.name = sc.next();

        System.out.println("Employee's Address :");
        this.address = sc.next();

        System.out.println("Employee's SSN :");
        this.SSN = sc.nextInt();

        System.out.println("Employee's number :");
        this.number = sc.nextInt();
    }

    public void mailCheck(){
        System.out.println("Mail de verification de "+name+" "+address);
    }
}