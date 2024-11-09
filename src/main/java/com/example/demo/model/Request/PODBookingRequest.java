package com.example.demo.model.Request;

public class PODBookingRequest {
    private String start;
    private String end;
    private Long bookId;
    private Long podId;

    // Getters and setters
    public Long getPodId() {
        return podId;
    }
    public void setPodId(Long podId) {
        this.podId = podId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}