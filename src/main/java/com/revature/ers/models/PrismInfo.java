package com.revature.ers.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ers_prism_info")
public class PrismInfo {
    @Id
    @Column(name="org_id")
    private String orgId;

    @Column(name="auth_code", nullable = false, unique = true)
    private String authCode;

    public PrismInfo() {
        super();
    }

    public PrismInfo(String orgId, String authCode) {
        this.orgId = orgId;
        this.authCode = authCode;
    }

    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "PrismInfo{" +
                "orgId='" + orgId + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
