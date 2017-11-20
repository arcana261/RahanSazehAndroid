package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by arcana on 11/16/17.
 */

@Entity
public class OutgoingVehicleRecord {
    @Id
    private Long id;

    @NotNull
    private Long userId;

    @ToOne(joinProperty = "userId")
    private User user;

    private boolean hasLoaded;
    private boolean hasUnLoaded;

    @NotNull
    private Long projectId;

    @ToOne(joinProperty = "projectId")
    private Project project;

    @NotNull
    private Long projectLineId;

    @ToOne(joinProperty = "projectLineId")
    private ProjectLine projectLine;

    private int year;
    private int month;
    private int day;

    private boolean hasArrivalTime;
    private int arrivalTimeHour;
    private int arrivalTimeMinute;
    private int arrivalTimeSecond;

    private boolean hasDepartureTime;
    private int departureTimeHour;
    private int departuerTimeMinute;
    private int departureTimeSecond;

    private int loadPassengerCount;
    private int unloadPassengerCount;

    private boolean hasSelectedHeadTerminal;

    @NotNull
    private String licensePlate;

    @NotNull
    private String vehicleType;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2071848765)
    private transient OutgoingVehicleRecordDao myDao;

    @Generated(hash = 1873093936)
    public OutgoingVehicleRecord(Long id, @NotNull Long userId, boolean hasLoaded, boolean hasUnLoaded, @NotNull Long projectId,
            @NotNull Long projectLineId, int year, int month, int day, boolean hasArrivalTime, int arrivalTimeHour,
            int arrivalTimeMinute, int arrivalTimeSecond, boolean hasDepartureTime, int departureTimeHour, int departuerTimeMinute,
            int departureTimeSecond, int loadPassengerCount, int unloadPassengerCount, boolean hasSelectedHeadTerminal,
            @NotNull String licensePlate, @NotNull String vehicleType) {
        this.id = id;
        this.userId = userId;
        this.hasLoaded = hasLoaded;
        this.hasUnLoaded = hasUnLoaded;
        this.projectId = projectId;
        this.projectLineId = projectLineId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hasArrivalTime = hasArrivalTime;
        this.arrivalTimeHour = arrivalTimeHour;
        this.arrivalTimeMinute = arrivalTimeMinute;
        this.arrivalTimeSecond = arrivalTimeSecond;
        this.hasDepartureTime = hasDepartureTime;
        this.departureTimeHour = departureTimeHour;
        this.departuerTimeMinute = departuerTimeMinute;
        this.departureTimeSecond = departureTimeSecond;
        this.loadPassengerCount = loadPassengerCount;
        this.unloadPassengerCount = unloadPassengerCount;
        this.hasSelectedHeadTerminal = hasSelectedHeadTerminal;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    @Generated(hash = 1056058307)
    public OutgoingVehicleRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getHasLoaded() {
        return this.hasLoaded;
    }

    public void setHasLoaded(boolean hasLoaded) {
        this.hasLoaded = hasLoaded;
    }

    public boolean getHasUnLoaded() {
        return this.hasUnLoaded;
    }

    public void setHasUnLoaded(boolean hasUnLoaded) {
        this.hasUnLoaded = hasUnLoaded;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectLineId() {
        return this.projectLineId;
    }

    public void setProjectLineId(Long projectLineId) {
        this.projectLineId = projectLineId;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean getHasArrivalTime() {
        return this.hasArrivalTime;
    }

    public void setHasArrivalTime(boolean hasArrivalTime) {
        this.hasArrivalTime = hasArrivalTime;
    }

    public int getArrivalTimeHour() {
        return this.arrivalTimeHour;
    }

    public void setArrivalTimeHour(int arrivalTimeHour) {
        this.arrivalTimeHour = arrivalTimeHour;
    }

    public int getArrivalTimeMinute() {
        return this.arrivalTimeMinute;
    }

    public void setArrivalTimeMinute(int arrivalTimeMinute) {
        this.arrivalTimeMinute = arrivalTimeMinute;
    }

    public int getArrivalTimeSecond() {
        return this.arrivalTimeSecond;
    }

    public void setArrivalTimeSecond(int arrivalTimeSecond) {
        this.arrivalTimeSecond = arrivalTimeSecond;
    }

    public boolean getHasDepartureTime() {
        return this.hasDepartureTime;
    }

    public void setHasDepartureTime(boolean hasDepartureTime) {
        this.hasDepartureTime = hasDepartureTime;
    }

    public int getDepartureTimeHour() {
        return this.departureTimeHour;
    }

    public void setDepartureTimeHour(int departureTimeHour) {
        this.departureTimeHour = departureTimeHour;
    }

    public int getDepartuerTimeMinute() {
        return this.departuerTimeMinute;
    }

    public void setDepartuerTimeMinute(int departuerTimeMinute) {
        this.departuerTimeMinute = departuerTimeMinute;
    }

    public int getDepartureTimeSecond() {
        return this.departureTimeSecond;
    }

    public void setDepartureTimeSecond(int departureTimeSecond) {
        this.departureTimeSecond = departureTimeSecond;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462495677)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getId();
            user__resolvedKey = userId;
        }
    }

    @Generated(hash = 1005767482)
    private transient Long project__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1654636707)
    public Project getProject() {
        Long __key = this.projectId;
        if (project__resolvedKey == null || !project__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectDao targetDao = daoSession.getProjectDao();
            Project projectNew = targetDao.load(__key);
            synchronized (this) {
                project = projectNew;
                project__resolvedKey = __key;
            }
        }
        return project;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1207172963)
    public void setProject(@NotNull Project project) {
        if (project == null) {
            throw new DaoException(
                    "To-one property 'projectId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.project = project;
            projectId = project.getId();
            project__resolvedKey = projectId;
        }
    }

    @Generated(hash = 396317904)
    private transient Long projectLine__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 801675444)
    public ProjectLine getProjectLine() {
        Long __key = this.projectLineId;
        if (projectLine__resolvedKey == null
                || !projectLine__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectLineDao targetDao = daoSession.getProjectLineDao();
            ProjectLine projectLineNew = targetDao.load(__key);
            synchronized (this) {
                projectLine = projectLineNew;
                projectLine__resolvedKey = __key;
            }
        }
        return projectLine;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1231955987)
    public void setProjectLine(@NotNull ProjectLine projectLine) {
        if (projectLine == null) {
            throw new DaoException(
                    "To-one property 'projectLineId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.projectLine = projectLine;
            projectLineId = projectLine.getId();
            projectLine__resolvedKey = projectLineId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public int getLoadPassengerCount() {
        return this.loadPassengerCount;
    }

    public void setLoadPassengerCount(int loadPassengerCount) {
        this.loadPassengerCount = loadPassengerCount;
    }

    public int getUnloadPassengerCount() {
        return this.unloadPassengerCount;
    }

    public void setUnloadPassengerCount(int unloadPassengerCount) {
        this.unloadPassengerCount = unloadPassengerCount;
    }

    public boolean getHasSelectedHeadTerminal() {
        return this.hasSelectedHeadTerminal;
    }

    public void setHasSelectedHeadTerminal(boolean hasSelectedHeadTerminal) {
        this.hasSelectedHeadTerminal = hasSelectedHeadTerminal;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1035213288)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOutgoingVehicleRecordDao() : null;
    }
}
