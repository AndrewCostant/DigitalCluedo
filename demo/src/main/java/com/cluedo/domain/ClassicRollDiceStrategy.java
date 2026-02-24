package com.cluedo.domain;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.cluedo.domain.dto.RollResult;

public class ClassicRollDiceStrategy implements RollDiceStrategy {

    @Override
    public RollResult possibleDestinations(Dice dice, Cell startPosition) {
        int steps = dice.roll() + dice.roll();
        Board board = Board.getInstance();

        Set<Cell> visited = new HashSet<>();
        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Integer> depth = new HashMap<>();

        visited.add(startPosition);
        queue.add(startPosition);
        depth.put(startPosition, 0);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int d = depth.get(current);

            if (d == steps) continue;

            for (DefaultEdge edge : board.edgesOf(current)) {
                Cell neighbor =
                        board.getOppositeVertex(current, edge);

                if (visited.add(neighbor)) {
                    depth.put(neighbor, d + 1);
                    queue.add(neighbor);
                }
            }
        }
		visited.remove(startPosition); // Exclude the starting position
        return new RollResult(steps, visited);

    }
    
}
