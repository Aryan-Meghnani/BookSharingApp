import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BookRequest {
    BookAndOwner bookAndOwner;
    Owner owner;
    LocalDateTime date;

    public BookRequest(BookAndOwner bookAndOwner, Owner owner, LocalDateTime date) {
        this.bookAndOwner = bookAndOwner;
        this.date = date;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return bookAndOwner +
                ", date=" + date +
                ", Request Made By " + owner;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRequest that = (BookRequest) o;
        return Objects.equals(bookAndOwner, that.bookAndOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookAndOwner, owner, date);
    }
}
