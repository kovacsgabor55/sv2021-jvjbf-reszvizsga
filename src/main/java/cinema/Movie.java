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
    private LocalDateTime playDate;
    private int maxSeat;
    private int freeSeat;

    public void reservation(int numberOfSeat) {
        if (freeSeat >= numberOfSeat) {
            maxSeat -= numberOfSeat;
        } else {
            throw new IllegalStateException("No more empty seat!");
        }
    }
}
