package RMI;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
[Mã câu hỏi (qCode): zFga0LNM].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
public interface DataService extends Remote {
public Object requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
Trong đó:
•	Interface DataService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một số nguyên dương amount từ server, đại diện cho số tiền cần đạt được.
b. Sử dụng thuật toán xếp đồng xu với các mệnh giá cố định [1, 2, 5, 10] để xác định số lượng đồng xu tối thiểu cần thiết để đạt được số tiền amount. Nếu không thể đạt được số tiền đó với các mệnh giá hiện có, trả về -1.
Ví dụ: Với amount = 18 và mệnh giá đồng xu cố định [1, 2, 5, 10], kết quả là 4 (18 = 10 + 5 + 2 + 1). Chuỗi cần gửi lên server là: 4; 10,5,2,1
c. Triệu gọi phương thức submitData để gửi chuỗi chứa kết quả số lượng đồng xu tối thiểu và giá trị các đồng xu tương ứng  trở lại server.
d. Kết thúc chương trình client.
 */

interface DataService extends Remote {
    Object requestData(String studentCode, String qCode) throws RemoteException;
    void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}

public class Data1 {
    private static final String HOST = "rmi://203.162.10.109/" + "RMIDataService";

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "zFga0LNM";

    public static void main(String[] args) throws Exception {
        DataService dataService = (DataService) Naming.lookup(HOST);
        Integer amount = (Integer) dataService.requestData(STUDENT_CODE, Q_CODE);
        int n10, n5, n2, n1;
        n10 = amount/10;
        n5 = amount%10/5;
        n2 = amount%10%5/2;
        n1 = amount%10%5%2;
        int sum = n10 + n5 + n2 + n1;
        String res = "-1";
        if (sum > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(sum).append("; ");
            for (int i = 0; i < n10; i++) {
                sb.append(10).append(',');
            }
            for (int i = 0; i < n5; i++) {
                sb.append(5).append(',');
            }
            for (int i = 0; i < n2; i++) {
                sb.append(2).append(',');
            }
            for (int i = 0; i < n1; i++) {
                sb.append(1).append(',');
            }
            sb.deleteCharAt(sb.length()-1);
            res = sb.toString();
        }
        System.out.println(res);
        dataService.submitData(STUDENT_CODE, Q_CODE, res);
    }
}
