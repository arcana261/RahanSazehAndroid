package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by arcana on 11/18/17.
 */

@Entity
public class OutgoingPassengerRecord {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long userId;

    @ToOne(joinProperty = "userId")
    private User user;

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

    private int startHour;
    private int startMinute;
    private int finishHour;
    private int finishMinute;

    private int passengerCount;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 278215829)
    private transient OutgoingPassengerRecordDao myDao;

    @Generated(hash = 584775806)
    public OutgoingPassengerRecord(Long id, @NotNull Long userId,
            @NotNull Long projectId, @NotNull Long projectLineId, int year,
            int month, int day, int startHour, int startMinute, int finishHour,
            int finishMinute, int passengerCount) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.projectLineId = projectLineId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.finishHour = finishHour;
        this.finishMinute = finishMinute;
        this.passengerCount = passengerCount;
    }

    @Generated(hash = 937469481)
    public OutgoingPassengerRecord() {
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

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return this.startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getFinishHour() {
        return this.finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMinute() {
        return this.finishMinute;
    }

    public void setFinishMinute(int finishMinute) {
        this.finishMinute = finishMinute;
    }

    public int getPassengerCount() {
        return this.passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1230165899)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOutgoingPassengerRecordDao() : null;
    }
}
