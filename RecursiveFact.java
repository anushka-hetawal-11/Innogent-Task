import java.util.Scanner;

public class RecursiveFact {
  
  //2. new stack frame for fact(4) method 
  //already contains n=4 in its stack frame
  public static int fact(int n){

    //5.at base condition fact(1) will compute and return result and stack frames will be destroyed
   //6. for every other stack frame will compute and destroyed
   if(n == 0 || n == 1)
    return 1 ;
   else

   //3. n is 4 so creates new stack frame for every functional calling 
   //4. fact(3),,fact(2),,fact(1) creates diff stack frame 
   return n*fact(n-1);
  }

//1. stack frame for main
  public static void main(String[] args) {
    //sc reference in stack
    //Scanned object created in Heap
  Scanner sc = new Scanner(System.in);
  System.out.println("Enter a number: ");
  //primitive n in stack 
  int n = sc.nextInt();
  //fact is called with n say 3 
  System.out.println(fact(n));

  //7. print result and main stack frame will be destroyed too
}
}

//8. JVM will delete unreferred Scanner obj from heap 
