import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Parsing {
    int tokenLoc = 0;
    Tokenlist tl = new Tokenlist();
    private int depth;

    private void printBranch() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }

    public Parsing() {
        System.out.println("程序");
        Prog();
    }

    public void Prog() {            //程序
        depth++;
        printBranch();
        System.out.println("程序首部");
        PH();
        printBranch();
        System.out.println("程序体");
        PB();
        depth--;
    }

    public void PH() {              //程序首部
        depth++;
        printBranch();
        System.out.println("program");
        if (tl.get(tokenLoc).getTag() == Tag.PROGRAM) {
            tokenLoc++;
            printBranch();
            System.out.println("程序名");
            PN();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void PB() {              //程序体
        depth++;
        printBranch();
        System.out.println("变量声明");
        VD();
        printBranch();
        System.out.println("复合语句");
        CS();
        depth--;
    }

    public void PN() {              //程序名
        depth++;
        printBranch();
        System.out.println("标识符");
        ID();
        depth--;
    }

    public void VD() {              //变量声明
        depth++;
        printBranch();
        System.out.println("var");
        if (tl.get(tokenLoc).getTag() == Tag.VAR) {
            printBranch();
            System.out.println("变量定义列表");
            tokenLoc++;
            VSL();
        }
        depth--;
    }

    public void VS() {              //变量定义
        depth++;
        printBranch();
        System.out.println("变量名列表");
        VNL();
        if (tl.get(tokenLoc).toString().equals(":")) {
            printBranch();
            System.out.println(":");
            tokenLoc++;
            printBranch();
            System.out.println("类型");
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
            printBranch();
            System.out.println("begin");
            tokenLoc++;
            printBranch();
            System.out.println("语句块");
            SB();
            if (tl.get(tokenLoc).getTag() == Tag.END) {
                printBranch();
                System.out.println("end");
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
            printBranch();
            System.out.println("变量定义");
            VS();
        }
        printBranch();
        System.out.println(";");
        tokenLoc++;
        depth--;
    }

    public void VNL() {             //变量名列表
        depth++;
        printBranch();
        System.out.println("变量名");
        VN();
        if (tl.get(tokenLoc).toString().equals(",")) {
            printBranch();
            System.out.println(",");
            tokenLoc++;
            VNL();
        }
        depth--;
    }

    public void type() {            //类型
        depth++;
        if (!(tl.get(tokenLoc).getTag() == Tag.INTEGER)) { 
            System.out.println("Invalid Input");
            System.exit(0);
        }
        printBranch();
        System.out.println("integer");
        tokenLoc++;
        depth--;
    }
    
    public void VN() {              //变量名
        depth++;
        printBranch();
        System.out.println("标识符");
        ID();
        depth--;
    }

    public void SB() {              //语句块
        depth++;
        printBranch();
        System.out.println("语句");
        statement();
        if (tl.get(tokenLoc).toString().equals(";")) {
            printBranch();
            System.out.println(";");
            tokenLoc++;
            SB();
        }
        depth--;
    }

    public void statement() {       //语句
        depth++;
        String str = tl.get(tokenLoc).toString();
        if (str.equals("if")) {
            printBranch();
            System.out.println("条件语句");
            JS();
        } else if (str.equals("while")) {
            printBranch();
            System.out.println("循环语句");
            LS();
        } else if (str.equals("begin")) {
            printBranch();
            System.out.println("复合语句");
            CS();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch();
            System.out.println("赋值语句");
            AS();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        depth--;
    }

    public void AS() {              //赋值语句
        depth++;
        printBranch();
        System.out.println("左部");
        LP();
        if (tl.get(tokenLoc).getTag() == Tag.IS) {
            printBranch();
            System.out.println(":=");
            tokenLoc++;
            printBranch();
            System.out.println("右部");
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
            printBranch();
            System.out.println("if");
            tokenLoc++;
            printBranch();
            System.out.println("关系表达式");
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.THEN) {
                tokenLoc++;
                printBranch();
                System.out.println("语句");
                statement();
                if (tl.get(tokenLoc).getTag() == Tag.ELSE) {
                    printBranch();
                    System.out.println("else");
                    tokenLoc++;
                    printBranch();
                    System.out.println("语句");
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
            printBranch();
            System.out.println("while");
            tokenLoc++;
            printBranch();
            System.out.println("关系表达式");
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.DO) {
                printBranch();
                System.out.println("do");
                tokenLoc++;
                printBranch();
                System.out.println("语句");
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
        printBranch();
        System.out.println("变量名");
        VN();
        depth--;
    }

    public void RP() {              //右部
        depth++;
        System.out.println("算术表达式");
        MS();
        depth--;
    }

    public void MS() {              //算术表达式
        depth++;
        System.out.println("项");
        nape();
        MS1();
        depth--;
    }

    public void MS1() {             //算术表达式(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("+")) {
            printBranch();
            System.out.println("+");
            tokenLoc++;
            printBranch();
            System.out.println("项");
            nape();
            MS1();
        }
        if (str.equals("-")) {
            printBranch();
            System.out.println("-");
            tokenLoc++;
            printBranch();
            System.out.println("项");
            nape();
            MS1();
        }
    }

    public void RS() {              //关系表达式
        depth++;
        System.out.println("RS");
        printBranch();
        System.out.println("算术表达式");
        MS();
        printBranch();
        System.out.println("关系运算符");
        OP();
        printBranch();
        System.out.println("算术表达式");
        MS();
        depth--;
    }

    public void nape() {            //项
        depth++;
        printBranch();
        System.out.println("因子");
        factor();
        nape1();
        depth--;
    }

    public void nape1() {           //项(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("*")) {
            printBranch();
            System.out.println("*");
            tokenLoc++;
            printBranch();
            System.out.println("项");
            factor();
            nape1();
        }
        if (str.equals("/")) {
            printBranch();
            System.out.println("/");
            tokenLoc++;
            printBranch();
            System.out.println("项");
            factor();
            nape1();
        }
    }

    public void ID() {              //标识符
        depth++;
        if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch();
            System.out.println(tl.get(tokenLoc).toString());
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
                printBranch();
                System.out.println(">=");
            case "<=":
                printBranch();
                System.out.println("<=");
            case "<>":
                printBranch();
                System.out.println("<>");
            case ":=":
                printBranch();
                System.out.println(":=");
            case "<":
                printBranch();
                System.out.println("<");
            case ">":
                printBranch();
                System.out.println(">");
            case "=":
                printBranch();
                System.out.println("=");
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
            printBranch();
            System.out.println(tl.get(tokenLoc).toString());
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
            printBranch();
            System.out.println("数字");
            number();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            printBranch();
            System.out.println("标识符");
            ID();
        } else if (tl.get(tokenLoc).toString().equals("(")) {
            printBranch();
            System.out.println("(");
            tokenLoc++;
            printBranch();
            System.out.println("算术表达式");
            MS();
            if (tl.get(tokenLoc).toString().equals(")")) {
                printBranch();
                System.out.println(")");
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