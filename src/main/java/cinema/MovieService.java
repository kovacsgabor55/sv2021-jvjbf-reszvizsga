package cinema;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final ModelMapper modelMapper;

    private final List<Movie> movies = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong();

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDTO> searchMovies(Optional<String> title) {
        Type targetListType = new TypeToken<List<MovieDTO>>() {
        }.getType();

        List<Movie> filtered = movies.stream()
                .filter(e -> title.isEmpty() || e.getTitle().equalsIgnoreCase(title.get()))
                .collect(Collectors.toList());

        return modelMapper.map(filtered, targetListType);
    }


    public MovieDTO searchMovieById(long id) {
        return modelMapper.map(findById(id), MovieDTO.class);
    }

    private Movie findById(long id) {
        return movies.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie not fount: " + id));
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getDate(), command.getMaxReservation(), command.getMaxReservation());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO reserveMovieForCinema(long id, cinema.CreateReservationCommand command) {
        Movie movie = findById(id);
        movie.reservation(command.getReservation());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO updateMovieDate(long id, UpdateDateCommand command) {
        Movie movie = findById(id);
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteAllMovie() {
        movies.clear();
        idGenerator.set(0L);
    }
}
