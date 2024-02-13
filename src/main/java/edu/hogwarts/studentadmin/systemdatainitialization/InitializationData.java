package edu.hogwarts.studentadmin.systemdatainitialization;

import edu.hogwarts.studentadmin.modals.Student;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class InitializationData implements CommandLineRunner {
    private final StudentRepository studentRepository;
    private final HouseRepository houseRepository;

    public InitializationData(HouseRepository houseRepository, StudentRepository studentRepository) {
        this.houseRepository = houseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        generateStudents();
    }

    private void generateStudents() {
        var gryffindor = houseRepository.findById(1L);
        var hufflepuff = houseRepository.findById(2L);
        var ravenclaw = houseRepository.findById(3L);
        var slytherin = houseRepository.findById(4L);
        if (gryffindor.isEmpty() || hufflepuff.isEmpty() || ravenclaw.isEmpty() || slytherin.isEmpty()) {
            return;
        }
        var harry = new Student(1L, "Harry", "James", "Potter", LocalDate.of(1980, 7, 31), gryffindor.get(), false, 1991, 1998, true);
        var hermione = new Student(2L, "Hermione", "Jean", "Granger", LocalDate.of(1979, 9, 19), gryffindor.get(), true, 1991, 1998, true);
        var ron = new Student(3L, "Ronald", "Bilius", "Weasley", LocalDate.of(1980, 3, 1), gryffindor.get(), false, 1991, 1998, true);
        var neville = new Student(4L, "Neville", "Frank", "Longbottom", LocalDate.of(1980, 7, 30), gryffindor.get(), false, 1991, 1998, true);
        var luna = new Student(5L, "Luna", "", "Lovegood", LocalDate.of(1981, 2, 13), ravenclaw.get(), false, 1992, 1999, true);
        var draco = new Student(6L, "Draco", "Lucius", "Malfoy", LocalDate.of(1980, 6, 5), slytherin.get(), false, 1991, 1998, true);
        var cedric = new Student(7L, "Cedric", "", "Diggory", LocalDate.of(1977, 9, 1), hufflepuff.get(), true, 1993, 1995, true);
        var cho = new Student(8L, "Cho", "", "Chang", LocalDate.of(1979, 9, 14), ravenclaw.get(), false, 1992, 1999, true);
        var ginny = new Student(9L, "Ginevra", "Molly", "Weasley", LocalDate.of(1981, 8, 11), gryffindor.get(), false, 1992, 1999, true);
        var seamus = new Student(10L, "Seamus", "", "Finnigan", LocalDate.of(1980, 3, 1), gryffindor.get(), false, 1991, 1998, true);
        var dean = new Student(11L, "Dean", "", "Thomas", LocalDate.of(1980, 1, 1), gryffindor.get(), false, 1991, 1998, true);
        var parvati = new Student(12L, "Parvati", "", "Patil", LocalDate.of(1980, 1, 1), gryffindor.get(), false, 1991, 1998, true);

        var students = new Student[]{harry, hermione, ron, neville, luna, draco, cedric, cho, ginny, seamus, dean, parvati};
        studentRepository.saveAll(Arrays.asList(students));
    }
}
