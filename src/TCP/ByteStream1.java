package TCP;

/*
[Mã câu hỏi (qCode): 0YbH9QOQ].
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
    c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server. Ví dụ: 5,10,20,25 có độ dài 4.
    d. Đóng kết nối và kết thúc chương trình.
 */

/*
Note: đề sai, dãy con không cần phải liên tiếp
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

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

            byte[] buffer = new byte[10240];
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
                } catch (NumberFormatException ignored) {
                }
            }

            ArrayList<LinkedList<Integer>> list = new ArrayList<>();

            int maxI = 0;
            for (int i = 0; i < arr.size(); i++) {
                list.add(new LinkedList<>());
                int maxJ = 0;
                for (int j = 0; j < i; j++) {
                    if (arr.get(j) < arr.get(i)) {
                        if (list.get(j).size() > list.get(maxJ).size()) {
                            maxJ = j;
                        }
                    }
                }
                list.get(i).addAll(list.get(maxJ));
                list.get(i).add(arr.get(i));

                if (list.get(i).size() > list.get(maxI).size()) {
                    maxI = i;
                }
            }

            StringBuilder sb = new StringBuilder();
            for(Integer x: list.get(maxI)) {
                sb.append(x).append(',');
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(';').append(list.get(maxI).size());
            System.out.println(sb);

            out.write(sb.toString().getBytes(), 0, sb.toString().getBytes().length);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
