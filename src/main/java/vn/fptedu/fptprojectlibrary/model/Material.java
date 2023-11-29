package vn.fptedu.fptprojectlibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.fptedu.fptprojectlibrary.dto.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(MAX)")
    private String name;
    @Column( columnDefinition = "nvarchar(MAX)")
    private String description;
    @Column( columnDefinition = "nvarchar(MAX)")
    private String fileName;
    @Column(columnDefinition = "varchar(MAX)")
    private String fileUrl;

    @JoinColumn(name = "postBy", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User postByUser;

    @Column(name = "postBy")
    private long postBy;



    public void setPostByUser(User postByUser) {
        setPostBy(postByUser.getId());
        this.postByUser = postByUser;
    }
}
