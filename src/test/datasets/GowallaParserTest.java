package test.datasets;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geocrowd.DatasetEnum;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.common.crowdsource.SpecializedWorker;
import org.geocrowd.common.entropy.Coord;
import org.geocrowd.common.entropy.Observation;
import org.geocrowd.datasets.synthesis.gowalla.GowallaProcessor;
import org.junit.Test;

public class GowallaParserTest {
	/**
	 * Compute location entropy.
	 */
	@Test
	public void computeLocationEntropy() {
		GowallaProcessor prep = new GowallaProcessor();
		GowallaProcessor.DATA_SET = DatasetEnum.GOWALLA;
		
		prep.readBoundary();
		prep.createGrid();
		
		// compute occurrences of each location id from Gowalla
		// each location id is associated with a grid 
		Hashtable<Integer, ArrayList<Observation>> occurances = prep
				.readRealEntropyData(GeocrowdConstants.gowallaFileName_CA);
		
		// compute entropy of each location id 
		prep.computeLocationEntropy(occurances);
		
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
		GowallaProcessor prep = new GowallaProcessor();
		GowallaProcessor.DATA_SET = DatasetEnum.GOWALLA;
		
		prep.readBoundary();
		prep.createGrid();

		// generating workers from Gowalla
		Hashtable<Date, ArrayList<SpecializedWorker>> hashTable = prep
				.generateRealWorkers(GeocrowdConstants.gowallaFileName_CA);
		prep.saveRealWorkersMax(hashTable);
		
//		prep.saveLocationDensity(prep.computeLocationDensity());
//		prep.regionEntropy();
	}
	
	
	// ------------------------------------------------------------
	/**
	 * Test extract coords.
	 */
	@Test
	public void testGenerateWorkers_privgeocrowd() {
		GowallaProcessor prep = new GowallaProcessor();
		GowallaProcessor.DATA_SET = DatasetEnum.GOWALLA;
		
		// CA: 32.1713906, -124.3041035, 41.998434033, -114.0043464333
		// Los Angeles: 33.699476,-118.570633, 34.319887,-118.192978
		// Bay area: 37.246147,-122.67746, 37.990176,-121.839752
		// SF: 37.711049,-122.51524, 37.832899,-122.360744
		// Yelp: 
		prep.filterInput("dataset/real/gowalla/gowalla_CA", 32.1713906, -124.3041035, 41.998434033, -114.0043464333);
		prep.computeBoundary();
//		prep.extractCoords("dataset/real/gowalla/gowalla_CA");
		prep.extractWorkersInstances("dataset/real/gowalla/gowalla_CA", "dataset/real/gowalla/worker/gowalla_workers", 50);
	}
	

	/**
	 * Test filter input.
	 */
	@Test
	public void testFilterInput() {
		GowallaProcessor prep = new GowallaProcessor();
		GowallaProcessor.DATA_SET = DatasetEnum.GOWALLA;
		
		prep.filterInput(GeocrowdConstants.gowallaFileName_CA, 32.1713906, -124.3041035, 41.998434033, -114.0043464333);

		prep.computeBoundary();
	}
}
