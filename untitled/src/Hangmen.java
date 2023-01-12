import java.io.File;
import java.util.*;

public class Hangmen {
    static Scanner scn = new Scanner(System.in);

    //method for the character input of the user // resets if more than one character is provided
    public static char charInput() {
        char ch;

        while (true) {
            System.out.print("Next guess: ");
            try {
                String c = scn.nextLine();
                if (c.length() == 1) {
                    ch = c.charAt(0);
                    if (charChecker(ch))
                        break;
                }
                throw new InputMismatchException("Invalid Input!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }
        return ch;
    }

    public static boolean charChecker (char c){
        if (Character.isLetter(c)){
            return true;
        }else
            return false;
    }

    public static boolean pathChecker (String path){
        File file = new File(path);
        if (file.exists())
            return true;
        else
            return false;

    }

    public static void main (String[]args){
        charInput();

    }
}
