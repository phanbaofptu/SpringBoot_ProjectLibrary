package vn.fptedu.fptprojectlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fptedu.fptprojectlibrary.model.Material;
import vn.fptedu.fptprojectlibrary.model.Semester;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}