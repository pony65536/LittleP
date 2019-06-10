public class LittleP {
    public static void main(String[] args) {
        Lexer.readFile("a.p");
        Tokenlist tl = new Tokenlist();
        System.out.println(tl.toString());
        Parsing p = new Parsing();
    }
}