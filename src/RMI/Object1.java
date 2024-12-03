package RMI;

/*
[Mã câu hỏi (qCode): elV9DMuS].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý mã hóa dữ liệu tác phẩm trong hệ thống quản lý thư viện. Chương trình sẽ ngẫu nhiên tạo ra đối tượng BookX với các giá trị ban đầu và cung cấp cho RMI client như sau:
    Giao diện từ xa:
public interface ObjectService extends Remote {
    public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
    public void submitObject(String studentCode, String qCode, Serializable object) throws RemoteException;
}
Lớp BookX gồm các thuộc tính: id String, title String, author String, yearPublished int, genre String, code String.
Trường dữ liệu: private static final long serialVersionUID = 20241124L;
02 hàm khởi dựng:
    public BookX()
    public BookX(int id, String title, String author, int yearPublished, String genre)
Trong đó:
    Interface ObjectService và lớp BookX được viết trong package RMI.
    Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer: RMIObjectService.

Yêu cầu:  Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với đối tượng sách được nhận từ RMI Server:
a. Triệu gọi phương thức requestObject để nhận đối tượng BookX ngẫu nhiên từ server.
b. Tạo mã code cho sách dựa trên các quy tắc mã hóa sau:
•	Lấy chữ cái đầu tiên và cuối cùng trong tên tác giả (author).
•	    Lấy hai chữ số cuối cùng của yearPublished.
•	    Số lượng chữ cái trong genre của sách.
•	    Độ dài của title chia lấy dư cho 10 (ví dụ: với tiêu đề dài 32 ký tự, giá trị này sẽ là 2).
    Kết hợp tất cả các thành phần trên để tạo ra mã code theo định dạng: [Chữ cái đầu và cuối tên tác giả][Hai chữ số cuối của năm xuất bản][Số chữ cái của genre][Độ dài title modulo 10].	Ví dụ, nếu tác giả là "Mark Twain", năm xuất bản là 1884, thể loại là "Fiction" với 7 ký tự, và tiêu đề có 24 ký tự, mã code sẽ là: "MT8474".
c. Cập nhật giá trị code trong đối tượng BookX.
d. Triệu gọi phương thức submitObject để gửi đối tượng BookX đã được xử lý trở lại server.
e. Kết thúc chương trình client.
 */

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

interface ObjectService extends Remote {
    Serializable requestObject(String studentCode, String qCode) throws RemoteException;

    void submitObject(String studentCode, String qCode, Serializable object) throws RemoteException;
}

class BookX implements Serializable {
    private static final long serialVersionUID = 20241124L;
    private String id;
    private String title;
    private String author;
    private int yearPublished;
    private String genre;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

public class Object1 {
    private static final String HOST = "rmi://203.162.10.109/" + "RMIObjectService";

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "elV9DMuS";

    public static void main(String[] args) throws Exception {
        ObjectService service = (ObjectService) Naming.lookup(HOST);
        BookX bookX = (BookX) service.requestObject(STUDENT_CODE, Q_CODE);

        StringBuilder sb = new StringBuilder();

        sb.append(bookX.getAuthor().charAt(0))
                .append(bookX.getAuthor().charAt(bookX.getAuthor().length()-1))
                .append(bookX.getYearPublished() % 100)
                .append(bookX.getGenre().length())
                .append(bookX.getTitle().length() % 10);

        bookX.setCode(sb.toString());
        service.submitObject(STUDENT_CODE, Q_CODE, bookX);
    }
}
