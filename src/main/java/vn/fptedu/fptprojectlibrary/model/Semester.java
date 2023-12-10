package vn.fptedu.fptprojectlibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semester")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Date fromDate;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private Date toDate;

    public Semester(String name, Date fromDate, Date toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
