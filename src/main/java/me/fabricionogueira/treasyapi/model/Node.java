package me.fabricionogueira.treasyapi.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "nodes")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_at", "updated_at" }, allowGetters = true)
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotBlank
    private String detail;

    private Long parent_id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent", insertable = true, updatable = true)
    private Node parent;

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)//Avoiding to show empty json arrays.objects
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<Node> childrens;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the Detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return created_at;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updated_at;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updated_at = updatedAt;
    }

    /**
     * @return the childrens
     */
    public Collection<Node> getChildrens() {
        return childrens;
    }

    /**
     * @param childrens the childrens to set
     */
    public void setChildrens(Collection<Node> childrens) {
        this.childrens = childrens;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the parent_id
     */
    public Long getParentId() {
        return parent_id;
    }

    /**
     * @param parent_id the parent_id to set
     */
    public void setParentId(Long parent_id) {
        this.parent_id = parent_id;
    }

}
