package egovframework.common.excepHndlr;

import org.apache.log4j.Logger;
import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class EgovComExcepHndlr implements ExceptionHandler {

    /**
     * 발생된 Exception을 처리한다.
     */
    public void occur(Exception ex, String packageName) {
      Logger.getRootLogger().debug("[HANDLER][PACKAGE]:::"+packageName);
      Logger.getRootLogger().debug("[HANDLER][Exception]:::"+ex);
    }
}
