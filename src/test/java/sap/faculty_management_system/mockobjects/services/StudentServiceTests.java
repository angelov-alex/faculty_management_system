package sap.faculty_management_system.mockobjects.services;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.service.StudentService;
import sap.faculty_management_system.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static sap.faculty_management_system.mockobjects.services.TestHelper.createListOfTwoStudents;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTests {

    private final StudentRepository studentRepositoryMock = Mockito.mock(StudentRepository.class);
    private final StudentService service = new StudentServiceImpl(studentRepositoryMock);

    @Test
    public void getAll_shouldReturnEmptyList() {
        //Arrange
        when(studentRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<StudentDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(0));

    }

    @Test
    public void getAll_shouldReturnList() {
        //Arrange
        when(studentRepositoryMock.findAll()).thenReturn(createListOfTwoStudents());

        //Act
        List<StudentDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(2));
        MatcherAssert.assertThat(result.get(0).getId(), is(createListOfTwoStudents().get(0).getId()));
        MatcherAssert.assertThat(result.get(0).getName(), is(createListOfTwoStudents().get(0).getName()));
        MatcherAssert.assertThat(result.get(0).getAcademicYear(), is(createListOfTwoStudents().get(0).getAcademicYear()));

        MatcherAssert.assertThat(result.get(1).getId(), is(createListOfTwoStudents().get(1).getId()));
        MatcherAssert.assertThat(result.get(1).getName(), is(createListOfTwoStudents().get(1).getName()));
        MatcherAssert.assertThat(result.get(1).getAcademicYear(), is(createListOfTwoStudents().get(1).getAcademicYear()));
    }
}
