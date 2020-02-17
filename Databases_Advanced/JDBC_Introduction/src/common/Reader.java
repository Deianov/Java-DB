package common;

import java.util.Scanner;

public class Reader {
    private Scanner scanner;

    public Reader() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine(){
        return scanner.nextLine();
    }

    public String readLine(String text){
        System.out.print(text);
        return scanner.nextLine();
    }
}
