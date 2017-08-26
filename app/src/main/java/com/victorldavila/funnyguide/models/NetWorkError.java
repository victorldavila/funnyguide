package com.victorldavila.funnyguide.models;

public class NetWorkError {
  private String status_message;
  private int status_code;
  private boolean success;

  public String getStatus_message() {
        return status_message;
    }

  public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

  public int getStatus_code() {
        return status_code;
    }

  public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

  public boolean isSuccess() {
        return success;
    }

  public void setSuccess(boolean success) {
        this.success = success;
    }
}
