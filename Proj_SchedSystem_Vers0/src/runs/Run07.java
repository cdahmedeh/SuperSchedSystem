package runs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.SigmaScaling;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import schedule.CourseBlock;
import schedule.CourseTime;
import solution.Solution;
import student.Student;

public class Run07{
	public static void main(String[] args) {
		final ArrayList<Student> students = Run07Generator.generateStudents();
		CandidateFactory<Solution> candidateFactory = new CandidateFactory<Solution>() {
			
			@Override
			public Solution generateRandomCandidate(Random rng) {
				Solution solution = new Solution();
				
				ArrayList<CourseBlock> blocks = 			
						Run07Generator.generateBlocksBasedOnStudents(students);
				
				for (CourseBlock block: blocks){
					block.setCourseTime(Run07Generator.generateRandomTime(rng));
				}
				
				solution.setBlocks(blocks);
				
				return solution;
			}
			
			@Override
			public List<Solution> generateInitialPopulation(int populationSize,
					Collection<Solution> seedCandidates, Random rng) {
				ArrayList<Solution> solutions = new ArrayList<Solution>();
				
				for (int i=0; i<populationSize; i++){
					solutions.add(generateRandomCandidate(rng));
				}
				
				return solutions;
			}
			
			@Override
			public List<Solution> generateInitialPopulation(int populationSize,
					Random rng) {
				return generateInitialPopulation(populationSize, null, rng);
			}
		};
		
		
		FitnessEvaluator<Solution> fitnessEvaluator = new FitnessEvaluator<Solution>() {
			
			@Override
			public boolean isNatural() {
				return false;
			}
			
			@Override
			public double getFitness(Solution candidate,
					List<? extends Solution> population) {
				ArrayList<Student> students = Run07Generator.generateStudents();
				
				double score = 0.0;
				
				for (Student student: students){
					student.register(candidate.getBlocks());
					score += student.getTotalRestraintsScore();
				}
				
				return score;
			}
		};
		
		EvolutionaryOperator<Solution> operator1 = new EvolutionaryOperator<Solution>() {
			
			@Override
			public List<Solution> apply(List<Solution> selectedCandidates, Random rng) {
				ArrayList<Solution> copies = new ArrayList<>();
				
				for (Solution solution: selectedCandidates){
					Solution copy = solution.copy();
					
					for (CourseBlock block: copy.getBlocks()){
						if (rng.nextDouble() < 0.1){
							block.setCourseTime(Run07Generator.generateRandomTime(rng));
						}
					}
					
					copies.add(copy);
				}
				
				return copies;
			}
		};
		
		List<EvolutionaryOperator<Solution>> operators = new LinkedList<>();
		operators.add(operator1);
		
		EvolutionaryOperator<Solution> pipeline
	    = new EvolutionPipeline<Solution>(operators);
		
		EvolutionEngine<Solution> ee = new GenerationalEvolutionEngine<Solution>(
				candidateFactory, 
				pipeline, 
				fitnessEvaluator, 
				new SigmaScaling(), 
				new MersenneTwisterRNG()
		);
		
		ee.addEvolutionObserver(new EvolutionObserver<Solution>() {

			@Override
			public void populationUpdate(PopulationData<? extends Solution> data) {
				System.out.println(data.getMeanFitness());
				System.out.println(data.getBestCandidateFitness());
			}
		});
		
		ee.evolve(1000, 10, new TargetFitness(0, false));
	}	
}