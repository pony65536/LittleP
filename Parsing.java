import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Parsing {
    int tokenLoc = 0;
    Tokenlist tl = new Tokenlist();

    public Parsing() {
        Prog();
    }

    public void Prog() {            //程序
        System.out.println("PROG");
        PH();
        PB();
    }

    public void PH() {              //程序首部
        System.out.println("PH");
        if (tl.get(tokenLoc).getTag() == Tag.PROGRAM) {
            tokenLoc++;
            PN();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void PB() {              //程序体
        System.out.println("PB");
        VD();
        CS();
    }

    public void PN() {              //程序名
        System.out.println("PN");
        ID();
    }

    public void VD() {              //变量声明
        System.out.println("VD");
        if (tl.get(tokenLoc).getTag() == Tag.VAR) {
            tokenLoc++;
            VSL();
        }
    }

    public void VS() {              //变量定义
        System.out.println("VS");
        VNL();
        if (tl.get(tokenLoc).toString().equals(":")) {
            tokenLoc++;
            type();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void CS() {              //复合语句
        System.out.println("CS");
        if (tl.get(tokenLoc).getTag() == Tag.BEGIN) {
            tokenLoc++;
            SB();
            if (tl.get(tokenLoc).getTag() == Tag.END) {
                System.out.println("end");
            }
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void VSL() {             //变量定义列表
        System.out.println("VSL");
        while (!tl.get(tokenLoc).toString().equals(";")) {
            VS();
        }
        tokenLoc++;
    }

    public void VNL() {             //变量名列表
        System.out.println("VNL");
        VN();
        if (tl.get(tokenLoc).toString().equals(",")) {
            tokenLoc++;
            VNL();
        }
    }

    public void type() {            //类型
        System.out.println("type");
        if (!(tl.get(tokenLoc).getTag() == Tag.INTEGER)) { 
            System.out.println("Invalid Input");
            System.exit(0);
        }
        tokenLoc++;
    }
    
    public void VN() {              //变量名
        System.out.println("VN");
        ID();
    }

    public void SB() {              //语句块
        System.out.println("SB");
        statement();
        if (tl.get(tokenLoc).toString().equals(";")) {
            tokenLoc++;
            SB();
        }
    }

    public void statement() {       //语句
        System.out.println("statement");
        String str = tl.get(tokenLoc).toString();
        if (str.equals("if")) {
            JS();
        } else if (str.equals("while")) {
            LS();
        } else if (str.equals("begin")) {
            CS();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            AS();
        }
    }

    public void AS() {              //赋值语句
        System.out.println("AS");
        LP();
        if (tl.get(tokenLoc).getTag() == Tag.IS) {
            tokenLoc++;
            RP();
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void JS() {              //条件语句
        System.out.println("JS");
        if (tl.get(tokenLoc).getTag() == Tag.IF) {
            tokenLoc++;
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.THEN) {
                tokenLoc++;
                statement();
                if (tl.get(tokenLoc).getTag() == Tag.ELSE) {
                    tokenLoc++;
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
    }

    public void LS() {              //循环语句
        System.out.println("LS");
        if (tl.get(tokenLoc).getTag() == Tag.WHILE) {
            tokenLoc++;
            RS();
            if (tl.get(tokenLoc).getTag() == Tag.DO) {
                tokenLoc++;
                statement();
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void LP() {              //左部
        System.out.println("LP");
        VN();
    }

    public void RP() {              //右部
        System.out.println("RP");
        MS();
    }

    public void MS() {              //算术表达式
        System.out.println("MS");
        nape();
        MS1();
    }

    public void MS1() {             //算术表达式(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("+") || str.equals("-")) {
            tokenLoc++;
            nape();
            MS1();
        }
    }

    public void RS() {              //关系表达式
        System.out.println("RS");
        MS();
        OP();
        MS();
    }

    public void nape() {            //项
        System.out.println("nape");
        factor();
        nape1();
    }

    public void nape1() {           //项(消除左递归)
        String str = tl.get(tokenLoc).toString();
        if (str.equals("*") || str.equals("/")) {
            tokenLoc++;
            factor();
            nape1();
        }
    }

    public void ID() {              //标识符
        System.out.println("ID");
        if (tl.get(tokenLoc).getTag() == Tag.ID) {
            System.out.println(tl.get(tokenLoc).toString());
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void OP() {              //关系运算符
        System.out.println("OP");
        switch (tl.get(tokenLoc).toString()) {
            case ">=":
            case "<=":
            case "<>":
            case ":=":
            case "<":
            case ">":
            case "=":
        }
        tokenLoc++;

    }

    public void number() {          //数字
        System.out.println("number");
        if (tl.get(tokenLoc).getTag() == Tag.NUM) {
            tokenLoc++;
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }

    public void factor() {          //因子
        System.out.println("factor");
        if (tl.get(tokenLoc).getTag() == Tag.NUM) {
            number();
        } else if (tl.get(tokenLoc).getTag() == Tag.ID) {
            ID();
        } else if (tl.get(tokenLoc).toString().equals("(")) {
            tokenLoc++;
            MS();
            if (tl.get(tokenLoc).toString().equals(")")) {
                tokenLoc++;
            } else {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }
}