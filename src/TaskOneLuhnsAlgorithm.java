import java.math.BigInteger;
import java.util.Locale;
import java.util.Scanner;

public class TaskOneLuhnsAlgorithm {

    /**
     Print text to screen
     */
    public static void print(String string) {
        System.out.print(string);
    }

    /**
     Print text to screen with new line at the end
     */
    public static void println(String string) {
        System.out.println(string);
    }

    /**
     Get char position and turn char into an integer like
     Assignment.intAt("123", 0) == 1;
     */
    private static int intAt(String input, int index)  {

        return Integer.parseInt(input.charAt(index) + "");
    }

    /**
     Take a Card number, remove whitespace and verify if an entered
     credit card or debit card number is correct or not using luhns algorithm
     */
    public static void taskOne() {
        Scanner scan = new Scanner(System.in);

        while(true) {
            print("Enter a 16 digit number, enter e to exit: ");
            String input = scan.nextLine();
            input = input.trim().replace(" ", "");  // remove whitespace

            if(input.toLowerCase(Locale.ROOT).equals("e")) {
                print("Exiting");
                scan.close();
                continue;
            }

            if(input.length() != 16) {
                // exit if length is not 16
                println("Input length isn't 16!");
                continue;
            }

            try {
                for(int index = 0; index < 16; index++) {
                    TaskOneLuhnsAlgorithm.intAt(input, index);
                }
            } catch (NumberFormatException exc) {
                println("Invalid input!");
                continue;
            }

            int sum = 0;
            for(int index = 0; index < 16; index+=2) {
                // when index, starting from the left is even
                int _prod = TaskOneLuhnsAlgorithm.intAt(input, index)  * 2;
                String prod = String.valueOf(_prod);

                int _sum = TaskOneLuhnsAlgorithm.intAt(prod, 0);

                if(prod.length() == 2){
                    _sum += TaskOneLuhnsAlgorithm.intAt(prod, 1);
                }
                sum += _sum;
//                print(String.valueOf(_sum));
//                print(" ");
            }

            for(int index = 1; index < 16; index+=2) {
                // when index, starting from the left is odd
                sum += TaskOneLuhnsAlgorithm.intAt(input, index);
//                print(String.valueOf(TaskOneLuhnsAlgorithm.intAt(input, index)));
//                print(" ");
            }
//            println(String.valueOf(sum));
            if(sum % 10 == 0) {
                println("Credit Card Number is Correct!");
            } else {
                println("Credit Card Number is Wrong!");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            TaskOneLuhnsAlgorithm.taskOne();
        }
    }
}