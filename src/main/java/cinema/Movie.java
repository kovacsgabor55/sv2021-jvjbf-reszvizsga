package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private LocalDateTime date;
    private int maxReservation;
    private int freeReservation;

    public void reservation(int numberOfSeat) {
        if (freeReservation >= numberOfSeat) {
            maxReservation -= numberOfSeat;
        } else {
            throw new IllegalStateException("No more empty seat!");
        }
    }
}
