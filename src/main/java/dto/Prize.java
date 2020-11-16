package dto;

public class Prize {

    private boolean goat;
    private boolean car;

    public Prize(boolean goat, boolean car) {
        this.goat = goat;
        this.car = car;
    }

    public boolean isGoat() {
        return goat;
    }

    public void setGoat(boolean goat) {
        this.goat = goat;
    }

    public boolean isCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }
}
