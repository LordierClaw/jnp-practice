package RMI;

import jdk.nashorn.internal.parser.JSONParser;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
[Mã câu hỏi (qCode): 2RfgpBHj].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
Giao diện từ xa:
public interface CharacterService extends Remote {
public String requestCharacter(String studentCode, String qCode) throws RemoteException;
public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}
Trong đó:
•	Interface CharacterService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
a. Triệu gọi phương thức requestCharacter để nhận chuỗi JSON ngẫu nhiên từ server với định dạng: "Chuỗi JSON đầu vào".
b. Phân tích cú pháp chuỗi JSON nhận được và trích xuất các cặp key: value dựa trên vị trí của chúng:
•	Các cặp key: value ở vị trí chẵn sẽ được đưa vào chuỗi đầu tiên.
•	Các cặp key: value ở vị trí lẻ sẽ được đưa vào chuỗi thứ hai.
•	Hai chuỗi kết quả sẽ được nối với nhau và phân tách bởi dấu ;
Ví dụ: Chuỗi JSON ban đầu {"name": "Alice", "age": 25, "city": "Wonderland", "country": "Fictionland"} -> Kết quả trích xuất: "name: Alice, city: Wonderland; age: 25, country: Fictionland".
c. Triệu gọi phương thức submitCharacter để gửi chuỗi kết quả trích xuất đã được định dạng trở lại server.
d. Kết thúc chương trình client.
 */

interface CharacterService extends Remote {
    String requestCharacter(String studentCode, String qCode) throws RemoteException;
    void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}

public class Character1 {
    private static final String HOST = "rmi://203.162.10.109/" + "RMICharacterService";

    private static final String STUDENT_CODE = "B21DCCN045";
    private static final String Q_CODE = "2RfgpBHj";

    public static void main(String[] args) throws Exception {
        CharacterService service = (CharacterService) Naming.lookup(HOST);

        String json = service.requestCharacter(STUDENT_CODE, Q_CODE);

    }
}
