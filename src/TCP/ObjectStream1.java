package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/*
[Mã câu hỏi (qCode): WzUPjzt1].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
a.	Đối tượng trao đổi là thể hiện của lớp Customer918 được mô tả như sau
      •	Tên đầy đủ của lớp: TCP.Customer918
      •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
      •	Trường dữ liệu: private static final long serialVersionUID = 918;
b.	Tương tác với server theo kịch bản dưới đây:
	1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
	2) Nhận một đối tượng là thể hiện của lớp Customer918 từ server với các thông tin đã được thiết lập
	3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
	4) Đóng socket và kết thúc chương trình.
 */

class Customer implements Serializable {
    private static final long serialVersionUID = 20170711;

    private int id;
    private String code;
    private String name;
    private String dayOfBirth;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

public class ObjectStream1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "WzUPjzt1";

    private static String getKey() {
        return STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            out.writeObject(getKey());
            out.flush();

            Customer customer = (Customer) in.readObject();

            System.out.println(customer.getName());

            process(customer);

            System.out.println(customer.getName());
            System.out.println(customer.getUserName());
            System.out.println(customer.getDayOfBirth());

            out.writeObject(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(Customer customer) {
        String[] words = customer.getName().split("\\s+");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length-1; i++) {
            sb.append(Character.toLowerCase(words[i].charAt(0)));
        }
        sb.append(words[words.length-1].toLowerCase());
        customer.setUserName(sb.toString());

        sb = new StringBuilder();
        sb.append(words[words.length - 1].toUpperCase()).append(",");
        for (int i = 0; i < words.length - 1; i++) {
            char[] a = words[i].toLowerCase().toCharArray();
            a[0] = Character.toUpperCase(a[0]);
            sb.append(' ').append(a);
        }
        customer.setName(sb.toString());

        String[] dates = customer.getDayOfBirth().split("-");
        customer.setDayOfBirth(String.format("%s/%s/%s", dates[1], dates[0], dates[2]));
    }
}