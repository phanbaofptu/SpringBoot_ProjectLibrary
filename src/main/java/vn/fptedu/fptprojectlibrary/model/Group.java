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
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @JoinColumn(name = "mentor", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User mentor;

    @Column(name = "mentor")
    private long mentorId;

    @JoinColumn(name = "semester", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Semester.class, fetch = FetchType.LAZY)
    private Semester semester;

    @Column(name = "semester")
    private long semesterId;

    @JoinColumn(name = "leader", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User leader;

    @Column(name = "leader")
    private long leaderId;

//    @OneToMany()
//    @JoinColumn(name="member")
//    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<User> members = new ArrayList<>();


    public void setMentor(User mentor) {
        setMentorId(mentor.getId());
        this.mentor = mentor;
    }

    public void setLeader(User leader) {
        setLeaderId(leader.getId());
        this.leader = leader;
    }

    public void setSemester(Semester semester) {
        setSemesterId(semester.getId());
        this.semester = semester;
    }


}
