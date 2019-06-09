public class Word extends Token {
    public final String lexeme;

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }
    public String toString() {
        return lexeme;
    }

    public static final Word 
        le  = new Word("<=", Tag.LE),   ge  = new Word(">=", Tag.GE),
        neq = new Word("<>", Tag.NEQ),  is  = new Word(":=", Tag.IS);
}