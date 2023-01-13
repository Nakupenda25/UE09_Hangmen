import java.io.*;
import java.util.*;

public class Hangmen {
    static Scanner scn = new Scanner(System.in);

    //checks if the path exists, and creates a new list containing all words within the given file
    public static ArrayList wordList (String path) throws IOException {
        ArrayList words = new ArrayList();
        File file = new File(path);

        if (file.exists() && file.length() != 0){
            BufferedReader inBuffer = new BufferedReader(new FileReader(path));
            String word;

            while ((word = inBuffer.readLine()) != null){
            words.add(word);
            }
        }else if (file.length() == 0)
            System.out.println("Error: Empty file!");
        else
            System.out.println("Error: Could not read file!");

        return words;
    }

    //checks if all elements of the list are valid and returns an error if they don't
    public static boolean validList (ArrayList<String> words){

        //.matches uses regex to check if the String only follows a valid pattern
        for (String s : words) {

            if (!s.matches("[a-zA-Z]+")){
                System.out.println("Error: Corrupt file!");
                return false;
            }
        }
        return true;
    }

    //method for the character input of the user // resets if more than one character is provided
    public static char charInput() {
        char ch;

        while (true) {
            System.out.print("Next guess: ");
            try {
                String c = scn.nextLine();
                if (c.length() == 1) {
                    ch = c.charAt(0);
                    if (Character.isLetter(ch))
                        break;
                }
                throw new InputMismatchException("No valid input");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }
        return ch;
    }

    public static void main (String[]args){
        ArrayList words = null;

        try {
            words = wordList(args[0]);
        } catch (IOException e) {}

        validList(words);
    }
}
