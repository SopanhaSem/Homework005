
package view;

import java.util.Scanner;

public class View {
    public static int menu() {
        System.out.println("=".repeat(40));
        System.out.println("[1]. Add New Course");
        System.out.println("[2]. List Course");
        System.out.println("[3]. Find Course By ID");
        System.out.println("[4]. Find Course By Title");
        System.out.println("[5]. Remove Course By ID");
        System.out.println("[0-99]. Exit The Program");
        System.out.print("[+] Insert Your Option: ");
        return (new Scanner(System.in)).nextInt();
    }
}
