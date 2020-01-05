package rsoi.lab5.seservice.entity.oauth;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import rsoi.lab5.seservice.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "oauth_client")
@EntityListeners(value = AuditingEntityListener.class)
public class OAuthClient implements Serializable {
    private static final long serialVersionUID = 43127469381264723L;

    @Id
    @GeneratedValue
    @Column(name = "client_id", nullable = false, updatable = false, unique = true)
    private UUID clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "scope")
    private String scope;

    @Column(name = "redirect_uri", nullable = false)
    private String redirectUri;

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "grant_password")
    private boolean grantPassword;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isGrantPassword() {
        return grantPassword;
    }

    public void setGrantPassword(boolean grantPassword) {
        this.grantPassword = grantPassword;
    }
}
