package compiler.component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler {
    public final ExceptionLauncher _throw;


    public ExceptionHandler() {
        _throw = new ExceptionLauncher();
    }


    public void _throw(String msg) {
        _throw(_throw.tokenExpectedException(msg));
    }

    @SneakyThrows
    public void _throw(Exception e) {
        throw e;
    }

    public void handle(String msg) {
        handle(_throw.tokenExpectedException(msg));
    }

    public void handle(Exception e) {
        log.error(e.getMessage());
        System.exit(-1);    //Nonzero status code indicates abnormal termination
    }

}
