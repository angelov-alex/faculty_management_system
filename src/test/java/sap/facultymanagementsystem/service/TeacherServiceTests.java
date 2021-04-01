package sap.facultymanagementsystem.service;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import sap.facultymanagementsystem.dto.TeacherDTO;
import sap.facultymanagementsystem.model.enums.Rank;
import sap.facultymanagementsystem.repository.TeacherRepository;
import sap.facultymanagementsystem.request.TeacherRequest;
import sap.facultymanagementsystem.response.TeacherResponse;
import sap.facultymanagementsystem.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static sap.facultymanagementsystem.service.TestHelper.createListOfOneTeacher;
import static sap.facultymanagementsystem.service.TestHelper.createListOfTwoTeachers;

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
        when(teacherRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(createListOfTwoTeachers());

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
