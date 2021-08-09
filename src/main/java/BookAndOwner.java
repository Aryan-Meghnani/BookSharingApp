import java.util.Objects;

public class BookAndOwner {
    Books books;
    Owner owner;
    int copies;

    public BookAndOwner(Books books, Owner owner, int copies) {
        this.books = books;
        this.owner = owner;
        this.copies = copies;
    }

    public Owner getOwner() {
        return owner;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Books getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return books +" " + owner +
                ", Copies=" + copies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAndOwner that = (BookAndOwner) o;
        return  Objects.equals(books, that.books) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, owner, copies);
    }
}
