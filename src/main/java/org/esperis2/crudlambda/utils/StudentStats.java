package org.esperis2.crudlambda.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.esperis2.crudlambda.model.Student;

/**
 * - Media voti totale
 * - Media voti per città/nazionalità
 * - Per ogni città, studente migliore (media voti più alta)
 */
public class StudentStats {
	private float average;
	
	private Map<String, Double> averageByCity;
	
	private Map<String, Double> averageByNationality;
	
	private Map<String, Student> bestByCity;
	
	public StudentStats(List<Student> students) {
		/*
			stream() -> Student s1, Student s2, Student s3, etc..
			map()    ->	Float 23, Float 25, Float 18, etc.
			reduce() -> 66 / 3 = 22
		*/
//		this.average = 
//			students.stream()
//				.map(s -> s.getAverageScore())
//				.reduce(Float::sum)
//				.orElse(0f) / students.size();
		
		this.average = getAverage(students);
		this.averageByCity = getAverageByCriteria(students, s -> s.getCityOfBirth());
		this.averageByNationality = getAverageByCriteria(students, s -> s.getNationality());
		this.bestByCity = getBestByCriteria(students, s -> s.getCityOfBirth());
	}
	
	private float getAverage(List<Student> students) {
		return students.stream()
				.collect(Collectors.averagingDouble(s -> s.getAverageScore()))
				.floatValue();
	}
	
	private Map<String, Student> getBestByCriteria(List<Student> students, Function<Student, String> classifier) {
		Map<String, List<Student>> byCriteria = 
			students.stream().collect(Collectors.groupingBy(classifier));
		
		Map<String, Student> best = new HashMap<>();
		for (String key : byCriteria.keySet()) {
			List<Student> studentsByCriteria = byCriteria.get(key);
			
			Student bestStudent =
				studentsByCriteria.stream()
					.sorted((a, b) -> Float.compare(b.getAverageScore(), a.getAverageScore()))
					.findFirst().get();
			
			best.put(key, bestStudent);
		}
		
		return best;
	}
	
	private Map<String, Double> getAverageByCriteria(List<Student> students, Function<Student, String> classifier) {
		Map<String, Double> byCriteria = students.stream()
				.collect(Collectors.groupingBy(classifier, Collectors.averagingDouble(s -> s.getAverageScore())));
		return byCriteria;
	}
	
//	private Map<String, Float> getAverageByCriteria(List<Student> students, Function<Student, String> classifier) {
//		Map<String, List<Student>> byCriteria = 
//			students.stream().collect(Collectors.groupingBy(classifier));
//		
//		Map<String, Float> average = new HashMap<>();
//		for (String key : byCriteria.keySet()) {
//			float averageCriteria = getAverage(byCriteria.get(key));
//				
//			average.put(
//				key, 
//				averageCriteria
//			);
//		}
//		return average;
//	}
	
//	private Map<String, Float> getAverageByNationality(List<Student> students) {
//		Map<String, List<Student>> studentsByCity = 
//			students.stream().collect(Collectors.groupingBy(s -> s.getNationality()));
//		
//		
//		// groupingBy(Function<? super Student, ? extends String> classifier)
//		Map<String, Float> averageByCity = new HashMap<>();
//		for (String city : studentsByCity.keySet()) {
//			float averageCity = getAverage(studentsByCity.get(city));
//				
//			averageByCity.put(
//				city, 
//				averageCity
//			);
//		}
//		return averageByCity;
//	}
//
//	
//	private Map<String, Float> getAverageByCity(List<Student> students) {
//		Map<String, List<Student>> studentsByCity = 
//			students.stream().collect(Collectors.groupingBy(s -> s.getCityOfBirth()));
//		
//		Map<String, Float> averageByCity = new HashMap<>();
//		for (String city : studentsByCity.keySet()) {
//			float averageCity = getAverage(studentsByCity.get(city));
//				
//			averageByCity.put(
//				city, 
//				averageCity
//			);
//		}
//		return averageByCity;
//	}

	public float getAverage() {
		return average;
	}

	public Map<String, Double> getAverageByCity() {
		return averageByCity;
	}

	public Map<String, Double> getAverageByNationality() {
		return averageByNationality;
	}

	public Map<String, Student> getBestByCity() {
		return bestByCity;
	}
	
	
}
