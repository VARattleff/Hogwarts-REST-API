package edu.hogwarts.studentadmin.config;

import edu.hogwarts.studentadmin.modals.House;
import edu.hogwarts.studentadmin.modals.Student;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class DataSetup implements ApplicationRunner {
    private final StudentRepository studentRepository;
    private final HouseRepository houseRepository;


    public DataSetup(HouseRepository houseRepository, StudentRepository studentRepository) {
        this.houseRepository = houseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateHouses();
        generateStudents();

    }

    public void generateHouses(){
        var gryffindorColors = new ArrayList<String>();
        gryffindorColors.add("Scarlet");
        gryffindorColors.add("Gold");

        var hufflepuffColors = new ArrayList<String>();
        hufflepuffColors.add("Yellow");
        hufflepuffColors.add("Black");

        var ravenclawColors = new ArrayList<String>();
        ravenclawColors.add("Blue");
        ravenclawColors.add("Bronze");

        var slytherinColors = new ArrayList<String>();
        slytherinColors.add("Green");
        slytherinColors.add("Silver");

        var gryffindor = new House(1L, "Gryffindor", "Godric Gryffindor", gryffindorColors);
        var hufflepuff = new House(2L, "Hufflepuff", "Helga Hufflepuff", hufflepuffColors);
        var ravenclaw = new House(3L, "Ravenclaw", "Rowena Ravenclaw", ravenclawColors);
        var slytherin = new House(4L, "Slytherin", "Salazar Slytherin", slytherinColors);


        houseRepository.save(gryffindor);
        houseRepository.save(hufflepuff);
        houseRepository.save(ravenclaw);
        houseRepository.save(slytherin);
    }

    private void generateStudents() {
        var gryffindor = houseRepository.findById(1L).orElse(null);
        var hufflepuff = houseRepository.findById(2L).orElse(null);
        var ravenclaw = houseRepository.findById(3L).orElse(null);
        var slytherin = houseRepository.findById(4L).orElse(null);
        if (gryffindor == null || hufflepuff == null || ravenclaw == null || slytherin == null) {
            return;
        }
        var harry = new Student(1L, "Harry", "James", "Potter", LocalDate.of(1980, 7, 31), gryffindor, false, 1991, 1998, true);
        var hermione = new Student(2L, "Hermione", "Jean", "Granger", LocalDate.of(1979, 9, 19), gryffindor, true, 1991, 1998, true);
        var ron = new Student(3L, "Ronald", "Bilius", "Weasley", LocalDate.of(1980, 3, 1), gryffindor, false, 1991, 1998, true);
        var neville = new Student(4L, "Neville", "Frank", "Longbottom", LocalDate.of(1980, 7, 30), gryffindor, false, 1991, 1998, true);
        var luna = new Student(5L, "Luna", "", "Lovegood", LocalDate.of(1981, 2, 13), ravenclaw, false, 1992, 1999, true);
        var draco = new Student(6L, "Draco", "Lucius", "Malfoy", LocalDate.of(1980, 6, 5), slytherin, false, 1991, 1998, true);
        var cedric = new Student(7L, "Cedric", "", "Diggory", LocalDate.of(1977, 9, 1), hufflepuff, true, 1993, 1995, true);
        var cho = new Student(8L, "Cho", "", "Chang", LocalDate.of(1979, 9, 14), ravenclaw, false, 1992, 1999, true);
        var ginny = new Student(9L, "Ginevra", "Molly", "Weasley", LocalDate.of(1981, 8, 11), gryffindor, false, 1992, 1999, true);
        var seamus = new Student(10L, "Seamus", "", "Finnigan", LocalDate.of(1980, 3, 1), gryffindor, false, 1991, 1998, true);
        var dean = new Student(11L, "Dean", "", "Thomas", LocalDate.of(1980, 1, 1), gryffindor, false, 1991, 1998, true);
        var parvati = new Student(12L, "Parvati", "", "Patil", LocalDate.of(1980, 1, 1), gryffindor, false, 1991, 1998, true);

        var students = new Student[]{harry, hermione, ron, neville, luna, draco, cedric, cho, ginny, seamus, dean, parvati};
        studentRepository.saveAll(Arrays.asList(students));
    }


}
