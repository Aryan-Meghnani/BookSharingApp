import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Manage {

    Scanner sc = new Scanner(System.in);

    LocalDateTime date = LocalDateTime.now();

    void addBook(List<BookAndOwner> bookAndOwner, Owner owner) {
        sc.nextLine();
        System.out.println("Enter Author Name");
        String author = sc.nextLine();
        System.out.println("Enter Book Name");
        String book = sc.nextLine();
        System.out.println("Enter Isbn Number");
        String isbn = sc.nextLine();
        System.out.println("Enter Number of Copies you have");
        int copies = sc.nextInt();

//        for(BookAndOwner b: bookAndOwner){
//            System.out.println(b.getBooks().equals((new Books(author, book, isbn))));
//        }
        if (bookAndOwner.contains(new BookAndOwner((new Books(author, book, isbn)), owner, copies))) {
            int j = bookAndOwner.indexOf(new BookAndOwner((new Books(author, book, isbn)), owner, copies));
            int c = bookAndOwner.get(j).getCopies();
            bookAndOwner.get(j).setCopies(copies + c);
            System.out.println("Book Already Exist, Number of Copies Updated");
        }
        //adding new book if book does not already exist
        else {
            bookAndOwner.add(new BookAndOwner((new Books(author, book, isbn)), owner, copies));
            System.out.println("Book Added Successfully");
        }
    }

    // Showing booklist with owner
    void showList(List<BookAndOwner> bookAndOwner) {
        int i = 0;
        for (BookAndOwner b : bookAndOwner) {
            System.out.println(++i + ": " + b);
        }
        System.out.println();
    }

    void showRequestList(List<BookRequest> bookRequest) {
        int i = 0;
        for (BookRequest b : bookRequest) {
            System.out.println(++i + ": " + b);
        }
        System.out.println();
    }

    // Searching for books, by name , author, isbn number using filter and issuing books by index number
    // calling recursively if want to search again or book not found
    // here bookAndOwner1 is the filtered list from bookAndOwner  List
    void search(List<BookAndOwner> bookAndOwner, List<BookIssue> bookIssue, Owner owner, List<BookRequest> bookRequest) {
        System.out.println("1. Search By Book Name");
        System.out.println("2. Search By Author");
        System.out.println("3. Search By Isbn Number");
        int p = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Keyword");
        String search = sc.nextLine();
        List<BookAndOwner> bookAndOwner1 = new ArrayList<>();
        if (p == 1) {

//            Matcher check = Pattern.compile(Pattern.quote(bookAndOwner.get(0).getBooks().getBook()), Pattern.CASE_INSENSITIVE).matcher(search);
            bookAndOwner1 = bookAndOwner.stream().filter(i -> i.getBooks().getBook().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        } else if (p == 2) {
            bookAndOwner1 = bookAndOwner.stream().filter(i -> i.getBooks().getAuthor().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());

        } else if (p == 3) {
            bookAndOwner1 = bookAndOwner.stream().filter(i -> i.getBooks().getIsbn().contains(search)).collect(Collectors.toList());

        } else {
            System.out.println("Incorrect Input: Try Again");
            search(bookAndOwner, bookIssue, owner, bookRequest);
        }
        if (bookAndOwner1.isEmpty()) {
            System.out.println("No Book Found");
            search(bookAndOwner, bookIssue, owner, bookRequest);
        } else {
            showList(bookAndOwner1);
            System.out.println("1. Issue");
            System.out.println("2. Search for New");
            int s = sc.nextInt();
            sc.nextLine();
            if (s == 1)
                issue(bookAndOwner, bookAndOwner1, bookIssue, owner, bookRequest);
            else
                search(bookAndOwner, bookIssue, owner, bookRequest);
        }

    }

    //here the main issue of book is happening if status of book is requested then if condition will happen
    //and book will be issue to requested else book will be issued
    void issue(List<BookAndOwner> bookAndOwner, List<BookAndOwner> bookAndOwner1, List<BookIssue> bookIssue, Owner owner, List<BookRequest> bookRequest) {
        synchronized (this) {
            //k to get the index of book to be issued and bookAndOwner1 is filtered list of bookAndOwner
            System.out.println("1. Issue Book at .... Index Number");
            int k = (sc.nextInt()) - 1;
            sc.nextLine();

            IssueReturnDate issueReturnDate = new IssueReturnDate();
            int index = bookAndOwner.indexOf(bookAndOwner1.get(k));
            int copy = bookAndOwner.get(index).getCopies();
            if (copy <= 0) {
                System.out.println("Not Enough Copies to issue");
                System.out.println("Your Request has been added");
                issueReturnDate.setStatus("Requested");
                issueReturnDate.setRequestDate(date);
                bookIssue.add(new BookIssue(bookAndOwner1.get(k), owner, issueReturnDate));
                bookRequest.add(new BookRequest(bookAndOwner1.get(k), owner, date));
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookIndex = bookIssue.indexOf(new BookIssue(bookAndOwner1.get(k), owner, issueReturnDate));
                issueReturnDate.setStatus("Issued");
                issueReturnDate.setIssueDate(date);
                int cop = bookAndOwner.get(index).getCopies();
                bookAndOwner.get(index).setCopies(cop - 1);
                bookIssue.set(bookIndex, new BookIssue(bookAndOwner1.get(k), owner, issueReturnDate));
                System.out.println("Returned Book: Given to Owner " + owner + " for request made");


            } else {

                issueReturnDate.setStatus("Issued");
                issueReturnDate.setIssueDate(date);
                bookIssue.add(new BookIssue(bookAndOwner1.get(k), owner, issueReturnDate));
                bookAndOwner.get(index).setCopies(copy - 1);
                System.out.println("Book Issued");
            }
        }
    }

    // issued books list
    void bookIssuedList(List<BookIssue> bookIssue) {
        int i = 0;
        for (BookIssue b : bookIssue) {
            System.out.println(++i + ": " + b);
        }
        System.out.println();
    }

    void bookReturn(List<BookIssue> bookIssue, Owner owner, List<BookAndOwner> bookAndOwner, List<BookRequest> bookRequest) {

        List<BookIssue> bookIssue1 = new ArrayList<>();
        bookIssue1 = bookIssue.stream().filter(i -> i.getOwner().equals(owner) && i.getIssueReturnDate().getStatus().contains("Issued")).collect(Collectors.toList());
        bookIssuedList(bookIssue1);

        if (bookIssue1.isEmpty()) {
            System.out.println("No Issued Books");
            return;
        } else {
            synchronized (this) {
                System.out.println("1. To Return");
                System.out.println("2. To Continue");
                int p = sc.nextInt();
                if (p == 1) {
                    System.out.println("1. Return Book at .... Index Number");
                    //here k is the index of the book that is to be returned
                    int k = (sc.nextInt()) - 1;
                    sc.nextLine();
                    int index = bookIssue.indexOf(bookIssue1.get(k));
                    bookIssue.get(index).issueReturnDate.setStatus("Returned");
                    bookIssue.get(index).issueReturnDate.setReturnDate(date);

                    int indexBookAndOwner = bookAndOwner.indexOf(bookIssue.get(index).bookAndOwner);
                    int copy = bookAndOwner.get(indexBookAndOwner).getCopies();

                    bookAndOwner.get(indexBookAndOwner).setCopies(copy + 1);
                    System.out.println("Book Returned Successfully");

                    int indexBookRequest = bookRequest.indexOf(new BookRequest(bookAndOwner.get(indexBookAndOwner), owner, date));

//                    bookRequest.get(indexBookRequest).getDate()
                    System.out.println(indexBookRequest);
                    if (bookRequest.contains(new BookRequest(bookAndOwner.get(indexBookAndOwner), owner, date))) {
                        bookRequest.remove(indexBookRequest);
                        notify();
                    }
                } else if (p == 2) {
                    return;
                } else {
                    System.out.println("Incorrect Input: Try Again");
                    bookReturn(bookIssue, owner, bookAndOwner, bookRequest);
                }

            }
        }
    }
}
