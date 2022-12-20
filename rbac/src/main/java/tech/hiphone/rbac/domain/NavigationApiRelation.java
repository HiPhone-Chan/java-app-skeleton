package tech.hiphone.rbac.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.rbac.domain.id.NavigationApiId;

//导航使用到的api关系
@Entity
@Table(name = "rbac_navigation_api_relation")
public class NavigationApiRelation {

    @EmbeddedId
    private NavigationApiId id;

    public NavigationApiId getId() {
        return id;
    }

    public void setId(NavigationApiId id) {
        this.id = id;
    }

}
