package test.datasets;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geocrowd.common.distribution.TaskCategoryDistributionEnum;
import org.geocrowd.common.distribution.TaskTypeEnum;
import org.geocrowd.common.distribution.WorkerTypeEnum;
import org.geocrowd.common.workertask.GenericWorker;
import org.geocrowd.common.entropy.Coord;
import org.geocrowd.common.entropy.EntropyUtility;
import org.geocrowd.common.entropy.Observation;
import org.geocrowd.GowallaConstants;
import org.geocrowd.synthesis.GowallaProcessor;
import org.junit.Test;

public class GowallaParserTest {
	/**
	 * Compute location entropy.
	 */
	@Test
	public void computeLocationEntropy() {
		GowallaProcessor prep = new GowallaProcessor(20,
                                                     WorkerTypeEnum.EXPERT, TaskTypeEnum.EXPERT_TASK,
                                                     TaskCategoryDistributionEnum.RANDOM);
		
		// compute occurrences of each location id from Gowalla
		// each location id is associated with a grid 
		Hashtable<Integer, ArrayList<Observation>> occurances = prep
				.readEntropyData(GowallaConstants.gowallaFileName_CA);
		
		// compute entropy of each location id 
		EntropyUtility.computeLocationEntropy(occurances, 
				GowallaConstants.gowallaEntropyFileName);
		
		// compute index (row, col) of each location id
		prep.debug();
		Hashtable<Integer, Coord> gridIndices = prep.locIdToCellIndices();
		prep.saveLocationEntropy(gridIndices);
	}

	/**
	 * Generate workers.  
	 */
	@Test
	public void generateWorkers_irain() {
		GowallaProcessor prep = new GowallaProcessor(28,
                                                     WorkerTypeEnum.GENERIC_WORKER, TaskTypeEnum.SENSING_TASK,
                                                     TaskCategoryDistributionEnum.RANDOM);

		// generating workers from Gowalla
		Hashtable<Date, ArrayList<GenericWorker>> hashTable = prep
				.generateWorkers(GowallaConstants.gowallaFileName_CA);
		prep.saveWorkersMax(hashTable);
		
//		prep.saveLocationDensity(prep.computeLocationDensity());
//		prep.regionEntropy();
	}
	
	
	// ------------------------------------------------------------
	/**
	 * Test extract coords.
	 */
	@Test
	public void testGenerateWorkers_privgeocrowd() {
		GowallaProcessor prep = new GowallaProcessor(20,
                                                     WorkerTypeEnum.EXPERT, TaskTypeEnum.SENSING_TASK,
                                                     TaskCategoryDistributionEnum.RANDOM);
		
		// CA: 32.1713906, -124.3041035, 41.998434033, -114.0043464333
		// Los Angeles: 33.699476,-118.570633, 34.319887,-118.192978
		// Bay area: 37.246147,-122.67746, 37.990176,-121.839752
		// SF: 37.711049,-122.51524, 37.832899,-122.360744
		// Yelp: 
		prep.filterInput("dataset/real/gowalla/gowalla_CA", 32.1713906, -124.3041035, 41.998434033, -114.0043464333);
		prep.computeBoundary();
//		prep.extractCoords("dataset/real/gowalla/gowalla_CA", 100);
		prep.extractWorkersInstances("dataset/real/gowalla/gowalla_CA", "dataset/real/gowalla/worker/workers", 50);
	}
	

	/**
	 * Test filter input.
	 */
	@Test
	public void testFilterInput() {
		GowallaProcessor prep = new GowallaProcessor(20,
                                                     WorkerTypeEnum.EXPERT, TaskTypeEnum.EXPERT_TASK,
                                                     TaskCategoryDistributionEnum.RANDOM);
		
		prep.filterInput(GowallaConstants.gowallaFileName_CA, 32.1713906, -124.3041035, 41.998434033, -114.0043464333);
		
//		prep.filterInput("dataset/real/gowalla/gowalla_sample", 7.841649, -176.037659, 33.575540, -118.176210);
//		prep.extractCoords("dataset/real/gowalla/gowalla_sample", 100);
		prep.computeBoundary();
	}
}