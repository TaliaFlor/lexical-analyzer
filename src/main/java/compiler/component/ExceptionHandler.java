package compiler.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler {
    public final ExceptionLauncher _throw;


    public ExceptionHandler() {
        _throw = new ExceptionLauncher();
    }


    public void handle(String msg) {
        handle(_throw.tokenExpectedException(msg));
    }

    public void handle(Exception e) {
        log.error(e.getMessage());
        System.exit(-1);    //Nonzero status code indicates abnormal termination
    }

}
