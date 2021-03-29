package sap.faculty_management_system.mockobjects.services;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.model.enums.Rank;
import sap.faculty_management_system.repository.TeacherRepository;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;
import sap.faculty_management_system.service.TeacherService;
import sap.faculty_management_system.service.TeacherServiceImpl;
import sap.faculty_management_system.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static sap.faculty_management_system.mockobjects.services.TestHelper.createListOfOneTeacher;
import static sap.faculty_management_system.mockobjects.services.TestHelper.createListOfTwoTeachers;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTests {

    private final TeacherRepository teacherRepositoryMock = Mockito.mock(TeacherRepository.class);
    private final TeacherService service = new TeacherServiceImpl(teacherRepositoryMock);

    @Test
    public void getAll_shouldReturnEmptyList() {
        //Arrange
        when(teacherRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<TeacherDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(0));

    }

    @Test
    public void getAll_shouldReturnList() {
        //Arrange
        when(teacherRepositoryMock.findAll()).thenReturn(createListOfTwoTeachers());

        //Act
        List<TeacherDTO> result = service.getAll();

        //Assert
        MatcherAssert.assertThat(result.size(), is(2));
        MatcherAssert.assertThat(result.get(0).getId(), is(createListOfTwoTeachers().get(0).getId()));
        MatcherAssert.assertThat(result.get(0).getName(), is(createListOfTwoTeachers().get(0).getName()));
        MatcherAssert.assertThat(result.get(0).getRank(), is(createListOfTwoTeachers().get(0).getRank()));

        MatcherAssert.assertThat(result.get(1).getId(), is(createListOfTwoTeachers().get(1).getId()));
        MatcherAssert.assertThat(result.get(1).getName(), is(createListOfTwoTeachers().get(1).getName()));
        MatcherAssert.assertThat(result.get(1).getRank(), is(createListOfTwoTeachers().get(1).getRank()));
    }

    @Test
    public void addOrUpdateTeacher_shouldFailIfParameterIsMissing() {

        //Assert
        MatcherAssert.assertThat(service.addOrUpdateTeacher(null).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateTeacher(null).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest(null, Rank.DOCENT)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest(null, Rank.DOCENT)).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest("", Rank.DOCENT)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest("", Rank.DOCENT)).getMessage(), is(Constants.REQUEST_IS_WRONG));

        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest("Pesho", null)).isCreated(), is(false));
        MatcherAssert.assertThat(service.addOrUpdateTeacher(new TeacherRequest("Pesho", null)).getMessage(), is(Constants.REQUEST_IS_WRONG));
    }

    @Test
    public void addOrUpdateTeacher_shouldCreateIfTeacherNotExists() {
        // Arrange
        TeacherRequest request = new TeacherRequest("Pesho", Rank.DOCENT);

        Mockito.when(teacherRepositoryMock.findAllByName(request.getName())).thenReturn(Collections.emptyList());
        // Act
        TeacherResponse response = service.addOrUpdateTeacher(request);
        // Assert
        MatcherAssert.assertThat(response.isCreated(), is(true));
        MatcherAssert.assertThat(response.getMessage().contains("was created"), is(true));
    }

    @Test
    public void addOrUpdateTeacher_shouldUpdateIfTeacherExists() {
        //Arrange
        TeacherRequest request = new TeacherRequest("Pesho", Rank.DOCENT);

        Mockito.when(teacherRepositoryMock.findAllByName(request.getName()))
                .thenReturn(createListOfOneTeacher(request.getName()));

        // Act
        TeacherResponse response = service.addOrUpdateTeacher(request);
        // Assert
        MatcherAssert.assertThat(response.isCreated(), is(true));
        MatcherAssert.assertThat(response.getMessage().contains("was updated"), is(true));
    }
}
