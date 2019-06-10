public class LittleP {
    public static void main(String[] args) {
        Lexer.readFile("a.p");
        Tokenlist tl = new Tokenlist();
        Parsing p = new Parsing();
    }
}