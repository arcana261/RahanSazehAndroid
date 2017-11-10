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
    public List<ServiceProject> getProjects() {
        sleep();

        ServiceProjectLine line = new ServiceProjectLine(
                "293", "میدان فاطمی", "پایانه سازمان آب");
        ServiceProject project = new ServiceProject(
                "ساماندهی سامانه تاکسی‌رانی تهران" + "\n" +
                        "ساماندهی میدان کتاب - منطقه ۲ تهران" + "\n" +
                        "طرح جامع حمل و نقل ترافیک سیرجان");
        project.addLine(line);

        ArrayList<ServiceProject> result = new ArrayList<>();
        result.add(project);

        return result;
    }
}
