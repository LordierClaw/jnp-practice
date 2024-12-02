package TCP;

/*
[Mã câu hỏi (qCode): 0YbH9QOQ].
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
    c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server. Ví dụ: 5,10,20,25 có độ dài 4.
    d. Đóng kết nối và kết thúc chương trình.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ByteStream1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2206;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "0YbH9QOQ";

    private static String getKey() {
        return STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();) {

            byte[] buffer = new byte[1024];
            out.write(getKey().getBytes(), 0, getKey().getBytes().length);
            out.flush();

            int len = in.read(buffer);

            String req = new String(buffer, 0, len);
            System.out.println(req);
            String[] a = req.split(",");
            ArrayList<Integer> arr = new ArrayList<>();
            for (String x: a) {
                try {
                    arr.add(Integer.parseInt(x));
                } catch (NumberFormatException ex) {
                    break;
                }
            }
            System.out.println(Arrays.toString(arr.toArray()));

            int lmax, gmax;
            lmax = gmax = 1;

            for (int i = 1; i < arr.size(); i++) {
                if (arr.get(i) > arr.get(i-1)) {
                    lmax ++;
                } else {
                    if (lmax > gmax) {
                        gmax = lmax;
                    }
                    lmax = 1;
                }
            }

            out.write(gmax);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
