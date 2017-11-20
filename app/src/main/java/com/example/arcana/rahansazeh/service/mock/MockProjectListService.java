package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.ProjectListService;
import com.example.arcana.rahansazeh.service.data.ServiceProject;
import com.example.arcana.rahansazeh.service.data.ServiceProjectLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public class MockProjectListService extends BaseMockService implements ProjectListService {
    @Override
    public List<ServiceProject> getProjects(String userName) {
        sleep();

        ServiceProjectLine line = new ServiceProjectLine(
                1, "293", "میدان فاطمی", "پایانه سازمان آب");
        ServiceProject project = new ServiceProject(1,
                "ساماندهی سامانه تاکسی‌رانی تهران");
        project.addLine(line);

        ArrayList<ServiceProject> result = new ArrayList<>();
        result.add(project);

        return result;
    }
}
