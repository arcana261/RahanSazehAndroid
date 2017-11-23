package com.example.arcana.rahansazeh.service;

import com.example.arcana.rahansazeh.service.data.ServiceProject;

import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public interface ProjectListService {
    List<ServiceProject> getProjects(String userName) throws Exception;
}
