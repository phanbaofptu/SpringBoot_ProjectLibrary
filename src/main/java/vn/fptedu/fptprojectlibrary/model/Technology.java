package vn.fptedu.fptprojectlibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;

    @ManyToMany(mappedBy = "technologies")
    private List<Project> projects = new ArrayList<>();


    public Technology(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Technology(String name) {
        this.name = name;
    }
}
