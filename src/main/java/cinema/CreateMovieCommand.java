package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCommand {

    @NotBlank(message = "Movie title cannot be empty!")
    private String title;
    private LocalDateTime date;

    @Min(message = "The maximum reservation must be at least 20", value = 20)
    private int maxReservation;
}
