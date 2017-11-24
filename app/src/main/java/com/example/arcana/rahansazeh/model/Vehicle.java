package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by arcana on 11/10/17.
 */

@Entity
public class Vehicle {
    @Id
    private Long id;

    @NotNull
    @Index
    private Long vehicleTypeId;

    @ToOne(joinProperty = "vehicleTypeId")
    private VehicleType vehicleType;

    @NotNull
    @Convert(converter = LicensePlate.Converter.class, columnType = String.class)
    @Index
    private LicensePlate license;

    @NotNull
    @Index
    private Long projectId;

    @ToOne(joinProperty = "projectId")
    private Project project;

    @Index
    private String externalId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 900796925)
    private transient VehicleDao myDao;

    @Generated(hash = 1321589710)
    public Vehicle(Long id, @NotNull Long vehicleTypeId, @NotNull LicensePlate license, @NotNull Long projectId,
            String externalId) {
        this.id = id;
        this.vehicleTypeId = vehicleTypeId;
        this.license = license;
        this.projectId = projectId;
        this.externalId = externalId;
    }

    @Generated(hash = 2006430483)
    public Vehicle() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleTypeId() {
        return this.vehicleTypeId;
    }

    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public LicensePlate getLicense() {
        return this.license;
    }

    public void setLicense(LicensePlate license) {
        this.license = license;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Generated(hash = 751547298)
    private transient Long vehicleType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1176774954)
    public VehicleType getVehicleType() {
        Long __key = this.vehicleTypeId;
        if (vehicleType__resolvedKey == null
                || !vehicleType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VehicleTypeDao targetDao = daoSession.getVehicleTypeDao();
            VehicleType vehicleTypeNew = targetDao.load(__key);
            synchronized (this) {
                vehicleType = vehicleTypeNew;
                vehicleType__resolvedKey = __key;
            }
        }
        return vehicleType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1016225483)
    public void setVehicleType(@NotNull VehicleType vehicleType) {
        if (vehicleType == null) {
            throw new DaoException(
                    "To-one property 'vehicleTypeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.vehicleType = vehicleType;
            vehicleTypeId = vehicleType.getId();
            vehicleType__resolvedKey = vehicleTypeId;
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
    @Generated(hash = 1588469812)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVehicleDao() : null;
    }
}
