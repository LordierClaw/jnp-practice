package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class String1 {
    /*
    [Mã câu hỏi (qCode): NHl77j0f].  [Loại bỏ ký tự đặc biệt, số, trùng và giữ nguyên thứ tự xuất hiện]
Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;06D6800D"
b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strInput"
•	requestId là chuỗi ngẫu nhiên duy nhất
•	strInput là chuỗi thông điệp cần xử lý
c.	Thực hiện loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của chúng. Gửi thông điệp lên server theo định dạng "requestId;strOutput", trong đó strOutput là chuỗi đã được xử lý ở trên
d.	Đóng socket và kết thúc chương trình.
     */

    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2208;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "NHl77j0f";

    private static String getKey() {
        return ";" + STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(HOST);
            DatagramPacket packet = new DatagramPacket(getKey().getBytes(), getKey().getBytes().length, host, PORT);
            socket.send(packet);

            byte[] buffer = new byte[10240];
            packet = new DatagramPacket(buffer, 0, buffer.length);
            socket.receive(packet);

            String req = new String(buffer).trim();

            System.out.println(req);

            String requestId = req.substring(0, req.indexOf(";"));
            String strInput = req.substring(req.indexOf(";")+1);

            char[] a = strInput.toCharArray();
            StringBuilder sb = new StringBuilder();
            Set<Character> set = new HashSet<>();
            for (char c : a) {
                if (Character.isAlphabetic(c)) {
                    if (!set.contains(c)) {
                        sb.append(c);
                    }
                }
                set.add(c);
            }

            String resp = requestId + ";" + sb.toString();
            System.out.println(resp);
            packet = new DatagramPacket(resp.getBytes(), resp.getBytes().length, host, PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
