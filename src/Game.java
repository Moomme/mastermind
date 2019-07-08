import java.util.InputMismatchException;
import java.util.Scanner;

public class Game{
    Scanner sc;
    Gamestate gstate;

    public Game(){
        this.sc = new Scanner(System.in);
        startGame();
    }

    private int getSeed(){
        int seed;
        System.out.printf("Enter the seed for random number generation: ");
        while(true){
            // nextInt() will throw a InputMismatchException
            // if something else is inputed
            // so loop until we get an int
            try{
                seed = sc.nextInt();
                // If we reach here we got an int and can break
                break;
            }
            catch (InputMismatchException e){
                System.err.printf("Seed must be an integer. Please input an integer: ");
                sc.next(); // Skip next line
                // Continue with loop;
                continue;
            }
        }
        System.out.println();
        return seed;
    }

    private int getMaxLetter(){
        char c;
        System.out.print("Enter the maximum letter for the game (A-Z): ");
        while(true){
            c = sc.next().charAt(0);
            // If char is outside the range A-Z print out error message and continue with loop;
            if(((int) c) < 65 || ((int) c) > 90 ){
                System.out.printf("Letter must be an uppercase letter: ");
                continue;
            }
            else {
                break;
            }
        }
        System.out.println();
        return (int) c;
    }

    private int getPositions(){
        int positions;
        System.out.printf("Enter the number of positions for the game (1-8): ");
        while(true){
            // nextInt() will throw a InputMismatchException
            // if something else is inputed
            // so loop until we get an int
            try{
                positions = sc.nextInt();
                if(positions < 1 || positions > 8){
                    System.err.printf("Positions must be an integer 1-8. Please input an integer (1-8): ");
                    sc.next(); // Skip next line
                    continue;
                }
                // If we reach here we got an int between 1-8 and can break
                break;
            }
            catch (InputMismatchException e){
                System.err.printf("Positions must be an integer 1-8. Please input an integer (1-8): ");
                sc.next(); // Skip next line
                // Continue with loop;
                continue;
            }
        }
        System.out.println();
        return positions;
        
    }

    private int getNumOfGuesses(){
        int guesses;
        System.out.printf("Enter the number of guesses allowed for the game: ");
        while(true){
            // nextInt() will throw a InputMismatchException
            // if something else is inputed
            // so loop until we get an int
            try{
                guesses = sc.nextInt();
                if(guesses < 1){
                    System.err.printf("Gusses must be an integer over 1. Please input an integer: ");
                    sc.next(); // Skip next line
                    continue;
                }
                // If we reach here we got an int 1 or higher and can break
                break;
            }
            catch (InputMismatchException e){
                System.err.printf("Gusses must be an integer over 1. Please input an integer: ");
                sc.next(); // Skip next line
                // Continue with loop;
                continue;
            }
        }
        System.out.println();
        return guesses;
        
    }

    public void startGame(){
        int seed;
        int maxLetter;
        int positions;
        int guesses;

        System.out.println("Welcome to Mastermind!");
        System.out.println();

        // Get seed to generate random string
        seed = getSeed();
        
        // Get largest character of string
        maxLetter = getMaxLetter();

        // Get amount of positions
        positions = getPositions();

        guesses = getNumOfGuesses();

        gstate = new Gamestate(seed, maxLetter, positions, guesses);

    }

    private boolean checkValidGuess(String guess){
        for(int i = 0; i < gstate.positions; i++){
            if(((int) guess.charAt(i)) < 65 || ((int) guess.charAt(i)) > gstate.maxLetter ){
                return false;
            }
        }
        return true;
    }

    public String getGuess(){
        String guess = "";
        int i = 0;
        boolean validInput = true;
        while(guess.length() < gstate.positions){
            guess += sc.next();
        }
        validInput = checkValidGuess(guess);
        if(!validInput){
            throw new InputMismatchException();
        }
        return guess;
    }

    public boolean alive(){
        return gstate.alive;
    }

    private int getExactGuesses(String guess){
        int exactGuesses = 0;
        for(int i = 0; i < guess.length(); i++){
            if(gstate.password.charAt(i) == guess.charAt(i)){
                exactGuesses++;
            }
        }
        return exactGuesses;
   }

    private int getInexactGuesses(String guess){
        int inexactGuesses = 0;
        for(int i = 0; i < gstate.password.length(); i++){
            if(gstate.password.charAt(i) == guess.charAt(i)){
                continue;
            }
            for(int j = 0; j < guess.length(); j++){
                if(gstate.password.charAt(i) == guess.charAt(j)){
                    inexactGuesses++;
                    break;
                }
            }
        }


        return inexactGuesses;
    }

    private void guessesLeft(){
        if(gstate.guesses < 1){
            System.out.println("Whoops, no guesses left. Bye!");
            gstate.alive = false;
        }
    }

    public void play(){
        String guess;
        int exactGuesses;
        int inexactGuesses;
        
        System.out.printf("Put in your guess: ");
        while(true){
            try{
                guess = getGuess();
                break;
            }
            catch (InputMismatchException e){
                System.err.printf("Invalid input, please try again: ");
                sc.next();
                continue;
            }
        }
        guess = guess.substring(0, gstate.positions);

        if(gstate.password.equals(guess)){
            System.out.println("Congratulations You Won!");
            gstate.alive = false;
            return;
        }

        exactGuesses = getExactGuesses(guess);
        inexactGuesses = getInexactGuesses(guess);
        System.out.println("Wrong, you had " + exactGuesses + " exact guesses and " + inexactGuesses + " inexact guesses");
        gstate.guesses--;
        guessesLeft();
        System.out.println();
    }
}