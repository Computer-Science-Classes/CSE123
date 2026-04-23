public final class QuoridorPawn {
    private int player;
    private int position;
    private static int wallsRemaining = 20;

    public QuoridorPawn(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getWallsRemaining() {
        return wallsRemaining;
    }

    public void removeWall() {
        wallsRemaining--;
    }
}
