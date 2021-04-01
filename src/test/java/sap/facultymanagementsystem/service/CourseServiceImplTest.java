package sap.facultymanagementsystem.service;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sap.facultymanagementsystem.dto.CourseDTO;
import sap.facultymanagementsystem.repository.CourseRepository;
import sap.facultymanagementsystem.repository.CreditRepository;
import sap.facultymanagementsystem.repository.StudentRepository;
import sap.facultymanagementsystem.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceImplTest {

    private final CourseRepository courseRepositoryMock = Mockito.mock(CourseRepository.class);
    private final StudentRepository studentRepositoryMock = Mockito.mock(StudentRepository.class);
    private final TeacherRepository teacherRepositoryMock = Mockito.mock(TeacherRepository.class);
    private final CreditRepository creditRepositoryMock = Mockito.mock(CreditRepository.class);

    private final CourseService service = new CourseServiceImpl(courseRepositoryMock, studentRepositoryMock
            , teacherRepositoryMock, creditRepositoryMock);

    @Test
    public void getAll_shouldReturnEmptyList() {
        //Arrange
        when(courseRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        when(studentRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        when(teacherRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        when(creditRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<CourseDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(0));
    }

    @Test
    public void testGetTopCourses() {
    }

    @Test
    public void enroll() {
    }

    @Test
    public void delist() {
    }
}