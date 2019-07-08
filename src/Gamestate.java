import java.util.Random;
import java.lang.Math;

public class Gamestate{
    int seed;
    int maxLetter;
    int positions;
    int guesses;
    String password;
    Random rnd;
    boolean alive;

    public Gamestate(int seed, int maxLetter, int positions, int guesses){
        this.seed = seed;
        this.maxLetter = maxLetter;
        this.positions = positions;
        this.guesses = guesses;
        this.rnd = new Random();
        this.alive = true;
        this.password = "";
        generatePassword();
    }

    public void generatePassword(){
        for(int i = 0; i < positions; i++){
            rnd.setSeed(seed);
            seed = (Math.abs(rnd.nextInt()) % (maxLetter - 65 + 1)) + 65;
            System.out.println(seed);
            password += ((char) seed);            
        }
        System.out.println(password);
        System.out.println();
    }
}