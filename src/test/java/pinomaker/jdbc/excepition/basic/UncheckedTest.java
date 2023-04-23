package pinomaker.jdbc.excepition.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    void unChecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unChecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                // 예외 처리
                log.info("예외처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 안 잡음
         */
        public void callThrow() {
            repository.call();
        }
    }

    /**
     * UnChecked 예외는
     * 예외를 잡거나 던지지 않는다.
     * 예외를 잡지 않으면 자동으로 밖으로 던짐
     */
    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }
}
