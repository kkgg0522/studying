package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {
    @Test
    void unchecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(controller::request)
                .isInstanceOf(RuntimeSQLException.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try{
            controller.request();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    static class Controller {
        Service service = new Service();

        public void request(){
            service.logic();
        }
    }

    static class Service {
        NetworkClient networkClient = new NetworkClient();
        Repository repository = new Repository();
        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RunTimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call(){
            try{
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        public void runSQL () throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RunTimeConnectException extends RuntimeException {
        public RunTimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException{
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
