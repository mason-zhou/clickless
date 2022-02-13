package com.clickless.workflow.exception;

/**
 * 工作流引擎自定义业务异常
 */
public class WorkflowException extends Exception {
    public WorkflowException() {
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }

    public WorkflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
