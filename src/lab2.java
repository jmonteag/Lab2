import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap; // import the HashMap class

//Don't forget to fix args

public class lab2 {

    public static void main(String[] args) throws Exception {

        HashMap<String, Integer> labels = new HashMap<>();

        HashMap<String, String> registers = new HashMap<>();

        registers.put("$0", "00000");
        registers.put("$zero", "00000");
        registers.put("$v0", "00010");
        registers.put("$v1", "00011");
        registers.put("$a0", "00100");
        registers.put("$a1", "00101");
        registers.put("$a2", "00110");
        registers.put("$a3", "00111");
        registers.put("$t0", "01000");
        registers.put("$t1", "01001");
        registers.put("$t2", "01010");
        registers.put("$t3", "01011");
        registers.put("$t4", "01100");
        registers.put("$t5", "01101");
        registers.put("$t6", "01110");
        registers.put("$t7", "01111");
        registers.put("$t8", "11000");
        registers.put("$t9", "11001");
        registers.put("$sp", "11101");
        registers.put("$ra", "11111");
        registers.put("$s0", "10000");
        registers.put("$s1", "10001");
        registers.put("$s2", "10010");
        registers.put("$s3", "10011");
        registers.put("$s4", "10100");
        registers.put("$s5", "10101");
        registers.put("$s6", "10110");
        registers.put("$s7", "10111");


        File file = new File(args[0]);

        BufferedReader pass1 = new BufferedReader(new FileReader(file));

        String st;
        int count = 0; // counts what line # we are on

        while ((st = pass1.readLine()) != null) { //as long as something is inside it will continue to iterate
            String sub = st.trim(); //removes white spaces in front of line

            if (sub.length() != 0 && sub.charAt(0) != '#') {//checks if line is not completely blank or a comment if so ignore
                if (sub.contains(":")) { //checks if label exists on line
                    String[] jAddress = sub.split(":", 2); //split by : to get label name
                    if (jAddress[1].length()!=0) {
                        labels.put(jAddress[0], count);
                        count++;
                    } else
                        labels.put(jAddress[0], count);
                } else
                    count++;
            }

        }
        pass1.close();

        BufferedReader pass2 = new BufferedReader(new FileReader(file));

        int executeCount = 0;

        while ((st = pass2.readLine()) != null) { //as long as something is inside it will continue to iterate
            String sub = st.trim(); //trims all white spaces after and before

            if (sub.length() != 0 && sub.charAt(0) != '#') { //checks if line is not completely blank or a comment if so ignore
                if (sub.contains(":")) { //if line has a label
                    String[] s = sub.split(":", 2); //split by ":"
                    String s1 = s[1].trim();

                    if (s1.length() != 0) { //if label was not on a line alone
                        executeCount++;
                        if (s1.contains("#")) { // if line has a comment
                            String end = s1.split("#")[0]; //removes comment by splitting string by ":"
                            end = end.trim();
                            instruPrint(end, labels, registers, executeCount);
                        } else //if it doesn't have a comment
                            instruPrint(s1, labels, registers, executeCount);
                    }
                } else { //if line doesn't have a label
                    executeCount++;
                    if (sub.contains("#")) { // if line has a comment
                        String end = sub.split("#")[0]; //removes comment by splitting string by "#"
                        instruPrint(end, labels, registers, executeCount);
                    } else //if line doesn't have a comment
                        instruPrint(sub, labels, registers, executeCount);
                }
            }
        }

    }

    public static void instruPrint(String instruction, HashMap<String, Integer> labels, HashMap<String, String> registers,
                                   int executeCount) {
        String[] newInstru = instruction.split("\\s+|\\$", 2);
        String command = newInstru[0];
        String arguments;

        if (newInstru[1].charAt(0) != '$') {
            arguments = '$' + newInstru[1];
        } else {
            arguments = newInstru[1];
        }

        switch (command) {
            case "and":
                andInstru(arguments, registers);
                break;
            case "or":
                orInstru(arguments, registers);
                break;
            case "add":
                addInstru(arguments, registers);
                break;
            case "addi":
                addiInstru(arguments, registers);
                break;
            case "sll":
                sllInstru(arguments, registers);
                break;
            case "sub":
                subInstru(arguments, registers);
                break;
            case "slt":
                sltInstru(arguments, registers);
                break;
            case "beq":
                beqInstru(arguments, labels, registers, executeCount);
                break;
            case "bne":
                bneInstru(arguments, labels, registers, executeCount);
                break;
            case "lw":
                lwInstru(arguments, registers);
                break;
            case "sw":
                swInstru(arguments, registers);
                break;
            case "j":
                jInstru(arguments, labels);
                break;
            case "jr":
                jrInstru(arguments, registers);
                break;
            case "jal":
                jalInstru(arguments, labels);
                break;
            default:
                System.out.println("invalid instruction: " + command);
                System.exit(0);

        }
    }

    public static void andInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //rt

        String rd = registers.get(reg1);
        String rs = registers.get(reg2);
        String rt = registers.get(reg3);

        System.out.println("000000 " + rs + " " + rt + " " + rd + " 00000 100100");
    }

    public static void orInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //rt

        String rd = registers.get(reg1);
        String rs = registers.get(reg2);
        String rt = registers.get(reg3);

        System.out.println("000000 " + rs + " " + rt + " " + rd + " 00000 100101");
    }

    public static void addInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //rt

        String rd = registers.get(reg1);
        String rs = registers.get(reg2);
        String rt = registers.get(reg3);

        System.out.println("000000 " + rs + " " + rt + " " + rd + " 00000 100000");
    }

    public static void addiInstru(String arguments, HashMap<String, String> registers) { //needs digits displayed fixed
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rt
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //imm

        String rt = registers.get(reg1);
        String rs = registers.get(reg2);
        int imm = Integer.parseInt(reg3);
        int bitNum = 32-16;

        System.out.println("001000 " + rs + " " + rt + " " + decToBin(bitNum,imm));
    }

    public static void sllInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rt
        String reg3 = regs[2].trim(); //sa

        String rd = registers.get(reg1);
        String rt = registers.get(reg2);
        int sa = Integer.parseInt(reg3);
        int bitNum = 32 - 27;

        System.out.println("000000 00000 " + rt + " " + rd + " " + decToBin(bitNum,sa) + " 000000");


    }

    public static void subInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //rt

        String rd = registers.get(reg1);
        String rs = registers.get(reg2);
        String rt = registers.get(reg3);

        System.out.println("000000 " + rs + " " + rt + " " + rd + " 00000 100010");
    }

    public static void sltInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rd
        String reg2 = regs[1].trim(); //rs
        String reg3 = regs[2].trim(); //rt

        String rd = registers.get(reg1);
        String rs = registers.get(reg2);
        String rt = registers.get(reg3);

        System.out.println("000000 " + rs + " " + rt + " " + rd + " 00000 101010");
    }

    public static void beqInstru(String arguments, HashMap<String, Integer> labels, HashMap<String, String> registers,
                                 int executeCount) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rs
        String reg2 = regs[1].trim(); //rt
        String reg3 = regs[2].trim(); //offset

        String rs = registers.get(reg1);
        String rt = registers.get(reg2);
        int target = labels.get(reg3);
        int bitNum = 32 - 16;

        System.out.println("000100 " + rs + " " + rt + " " + offset(executeCount,target,bitNum));
    }

    public static void bneInstru(String arguments, HashMap<String, Integer> labels, HashMap<String, String> registers,
                                 int executeCount) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rs
        String reg2 = regs[1].trim(); //rt
        String reg3 = regs[2].trim(); //offset

        String rs = registers.get(reg1);
        String rt = registers.get(reg2);
        int target = labels.get(reg3);
        int bitNum = 32 - 16;

        System.out.println("000101 " + rs + " " + rt + " " + offset(executeCount,target,bitNum));
    }

    public static void lwInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rt
        String reg2 = regs[1].trim(); //offset(rs)

        String[] regsSplit = reg2.split("\\(", 2);
        String off = regsSplit[0];
        int offset = Integer.parseInt(off);

        String rs = regsSplit[1].split("\\)")[0];
        rs = registers.get(rs);
        String rt = registers.get(reg1);

        int bitNum = 32 -16;

        System.out.println("100011 " + rs + " " + rt + " " + decToBin(bitNum,offset));
    }

    public static void swInstru(String arguments, HashMap<String, String> registers) {
        String[] regs = arguments.split(",", 4);
        String reg1 = regs[0].trim(); //rt
        String reg2 = regs[1].trim(); //offset(rs)

        String[] regsSplit = reg2.split("\\(", 2);
        String off = regsSplit[0];
        int offset = Integer.parseInt(off);

        String rs = regsSplit[1].split("\\)")[0];
        rs = registers.get(rs);

        String rt = registers.get(reg1);

        int bitNum = 32 -16;

        System.out.println("101011 " + rs + " " + rt + " " + decToBin(bitNum,offset));
    }

    public static void jInstru(String arguments, HashMap<String, Integer> labels) {
        String spl = arguments.split("\\$")[1];
        int target = labels.get(spl);
        int bitNum = 32-6;
        if(target==1){
            target = 0;
        }
        System.out.println("000010 " + decToBin(bitNum,target));
    }

    public static void jrInstru(String arguments, HashMap<String, String> registers) {
        String split = registers.get(arguments);
        int rs = Integer.parseInt(split);

        System.out.println("000000 " + rs + " 000000000000000 001000");
    }

    public static void jalInstru(String arguments, HashMap<String, Integer> labels) { //needs fixing
        String spl = arguments.split("\\$")[1];
        int target = labels.get(spl);
        int bitNum = 32-6;
        System.out.println("000011 " + decToBin(bitNum,target));
    }

    public static String decToBin(int bitNum, int decminal){
        String bin = Integer.toBinaryString(decminal);
        if(decminal < 0)
            bin = bin.substring(bin.length() - bitNum,bin.length());
        else{
            while(bin.length() != bitNum)
                bin = "0" + bin;
        }
        return bin;
    }

    public static String offset(int executeNum, int targetNum, int bitNum){
        int offset = targetNum - executeNum;
        return decToBin(bitNum,offset);
    }


}
