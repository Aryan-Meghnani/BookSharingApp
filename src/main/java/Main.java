import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Owner u1 = new Owner("Owner1");
        Owner u2 = new Owner("Owner2");
        Owner u3 = new Owner("Owner3");
        Owner u4 = new Owner("Owner4");


        List<BookAndOwner> bookAndOwner = new ArrayList<>();
        List<BookIssue> bookIssue = new ArrayList<>();
        List<BookRequest> bookRequest = new ArrayList<>();
        Manage manage = new Manage();

        ArrayList<Thread> threads = new ArrayList<Thread>();
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService es = Executors.newFixedThreadPool(coreCount);


        bookAndOwner.add(new BookAndOwner(new Books("John Bidwell", "A Boy at Seven", "1-86092-022-5"), u1, 1));
        bookAndOwner.add(new BookAndOwner(new Books("JK Rowling", "Harry Potter", "9780747532743"), u2, 3));
        bookAndOwner.add(new BookAndOwner(new Books("Charles Dickens ", "The Signalman", "9780747532743"), u3, 3));
        bookAndOwner.add(new BookAndOwner(new Books("Stephen Crane", "The Open Boat", "1-86092-038-1"), u4, 4));
        bookAndOwner.add(new BookAndOwner(new Books("Ngaio Marsh", "Death on the Air", "1-86092-020-9"), u3, 5));
        bookAndOwner.add(new BookAndOwner(new Books("Ngaio Marsh", "Death on the Air", "1-86092-020-9"), u2, 5));

        System.out.println("Hello, Welcome to Book Sharing App: ");
        Scanner sc = new Scanner(System.in);

        Owner owner = u1;
        while (true) {
            System.out.println("Select User/Owner");
            System.out.println("1. Owner1");
            System.out.println("2. Owner2");
            System.out.println("3. Owner3");
            System.out.println("4. Owner4");
            int own = sc.nextInt();
            sc.nextLine();
            switch (own) {
                case 1:
                    break;
                case 2:
                    owner = u2;
                    break;
                case 3:
                    owner = u3;
                    break;
                case 4:
                    owner = u4;
                    break;
                default:
                    System.out.println("Incorrect Input: Try Again\n");
                    continue;
            }

            while (true) {
                System.out.println("1. To Add Books");
                System.out.println("2. To Show Books");
                System.out.println("3. To Search and Issue Books");
                System.out.println("4. To Return Books/Show Issued Books");
                System.out.println("5. To Show all Issued/Requested Books");
                System.out.println("6. To Switch User");
                System.out.println("7. To Show Book Requests");
                System.out.println("8. To Exit");

                int point = sc.nextInt();
                if (point == 1) {
                    manage.addBook(bookAndOwner, owner);
                    sc.nextLine();

                    continue;
                } else if (point == 2) {
//                System.out.println(bookAndOwner);
                    manage.showList(bookAndOwner);
                    sc.nextLine();

                    continue;
                } else if (point == 3) {
                    Owner finalOwner = owner;
                    Thread t1 = new Thread(() ->
                    {
                        manage.search(bookAndOwner, bookIssue, finalOwner, bookRequest);
                    });
                    t1.start();
//                    System.out.println(t1.getName());
                    while (true) {
                        if (t1.getState() == Thread.State.TERMINATED || t1.getState() == Thread.State.WAITING)
                            break;
                    }
//                    manage.search(bookAndOwner, bookIssue, owner);

                    sc.nextLine();

                    System.out.println("1. To Continue");
                    System.out.println("2. To Exit");
                    int cont = sc.nextInt();
                    if (cont == 1)
                        continue;
                    else
                        System.exit(0);

                } else if (point == 4) {
                    manage.bookReturn(bookIssue, owner, bookAndOwner, bookRequest);
                    sc.nextLine();
                } else if (point == 5) {
                    manage.bookIssuedList(bookIssue);
                    sc.nextLine();
                } else if (point == 6) {
                    break;
                } else if (point == 7) {
                    manage.showRequestList(bookRequest);
                } else if (point == 8) {
                    System.exit(0);
                } else {
                    System.out.println("Incorrect Input, Try Again\n");
                }
            }
        }
    }
}
