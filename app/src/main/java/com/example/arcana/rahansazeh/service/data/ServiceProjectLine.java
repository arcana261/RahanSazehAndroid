package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceProjectLine {
    private String title;
    private String head;
    private String tail;

    public ServiceProjectLine() {
    }

    public ServiceProjectLine(String title, String head, String tail) {
        this.title = title;
        this.head = head;
        this.tail = tail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
