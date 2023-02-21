package org.esperis2.crudlambda.repository;

import org.esperis2.crudlambda.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
