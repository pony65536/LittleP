public class Token {
    public final int tag;
    
    public Token(int t) {
        tag = t;
    }

    public int getTag() {
        return tag;
    }
    public String toString() {
        return "" + (char)tag;
    }
}