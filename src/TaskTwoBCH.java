import java.lang.Math;
import java.util.Scanner;

/**
 * BCH IMPLEMENTATION
 */
class TaskTwoBCH {

    /**
     * Matrix to generate the encoded data
     */
    int[][] generatorMatrix = {
        {4, 7, 9, 1 },
        {10, 8, 1, 2},
        {9, 7, 7, 9 },
        {2, 1, 8, 10 },
        {1, 9, 7, 4  },
        {7, 6, 7, 1  },
    };


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
     * String and an integer x, get the integer value of strings position x
     * @param input String
     * @param index integer of the index
     * @return integer
     */
    private static int intAt(String input, int index)  {
        return Integer.parseInt(input.charAt(index) + "");
    }

    int getMultiplier(int x) {
        int total = 0;

        for(int i = 1; i < 7; i++) {
            total += ((int) Math.pow(i, x) % 11);
        }

        return total;
    }

    /**
     * BCH encoding of a number
     * @param input Raw data
     * @return encoded data where parity values are trailing
     * @throws Exception invalid string
     */
    String encode(String input) throws Exception {
        if (input.length() != 6) {
            throw new Exception("Invalid input length!");
        }
        String parityRow = "";
        for(int x = 0; x < 4; x++) {
            int total = 0;
            for(int i = 0; i < 6; i++) {
                int num = intAt(input, i); // num = int(input[i])
                int multiplier = generatorMatrix[i][x];
                total += multiplier*num;
            }
            parityRow += String.valueOf((total % 11));
        }

        return input + parityRow;
    }

    /**
     * Checking if a number is invalid with the BCH algorithm
     * @param input encoded data
     */
    void checkSyndrome(String input) {
        boolean isErrored = false;

        int[] syndromeValues = {0, 0, 0, 0};
        for(int index = 0; index < 4; index++) {
            int total = 0;
            String s = "";
            for(int x = 0; x < 10; x++) {
                int inpVal = intAt(input, x);
                int pow = ((int) Math.pow(x+1, index) % 11);
                total += inpVal * pow;
                s += inpVal + "*" + pow + "+ ";
            }
            int mod = total % 11;
            if(mod != 0) {
                syndromeValues[index] = mod;
                isErrored = true;
//                print(" " + mod);
            }
//            println(s);
        }

        if(isErrored) {
//            println("There are errors!");

            int p = ((
                    (int) Math.pow(syndromeValues[1], 2)
            ) - syndromeValues[0] * syndromeValues[2]) % 11;
            int q = ((syndromeValues[0] * syndromeValues[3]) -
                    (syndromeValues[1] * syndromeValues[2])) % 11;
            int r = ((
                    (int) Math.pow(syndromeValues[2], 2)
            ) - syndromeValues[1] * syndromeValues[3]) % 11;
            if(p < 0) {
                p += 11;
            }
            if(q < 0) {
                q += 11;
            }
            if(r < 0) {
                r += 11;
            }

//            println("P = " + p);
//            println("Q = " + q);
//            println("R = " + r);

            int thirdClauseRaw = ((int) Math.pow(q, 2) - 4*p*r) %11;
            if(thirdClauseRaw < 0) {
                thirdClauseRaw += 11;
            }
            double thirdClause = Math.sqrt(thirdClauseRaw);

            if(p == 0 && q == 0 && r == 0) {
                println("There is a single error!");
                int pos = (syndromeValues[1] / syndromeValues[0]);
                int mag = syndromeValues[0];
                int diff = intAt(input, pos-1) - mag;
                String corrected = input.substring(0, pos-1) + diff + input.substring(pos);
                println("Error is at position " + (pos));
                println("Corrected: " + corrected);
//                println("Magnitude " + (mag));
//                println("Corrected: " + corrected);
            } else if (thirdClause % 1 != 0) {
                println("There are three errors!");
            } else {
                int qSquare = (int) Math.pow(q, 2);
                int fourPR = 4 * p * r;
//                println(qSquare  + "");
//                println(fourPR  + "");
                int bsqFourAC = (qSquare - fourPR) % 11;
                if(bsqFourAC < 0) {
                    bsqFourAC += 11;
                }
//                println(bsqFourAC  + "");
                double positive = ((double) (q * -1) + bsqFourAC) / (2*p);
                double negative = ((double) (q * -1) - bsqFourAC) / (2*p);
//                println("positive = " + (positive));
//                println("negative = " + (negative));

                int pos1 = (int) positive % 11;
                int pos2 = (int) negative % 11;

                println("There are two errors!");

                println("Error Position 1 = " + (pos1+1));
                println("Error Position 2 = " + (pos2+1));
            }
        } else {
            println("There are no errors!");
        }
    }

    public TaskTwoBCH() throws Exception {
        Scanner scan = new Scanner(System.in);

        while(true) {
            println("Select An Option");
            println("Enter 1 for encoding");
            println("Enter 2 for decoding");
            println("Enter 3 to exit");
            print("Option: ");
            String input = scan.nextLine().trim();

            if(input.equals("3")) {
                break;
            } else if(input.equals("1")) {
                print("Enter Text For Encoding: ");
                String message = scan.nextLine().trim();
                try {
                    String encoded = encode(message);
                    println("Your Encoded Message is: " + encoded);
                } catch (Exception e) {
                    println(e.toString());
                }


            } else if (input.equals("2")) {
                try {
                    print("Enter Text For Decoding: ");
                    String message = scan.nextLine().trim();
                    checkSyndrome(message);
                } catch (Exception e) {
                    println(e.toString());
                }
            } else {
                println("Invalid input!");
            }
        }
        scan.close();
    }
    public static void main(String[] args) throws Exception {
        new TaskTwoBCH();
    }
}