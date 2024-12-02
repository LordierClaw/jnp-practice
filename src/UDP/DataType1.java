package UDP;

/*
[Mã câu hỏi (qCode): uPwxt4IJ].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:

a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".

b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n, n", với:
--- requestId là chuỗi ngẫu nhiên duy nhất.
--- n là một số nguyên ngẫu nhiên ≤ 100.

c. Tính và gửi về server danh sách n số nguyên tố đầu tiên theo định dạng "requestId;p1,p2,...,pk", trong đó p1,p2,...,pk là các số nguyên tố.

d. Đóng socket và kết thúc chương trình.
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataType1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2207;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "uPwxt4IJ";

    private static String getKey() {
        return ";" + STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(HOST);

            DatagramPacket packet = new DatagramPacket(getKey().getBytes(), getKey().getBytes().length, host, PORT);

            socket.send(packet);

            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            String req = new String(buffer).trim();
            String requestId = req.substring(0, req.indexOf(';'));
            int n = Integer.parseInt(req.substring(req.indexOf(';')+1));
            StringBuilder sb = new StringBuilder();
            sb.append(2);
            n--;
            int x = 3;
            while (n > 0) {
                if (isPrime(x)) {
                    sb.append(',').append(x);
                    n--;
                }
                x++;
            }

            String resp = requestId + ";" + sb;

            System.out.println(resp);

            packet = new DatagramPacket(resp.getBytes(), resp.getBytes().length, host, PORT);
            socket.send(packet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i*i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
