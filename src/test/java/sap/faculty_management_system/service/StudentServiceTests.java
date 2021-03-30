package sap.faculty_management_system.service;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.model.enums.AcademicYear;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.request.StudentRequest;
import sap.faculty_management_system.response.StudentResponse;
import sap.faculty_management_system.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static sap.faculty_management_system.service.TestHelper.createListOfOneStudent;
import static sap.faculty_management_system.service.TestHelper.createListOfTwoStudents;

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
        when(studentRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(createListOfTwoStudents());

        //Act
        List<StudentDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(2));
        MatcherAssert.assertThat(result.get(0).getId(), is(createListOfTwoStudents().get(0).getId()));
        MatcherAssert.assertThat(result.get(0).getName(), is(createListOfTwoStudents().get(0).getName()));
        MatcherAssert.assertThat(result.get(0).getAcademicYear(), is(createListOfTwoStudents().get(0).getAcademicYear().getValue()));

        MatcherAssert.assertThat(result.get(1).getId(), is(createListOfTwoStudents().get(1).getId()));
        MatcherAssert.assertThat(result.get(1).getName(), is(createListOfTwoStudents().get(1).getName()));
        MatcherAssert.assertThat(result.get(1).getAcademicYear(), is(createListOfTwoStudents().get(1).getAcademicYear().getValue()));
    }

    @Test
    public void addOrUpdateStudent_shouldFailIfParameterIsMissing() {

        //Assert
        MatcherAssert.assertThat(service.addOrUpdateStudent(null).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateStudent(null).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest(null, AcademicYear.TWO)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest(null, AcademicYear.TWO)).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest("", AcademicYear.TWO)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest("", AcademicYear.TWO)).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest("Michael", null)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateStudent(new StudentRequest("Michael", null)).getMessage(), is(Constants.REQUEST_IS_WRONG));
    }

    @Test
    public void addOrUpdateStudent_shouldCreateIfStudentNotExists() {
        // Arrange
        StudentRequest request = new StudentRequest("Misho", AcademicYear.THREE);

        Mockito.when(studentRepositoryMock.findAllByName(request.getName())).thenReturn(Collections.emptyList());
        // Act
        StudentResponse response = service.addOrUpdateStudent(request);
        // Assert
        MatcherAssert.assertThat(response.isCreated(), is(true));
        MatcherAssert.assertThat(response.getMessage().contains("was created successfully"), is(true));
    }

    @Test
    public void addOrUpdateStudent_shouldUpdateIfStudentExists() {
        //Arrange
        StudentRequest request = new StudentRequest("Blago", AcademicYear.FOUR);

        Mockito.when(studentRepositoryMock.findAllByName(request.getName()))
                .thenReturn(createListOfOneStudent(request.getName()));

        // Act
        StudentResponse response = service.addOrUpdateStudent(request);
        // Assert
        MatcherAssert.assertThat(response.isCreated(), is(true));
        MatcherAssert.assertThat(response.getMessage().contains("was updated successfully"), is(true));
    }

}
