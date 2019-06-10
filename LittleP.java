public class LittleP {
    public static void main(String[] args) {
        Lexer.readFile(args[0]);
        Tokenlist tl = new Tokenlist();
        Parsing p = new Parsing();
    }
}