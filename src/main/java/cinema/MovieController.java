package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
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
    public MovieDTO createMovie(@RequestBody @Valid CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @PostMapping("/{id}/reserve")
    public MovieDTO reserveMovieForCinema(@PathVariable long id, @RequestBody @Valid CreateReservationCommand command) {
        return movieService.reserveMovieForCinema(id, command);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovieDate(@PathVariable long id, @RequestBody UpdateDateCommand command) {
        return movieService.updateMovieDate(id, command);
    }

    @DeleteMapping()
    public void deleteAllMovie() {
        movieService.deleteAllMovie();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("cinema/not-found"))
                        .withTitle("Not found")
                        .withStatus(Status.NOT_FOUND)
                        .withDetail(iae.getMessage())
                        .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Problem> noFreeSeat(IllegalStateException ise) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("cinema/bad-reservation"))
                        .withTitle("Bad reservation")
                        .withStatus(Status.BAD_REQUEST)
                        .withDetail(ise.getMessage())
                        .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
