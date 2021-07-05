package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDTO> searchMovies(@RequestParam Optional<String> title) {
        return movieService.searchMovies(title);
    }

    @GetMapping("/{id}")
    public MovieDTO searchMovieById(@PathVariable long id) {
        return movieService.searchMovieById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createMovie(@RequestBody CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @PutMapping("/{id}/reserve")
    public MovieDTO reserveMovieForCinema(@PathVariable long id, @RequestBody CreateReservationCommand command) {
        return movieService.reserveMovieForCinema(id, command);
    }
}
