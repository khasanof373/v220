package uz.javlon.v220.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.javlon.v220.domain.Vehicle;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select vehicle from Vehicle vehicle where vehicle.user.login = ?#{principal.username}")
    List<Vehicle> findByUserIsCurrentUser();

    default Optional<Vehicle> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Vehicle> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Vehicle> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct vehicle from Vehicle vehicle left join fetch vehicle.tag",
        countQuery = "select count(distinct vehicle) from Vehicle vehicle"
    )
    Page<Vehicle> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct vehicle from Vehicle vehicle left join fetch vehicle.tag")
    List<Vehicle> findAllWithToOneRelationships();

    @Query("select vehicle from Vehicle vehicle left join fetch vehicle.tag where vehicle.id =:id")
    Optional<Vehicle> findOneWithToOneRelationships(@Param("id") Long id);
}
