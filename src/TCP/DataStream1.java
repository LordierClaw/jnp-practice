package TCP;

/*
[Mã câu hỏi (qCode): Rt0H41A5].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng chương trình client tương tác với server bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B10DCCN000;0D135D6A".
b. Nhận từ server một số nguyên n, là số lần tung xúc xắc. Ví dụ: Nếu bạn nhận được n = 21 từ máy chủ, có nghĩa bạn sẽ nhận giá trị tung xúc xắc 21 lần.
b. Nhận từ server các giá trị sau mỗi lần tung xúc xắc. Ví dụ: Server gửi lần lượt 21 giá trị là 1,6,4,4,4,3,2,6,3,4,5,4,5,2,4,5,4,6,1,5,5
c. Tính xác suất xuất hiện của các giá trị [1,2,3,4,5,6] khi tung xúc sắc và gửi lần lượt xác suất này (dưới dạng float) lên server theo đúng thứ tự. Ví dụ gửi lên server lần lượt 6 giá trị là 0.0952381, 0.0952381, 0.0952381, 0.33333334, 0.232209524, 0.14285715
d. Đóng kết nối và kết thúc chương trình.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class DataStream1 {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2207;

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "Rt0H41A5";

    private static String getKey() {
        return STUDENT_CODE + ";" + Q_CODE;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream());) {

            out.writeUTF(getKey());
            out.flush();

            int n = in.readInt();

            int[] cnt = new int[7];
            for (int i = 1; i<= 6; i++) {
                cnt[i] = 0;
            }

            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                cnt[x]++;
            }

            for (int i = 1; i <= 6; i++) {
                out.writeFloat((float) (1.0*cnt[i]/n));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
