package org.softuni.mymoviemaster.repository;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, String> {
    Optional<Director> findByName(String name);

    @Query(value = "select * from my_movie_master_db.directors as directors limit 5", nativeQuery = true)
    List<Director> findAllLimitFive();
}