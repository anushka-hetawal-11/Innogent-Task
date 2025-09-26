import java.util.Scanner;

//1. JVM loads this class into Method Area 
public class CalculateFact{

  //3. fact will be called with some value of n say 5 creates a stack frame for fact method 
  //stack frame of fact contains n beacause passed as parameter 
  public static int fact(int n){
    //fact variable is primitive int goes to stack 
  int fact = 1;
  // i in stack ,loop will run for i from 2 --> 5 
  for(int i=2 ; i<=n ;i++){
     fact *= i;
  }
  //final value of fact say 120 will be returned to stack frame of main
  return fact;

  //5. after method execution is over stack frame from memory will be deleted  
}

//2. main method will execute first and a separate stack block in stack memory is created called stack frame
public static void main(String[] args) {
  // sc is reference stores in stack
  // whereas Scanner is obj using new keyword goes to heap 
  Scanner sc = new Scanner(System.in);
  System.out.println("Enter a number: ");
  // n is primitive goes to stack  
  int n = sc.nextInt();
  System.out.println(fact(n));
  //4. prints the result and stack frame of main will be deleted 
}
}

//6. Garbage Collector will auto delete Scanner obj from heap as unreferred anymore 