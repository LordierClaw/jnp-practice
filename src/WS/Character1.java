package WS;

/*
[Mã câu hỏi (qCode): SkkxqKM5].  Một dịch vụ web được định nghĩa và mô tả trong tệp CharacterService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/CharacterService?wsdl để xử lý các bài toán về chuỗi và ký tự.
Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với CharacterService thực hiện các công việc sau:
a. Triệu gọi phương thức requestString với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một chuỗi (String) từ server. Chuỗi này có thể chứa các từ và khoảng trắng.
b. Xử lý chuỗi nhận được để tìm từ có độ dài lớn nhất và từ có độ dài nhỏ nhất trong chuỗi. Nếu có nhiều từ có cùng độ dài lớn nhất hoặc nhỏ nhất, chọn từ xuất hiện đầu tiên trong chuỗi.
c. Tạo một chuỗi mới theo định dạng: "[từ lớn nhất]; [từ nhỏ nhất]".
d. Triệu gọi phương thức submitCharacterString(String studentCode, String qCode, String data) để gửi chuỗi kết quả đã xử lý trở lại server.
Ví dụ: Nếu chuỗi nhận được từ phương thức requestCharacter là "this is a sample test", từ có độ dài lớn nhất là "sample" và từ có độ dài nhỏ nhất là "a". Chuỗi kết quả sẽ là "sample; a", và được gửi lại server qua phương thức submitCharacter.
e. Kết thúc chương trình client.
 */

import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

public class Character1 {
    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "SkkxqKM5";

    public static void main(String[] args) {
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();

        String req = port.requestString(STUDENT_CODE, Q_CODE);

        System.out.println(req);

        String[] arr = req.split("\\s+");

        int lmax = 0, lmin = req.length();
        for (String s: arr) {
            lmax = Math.max(lmax, s.length());
            lmin = Math.min(lmin, s.length());
        }

        String smax = "", smin = "";
        for (String s: arr) {
            if (s.length() == lmax) {
                smax = s;
                break;
            }
        }
        for (String s: arr) {
            if (s.length() == lmin) {
                smin = s;
                break;
            }
        }
        String res = String.format("%s; %s", smax, smin);
        port.submitCharacterString(STUDENT_CODE, Q_CODE, res);
    }
}
