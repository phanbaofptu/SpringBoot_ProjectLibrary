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
@Table(name = "groupdb")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @JoinColumn( insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User mentor;

    @Column(name = "mentor_id")
    private long mentorId;

    @JoinColumn(insertable = false, updatable = false)
    @ManyToOne(targetEntity = Semester.class, fetch = FetchType.LAZY)
    private Semester semester;

    @Column(name = "semester_id")
    private long semesterId;

    @JoinColumn( insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User leader;

    @Column(name = "leader_id")
    private long leaderId;

    @OneToMany(mappedBy = "groups")
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
