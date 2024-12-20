package TCP;

/*
[Mã câu hỏi (qCode): JEp3fQKG].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự (BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;X1107ABC".
b. Nhận từ server một chuỗi ngẫu nhiên chứa nhiều từ, các từ phân tách bởi khoảng trắng.
c. Thực hiện các bước xử lý:
    Bước 1: Tách chuỗi thành các từ dựa trên khoảng trắng.
    Bước 2: Sắp xếp các từ theo thứ tự từ điển (có phân biệt chữ cái hoa thường).
d. Gửi lại chuỗi đã sắp xếp theo thứ tự từ điển lên server.

e. Đóng kết nối và kết thúc chương trình.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class CharacterStream1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2208;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "JEp3fQKG";

    private static String getKey() {
        return STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            out.write(getKey(), 0, getKey().length());
            out.write('\n');
            out.flush();

            char[] buffer = new char[1024];
            in.read(buffer, 0, buffer.length);
            String req = new String(buffer);

            System.out.println(req);

            String[] words = req.split("\\s+");
            Arrays.sort(words);
            StringBuilder sb = new StringBuilder();
            for (String w : words) {
                sb.append(w).append(' ');
            }

            String res = sb.toString().trim();

            System.out.println(res);

            out.write(res, 0 , res.length());
            out.write('\n');
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
