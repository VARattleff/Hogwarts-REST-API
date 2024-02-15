package edu.hogwarts.studentadmin.config;

import edu.hogwarts.studentadmin.models.EmpType;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import edu.hogwarts.studentadmin.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;

    public DataSetup(HouseRepository houseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.houseRepository = houseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateHouses();
        generateStudents();
        generetaTeacher();

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

        public void generetaTeacher(){
            var gryffindor = houseRepository.findById(1L);
            var hufflepuff = houseRepository.findById(2L);
            var ravenclaw = houseRepository.findById(3L);
            var slytherin = houseRepository.findById(4L);
            if (gryffindor.isEmpty() || hufflepuff.isEmpty() || ravenclaw.isEmpty() || slytherin.isEmpty()) {
                return;
            }
            var mcGonagall = new Teacher(1L, "Minerva", "", "McGonagall", LocalDate.of(1935, 10, 4), gryffindor.get(), true, EmpType.TENURED, LocalDate.of(1956, 9, 1), null);
            var snape = new Teacher(2L, "Severus", "", "Snape", LocalDate.of(1960, 1, 9), slytherin.get(), true, EmpType.TENURED, LocalDate.of(1981, 9, 1), LocalDate.of(1998, 6, 30));
            var sprout = new Teacher(3L, "Pomona", "", "Sprout", LocalDate.of(1931, 5, 15), hufflepuff.get(), true, EmpType.TENURED, LocalDate.of(1952, 9, 1), null);
            var flitwick = new Teacher(4L, "Filius", "", "Flitwick", LocalDate.of(1930, 10, 17), ravenclaw.get(), true, EmpType.TENURED, LocalDate.of(1951, 9, 1), null);
            var hagrid = new Teacher(5L, "Rubeus", "", "Hagrid", LocalDate.of(1928, 12, 6), gryffindor.get(), false, EmpType.TENURED, LocalDate.of(1968, 9, 1), LocalDate.of(1998, 6, 30));
            var trelawney = new Teacher(6L, "Sybill", "", "Trelawney", LocalDate.of(1963, 3, 9), ravenclaw.get(), false, EmpType.TENURED, LocalDate.of(1993, 9, 1), null);
            var binns = new Teacher(7L, "Cuthbert", "", "Binns", LocalDate.of(1865, 1, 1), null, false, EmpType.TENURED, LocalDate.of(1886, 9, 1), LocalDate.of(1986, 6, 30));
            var quirrell = new Teacher(8L, "Quirinus", "", "Quirrell", LocalDate.of(1968, 9, 26), ravenclaw.get(), false, EmpType.TEMPORARY, LocalDate.of(1991, 9, 1), LocalDate.of(1992, 6, 30));

            var teachers = new Teacher[]{mcGonagall, snape, sprout, flitwick, hagrid, trelawney, binns, quirrell};
            teacherRepository.saveAll(Arrays.asList(teachers));
        }

}
