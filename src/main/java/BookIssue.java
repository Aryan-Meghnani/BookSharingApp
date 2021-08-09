import java.util.Objects;

public class BookIssue {
    BookAndOwner bookAndOwner;
    Owner owner;
    IssueReturnDate issueReturnDate;

    public BookIssue(BookAndOwner bookAndOwner, Owner owner, IssueReturnDate issueReturnDate) {
        this.bookAndOwner = bookAndOwner;
        this.owner = owner;
        this.issueReturnDate = issueReturnDate;
    }

    public BookAndOwner getBookAndOwner() {
        return bookAndOwner;
    }

    public IssueReturnDate getIssueReturnDate() {
        return issueReturnDate;
    }

    public Owner getOwner() {
        return owner;
    }


    @Override
    public String toString() {
        return "Author='" + bookAndOwner.getBooks().getAuthor() + '\'' +
                ", Book='" + bookAndOwner.getBooks().getBook() + '\'' +
                ", Isbn='" + bookAndOwner.getBooks().getIsbn() + '\'' +
                ", Owner of Book='" + bookAndOwner.getOwner() + '\'' +
                ", Issued to Owner=" + owner + '\'' +
                ", IssueAndReturn='" + issueReturnDate + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookIssue bookIssue = (BookIssue) o;
        return Objects.equals(bookAndOwner, bookIssue.bookAndOwner) && Objects.equals(owner, bookIssue.owner) && issueReturnDate.getStatus() == "Requested";
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookAndOwner, owner, issueReturnDate);
    }
}
