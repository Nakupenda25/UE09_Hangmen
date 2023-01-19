import java.io.*;
import java.util.*;

public class Hangmen {
    static Scanner scn = new Scanner(System.in);
    public static final int MAX_MISTAKES = 11;

    public static void separatorLine() {
        System.out.printf("-".repeat(80) + "\n");
    }

    //returns a String randomly chosen from an ArrayList
    public static String getRandomWord(ArrayList<String> listOfWords) {
        Random random = new Random();
        String word = listOfWords.get(random.nextInt(listOfWords.size()));
        return word;
    }

    //method to that accepts an input and draws different sketches based on it
    public static boolean drawHangmanLoss(int mistakes) {

        switch (mistakes) {
            case 0:
                return false;
            case 1:
                System.out.println("===");
                return false;
            case 2:
                System.out.print(" |\n".repeat(4));
                System.out.println("===");
                return false;
            case 3:
                System.out.println("  ----");
                System.out.print(" |\n".repeat(4));
                System.out.println("===");
                return false;
            case 4:
                System.out.println("  ----");
                System.out.print(" |/\n");
                System.out.print(" |\n".repeat(3));
                System.out.println("===");
                return false;
            case 5:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.printf(" |\n".repeat(3));
                System.out.println("===");
                return false;
            case 6:
                System.out.println("  ----");
                System.out.printf(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.printf(" |\n".repeat(2));
                System.out.println("===");
                return false;
            case 7:
                System.out.println("  ----");
                System.out.printf(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |\n");
                System.out.println("===");
                return false;
            case 8:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |   /\n");
                System.out.println("===");
                return false;
            case 9:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return false;
            case 10:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |   /|\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return false;
            case 11:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |   /|\\\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return true;

            default:
                throw new IllegalStateException("Unexpected value: " + mistakes);
        }
    }

    //adds every character of a String to an ArrayList for later use
    public static ArrayList<Character> wordIntoCharList(String word) {
        ArrayList<Character> wordAsList = new ArrayList<>();

        for (char c : word.toCharArray()) {
            wordAsList.add(c);
        }
        return wordAsList;
    }

    //checks if the path exists, and creates a new list containing all words within the given file
    public static ArrayList<String> getWordList(String path) {
        ArrayList words = new ArrayList();
        File file = new File(path);

        try {
            if (file.exists() && file.length() != 0) {
                BufferedReader inBuffer = new BufferedReader(new FileReader(path));
                String word;

                while ((word = inBuffer.readLine()) != null) {
                    words.add(word.toUpperCase());
                }
            } else
                System.out.println("Error: Empty file!");

        } catch (IOException e) {
            System.out.println("Error: Could not read file!");
        }


        return words;
    }

    //goes through each element of an arraylist
    public static ArrayList<Character> setGuessedChars(ArrayList<Character> printList, ArrayList<Character> currentWord, HashSet userGuessedChars){

        for (int letternumber = 0; letternumber < currentWord.size(); letternumber++) {

            if (userGuessedChars.contains(currentWord.get(letternumber))) {
                printList.set(letternumber, currentWord.get(letternumber));
            }
        }

        return printList;
    }

    //prints each element of the given ArrayList
    public static void wordWithHoles(ArrayList<Character> toPrint) {
        System.out.print("Word: ");

        int counter = 0;
        for (Character symbol : toPrint) {
            if (counter == 0) {
                System.out.print(symbol + " ");
                counter++;
            } else if (counter == toPrint.size() - 1)
                System.out.print(Character.toLowerCase(symbol));

            else {
                System.out.print(Character.toLowerCase(symbol) + " ");
                counter++;
            }

        }
        System.out.println();
    }

    //checks if all elements of the list are valid and returns an error if they don't
    public static boolean validList(ArrayList<String> words) {

        //.matches uses regex to check if the String only follows a valid pattern
        for (String s : words) {

            if (words.isEmpty() || s.matches(" "))
                if (!s.matches("[a-zA-Z]+")) {
                    System.out.println("Error: Corrupt file!");
                    return false;
                }
        }
        return true;
    }

    //method for the character input of the user // resets if more than one character is provided
    public static char charInput() throws InputMismatchException {
        char ch = 0;

        String inputChar = scn.nextLine();
        if (inputChar.length() != 1) {
            System.out.println("Invalid Input!");
            return ch;
        }

        if (inputChar.matches("[a-zA-Z]")) {
            ch = inputChar.charAt(0);
        } else
            System.out.println("Invalid Character!");

        return ch;
    }

    //simple method for cleaner formatting;
    public static void printMissinputs(ArrayList<Character> missInputs) {

        if (missInputs.size() == 0) {
            System.out.print("Misses (" + missInputs.size() + "/11)");
            System.out.println();
            return;

        } else {
            int counter = 0;
            System.out.print("Misses (" + missInputs.size() + "/11):");
            for (Character element : missInputs) {
                if (counter == missInputs.size() - 1) {
                    System.out.print(" " + element.toString().toUpperCase());
                    System.out.println();
                } else {
                    System.out.print(" " + element.toString().toUpperCase() + ",");
                    counter++;
                }
            }
        }


        System.out.println();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No file name given!");
            return;
        }

        ArrayList<String> words = new ArrayList();
        //list that is just used for printing
        ArrayList<Character> wordPrintList = new ArrayList();
        ArrayList<Character> currentWord = new ArrayList<Character>();
        ArrayList<Character> missInputs = new ArrayList<>();
        HashSet<Character> userGuessedChars = new HashSet<>();
        words = getWordList(args[0]);
        final int ROUND_NUMBER = words.size();

        System.out.println("=".repeat(80));
        System.out.println("HANGMEN (" + words.size() + " Word(s))");
        separatorLine();

        for (int round = 1; round <= ROUND_NUMBER; round++) {

            //get a random word from the word list and remove it from the list afterwards.
            String temp;
            temp = getRandomWord(words);
            words.remove(temp);

            //turn the word into a character-list for later use
            currentWord = wordIntoCharList(temp);

            System.out.printf("Word #" + round + ": \n\n");

            //fills the print list with underscores depending on how long the word this round is
            for (int underscores = 1; underscores <= currentWord.size(); underscores++) {
                wordPrintList.add('_');
            }

            while (true) {

                setGuessedChars(wordPrintList, currentWord, userGuessedChars);

                drawHangmanLoss(missInputs.size());
                wordWithHoles(wordPrintList);

                if (userGuessedChars.containsAll(currentWord))
                    break;
                else if (missInputs.size() == MAX_MISTAKES)
                    break;

                printMissinputs(missInputs);

                System.out.print("Next guess: ");
                Character c = Character.toUpperCase(charInput());
                if (c != 0 && !userGuessedChars.contains(c)) {
                    userGuessedChars.add(c);
                    if (!currentWord.contains(c)) {
                        missInputs.add(c);
                    }
                } else if (userGuessedChars.contains(c)) {
                    System.out.println("Character already guessed!");
                }
                System.out.println();

            }
            if (missInputs.size() == MAX_MISTAKES)
                System.out.println("YOU LOSE!\n");
            else
                System.out.println("YOU WIN!\n");

            userGuessedChars.clear();
            missInputs.clear();
            wordPrintList.clear();
        }
        scn.close();
        System.out.println("GOODBYE!!!");
    }
}
