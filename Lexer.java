import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.lang.Character;

public class Lexer{

    public Lexer(String str) {
        readFile(str);
    }

    public static void lexer(String codeIn) {
        int codeSize = codeIn.length() - 1;
        char readCh = ' ';
        for (int charLoc = 0; charLoc < codeSize; charLoc++) {
            readCh = codeIn.charAt(charLoc);
            if (readCh == ' ' || readCh == '\t')
                continue;

            if (Character.isLetter(readCh)) {
                StringBuffer buffer = new StringBuffer();
                while (charLoc < codeSize && Character.isLetterOrDigit(readCh)) {
                    buffer.append(readCh);
                    charLoc++;
                    readCh = codeIn.charAt(charLoc);
                }
                charLoc--;
                if (Character.isLetterOrDigit(readCh))
                    buffer.append(readCh);
                String s = buffer.toString();
                Word w;
                switch (s) {
                    case "program":
                        w = new Word(s, Tag.PROGRAM);
                        Tokenlist.appendword(w);
                        continue;
                    case "var":
                        w = new Word(s, Tag.VAR);
                        Tokenlist.appendword(w);
                        continue;
                    case "integer":
                        w = new Word(s, Tag.INTEGER);
                        Tokenlist.appendword(w);
                        continue;
                    case "begin":
                        w = new Word(s, Tag.BEGIN);
                        Tokenlist.appendword(w);
                        continue;
                    case "end":
                        w = new Word(s, Tag.END);
                        Tokenlist.appendword(w);
                        continue;
                    case "if":
                        w = new Word(s, Tag.IF);
                        Tokenlist.appendword(w);
                        continue;
                    case "then":
                        w = new Word(s, Tag.THEN);
                        Tokenlist.appendword(w);
                        continue;
                    case "else":
                        w = new Word(s, Tag.ELSE);
                        Tokenlist.appendword(w);
                        continue;
                    case "while":
                        w = new Word(s, Tag.WHILE);
                        Tokenlist.appendword(w);
                        continue;
                    case "do":
                        w = new Word(s, Tag.DO);
                        Tokenlist.appendword(w);
                        continue;
                    default:
                        w = new Word(s, Tag.ID);
                        Tokenlist.appendword(w);
                        continue;
                }
            }

            if (Character.isDigit(readCh)) {
                int num = 0;
                while (Character.isDigit(readCh)) {
                    num = 10 * num + Character.digit(readCh, 10);
                    charLoc++;
                    readCh = codeIn.charAt(charLoc);
                }
                charLoc--;
                if (Character.isDigit(readCh))
                    num = 10 * num + Character.digit(readCh, 10);
                Tokenlist.appendword(new Num(num));
                continue;
            }

            switch (readCh) {
                case ':':
                    if (codeIn.charAt(charLoc + 1) == '=') {
                        Tokenlist.appendword(Word.is);
                        charLoc++;
                        continue;
                    } else {
                        Tokenlist.appendword(new Token(':'));
                    }
                    continue;
                case '<':
                    if (codeIn.charAt(charLoc + 1) == '=') {
                        Tokenlist.appendword(Word.le);
                        charLoc++;
                        continue;
                    } else if (codeIn.charAt(charLoc + 1) == '>') {
                        Tokenlist.appendword(Word.neq);
                        charLoc++;
                        continue;
                    } else {
                        Tokenlist.appendword(new Token('<'));
                    }
                    
                case '>':
                    if (codeIn.charAt(charLoc + 1) == '=') {
                        Tokenlist.appendword(Word.ge);
                        charLoc++;
                        continue;
                    } else {
                        Tokenlist.appendword(new Token('>'));
                    }
            }
            Token tok = new Token(readCh);
            Tokenlist.appendword(tok);
        }
    }

    public static void readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            String tempString = null;
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine())  != null) {
                lexer(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}