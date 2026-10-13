class SynWin {
    public String winner = "";
    public synchronized void setWinner(String newWinner) {
        synchronized (this) {
            winner = newWinner;
        }
    }

    public synchronized String getWinner() {
        synchronized (this) {
            return winner;
        }
    }
}
class Chiken extends Thread {
    private SynWin synWin; // поле чтобы поток знал про объект

    public Chiken(String chicken, SynWin synWin) {
        super(chicken);
        this.synWin = synWin;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("курица");
            synWin.setWinner("курица");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Egg extends Thread {
    private SynWin synWin; // поле чтобы поток знал про объект

    public Egg(String egg, SynWin synWin) {
        super(egg);
        this.synWin = synWin;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("яйцо");
            synWin.setWinner("яйцо");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SynWin synWin = new SynWin();

        Chiken chiken = new Chiken("курица", synWin);
        Egg egg = new Egg("яйцо", synWin);

        chiken.start();
        egg.start();

        try {
            while (chiken.isAlive() && egg.isAlive()) {
                Thread.sleep(20);
            }
            chiken.join();
            egg.join();
            System.out.println("спор окончен, победа: " + synWin.getWinner());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("основной поток завершает работу.");
    }
}