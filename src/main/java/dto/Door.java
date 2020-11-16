package dto;

public class Door {

    private int id;
    private Prize prize;

    public Door(int id, Prize prize) {
        this.id = id;
        this.prize = prize;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
