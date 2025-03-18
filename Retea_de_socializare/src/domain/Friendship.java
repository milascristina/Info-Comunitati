package domain;

import java.time.LocalDateTime;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Friendship extends Entity<Long>{
    LocalDateTime friendsfrom;

    Long idUser1;
    Long idUser2;

    public Friendship(Long User1, Long User2) {
        this.idUser1 = User1;
        this.idUser2=User2;
    }
    public Long getIdUser1() {
        return idUser1;
    }
    public Long getIdUser2() {
        return idUser2;
    }
    public LocalDateTime getDate() {
        return friendsfrom;
    }
}
