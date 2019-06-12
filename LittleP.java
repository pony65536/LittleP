import java.io.IOException;

public class LittleP {
    public static void main(String[] args) throws IOException {
        Lexer.readFile(args[0]);
        Tokenlist tl = new Tokenlist();
        Parsing p = new Parsing();
        p.outputTree();
    }
}