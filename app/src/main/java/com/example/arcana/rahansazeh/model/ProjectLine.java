package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

@Entity
public class ProjectLine {
    @Id
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Index
    private Long projectId;

    @ToOne(joinProperty = "projectId")
    private Project project;

    @NotNull
    private String head;

    @NotNull
    private String tail;

    @NotNull
    @Index(unique = true)
    private String externalId;

    @Index
    private long sortCriteria;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1330246484)
    private transient ProjectLineDao myDao;

    @Generated(hash = 1584299589)
    public ProjectLine() {
    }

    @Generated(hash = 1949245003)
    public ProjectLine(Long id, @NotNull String title, @NotNull Long projectId, @NotNull String head,
            @NotNull String tail, @NotNull String externalId, long sortCriteria) {
        this.id = id;
        this.title = title;
        this.projectId = projectId;
        this.head = head;
        this.tail = tail;
        this.externalId = externalId;
        this.sortCriteria = sortCriteria;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public String getHead() {
        return this.head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return this.tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public long getSortCriteria() {
        return this.sortCriteria;
    }

    public void setSortCriteria(long sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1063358197)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectLineDao() : null;
    }
}
