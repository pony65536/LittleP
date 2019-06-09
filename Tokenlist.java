import java.util.ArrayList;
import java.util.Iterator;

public class Tokenlist{
    private static ArrayList<Token> tokenlist = new ArrayList<Token>();
    public static StringBuffer str = new StringBuffer();

    public static void appendword(Token t) {
        tokenlist.add(t);
        str.append(t.toString());
        str.append("\n");
    }

    public String toString() {
        return str.toString();
    }

    public static Token get(int index) {
        return tokenlist.get(index);
    }
}