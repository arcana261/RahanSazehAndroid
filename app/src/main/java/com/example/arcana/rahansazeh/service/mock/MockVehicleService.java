package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.data.ServiceVehicle;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleChange;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arcana on 11/10/17.
 */

public class MockVehicleService extends BaseMockService implements VehicleService {
    private List<ServiceVehicle> getAllVehicles(String userName, String projectId) {
        ArrayList<ServiceVehicle> result = new ArrayList<>();

        result.add(new ServiceVehicle("1",
                23, 'ت', 764,
                10, "1", projectId));

        result.add(new ServiceVehicle("2",
                46, 'ت', 234,
                10, "2", projectId));

        result.add(new ServiceVehicle("3",
                80, 'ت', 912,
                10, "1", projectId));

        return result;
    }

    private List<ServiceVehicleChange> getAllVehicleChanges(String userName, String projectId) {
        return new ArrayList<>(Queriable.create(getAllVehicles(userName, projectId))
                .map(new Selector<ServiceVehicle, ServiceVehicleChange>() {
                    @Override
                    public ServiceVehicleChange select(ServiceVehicle vehicle) {
                        return new ServiceVehicleChange(Long.parseLong(vehicle.getId()),
                                "add", vehicle.getId(), vehicle);
                    }
                }).execute());
    }

    private List<ServiceVehicleChange> getAllVehicleChanges(String userName, String projectId,
                                                            final Long epoch) {
        return new ArrayList<>(Queriable.create(getAllVehicleChanges(userName, projectId))
                .filter(new Predicate<ServiceVehicleChange>() {
                    @Override
                    public boolean predict(ServiceVehicleChange serviceVehicleChange) {
                        return serviceVehicleChange.getEpoch() > epoch;
                    }
                }).execute());
    }

    @Override
    public List<ServiceVehicle> getVehicles(String userName, String projectId,
                                            Long page,
                                            Long pageSize) throws Exception {
        sleep();

        List<ServiceVehicle> result = getAllVehicles(userName, projectId);

        return new ArrayList<>(Queriable.create(result)
                .take((int) (page * pageSize), pageSize.intValue())
                .execute());
    }

    @Override
    public Long getVehicleChangeCount(String userName, String projectId, Long epoch) throws Exception {
        sleep();

        return (long) getAllVehicleChanges(userName, projectId, epoch).size();
    }

    @Override
    public List<ServiceVehicleChange> getVehicleChanges(String userName, String projectId, final Long epoch, Long page, Long pageSize) throws Exception {
        sleep();

        return new ArrayList<>(Queriable.create(getAllVehicleChanges(userName, projectId, epoch))
                .take((int) (page * pageSize), pageSize.intValue())
                .execute());
    }

    @Override
    public Long getVehicleCount(String userName, String projectId) throws Exception {
        sleep();

        return (long) getAllVehicles(userName, projectId).size();
    }
}
