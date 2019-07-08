public class main{

    public static void main(String[] args) {
        Game game = new Game();

       while(game.alive()){
           game.play();
       }

    }
}