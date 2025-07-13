package game.Initialiser;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.GameBoard.Coordinate;
import game.Objects.Worker;
import game.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkerInitialiser {

    /**
     * Randomly places two workers per player on unoccupied cells of the board.
     * @param players the players to assign workers to
     * @param board the board to place workers on
     */
    public void assignWorkersToBoard(List<Player> players, Board board) {
        int width = board.getBoardWidth();
        int height = board.getBoardHeight();
        Random rand = new Random();

        for (Player player : players) {
            List<Worker> workers = new ArrayList<>();
            int placed = 0;

            while (placed < 2) {
                int x = rand.nextInt(width);
                int y = rand.nextInt(height);
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = board.getCell(coordinate);

                if (cell.notOccupied()) {
                    Worker worker = new Worker(cell, player.getPlayerId().getImagePathString(),
                            player);
                    cell.addOccupant(worker);
                    workers.add(worker);
                    placed++;
                }
            }

            player.initialiseWorkers(workers);
        }
    }
}
