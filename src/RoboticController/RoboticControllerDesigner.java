package RoboticController;

import java.util.logging.Logger;
import static RoboticController.ParameterConstant.*;

public class RoboticControllerDesigner {
	private final static int chromosomelength = 128;
	private static final Logger log = Logger.getLogger(Maze.class.getName());
	
	public static void main(String[] args) {
		
		//0- Vacant space 1 - Obstacle/wall 2 - start point 3 - best route 4 - end point
		Maze maze = new Maze(new int[][] {
				{ 0, 0, 0, 0, 1, 0, 1, 3, 4 }, 
				{ 1, 0, 1, 1, 1, 0, 1, 3, 1 },
				{ 1, 0, 0, 1, 3, 3, 3, 3, 1 },
				{ 3, 3, 3, 1, 3, 1, 1, 0, 1 },
				{ 3, 1, 3, 3, 3, 1, 1, 0, 0 },
				{ 3, 3, 1, 1, 1, 1, 0, 1, 1 },
				{ 1, 3, 0, 1, 3, 3, 3, 3, 3 },
				{ 0, 3, 1, 1, 3, 1, 0, 1, 3 },
				{ 1, 3, 3, 3, 3, 1, 1, 1, 2 }
			});
		// Create GA object
		//Parameter List ( population size, mutation rate , crossover rate, eliteCount, tornament size ) 
		GeneticAlgorithm ga = new GeneticAlgorithm(POPULATIONSIZE, MUTATIONSIZE,CROSSOVERRATE, ELITSMCOUNT, TOURNAMENTSIZE);
		
		// Initialize population
		Population population = ga.initPopulation(chromosomelength);
		log.info("Inital population Created");
		
		// Evaluate population
		ga.evalPopulation(population, maze);

		// Keep track of current generation
		int generation = 1;
		double mazeMaxScore = maze.getMaxScore();
		double currScore = population.getFittest(0).getFitness();

		double starttime = System.currentTimeMillis();
		while (ga.isTerminationConditionMet(generation,MAXGENERATION,mazeMaxScore,currScore) == false) {
			
			// Print fittest individual from population
			System.out.println("New solution: " +"(" +  population.getFittest(0).getFitness() +"):" + population.getFittest(0).toString());
			// Apply crossover
			population = ga.crossoverPopulation(population);
			// Apply mutation
			population = ga.mutatePopulation(population);
			// Evaluate population
			ga.evalPopulation(population, maze);
			// Increment the current generation
			currScore = population.getFittest(0).getFitness();
			generation++;
			
		}
		
		double endttime = System.currentTimeMillis();
		System.out.println("Overall Time Taken(Sec) is " + (endttime-starttime)/1000 + "s");
		System.out.println("Found solution in " + generation + " generations with fitness Score = " + currScore);
		System.out.println("Final solution: " + population.getFittest(0).toString());
		log.info("Final solution: " + population.getFittest(0).toString());
	}
}
