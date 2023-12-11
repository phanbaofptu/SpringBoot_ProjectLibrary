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
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "text")
    private String name;
    @Column( columnDefinition = "text")
    private String description;
    @Column( columnDefinition = "text")
    private String fileName;
    @Column(columnDefinition = "text")
    private String fileUrl;

    @JoinColumn( insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name="user_id")
    private long postBy;


    public void setUser(User user) {
        setPostBy(user.getId());
        this.user = user;
    }
}
