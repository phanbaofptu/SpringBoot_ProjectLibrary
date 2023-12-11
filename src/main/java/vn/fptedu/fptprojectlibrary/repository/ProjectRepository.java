package vn.fptedu.fptprojectlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.fptedu.fptprojectlibrary.model.Group;
import vn.fptedu.fptprojectlibrary.model.Project;
import vn.fptedu.fptprojectlibrary.model.Technology;
import vn.fptedu.fptprojectlibrary.model.User;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectByGroupId(Long groupId);

    List<Project> findProjectByStatus(String status);

    @Query("select g from Project g where g.group.mentorId = ?1")
    List<Project> findByMentorId(Long mentorId);
}