package runs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.SigmaScaling;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import schedule.CourseBlock;
import schedule.CourseTime;
import solution.Solution;
import student.Student;
import ui.DoubleWrapper;
import ui.SolutionDemonstrator;

public class Run10FastExternalizer{
	private static DoubleWrapper initialFitness;
	protected static String superValue;

	public static String solutionProcessorStart(final HashMap<Integer,String> classList,  final ArrayList<Student> students, final StringBuffer buffer, DoubleWrapper initialFitness) {		
		Run10FastExternalizer.initialFitness = initialFitness;
		CandidateFactory<Solution> candidateFactory = new CandidateFactory<Solution>() {			
			@Override
			public Solution generateRandomCandidate(Random rng) {
				Solution solution = new Solution();
				
				HashMap<String, CourseBlock> blocks = Run09Generator.generateBlocksBasedClassList(classList);
						
				for (CourseBlock block: blocks.values()){
					block.setCourseTime(Run09Generator.generateRandomTime(rng));
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
				ArrayList<Student> studentsCopy = new ArrayList<>();	
				
				for (Student student: students){
					studentsCopy.add(student.clone());
				}
				double score = 0.0;
				
				for (Student student: studentsCopy){
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
					
					for (CourseBlock block: copy.getBlocks().values()){
						if (rng.nextDouble() < 0.1){
							block.setCourseTime(Run07Generator.generateRandomTime(rng));
						}
					}
					
					copies.add(copy);
				}
				
				return copies;
			}
		};
		
		EvolutionaryOperator<Solution> operator2 = new EvolutionaryOperator<Solution>() {
			
			@Override
			public List<Solution> apply(List<Solution> selectedCandidates, Random rng) {
				List<Solution> copies = selectedCandidates;
				ArrayList<Solution> rcopies = new ArrayList<>();
				
				for (int i=0; i<copies.size()-1; i=i+2){
					Solution parent1 = copies.get(i);
					Solution parent2 = copies.get(i+1);
					
					int point = rng.nextInt(parent1.getBlocks().values().size());
					
					Solution offspring1 = new Solution();
					HashMap<String, CourseBlock> blocks1 = new HashMap<>();
					
					Solution offspring2 = new Solution();
					HashMap<String, CourseBlock> blocks2 = new HashMap<>();
					
					for (int j=0; j<Math.min(parent1.getBlocks().values().size(), parent2.getBlocks().values().size()); j++){
						CourseBlock courseBlock = new ArrayList<CourseBlock>(parent2.getBlocks().values()).get(j);
						CourseBlock courseBlock2 = new ArrayList<CourseBlock>(parent1.getBlocks().values()).get(j);
						if (j<point){
							blocks1.put(courseBlock.getCourseName(), courseBlock);
							blocks2.put(courseBlock2.getCourseName(), courseBlock2);
						}else{
							blocks1.put(courseBlock2.getCourseName(), courseBlock2);
							blocks2.put(courseBlock.getCourseName(), courseBlock);
						}
					}
					
					offspring1.setBlocks(blocks1);
					offspring2.setBlocks(blocks2);
					rcopies.add(offspring1);
					rcopies.add(offspring2);
//					rcopies.add(parent1);
				}
				
				if (copies.size() %2 != 0){
					rcopies.add(selectedCandidates.get(selectedCandidates.size()-1));
				}
				
				return rcopies;
			}
		};
		
		EvolutionaryOperator<Solution> operator3 = new EvolutionaryOperator<Solution>() {
			
			@Override
			public List<Solution> apply(List<Solution> selectedCandidates, Random rng) {
				if (rng.nextDouble() < 0.1) {
				ArrayList<Solution> copies = new ArrayList<>();
				
				for (Solution solution: selectedCandidates){
					Solution copy = solution.copy();

					if (rng.nextDouble() < 0.5){
						for (CourseBlock block: copy.getBlocks().values()){
								block.setCourseTime(Run07Generator.generateRandomTime(rng));
						}
					}
					
					copies.add(copy);
				}
				
				return copies;
				}
				
				return selectedCandidates;
			}
		};
		
		List<EvolutionaryOperator<Solution>> operators = new LinkedList<>();
		operators.add(operator3);
		operators.add(operator1);
		operators.add(operator2);
		
		EvolutionaryOperator<Solution> pipeline
	    = new EvolutionPipeline<Solution>(operators);
		
		EvolutionEngine<Solution> ee = new GenerationalEvolutionEngine<Solution>(
				candidateFactory, 
				pipeline, 
				fitnessEvaluator, 
//				new SigmaScaling(),
//				new TournamentSelection(new Probability(0.6)),
				new RouletteWheelSelection(),
//				new StochasticUniversalSampling(),
//				new RankSelection(),
				new MersenneTwisterRNG()
		);
		
		ee.addEvolutionObserver(new EvolutionObserver<Solution>() {

			@Override
			public void populationUpdate(PopulationData<? extends Solution> data) {
//				System.out.println(data.getMeanFitness());
//				System.out.println(data.getPopulationSize());
//				buffer.append(data.getMeanFitness());
//				buffer.append("\n");
//				buffer.append(data.getPopulationSize());
//				buffer.append("\n");
				
				double fit = data.getBestCandidateFitness();
//				System.out.println(fit);
				buffer.delete(0, buffer.length());
				buffer.append(fit);
//				buffer.append("\n");
				
				if (Run10FastExternalizer.initialFitness.getDbl() == -1.0) {
					Run10FastExternalizer.initialFitness.setDbl(fit);
				}
				
				if (fit == 0.0){
					ArrayList<Student> studentsCopy = new ArrayList<>();	
					
					for (Student student: students){
						studentsCopy.add(student.clone());
					}
					
					StringBuilder sb = new StringBuilder();
					
					for (Student student: studentsCopy){
						student.register(data.getBestCandidate().getBlocks());
//						System.out.println(student.getSchedule());
//						buffer.append(student.getSchedule().toString());
//						buffer.append("\n");
						sb.append(student.getSchedule().toString());
						sb.append("\n");
					}
					
					superValue = sb.toString();
				}
			}
		});
		
		long start = System.currentTimeMillis();
		ee.evolve(30, 10, new TargetFitness(0, false));
		long end = System.currentTimeMillis();
//		buffer.append(end-start);
//		buffer.append("\n");
		
		return superValue;
	}

}