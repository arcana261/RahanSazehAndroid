package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceProjectLine {
    private long id;
    private String title;
    private String head;
    private String tail;

    public ServiceProjectLine(long id, String title, String head, String tail) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
