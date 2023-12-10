package vn.fptedu.fptprojectlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.fptedu.fptprojectlibrary.model.Group;
import vn.fptedu.fptprojectlibrary.model.Technology;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where g.mentorId = ?1")
    List<Group> findByMentorId(Long mentorId);
}