package vn.fptedu.fptprojectlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fptedu.fptprojectlibrary.model.Project;
import vn.fptedu.fptprojectlibrary.model.Role;
import vn.fptedu.fptprojectlibrary.model.Technology;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    List<Technology> findTechnologiesByOrderByCategory();

}