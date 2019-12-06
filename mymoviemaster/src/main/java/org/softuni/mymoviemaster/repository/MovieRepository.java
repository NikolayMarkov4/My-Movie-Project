package org.softuni.mymoviemaster.repository;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.entities.Movie;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query(value = "select * from my_movie_master_db.movies as movie inner join my_movie_master_db.movies_actors AS ma ON ma.movie_id = movie.id WHERE ma.actor_id = :id", nativeQuery = true)
    List<Movie> getAllMoviesForActor(@Param(value = "id") String id);

    List<Movie> getAllByDirectorId(String id);

    @Query(value = "select * from my_movie_master_db.movies as movies limit 5", nativeQuery = true)
    List<Movie> findAllLimitFive();
}