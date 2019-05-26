package bankband.bank;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length == 1 && args[0].equals("install")) {
            Database.getInstance().install();

            System.out.println("Installation successful.");
            return;
        }

        System.out.println("Hello, World!");
    }

}
