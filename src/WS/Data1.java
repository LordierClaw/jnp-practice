package WS;

/*
[Mã câu hỏi (qCode): 9yjpzwuv].  Một dịch vụ web được định nghĩa và mô tả trong tệp DataService?wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/DataService?wsdl để xử lý các bài toán với dữ liệu nguyên thủy.
Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với DataService thực hiện các công việc sau:
a. Triệu gọi phương thức getData với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một mảng số nguyên (int[]) từ server.
    Ví dụ: 7602,9136,1090,34319,7830,6179,10584,20166,28199,30250,32179,22544,3222,10320,30590,19279
b. Thực hiện tìm số lớn nhất có thể tạo dược từ a,b,c,d...
c. Triệu gọi phương thức submitDataString(String studentCode, String qCode, String data) để gửi kết quả tổng đã tính được trở lại server.
    Ví dụ: 91367830760261793431932223217930590302502819922544201661927910901058410320
d. Kết thúc chương trình client.
 */


import vn.medianews.DataService;
import vn.medianews.DataService_Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Data1 {
    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "9yjpzwuv";

    public static void main(String[] args) {
        DataService_Service service = new DataService_Service();
        DataService port = service.getDataServicePort();

        List<Integer> arr = port.getData(STUDENT_CODE, Q_CODE);
        System.out.println(Arrays.toString(arr.toArray()));

        Collections.sort(arr, (o1, o2) -> String.valueOf(o2).compareTo(String.valueOf(o1)));

        StringBuilder sb = new StringBuilder();
        for (Integer num : arr) {
            sb.append(num);
        }
        System.out.println(sb);
        port.submitDataString(STUDENT_CODE, Q_CODE, sb.toString());
    }
}
