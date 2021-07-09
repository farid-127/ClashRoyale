package Models.Cards.buildings;

public class Cannon extends Building {
    private int damage;
    public Cannon(int cost) {
        super(cost);
    }

    @Override
    public void born() {

    }

    @Override
    public void die() {

    }


    @Override
    public void run() {

    }


    @Override
    public void setLevel(int level) {
        if (level == 1) {
            setHp(380);
            setDamage(60);
        } else if (level == 2) {
            setHp(418);
            setDamage(66);
        } else if (level == 3) {
            setHp(459);
            setDamage(72);
        } else if (level == 4) {
            setHp(505);
            setDamage(79);
        } else if (level == 5) {
            setHp(554);
            setDamage(87);
        } else {
            try {
                throw new Exception("Unknown input!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
