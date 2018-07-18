package org.aksw.limes.core.ml.algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.aksw.limes.core.evaluation.evaluationDataLoader.DataSetChooser;
import org.aksw.limes.core.evaluation.evaluationDataLoader.EvaluationData;
import org.aksw.limes.core.execution.engine.SimpleExecutionEngine;
import org.aksw.limes.core.execution.planning.planner.CanonicalPlanner;
import org.aksw.limes.core.io.ls.LinkSpecification;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.measures.mapper.FuzzyOperators.YagerSetOperations;
import org.aksw.limes.core.ml.algorithm.fptld.fitness.SimFuzzyRMSE;

public class Test {
	public static final double[] testValues = new double[] { 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 2, 3,
			4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };

	public static void main(String[] args) throws IOException {
		String[] dataSetNames = new String[] {
				// "restaurantsfixed",
				// "person1", "person2", "ABTBUY", "AMAZONGOOGLEPRODUCTS", "DBPLINKEDMDB"
				"DBLPSCHOLAR"
				};
		for (String d : dataSetNames) {
			EvaluationData eval = DataSetChooser.getData(d);
			System.out.println(d);
			switch(d){
			case "restaurantsfixed":
				ArrayList<Double> resValues = new ArrayList<Double>();
				LinkSpecification left = new LinkSpecification("qgrams(x.http://www.okkam.org/ontology_restaurant1.owl#phone_number,y.http://www.okkam.org/ontology_restaurant2.owl#phone_number)", 0.43046721000000016);
				LinkSpecification right = new LinkSpecification(
						"LUKTCO(LUKTCO( jaccard(x.http://www.okkam.org/ontology_restaurant1.owl#name,y.http://www.okkam.org/ontology_restaurant2.owl#name)|0.47829690000000014, jaccard(x.http://www.okkam.org/ontology_restaurant1.owl#name,y.http://www.okkam.org/ontology_restaurant2.owl#has_category)|1.0)|0.0, jaccard(x.http://www.okkam.org/ontology_restaurant1.owl#name,y.http://www.okkam.org/ontology_restaurant2.owl#has_category)|1.0) ",
						0.0);
                CanonicalPlanner dp = new CanonicalPlanner();
                SimpleExecutionEngine ee = new SimpleExecutionEngine(eval.getSourceCache(),
                eval.getTargetCache(), "?x", "?y");
                AMapping leftM = ee.execute(left, dp);
                AMapping rightM = ee.execute(right, dp);
				for(double p: testValues){
					AMapping yager = YagerSetOperations.INSTANCE.union(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "person1":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification(
						"qgrams(x.http://www.okkam.org/ontology_person1.owl#soc_sec_id,y.http://www.okkam.org/ontology_person2.owl#soc_sec_id)",
						0.6634204312890623);
				right = new LinkSpecification(
						"jaccard(x.http://www.okkam.org/ontology_person1.owl#phone_numer,y.http://www.okkam.org/ontology_person2.owl#phone_numer)",
						1.0);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.union(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "person2":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification(
						"qgrams(x.http://www.okkam.org/ontology_person1.owl#has_address,y.http://www.okkam.org/ontology_person2.owl#has_address)",
						0.81);
				right = new LinkSpecification(
						"jaccard(x.http://www.okkam.org/ontology_person1.owl#soc_sec_id,y.http://www.okkam.org/ontology_person2.owl#soc_sec_id)",
						1.0);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.union(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "ABTBUY":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification("qgrams(x.name,y.name)", 0.41812033521917696);
				right = new LinkSpecification("cosine(x.description,y.description)", 0.41812033521917696);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.union(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "AMAZONGOOGLEPRODUCTS":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification("cosine(x.description,y.description)", 0.41812033521917696);
				right = new LinkSpecification("cosine(x.description,y.description)", 0.41812033521917696);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.intersection(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "DBPLINKEDMDB":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification("cosine(x.title,y.title)", 0.7290000000000001);
				right = new LinkSpecification("trigrams(x.title,y.title)", 0.7290000000000001);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.intersection(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			case "DBLPSCHOLAR":
				resValues = new ArrayList<Double>();
				left = new LinkSpecification("qgrams(x.title,y.title)", 0.43046721000000016);
				right = new LinkSpecification("qgrams(x.title,y.title)", 0.43046721000000016);
				dp = new CanonicalPlanner();
				ee = new SimpleExecutionEngine(eval.getSourceCache(), eval.getTargetCache(), "?x", "?y");
				leftM = ee.execute(left, dp);
				rightM = ee.execute(right, dp);
				for (double p : testValues) {
					AMapping yager = YagerSetOperations.INSTANCE.intersection(leftM, rightM, p);
					double sim = SimFuzzyRMSE.INSTANCE.getSimilarity(yager, eval.getReferenceMapping());
					System.out.println(sim);
					resValues.add(sim);
				}
				write(Paths.get(args[0] + "/" + d), resValues);
				break;
			}
		}
	}

	private static void write(Path path, ArrayList<Double> resValues) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write("p,sim\n");
			for (int i = 0; i < testValues.length; i++) {
				writer.write(testValues[i] + "," + resValues.get(i) + "\n");
			}
		}
	}
}
