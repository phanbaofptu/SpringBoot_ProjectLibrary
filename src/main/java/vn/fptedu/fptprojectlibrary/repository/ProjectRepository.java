package vn.fptedu.fptprojectlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fptedu.fptprojectlibrary.model.Project;
import vn.fptedu.fptprojectlibrary.model.Technology;
import vn.fptedu.fptprojectlibrary.model.User;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectByGroupId(Long groupId);

}