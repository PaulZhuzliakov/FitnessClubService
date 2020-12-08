package org.demo.project.model;

import java.sql.Date;

public class VisitDate {
    int clientId;
    Date date;

    public VisitDate() {
    }

    //задаёт текущую дату. для отметки посещения клиентом клуба
    public VisitDate(int clientId) {
        this.clientId = clientId;
    }

    public VisitDate(int clientId, Date date) {
        this.clientId = clientId;
        this.date = date;
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
