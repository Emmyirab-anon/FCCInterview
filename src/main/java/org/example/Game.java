package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {
	private Map<Integer, Integer> scoreCount = new HashMap<>();
	private Integer iterations = 0;
	private Integer diceAmount = 0;
	private Integer originalDiceAmount = 0;
	private double timeToRunSImulation = 0;

	Game(Integer iterations, Integer diceAmount) {
		this.iterations = iterations;
		this.diceAmount = diceAmount;
		this.originalDiceAmount = diceAmount;
	}

	public void play() {
		long startTime = System.nanoTime();

		for (int i = 0; i < iterations; i++) {
			int score = 0;
			while (!gaveOver()) {
				List<Integer> result = roleDice();
				if (result.contains(3)) {
					int amountofThrees = Collections.frequency(result, 3);
					removeDie(amountofThrees);
				} else {
					score += result.get(0);
					removeDie(1);
				}
			}
			updateScoreCount(score);
			resetGame();
		}

		long endTime = System.nanoTime();
		timeToRunSImulation = ((endTime - startTime) / 1_000_000_000.0);
		printResult();
	}


	private void removeDie(Integer diceAmount) {
		this.diceAmount -= diceAmount;
	}

	private void resetGame() {
		this.diceAmount = originalDiceAmount;
	}

	private List<Integer> roleDice() {
		List<Integer> result = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < diceAmount; i++) {
			result.add(random.nextInt(6) + 1);
		}
		return result.stream().sorted().toList();
	}

	private boolean gaveOver() {
		return diceAmount < 1;
	}

	private void updateScoreCount(int score) {
		if (scoreCount.containsKey(score)) {
			scoreCount.put(score, scoreCount.get(score) + 1);
		} else {
			scoreCount.put(score, 1);
		}
	}

	private void printResult() {
		System.out.printf("Number of simulations was %d using %d dice.%n", iterations, originalDiceAmount);
		scoreCount.forEach((k, v) -> {
			System.out.printf("Total %d occurs %.2f occurred %d times.%n", k, (double) v / 10000, v);
		});

		System.out.println("Total simulation took " + String.format("%.1f", timeToRunSImulation) + " seconds.");
	}

}
