package RMI;

/*
[Mã câu hỏi (qCode): dZ7DDBI2].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
Giao diện từ xa:
public interface ByteService extends Remote {
public byte[] requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}
Trong đó:
•	Interface ByteService được viết trong package RMI.
Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.
b. Tìm phần tử xuất hiện nhiều nhất trong mảng byte[]. Nếu có nhiều phần tử có cùng số lần xuất hiện cao nhất, chỉ cần trả về phần tử đầu tiên xuất hiện trong các phần tử đó.
Ví dụ: Nếu mảng dữ liệu nhận được là [1, 2, 3, 2, 1, 2], phần tử xuất hiện nhiều nhất là 2, với tần suất xuất hiện 3 lần.
c. Triệu gọi phương thức submitData để gửi mảng byte chứa phần tử phổ biến nhất, cùng với tần suất xuất hiện của nó trở lại server.
d. Kết thúc chương trình client
 */

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;

interface ByteService extends Remote {
    byte[] requestData(String studentCode, String qCode) throws RemoteException;
    void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}

public class Byte1 {
    private static final String HOST = "rmi://203.162.10.109/" + "RMIByteService";

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "dZ7DDBI2";

    public static void main(String[] args) throws Exception {
        ByteService service = (ByteService) Naming.lookup(HOST);
        byte[] buffer = service.requestData(STUDENT_CODE, Q_CODE);
        int[] a = new int[260];
        int gmax = 0;
        for (byte x: buffer) {
            gmax = Math.max(gmax, ++a[x]);
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] == gmax) {
                byte[] res = {(byte) i, (byte) a[i]};
                System.out.println(Arrays.toString(res));
                service.submitData(STUDENT_CODE, Q_CODE, res);
                return;
            }
        }
    }
}
