import java.io.*;
import java.util.*;

public class Hangmen {
    static Scanner scn = new Scanner(System.in);

    //the max amount of mistakes a player is allowed to make per round
    public static final int MAX_MISTAKES = 11;

    //Separator that lets the user choose the thickness
    public static void separatorLine(Character thickness){

        switch (thickness){
            case '-':
                System.out.println("-".repeat(80));
                break;
            case '=':
                System.out.println("=".repeat(80));
                break;
        }
    }

    //returns a String randomly chosen from an ArrayList
    public static String getRandomWord(ArrayList<String> listOfWords) {

        //uses the "Random" class to get a random integer. Then uses that integer in the .get function to choose a String for this round
        Random random = new Random();

        //int value between 0 and given integer (.size in this case)
        return listOfWords.get(random.nextInt(listOfWords.size()));
    }

    //method to that accepts an input and draws different sketches based on it
    public static void drawHangman(int mistakes) {

        switch (mistakes) {
            case 0:
                return;
            case 1:
                System.out.println("===");
                return;
            case 2:
                System.out.print(" |\n".repeat(4));
                System.out.println("===");
                return;
            case 3:
                System.out.println("  ----");
                System.out.print(" |\n".repeat(4));
                System.out.println("===");
                return;
            case 4:
                System.out.println("  ----");
                System.out.print(" |/\n");
                System.out.print(" |\n".repeat(3));
                System.out.println("===");
                return;
            case 5:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |\n".repeat(3));
                System.out.println("===");
                return;
            case 6:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |\n".repeat(2));
                System.out.println("===");
                return;
            case 7:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |\n");
                System.out.println("===");
                return;
            case 8:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |   /\n");
                System.out.println("===");
                return;
            case 9:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |    |\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return;
            case 10:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |   /|\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return;
            case 11:
                System.out.println("  ----");
                System.out.print(" |/   |\n");
                System.out.print(" |    O\n");
                System.out.print(" |   /|\\\n");
                System.out.print(" |   / \\\n");
                System.out.println("===");
                return;

            default:
                throw new IllegalStateException("Unexpected value: " + mistakes);
        }
    }

    //adds every character of a String to an ArrayList for later use
    public static ArrayList<Character> wordIntoCharList(String word) {
        ArrayList<Character> wordAsList = new ArrayList<>();

        //converts the whole String into a list
        for (char c : word.toCharArray()) {
            wordAsList.add(c);
        }

        return wordAsList;
    }

    //all in one method that returns true if a path exists, is readable and not empty. Spits out an error otherwise
    public static boolean pathChecker (String path){
        File file = new File(path);

        //prints an error and returns false if the path cannot be found
        if (!file.exists()){
            System.out.println("Error: File not found!");
            return false;
        }

        try (BufferedReader buffer = new BufferedReader(new FileReader(path))){
            String tester;

            //returns an error if the file cat be read
            if (!file.canRead()) {
                System.out.println("Error: Could not read file!");
                return false;

            //returns an error if the file is empty
            }else if ((tester = buffer.readLine()) == null || tester.isBlank()){
                System.out.println("Error: Empty file!");
                return false;
            }

        }catch (IOException e){}

        //only returns true if all validations are met
        return true;
    }

    //checks if the path exists, and creates a new list containing all words within the given file
    public static ArrayList<String> getWordList(String path) {
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader inBuffer = new BufferedReader(new FileReader(path))){
                String word;

                //goes through each line of the file and checks if it matches the regex. If it does, adds the word to the wordList for this game
                while ((word = inBuffer.readLine()) != null) {
                    if (word.matches("[a-zA-Z]*"))
                        words.add(word.toUpperCase());
                    else {
                        System.out.println("Error: Corrupt file!");
                        words.clear();
                        break;
                    }
                }

        } catch (IOException e) {}

        return words;
    }

    //goes through each element of an arraylist
    public static ArrayList<Character>  setGuessedChars(ArrayList<Character> printList, ArrayList<Character> currentWord, HashSet userGuessedChars){

        //goes through each character of the word being used this round
        for (int letterNumber = 0; letterNumber < currentWord.size(); letterNumber++) {

            //if one of the elements of the guessed chars matches a specific character in the current Word, set the element of the printList at that point to that character
            if (userGuessedChars.contains(currentWord.get(letterNumber))) {
                printList.set(letterNumber, currentWord.get(letterNumber));
            }
        }

        return printList;
    }

    //prints each element of the given ArrayList
    public static void wordWithHoles(ArrayList<Character> toPrint) {
        System.out.print("Word: ");
        int counter = 0;
        //takes the arraylist that contains underscores and guessed letters and prints it
        for (Character symbol : toPrint) {

            //prints an uppercase letter first + a space, or only a lowercase letter without a space if it is the last element
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

    //method for the character input of the user // resets if more than one character is provided
    public static char charInput(){
        char ch = 0;
        String inputChar = scn.nextLine();

        //if the String is longer than 1, returns a "1" (false)
        if (inputChar.length() != 1) {
            System.out.println("\nInvalid input!");
            return ch;
        }

        //checks if the String matches the given regex (either one letter from a-z or A-Z, and prints an error if not
        if (inputChar.matches("[a-zA-Z]")) {
            ch = inputChar.charAt(0);
        } else
            System.out.println("\nInvalid character!");

        return ch;
    }

    //simple method for cleaner formatting; Prints all characters currently in the setList given
    public static void missInputNumber(HashSet<Character> missInputs) {

        //case if no mistake has been made yet
        if (missInputs.size() == 0) {
            System.out.println("Misses (" + 0 + "/11)");

        } else {
            int counter = 0;
            System.out.print("Misses (" + missInputs.size() + "/11):");

            //prints each character of the set, and if it is not the last one, prints a comma too
            for (Character element : missInputs) {
                if (counter == missInputs.size() - 1) {
                    System.out.println(" " + element.toString().toUpperCase());
                }else {
                    System.out.print(" " + element.toString().toUpperCase() + ",");
                    counter++;
                }
            }
        }

    }

    public static void main(String[] args) {

        //stops the program if no command line arguments are given
        if (args.length == 0) {
            System.out.println("Error: No file name given!");
            return;
        }

        //INITIALIZE NECESSARY VARIABLES
        //list that contains the full Strings taken from the .txt file
        ArrayList<String> words = new ArrayList<String>();

        //list that is just used for printing. Usually contains underscores and letters
        ArrayList<Character> wordPrintList = new ArrayList<>();

        //the word that will be used for a round as a character list (to read each single character easily)
        ArrayList<Character> currentWord = new ArrayList<>();

        //a set that saves all guesses that are NOT in the current word
        HashSet<Character> missInputs = new HashSet<>();

        //a set that saves all guesses that ARE part of the current word
        HashSet<Character> userGuessedChars = new HashSet<>();

        //stops the program if an error occurs within "pathChecker"
        if (pathChecker(args[0])) {
            words = getWordList(args[0]);
            if (words.size() == 0)
                return;
        }else
            return;

        //final int that matches the amount of rounds that will be played
        final int ROUND_NUMBER = words.size();

        int roundsWon = 0;

        separatorLine('=');
        System.out.println("HANGMEN (" + words.size() + " Word(s))");
        separatorLine('-');

        //for loop that repeats the program for each round. Program ends when this loop is done
        for (int round = 1; round <= ROUND_NUMBER; round++) {

            //get a random word from the word list and remove it from the list afterwards.
            String temp;
            temp = getRandomWord(words);
            words.remove(temp);

            //turn the word into a character-list for later use
            currentWord = wordIntoCharList(temp);

            System.out.println("Word #" + round + ":\n\n");

            //fills the print list with underscores depending on how long the word this round is
            for (int underscores = 1; underscores <= currentWord.size(); underscores++) {
                wordPrintList.add('_');
            }

            while (true) {

                wordPrintList = setGuessedChars(wordPrintList, currentWord, userGuessedChars);
                drawHangman(missInputs.size());
                wordWithHoles(wordPrintList);
                missInputNumber(missInputs);

                //stops the game if the game is either won or lost
                if (userGuessedChars.containsAll(currentWord))
                    break;
                else if (missInputs.size() == MAX_MISTAKES)
                    break;

                System.out.print("Next guess: ");
                Character guess = Character.toUpperCase(charInput());

                //if the character has not already been guessed, adds it to the "userGuessedChars" set
                if (guess != 0 && !userGuessedChars.contains(guess)) {
                    userGuessedChars.add(guess);

                    //adds the character to the missInputs set if it is not contained within the current word
                    if (!currentWord.contains(guess)) {
                        missInputs.add(guess);
                    }

                } else if (userGuessedChars.contains(guess)) {
                    System.out.println("Character already guessed!");
                }
                System.out.println();
                System.out.println();

            }

            //if the user made 11 mistakes, he loses
            if (missInputs.size() == MAX_MISTAKES)
                System.out.println("\nYOU LOSE!");
            else {
                System.out.println("\nYOU WIN!");
                roundsWon++;
            }

            if (round == ROUND_NUMBER)
                separatorLine('=');
            else
                separatorLine('-');

            //clears all necessary lists/sets to prepare for next round
            userGuessedChars.clear();
            missInputs.clear();
            wordPrintList.clear();
        }
        scn.close();
        System.out.printf("WINS: %d/%d\n", roundsWon, ROUND_NUMBER);
    }
}
