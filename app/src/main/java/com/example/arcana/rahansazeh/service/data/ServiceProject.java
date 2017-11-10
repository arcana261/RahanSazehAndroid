package com.example.arcana.rahansazeh.service.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceProject {
    private String title;
    private List<ServiceProjectLine> lines;

    public ServiceProject() {
    }

    public ServiceProject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ServiceProjectLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }

        return lines;
    }

    public void addLine(ServiceProjectLine line) {
        getLines().add(line);
    }

    public void removeLine(ServiceProjectLine line) {
        getLines().remove(line);
    }
}
