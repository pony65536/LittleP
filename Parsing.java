import javax.swing.plaf.synth.SynthOptionPaneUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Parsing {
    int tokenLoc = 0;
    Tokenlist tl = new Tokenlist();
    private int depth;
    static String outputString = new String();

    private void printBranch(String str) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
            outputString += "  ";
        }
        System.out.println(str);
        outputString += str + "\r";
    }

    public Parsing() {
        System.out.println("程序");
        Prog();
    }

    public void outputTree() throws IOException {
        File writename = new File("./output.txt");
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(outputString);
        out.flush();
        out.close();
    }

    public void Prog() {            //程序
        depth++;
        printBranch("程序首部");
        PH();
        printBranch("程序体");
        PB();
        depth--;
    }

    public void PH() {              //程序首部
        depth++;
        printBranch("program");
        if (tl.get(tokenLoc).getTag() == Tag.PROGRAM) {
            tokenLoc++;
            printBranch("程序名");
            PN();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void PB() {              //程序体
        depth++;
        printBranch("变量声明");
        VD();
        printBranch("复合语句");
        CS();
        depth--;
    }

    public void PN() {              //程序名
        depth++;
        printBranch("标识符");
        ID();
        depth--;
    }

    public void VD() {              //变量声明
        depth++;
        printBranch("var");
        if (tl.get(tokenLoc).getTag() == Tag.VAR) {
            printBranch("变量定义列表");
            tokenLoc++;
            VSL();
        }
        depth--;
    }

    public void VS() {              //变量定义
        depth++;
        printBranch("变量名列表");
        VNL();
        if (tl.get(tokenLoc).toString().equals(":")) {
            printBranch(":");
            tokenLoc++;
            printBranch("类型");
            type();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void CS() {              //复合语句
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.BEGIN) {
            printBranch("begin");
            tokenLoc++;
            printBranch("语句块");
            SB();
            if (tl.get(tokenLoc).getTag() == Tag.END) {
                printBranch("end");
            }
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void VSL() {             //变量定义列表
        depth++;
        while (!tl.get(tokenLoc).toString().equals(";")) {
            printBranch("变量定义");
            VS();
        }
        printBranch(";");
        tokenLoc++;
        depth--;
    }

    public void VNL() {             //变量名列表
        depth++;
        printBranch("变量名");
        VN();
        if (tl.get(tokenLoc).toString().equals(",")) {
            printBranch(",");
            tokenLoc++;
            depth--;
            VNL();
            depth++;
        }
        depth--;
    }

    public void type() {            //类型
        depth++;
        if (!(tl.get(tokenLoc).getTag() == Tag.INTEGER)) { 
            System.out.println("Invalid Input");
            System.exit(0);
        }
        printBranch("integer");
        tokenLoc++;
        depth--;
    }
    
    public void VN() {              //变量名
        depth++;
        printBranch("标识符");
        ID();
        depth--;
    }

    public void SB() {              //语句块
        depth++;
        printBranch("语句");
        statement();
        if (tl.get(tokenLoc).toString().equals(";")) {
            printBranch(";");
            tokenLoc++;
            SB();
        }
        depth--;
    }

    public void statement() {       //语句
        depth++;
        String str = tl.get(tokenLoc).toString();
        if (str.equals("if")) {
            printBranch("条件语句");
            JS();
        } else if (str.equals("while")) {
            printBranch("循环语句");
            LS();
        } else if (str.equals("begin")) {
            printBranch("复合语句");
            CS();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch("赋值语句");
            AS();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void AS() {              //赋值语句
        depth++;
        printBranch("左部");
        LP();
        if (tl.get(tokenLoc).getTag() == Tag.IS) {
            printBranch(":=");
            tokenLoc++;
            printBranch("右部");
            RP();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void JS() {              //条件语句
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.IF) {
            printBranch("if");
            tokenLoc++;
            printBranch("关系表达式");
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.THEN) {
                tokenLoc++;
                printBranch("then");
                printBranch("语句");
                statement();
                if (tl.get(tokenLoc).getTag() == Tag.ELSE) {
                    printBranch("else");
                    tokenLoc++;
                    printBranch("语句");
                    statement();
                } else {
                    System.out.println("Invalid Input");
                    System.exit(0);
                }
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void LS() {              //循环语句
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.WHILE) {
            printBranch("while");
            tokenLoc++;
            printBranch("关系表达式");
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.DO) {
                printBranch("do");
                tokenLoc++;
                printBranch("语句");
                statement();
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void LP() {              //左部
        depth++;
        printBranch("变量名");
        VN();
        depth--;
    }

    public void RP() {              //右部
        depth++;
        printBranch("算术表达式");
        MS();
        depth--;
    }

    public void MS() {              //算术表达式
        depth++;
        printBranch("项");
        nape();
        MS1();
        depth--;
    }

    public void MS1() {             //算术表达式(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("+")) {
            printBranch("+");
            tokenLoc++;
            printBranch("项");
            nape();
            MS1();
        }
        if (str.equals("-")) {
            printBranch("-");
            tokenLoc++;
            printBranch("项");
            nape();
            MS1();
        }
    }

    public void RS() {              //关系表达式
        depth++;
        printBranch("算术表达式");
        MS();
        printBranch("关系运算符");
        OP();
        printBranch("算术表达式");
        MS();
        depth--;
    }

    public void nape() {            //项
        depth++;
        printBranch("因子");
        factor();
        nape1();
        depth--;
    }

    public void nape1() {           //项(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("*")) {
            printBranch("*");
            tokenLoc++;
            printBranch("项");
            factor();
            nape1();
        }
        if (str.equals("/")) {
            printBranch("/");
            tokenLoc++;
            printBranch("项");
            factor();
            nape1();
        }
    }

    public void ID() {              //标识符
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch(tl.get(tokenLoc).toString());
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void OP() {              //关系运算符
        depth++;
        switch (tl.get(tokenLoc).toString()) {
            case ">=":
                printBranch(">=");
                break;
            case "<=":
                printBranch("<=");
                break;
            case "<>":
                printBranch("<>");
                break;
            case ":=":
                printBranch(":=");
                break;
            case "<":
                printBranch("<");
                break;
            case ">":
                printBranch(">");
                break;
            case "=":
                printBranch("=");
                break;
            default:
                System.out.println("Invalid Input");
                System.exit(0);
        }
        tokenLoc++;
        depth--;
    }

    public void number() {          //数字
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.NUM) {
            printBranch(tl.get(tokenLoc).toString());
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void factor() {          //因子
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.NUM) {
            printBranch("数字");
            number();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch("标识符");
            ID();
        } else if (tl.get(tokenLoc).toString().equals("(")) {
            printBranch("(");
            tokenLoc++;
            printBranch("算术表达式");
            MS();
            if (tl.get(tokenLoc).toString().equals(")")) {
                printBranch(")");
                tokenLoc++;
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }
}