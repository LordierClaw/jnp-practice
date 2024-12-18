package WS;

/*
[Mã câu hỏi (qCode): MUrpluIN].  Một dịch vụ web được định nghĩa và mô tả trong tệp ObjectService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/ObjectService?wsdl để xử lý các bài toán với đối tượng.
Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với ObjectService thực hiện các công việc sau:
a. Triệu gọi phương thức requestListStudent với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách đối tượng Student từ server. Mỗi đối tượng Student có các thuộc tính:
•	name: kiểu String, đại diện cho tên của sinh viên.
•	score: kiểu float, đại diện cho điểm trung bình của sinh viên.
b. Thực hiện lọc và giữ lại các sinh viên có điểm thuộc nhóm A, D. Biết rằng điểm các mức sau:  A: điểm từ 8.0 trở lên; B: điểm từ 6.5 đến dưới 8.0; C: điểm từ 5.0 đến dưới 6.5; D: điểm dưới 5.0
c. Triệu gọi phương thức submitListStudent(String studentCode, String qCode, List<Student> students) để gửi danh sách sinh viên thuộc nhóm A và D.
d. Kết thúc chương trình client.
 */

import vn.medianews.ObjectService;
import vn.medianews.ObjectService_Service;

import java.util.List;
import java.util.stream.Collectors;

public class Object1 {
    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "MUrpluIN";

    public static void main(String[] args) {
        ObjectService_Service service = new ObjectService_Service();
        ObjectService port = service.getObjectServicePort();

        List<vn.medianews.Student> students = port.requestListStudent(STUDENT_CODE, Q_CODE);

        port.submitListStudent(STUDENT_CODE, Q_CODE, students.stream()
                .filter(s -> s.getScore() >= 8.0 || s.getScore() <= 5.0)
                .collect(Collectors.toList()));
    }
}
