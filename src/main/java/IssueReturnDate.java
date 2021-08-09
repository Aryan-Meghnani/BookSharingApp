import java.time.LocalDate;
import java.util.Date;

public class IssueReturnDate {
    private String status;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate requestDate;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return  "Status='" + status + '\'' +
                ", IssueDate=" + issueDate +
                ", ReturnDate=" + returnDate +
                ", RequestDate=" + requestDate;
    }
}
