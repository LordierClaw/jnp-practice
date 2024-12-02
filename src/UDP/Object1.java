package UDP;

/*
[Mã câu hỏi (qCode): pc5llnuJ].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
•	Tên đầy đủ lớp: UDP.Student
•	Các thuộc tính: id String,code String, name String, email String
•	02 Hàm khởi tạo:
o	public Student(String id, String code, String name, String email)
o	public Student(String code)
•	Trường dữ liệu: private static final long serialVersionUID = 20171107
Thực hiện:
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
b.	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
c.	Yêu cầu:
-	Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
-	Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
-	Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
d.	Đóng socket và kết thúc chương trình.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Student implements Serializable {
    private static final long serialVersionUID = 20171107;

    private String id;
    private String code;
    private String name;
    private String email;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

public class Object1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "pc5llnuJ";

    private static String getKey() {
        return ";" + STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();) {
            InetAddress host = InetAddress.getByName(HOST);
            DatagramPacket packet = new DatagramPacket(getKey().getBytes(), getKey().getBytes().length, host, PORT);
            socket.send(packet);

            byte[] buffer = new byte[10240];
            packet = new DatagramPacket(buffer, 0 , buffer.length);
            socket.receive(packet);

            byte[] requestIdBuffer = new byte[8];
            System.arraycopy(buffer, 0, requestIdBuffer, 0, 8);

            byte[] objectBuffer = new byte[packet.getLength()-8];
            System.arraycopy(buffer, 8, objectBuffer, 0, objectBuffer.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(objectBuffer));
                 ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
                UDP.Student student = (Student) ois.readObject();
                String[] words = student.getName().split("\\s+");

                StringBuilder sb = new StringBuilder();
                for (String x: words) {
                    char[] a = x.toLowerCase().toCharArray();
                    a[0] = Character.toUpperCase(a[0]);
                    sb.append(a).append(' ');
                }
                student.setName(sb.toString().trim());

                sb = new StringBuilder();
                sb.append(words[words.length-1].toLowerCase());
                for (int i = 0; i < words.length-1; i++) {
                    sb.append(words[i].toLowerCase().charAt(0));
                }
                sb.append("@ptit.edu.vn");
                student.setEmail(sb.toString());

                oos.writeObject(student);
            }
            buffer = new byte[10240];
            System.arraycopy(requestIdBuffer, 0, buffer, 0, 8);
            System.arraycopy(outputStream.toByteArray(), 0, buffer, 8, outputStream.size());

            packet = new DatagramPacket(buffer, buffer.length, host, PORT);
            socket.send(packet);

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
