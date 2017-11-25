package com.example.arcana.rahansazeh.service.api;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.service.DataEntryService;
import com.example.arcana.rahansazeh.service.LoginService;
import com.example.arcana.rahansazeh.service.ProjectListService;
import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.VehicleTypeService;
import com.example.arcana.rahansazeh.service.data.ServiceProject;
import com.example.arcana.rahansazeh.service.data.ServiceProjectLine;
import com.example.arcana.rahansazeh.service.data.ServiceVehicle;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleChange;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/23/17.
 */

public class RahanSazehApiClient extends BaseClient
        implements LoginService, VehicleTypeService,
        VehicleService, ProjectListService, DataEntryService {
    public RahanSazehApiClient(String baseUrl) {
        super(baseUrl);
    }

    public RahanSazehApiClient() {
        //super("http://192.168.2.7:8080");
        super("http://178.162.207.98:8080");
    }

    @Override
    public boolean Login(String userName) throws Exception {
        JSONObject params = new JSONObject();
        params.put("userName", userName);

        JSONObject result = postObject("/login", params);

        if (result != null) {
            return result.getBoolean("result");
        } else {
            return false;
        }
    }

    @Override
    public Long getVehicleCount(String userName, String projectId) throws Exception {
        JSONObject response = getObject("/vehicle/" + projectId + "?page=0&pageSize=1");
        return response.getLong("recordsTotal");
    }

    @Override
    public List<ServiceVehicle> getVehicles(String userName, String projectId,
                                            Long page,
                                            Long pageSize) throws Exception {
        ArrayList<ServiceVehicle> result = new ArrayList<>();

        JSONObject rawResponse = getObject("/vehicle/" + projectId +
                "?page=" + page + "&pageSize=" + pageSize);
        JSONArray response = rawResponse.getJSONArray("data");

        for (int i = 0; i < response.length(); i++) {
            JSONObject responseVehicle = response.getJSONObject(i);

            String id = responseVehicle.getString("id");
            int licensePlateLeft = responseVehicle.getInt("licensePlateLeft");
            int licensePlateRight = responseVehicle.getInt("licensePlateRight");
            int licensePlateNationalCode = responseVehicle.getInt("licensePlateNationalCode");
            String licensePlateType = responseVehicle.getString("licensePlateType");
            JSONObject vehicleType = responseVehicle.getJSONObject("vehicleType");
            String vehicleTypeId = vehicleType.getString("id");

            result.add(new ServiceVehicle(id, licensePlateLeft, licensePlateType.charAt(0),
                    licensePlateRight, licensePlateNationalCode,
                    vehicleTypeId, projectId));
        }

        return result;
    }

    @Override
    public Long getVehicleChangeCount(String userName, String projectId, Long epoch) throws Exception {
        JSONObject result = getObject("/vehicleChange/" + projectId +
                "?page=0&pageSize=1&epoch=" + epoch);

        return result.getLong("recordsTotal");
    }

    @Override
    public List<ServiceVehicleChange> getVehicleChanges(String userName, String projectId,
                                                        Long epoch, Long page,
                                                        Long pageSize) throws Exception {
        JSONObject rawResponse = getObject("/vehicleChange/" + projectId +
                "?page=" + page + "&pageSize=" + pageSize + "&epoch=" + epoch);
        JSONArray response = rawResponse.getJSONArray("data");

        ArrayList<ServiceVehicleChange> result = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);

            String eventType = item.getString("eventType");
            String vehicleId = item.getString("vehicleId");
            long itemEpoch = item.getLong("epoch");

            ServiceVehicle vehicle = null;

            if (!item.isNull("vehicle")) {
                JSONObject responseVehicle = item.getJSONObject("vehicle");

                String id = responseVehicle.getString("id");
                int licensePlateLeft = responseVehicle.getInt("licensePlateLeft");
                int licensePlateRight = responseVehicle.getInt("licensePlateRight");
                int licensePlateNationalCode = responseVehicle.getInt("licensePlateNationalCode");
                String licensePlateType = responseVehicle.getString("licensePlateType");
                JSONObject vehicleType = responseVehicle.getJSONObject("vehicleType");
                String vehicleTypeId = vehicleType.getString("id");

                vehicle = new ServiceVehicle(id, licensePlateLeft, licensePlateType.charAt(0),
                        licensePlateRight, licensePlateNationalCode,
                        vehicleTypeId, projectId);
            }

            result.add(new ServiceVehicleChange(itemEpoch, eventType, vehicleId, vehicle));
        }

        return result;
    }

    @Override
    public List<ServiceProject> getProjects(String userName) throws Exception {
        ArrayList<ServiceProject> result = new ArrayList<>();

        JSONArray response = getArray("/project");

        for (int i = 0; i < response.length(); i++) {
            JSONObject responseProject = response.getJSONObject(i);

            String id = responseProject.getString("id");
            String title = responseProject.getString("title");

            ServiceProject project = new ServiceProject(id, title);

            JSONArray responseLines = responseProject.getJSONArray("lines");

            for (int j = 0; j < responseLines.length(); j++) {
                JSONObject responseLine = responseLines.getJSONObject(j);

                String lineId = responseLine.getString("id");
                String lineTitle = responseLine.getString("title");
                String head = responseLine.getString("head");
                String tail = responseLine.getString("tail");

                project.addLine(new ServiceProjectLine(lineId, lineTitle, head, tail));
            }

            result.add(project);
        }

        return result;
    }

    @Override
    public List<ServiceVehicleType> getVehicleTypes(String userName, String projectId) throws Exception {
        ArrayList<ServiceVehicleType> result = new ArrayList<>();

        JSONArray response = getArray("/vehicleType/" + projectId);

        for (int i = 0; i < response.length(); i++) {
            JSONObject responseVehicleType = response.getJSONObject(i);

            String id = responseVehicleType.getString("id");
            String title = responseVehicleType.getString("title");

            result.add(new ServiceVehicleType(id, title));
        }

        return result;
    }

    @Override
    public void addVehicleRecord(OutgoingVehicleRecord record) throws Exception {
        JSONObject params = new JSONObject();

        params.put("userName", record.getUser().getNationalCode());
        params.put("hasLoaded", record.getHasLoaded());
        params.put("hasUnLoaded", record.getHasUnLoaded());
        params.put("projectId", record.getProject().getExternalId());
        params.put("projectLineId", record.getProjectLine().getExternalId());
        params.put("year", record.getYear());
        params.put("month", record.getMonth());
        params.put("day", record.getDay());
        params.put("hasArrivalTime", record.getHasArrivalTime());
        params.put("arrivalTimeHour", record.getArrivalTimeHour());
        params.put("arrivalTimeMinute", record.getArrivalTimeMinute());
        params.put("arrivalTimeSecond", record.getArrivalTimeSecond());
        params.put("hasDepartureTime", record.getHasDepartureTime());
        params.put("departureTimeHour", record.getDepartureTimeHour());
        params.put("departureTimeMinute", record.getDepartureTimeMinute());
        params.put("departureTimeSecond", record.getDepartureTimeSecond());
        params.put("loadPassengerCount", record.getLoadPassengerCount());
        params.put("unloadPassengerCount", record.getUnloadPassengerCount());
        params.put("hasSelectedHeadTerminal", record.getHasSelectedHeadTerminal());
        params.put("clientId", record.getClientId());
        params.put("licensePlate", record.getLicensePlate());
        params.put("vehicleType", record.getVehicleType());

        JSONObject result = postObject("/dataentry/vehicle", params);
    }

    @Override
    public void addPassengerRecord(OutgoingPassengerRecord record) throws Exception {
        JSONObject params = new JSONObject();

        params.put("userName", record.getUser().getNationalCode());
        params.put("projectId", record.getProject().getExternalId());
        params.put("projectLineId", record.getProjectLine().getExternalId());
        params.put("year", record.getYear());
        params.put("month", record.getMonth());
        params.put("day", record.getDay());
        params.put("startHour", record.getStartHour());
        params.put("startMinute", record.getStartMinute());
        params.put("finishHour", record.getFinishHour());
        params.put("finishMinute", record.getFinishMinute());
        params.put("passengerCount", record.getPassengerCount());
        params.put("hasSelectedHead", record.getHasSelectedHeadTerminal());
        params.put("clientId", record.getClientId());

        JSONObject result = postObject("/dataentry/passenger", params);
    }
}
