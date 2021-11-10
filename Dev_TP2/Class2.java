package Dev_TP2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Class2 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Employee> al = new ArrayList<Employee>();
        boolean cont = true;
        try {
            FileInputStream fileIn = new FileInputStream("emp.dat");
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            while(cont){
                Employee obj=null;
                try {
                    obj = (Employee) ois.readObject();
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(obj != null)
                    al.add(obj);
                else{
                    cont = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**RandomAccessFile*/
        Scanner sc = new Scanner(System.in);
        long [] pointer = new long[al.size()];
        try{
            RandomAccessFile r = new RandomAccessFile("empdirect.dat", "rw");

            for (int i = 0; i < al.size() ; i++) {
                Employee obj=  al.get(i);
                r.writeUTF("Nom:" + obj.name);
                r.writeUTF("Adresse:" + obj.address);
                r.writeInt( obj.SSN);
                r.writeInt( obj.number);
                pointer[i]=r.getFilePointer();
//                System.out.println(pointer[i]);
            }

            /**Class 3**/
            //Demander à l'utilisateur d'entrer un numéro d'employé

            int numWanted;
            do{
                System.out.println("Insert the employee number to search");
                numWanted =sc.nextInt();
            }while (numWanted <= 0);


            // rechrcher a l employee demander par l utilisateur avec un pointeur
            if (numWanted==1) {
                r.seek(0);
                System.out.println(r.readUTF());
                System.out.println(r.readUTF());
                System.out.println(r.readInt());
                System.out.println(r.readInt());
            }
            else{
                r.seek(pointer[numWanted-2]);
                System.out.println(r.readUTF());
                System.out.println(r.readUTF());
                System.out.println(r.readInt());
                System.out.println(r.readInt());
            }
            r.close();
        }
       catch (EOFException x){
            //affiche le message dans le cas ou l'employeee n'existe pas!
           System.out.println("employee doesn't exist");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

