import java.io.*;
import java.util.*;

public class Hangmen {
    static Scanner scn = new Scanner(System.in);

    public static void separatorLine(){
        System.out.printf("-".repeat(80) + "\n");
    }

    //returns a String randomly chosen from an ArrayList
    public static String getRandomWord (ArrayList<String> listOfWords){
        Random random = new Random();
        String word = listOfWords.get(random.nextInt(listOfWords.size()));
        return word;
    }

    //method to that accepts an input and draws different sketches based on it
    public static boolean drawHangmanLoss (int mistakes){

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
    public static ArrayList<Character> wordIntoCharList (String word){
        ArrayList<Character> wordAsList = new ArrayList<>();

        for (char c : word.toCharArray()) {
            wordAsList.add(c);
        }
        return wordAsList;
    }

    //checks if the path exists, and creates a new list containing all words within the given file
    public static ArrayList<String> getWordList (String path) {
        ArrayList words = new ArrayList();
        File file = new File(path);

        try {
            if (file.exists() && file.length() != 0){
                BufferedReader inBuffer = new BufferedReader(new FileReader(path));
                String word;

                while ((word = inBuffer.readLine()) != null){
                    words.add(word.toUpperCase());
                }
            }else
                System.out.println("Error: Empty file!");

        }catch (IOException e){
            System.out.println("Error: Could not read file!");
        }


        return words;
    }

    //prints each element of the given ArrayList
    public static void wordWithHoles(ArrayList<Character> toPrint){
        System.out.print("Word: ");

        int counter = 0;
        for (Character symbol : toPrint) {
            if (counter == 0) {
                System.out.print(symbol + " ");
                counter++;
            }
            else if (counter == toPrint.size() - 1)
                System.out.print(Character.toLowerCase(symbol));

            else {
                System.out.print(Character.toLowerCase(symbol) + " ");
                counter++;
            }

        }
        System.out.println();
    }

    //checks if all elements of the list are valid and returns an error if they don't
    public static boolean validList (ArrayList<String> words){

        //.matches uses regex to check if the String only follows a valid pattern
        for (String s : words) {

            if (words.isEmpty() || s.matches(" "))
            if (!s.matches("[a-zA-Z]+")){
                System.out.println("Error: Corrupt file!");
                return false;
            }
        }
        return true;
    }

    //method for the character input of the user // resets if more than one character is provided
    public static char charInput() throws InputMismatchException{
        char ch = 0;

        String c = scn.nextLine();
        if (c.length() == 1) {
            ch = c.charAt(0);
            if (Character.isLetter(ch)){
                return ch;
            }
            else{
                ch = 0;
                System.out.println("Invalid character!");
                throw new InputMismatchException("Invalid Character");
            }
        }
        else {
            System.out.println("Invalid input!");
            throw new InputMismatchException("Invalid input!");
        }

    }

    //simple method for cleaner formatting;
    public static void printMissinputs(ArrayList<Character> missInputs){

        if (missInputs.size() == 0) {
            System.out.print("Misses (" + missInputs.size() + "/11)");
            System.out.println();
            return;

        }else{
            int counter = 0;
            System.out.print("Misses (" + missInputs.size() + "/11):");
            for (Character element : missInputs) {
                if (counter == missInputs.size() - 1){
                    System.out.print(" " + element.toString().toUpperCase());
                    System.out.println();
                }else {
                    System.out.print(" " + element.toString().toUpperCase() + ",");
                    counter++;
                }
            }
        }


        System.out.println();
    }

    public static void main (String[]args){
        if (args.length == 0) {
            System.out.println("Error: No file name given!");
            return;
        }

        ArrayList<String> words = new ArrayList();
        ArrayList<Character> wordPrintList = new ArrayList();
        ArrayList<Character> currentWord = new ArrayList<Character>();
        ArrayList<Character> missInputs = new ArrayList<>();
        TreeSet<Character> userGuessedChars = new TreeSet<>();
        words = getWordList(args[0]);


        int roundCounter = 1;
        int guessedLetters = 0;

        System.out.println("=".repeat(80));
        System.out.println("HANGMEN (" + words.size() + " Word(s))");
        separatorLine();
        System.out.printf("Word #" + roundCounter +": \n\n");

        for (int round = 1; round <= words.size(); round++) {
            String temp;
            temp = getRandomWord(words);
            words.remove(temp);
            currentWord = wordIntoCharList(temp);
            for(int underscores = 1; underscores <= currentWord.size(); underscores++){
                wordPrintList.add('_');
            }

            System.out.println(currentWord + "\n");

            while (true) {

                if (missInputs.size() != 11) {
                    try {
                        for (int letternumber = 0; letternumber < currentWord.size(); letternumber++) {

                            if (userGuessedChars.contains(currentWord.get(letternumber)) && wordPrintList.get(letternumber) != currentWord.get(letternumber)) {
                                wordPrintList.set(letternumber, currentWord.get(letternumber));
                                guessedLetters++;
                            }
                        }

                        drawHangmanLoss(missInputs.size());
                        wordWithHoles(wordPrintList);
                        printMissinputs(missInputs);

                        if (guessedLetters == currentWord.size())
                            break;


                        System.out.print("Next guess: ");
                        Character c = Character.toUpperCase(charInput());
                        if (c != 0 && !userGuessedChars.contains(c)) {
                            userGuessedChars.add(c);
                            if (!currentWord.contains(c)){
                                missInputs.add(c);
                            }
                        }
                        else if (userGuessedChars.contains(c)){
                            System.out.println("Character already guessed!");
                        }
                        System.out.println();

                    } catch (InputMismatchException e) {}
                }else
                    break;
            }
            return;
        }


    }
}
