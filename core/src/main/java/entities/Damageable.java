package entities;

public interface Damageable {
    int getHp();
    void reduceHp(int amount);
}