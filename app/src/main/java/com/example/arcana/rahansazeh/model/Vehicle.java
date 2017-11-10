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
    private LicensePlate license;

    @NotNull
    @Index
    private Long projectLineId;

    @ToOne(joinProperty = "projectLineId")
    private ProjectLine projectLine;

    @Index
    private Long externalId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 900796925)
    private transient VehicleDao myDao;

    @Generated(hash = 668040691)
    public Vehicle(Long id, @NotNull Long vehicleTypeId, @NotNull LicensePlate license,
            @NotNull Long projectLineId, Long externalId) {
        this.id = id;
        this.vehicleTypeId = vehicleTypeId;
        this.license = license;
        this.projectLineId = projectLineId;
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

    public Long getProjectLineId() {
        return this.projectLineId;
    }

    public void setProjectLineId(Long projectLineId) {
        this.projectLineId = projectLineId;
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

    public Long getExternalId() {
        return this.externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1588469812)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVehicleDao() : null;
    }
}
