package vn.fptedu.fptprojectlibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(MAX)")
    private String name;
    @Column( columnDefinition = "nvarchar(MAX)")
    private String description;
    @Column( columnDefinition = "nvarchar(10)")
    private String status;
    @Column(columnDefinition = "nvarchar(MAX)")
    private String comment;

    @JoinColumn(name = "groups", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    private Group group;

    @Column(name = "groups")
    private long groupId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "project_technology",
            joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "technology_id", referencedColumnName = "id")}
    )
    private List<Technology> technologies = new ArrayList<>();

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Column()
    private Date dateRegister;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Column()
    private Date dateStatus;

    public void setGroup(Group group) {
        setGroupId(group.getId());
        this.group = group;
    }


}
