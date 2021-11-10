package Dev_TP2;

import java.io.*;
import java.util.Scanner;

public class Class1 {
    public static void main(String[] args) {

        int nbrObj ;
        Scanner sc = new Scanner(System.in);
        System.out.println("The number of the objects ?");
        nbrObj = sc.nextInt();

        /** array of Objects **/
        Employee [ ] arrEmp= new Employee[nbrObj];

        try
        {
            FileOutputStream fileOut = new FileOutputStream("emp3.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // fill my array of Objects
            for (int i=0 ; i< arrEmp.length ;i++){
                arrEmp[i]=new Employee(sc);
                arrEmp[i].mailCheck();
                out.writeObject(arrEmp[i]);
            }

            out.close();
            fileOut.close();
            System.out.printf("donnees serialisees sauvegardees dans emp1.dat"+"\n");
        }
        catch(IOException b) {
            b.printStackTrace();
        }
    }
}
