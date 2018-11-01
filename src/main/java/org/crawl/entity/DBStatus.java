package org.crawl.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "tweet")
public class DBStatus {

    @Id
    private String filename;
    private String pathname;
    private String user;
    private long statusID;
    private Date createdAt;

    public DBStatus(String filename, long statusID,  String pathname, String user, Date createdAt) {
        this.filename = filename;
        this.statusID = statusID;
        this.pathname = pathname;
        this.user = user;
        this.createdAt = createdAt;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public long getStatusID() {
        return statusID;
    }

    public void setStatusID(long statusID) {
        this.statusID = statusID;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusID=" + statusID +
                ", createdAt=" + createdAt +
                ", user='" + user + '\'' +
                ", pathname='" + pathname + '\'' +
                '}';
    }

}
