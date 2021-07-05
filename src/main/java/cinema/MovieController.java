package cinema;

import org.springframework.web.bind.annotation.*;

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

}
