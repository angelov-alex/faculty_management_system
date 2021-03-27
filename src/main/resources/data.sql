-- noinspection SqlNoDataSourceInspectionForFile

-- the file must have name data.sql to automatically load the input on start

-- add credits values
INSERT INTO SAP.CREDIT (UUID, CREDIT_HOURS)
VALUES ('297e6814787069b20178706cea650001', 1.0);
INSERT INTO SAP.CREDIT (UUID, CREDIT_HOURS)
VALUES ('297e6814787069b20178706cea650002', 2.0);
INSERT INTO SAP.CREDIT (UUID, CREDIT_HOURS)
VALUES ('297e6814787069b20178706cea650003', 1.5);

-- add teacher values
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650011', 'Pesho', 'PROFESSOR');
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650021', 'Nade', 'DOCENT');
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650031', 'Karamfil', 'ASSISTANT');
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650041', 'Bozman', 'ASSISTANT');
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650051', 'Gosho', 'ASSISTANT');
INSERT INTO SAP.TEACHER (UUID, NAME, RANK)
VALUES ('297e6814787069b20178706cea650061', 'Tosho', 'ASSISTANT');

-- add course values
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650111', 'Math', '297e6814787069b20178706cea650011',
        '297e6814787069b20178706cea650003');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650211', 'Lathin', '297e6814787069b20178706cea650021',
        '297e6814787069b20178706cea650002');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650311', 'English', '297e6814787069b20178706cea650021',
        '297e6814787069b20178706cea650001');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650411', 'German', '297e6814787069b20178706cea650041',
        '297e6814787069b20178706cea650003');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650511', 'Spanish', '297e6814787069b20178706cea650061',
        '297e6814787069b20178706cea650002');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650611', 'Medicine', '297e6814787069b20178706cea650061',
        '297e6814787069b20178706cea650003');
INSERT INTO SAP.COURSE (UUID, NAME, TEACHER_UUID, CREDIT_UUID)
VALUES ('297e6814787069b20178706cea650711', 'Football', '297e6814787069b20178706cea650051',
        '297e6814787069b20178706cea650001');

-- add student values
INSERT INTO SAP.STUDENT (UUID, NAME, ACADEMIC_YEAR)
VALUES ('297e6814787069b20178706cea651111', 'Alex', 'ONE');
INSERT INTO SAP.STUDENT (UUID, NAME, ACADEMIC_YEAR)
VALUES ('297e6814787069b20178706cea652111', 'Denski', 'FOUR');
INSERT INTO SAP.STUDENT (UUID, NAME, ACADEMIC_YEAR)
VALUES ('297e6814787069b20178706cea653111', 'Kalina', 'TWO');
INSERT INTO SAP.STUDENT (UUID, NAME, ACADEMIC_YEAR)
VALUES ('297e6814787069b20178706cea654111', 'Mario', 'TWO');
INSERT INTO SAP.STUDENT (UUID, NAME, ACADEMIC_YEAR)
VALUES ('297e6814787069b20178706cea655111', 'BenJ', 'THREE');

-- enroll students in courses
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea651111', '297e6814787069b20178706cea650111');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea651111', '297e6814787069b20178706cea650211');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea651111', '297e6814787069b20178706cea650311');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea652111', '297e6814787069b20178706cea650311');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea652111', '297e6814787069b20178706cea650211');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea653111', '297e6814787069b20178706cea650111');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea653111', '297e6814787069b20178706cea650311');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea654111', '297e6814787069b20178706cea650111');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea655111', '297e6814787069b20178706cea650211');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea655111', '297e6814787069b20178706cea650311');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea655111', '297e6814787069b20178706cea650611');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea655111', '297e6814787069b20178706cea650411');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea655111', '297e6814787069b20178706cea650511');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea653111', '297e6814787069b20178706cea650611');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea653111', '297e6814787069b20178706cea650511');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea651111', '297e6814787069b20178706cea650711');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea652111', '297e6814787069b20178706cea650411');
INSERT INTO SAP.STUDENT_COURSE (STUDENT_UUID, COURSE_UUID)
VALUES ('297e6814787069b20178706cea652111', '297e6814787069b20178706cea650511');