package org.softuni.mymoviemaster.repository;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ActorRepository extends JpaRepository<Actor, String> {
    Optional<Actor> findByName(String name);

    @Query(value = "select * from my_movie_master_db.actors as actor limit 5", nativeQuery = true)
    List<Actor> findFirst10Actors();

    @Query(value = "select * from my_movie_master_db.actors as a inner join my_movie_master_db.movies_actors as ma ON a.id = ma.actor_id WHERE ma.movie_id = :id", nativeQuery = true)
    List<Actor> getAllActorsForMovieId(@Param(value = "id") String id);
}
