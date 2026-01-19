package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.CollisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CollisionRepository extends JpaRepository<CollisionEntity,Long> {

    /**
     * Oppure con JPQL
     * @Query("SELECT c.latitude, c.longitude FROM CollisionEntity c WHERE c.borough IS NOT NULL")
     *     List<Object[]> getLatLongFromBorough();
     *
     * oppure con costruttore del DTO nella query
     * @Query("SELECT new org.example.DTO.LatLon(c.latitude,c.longitude) FROM CollisionEntity c WHERE c.borough IS NOT NULL")
     *      List<LatLon> getLatLongFromBorough();
     */
    @Query(
            value = "SELECT collision_id, latitude,longitude FROM collision WHERE borough IS NULL AND latitude IS NOT NULL AND longitude IS NOT NULL",
            nativeQuery = true
    )
    List<Object[]> getLatLongFromBoroughNotNull();

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE collision SET borough = :suburb WHERE collision_id = :id",
            nativeQuery = true
    )
    void updateBorough(@Param("id") Long id, @Param("suburb") String suburb);
}
