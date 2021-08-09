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
                ", Isbn='" + bookAndOwner.getBooks().getIsbn() + '\''+
                ", Owner of Book='" + bookAndOwner.getOwner() + '\''+
                ", Issued to Owner=" + owner+'\''+
                ", IssueAndReturn='"+ issueReturnDate+'\'';
    }
}
