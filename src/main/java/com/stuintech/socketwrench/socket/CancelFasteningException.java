package com.stuintech.socketwrench.socket;

public class CancelFasteningException extends Exception {
    public CancelFasteningException() {
        super("No action should be taken");
    }
}
