package com.project.co.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.project.co.dto.*;
import com.project.co.entity.CourseWeight;
import com.project.co.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.co.entity.Examsheet;
import com.project.co.entity.StudentNotification;
import com.project.co.repo.EmailMessage;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin (origins = "http://localhost:3000")

public class controller {

    @Autowired
    private Courseservice courseservice;

    @Autowired
    private Departmentservice departmentservice;

    @Autowired
    private Adminservice adminservice;

    @Autowired
    private Studentservice studentservice;

    @Autowired
    private S_resultservice s_resultservice;

    @Autowired
    private S_attendservice s_attendservice;

    @Autowired
    private Eventservice eventservice;

    @Autowired
    private S_rankservice s_rankservice;

    @Autowired
    private EventAdminservice eventAdminservice;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminProfileService adminProfileService;

    @Autowired
    private Assignmentservice assignmentservice;

    @Autowired
    private LectureNoteServices lectureNoteservice;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private Quizservice quizservice;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StudentNotificationService studentnotificationService;

    @Autowired
    private GoogleAPIService googleApiService;

    @Autowired
    private Examsheetservice examsheetservice;

    @Autowired
    private ExamMarkTotalService examMarkTotalService;

    @Autowired
    private AssignmentMarkTotalService assignmentMarkTotalService;

    @Autowired
    private QuizMarkTotalService quizMarkTotalService;

    @Autowired
    private CombinedMarksService combinedMarksService;

    @Autowired
    private CourseWeightService courseWeightService;

    // save values in table
    @PostMapping("/savecourse")
    public Coursedto saveuser(@RequestBody Coursedto coursedto) {
        return courseservice.saveuser(coursedto);
    }

    @PostMapping("/savedepartment")
    public Departmentdto saveuser(@RequestBody Departmentdto departmentdto) {
        return departmentservice.saveuser(departmentdto);
    }

    @PostMapping("/savestudent")
    public Studentdto saveuser(@RequestBody Studentdto studentdto) {
        return studentservice.saveuser(studentdto);
    }

    @PostMapping("/saves_result")
    public S_resultrdto saveuser(@RequestBody S_resultrdto s_resultrdto) {
        return s_resultservice.saveuser(s_resultrdto);
    }

    @PostMapping("/saves_attend")
    public S_attenddto saveuser(@RequestBody S_attenddto s_attenddto) {
        return s_attendservice.saveuser(s_attenddto);
    }

    @PostMapping("/saveevents")
    public Eventdto saveEvent(@RequestBody Eventdto eventdto) {
        return eventservice.saveEvent(eventdto);
    }

    @PostMapping("/saveeventsadmin")
    public EventAdmindto saveEvent(@RequestBody EventAdmindto eventadmindto) {
        return eventAdminservice.saveEvent(eventadmindto);
    }

    @PostMapping("/saveUser")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/saveAdminProfile")
    public AdminProfileDto saveAdminProfile(@RequestBody AdminProfileDto adminProfileDto) {
        return adminProfileService.saveAdminProfile(adminProfileDto);
    }

    @PostMapping("/enroll")
    public Studentdto enrollStudent(@RequestBody Enrollmentdto enrollmentDTO) {
        return enrollmentService.enrollStudent(enrollmentDTO);
    }

    @PostMapping("/saveQuiz")
    public Quizdto saveQuiz(@RequestBody Quizdto quizdto) {
        return quizservice.saveQuiz(quizdto);
    }

    // File handling part
    @PostMapping("/saveAssignment")
    public void saveAssignment(@RequestParam("files") MultipartFile[] files, @RequestParam("assignmentName") String assignmentName, @RequestParam("deadline") String deadline, @RequestParam("message") String message, @RequestParam("adminId") String adminID, @RequestParam("courseId") String courseId) {

        String Assignmentdir = "Assignment Folder";

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No files uploaded.");
        }

        Arrays.stream(files).forEach(file -> {

            try {
                // Save the file using your service
                assignmentservice.saveAssignment(Assignmentdir, assignmentName, deadline, message, adminID, courseId, file);

            } catch (IOException e) {

            }
        });
    }

    // File handling part
    @PostMapping("/saveStdAssignment")
    public void saveStdAssignment(@RequestParam("files") MultipartFile[] files, @RequestParam("assignmentName") String assignmentName, @RequestParam("studentId") String studentID, @RequestParam("courseId") String courseId, @RequestParam("assignmentId") Long assignmentId) {

        String AssignmentStddir = "Assignment Student Folder";

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No files uploaded.");
        }

        Arrays.stream(files).forEach(file -> {

            try {
                // Save the file using your service
                studentAssignmentService.saveStdAssignment(AssignmentStddir, assignmentName, studentID, courseId, assignmentId, file);

            } catch (IOException e) {

            }
        });
    }

    @DeleteMapping("/delete")
    public void deleteAssignment(
            @RequestParam("assignmentName") String assignmentName,
            @RequestParam("adminId") String adminId,
            @RequestParam("courseId") String courseId) {

        // Call the service method
        assignmentservice.deleteAssignment(assignmentName, adminId, courseId);
    }

    @DeleteMapping("/studentDelete")
    public void deleteStdAssignment(
            @RequestParam("assignmentName") String assignmentName,
            @RequestParam("studentId") String studentId,
            @RequestParam("courseId") String courseId) {

        // Call the service method
        studentAssignmentService.deleteStdAssignment(assignmentName, studentId, courseId);
    }

    @PostMapping("/edit")
    public ResponseEntity<Assignmentdto> editAssignment(@RequestBody EditAssignmentRequestdto request) {
        try {
            // Retrieve and process the request data
            Assignmentdto assignment = assignmentservice.getAssignmentDetails(
                    request.getAssignmentName(), request.getAdminId(), request.getCourseId(), request.getDeadLine(), request.getMessage(), request.getFilePath()
            );
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/editStudentAssignment")
    public ResponseEntity<StudentAssignmentDto> editStdAssignment(@RequestBody EditStdAssignmentRequestDto request) {
        try {
            // Retrieve and process the request data
            StudentAssignmentDto assignment = studentAssignmentService.getStdAssignmentDetails(
                    request.getAssignmentName(), request.getStudentId(), request.getCourseId(), request.getFilePath()
            );
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveedit")
    public ResponseEntity<?> updateAssignment(@RequestBody EditAssignmentRequestdto request) {
        try {
            assignmentservice.updateAssignment(request.getAssignmentName(), request.getDeadLine(), request.getAdminId(), request.getCourseId(), request.getMessage());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/saveStdEdit")
    public ResponseEntity<?> updateStdAssignment(@RequestBody EditStdAssignmentRequestDto request) {
        try {
            studentAssignmentService.updateStdAssignment(request.getAssignmentName(), request.getStudentId(), request.getCourseId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // File handling part
    @PostMapping("/saveLectureNote")
    public void saveLectureNote(@RequestParam("files") MultipartFile[] files, @RequestParam("lectureNoteName") String lectureNoteName, @RequestParam("publishDate") String publishDate, @RequestParam("message") String message, @RequestParam("adminId") String adminID, @RequestParam("courseId") String courseId) {
        String LectureNotedir = "Lecture Notes Folder";

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No files uploaded.");
        }

        Arrays.stream(files).forEach(file -> {

            try {
                // Save the file using your service
                lectureNoteservice.saveLectureNote(LectureNotedir, lectureNoteName, publishDate, message, adminID, courseId, file);

            } catch (IOException e) {

            }
        });
    }


    // Get the file

    private final Path rootLocationLectureNote = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Lecture Notes");

    @GetMapping("/getLectureNoteFile/{adminId}/{courseId}")
    public ResponseEntity<Resource> serveLectureNoteFile(@PathVariable String adminId, @PathVariable String courseId, @RequestParam String fileName) {
        try {
            // Construct the file path based on LectureNotedir, courseId, and fileName
            Path targetDir = rootLocationLectureNote.resolve("Lecture Notes Folder");  // Adjust Assignmentdir as needed
            Path file = targetDir.resolve(courseId).resolve(fileName);  // Resolve courseId and fileName

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get file details
    @GetMapping("/allLectureNote/{adminId}/{courseId}")
    public List<LectureNotedto> allLectureNote(@PathVariable String adminId, @PathVariable String courseId) {
        return lectureNoteservice.allLectureNote(adminId, courseId);
    }

    @PostMapping("/editLectureNote")
    public ResponseEntity<LectureNotedto> editLectureNote(@RequestBody EditLectureNoteRequestdto request) {
        try {
            // Retrieve and process the request data
            LectureNotedto lectureNote = lectureNoteservice.getlectureNoteDetails(
                    request.getLectureNoteName(), request.getAdminId(), request.getCourseId(), request.getPublishDate(), request.getMessage(), request.getFilePath()
            );
            return ResponseEntity.ok(lectureNote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveLectureNoteEdit")
    public ResponseEntity<?> updateLectureNote(@RequestBody EditLectureNoteRequestdto request) {
        try {
            lectureNoteservice.updateLectureNote(request.getLectureNoteName(), request.getPublishDate(), request.getAdminId(), request.getCourseId(), request.getMessage());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("lectureNotes/{courseId}")
    public ResponseEntity<List<LectureNotedto>> getLectureNotesByCourseId(@PathVariable String courseId) {
        List<LectureNotedto> lectureNotes = lectureNoteservice.allLectureNoteByCourseID(courseId);
        return ResponseEntity.ok(lectureNotes);
    }


    @DeleteMapping("/deleteLectureNote")
    public void deleteLectureNote(
            @RequestParam("lectureNoteName") String lectureNoteName,
            @RequestParam("adminId") String adminId,
            @RequestParam("courseId") String courseId) {

        // Call the service method
        lectureNoteservice.deleteLectureNote(lectureNoteName, adminId, courseId);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Other methods
    @GetMapping("/login_student/{Student_id}/{Student_name}")
    public String login_student(@PathVariable String Student_id, @PathVariable String Student_name) {
        return studentservice.login_student(Student_id, Student_name);
    }

    @GetMapping("/login_lecturer/{lecturer_id}/{password}")
    public String login_lecturer(@PathVariable String lecturer_id, @PathVariable String password) {
        return adminservice.login_lecturer(lecturer_id, password);
    }

    @GetMapping("/print_result/{Student_id}")
    public List<S_resultrdto> print_result(@PathVariable String Student_id) {
        return s_resultservice.print_result(Student_id);
    }

    @GetMapping("/average_result/{Student_id}")
    public String average_result(@PathVariable String Student_id) {
        return s_resultservice.average_result(Student_id);
    }

    @GetMapping("/events/{Student_id}")
    public List<Eventdto> getAllEvents(@PathVariable String Student_id) {
        return eventservice.findAllEventsByStudentId(Student_id);
    }

    @GetMapping("/eventsAdmin/{Admin_id}")
    public List<EventAdmindto> getAllEventsAdmin(@PathVariable String Admin_id) {
        return eventAdminservice.findAllEventsByAdminId(Admin_id);
    }

    @GetMapping("/courses/{Student_id}")
    public List<Coursedto> getAllCourses(@PathVariable String Student_id) {
        return courseservice.findAllCoursesByStudentId(Student_id);
    }

    @GetMapping("/coursesAdmin/{Admin_id}")
    public List<Coursedto> getAllCoursesAdmin(@PathVariable String Admin_id) {
        return courseservice.findAllCoursesByAdminId(Admin_id);
    }

    @GetMapping("/regCourse")
    public List<Coursedto> getRegisteringCourses() {
        return courseservice.findAllRegisteringCourses();
    }


    @GetMapping("/isEnrolled/{studentId}/{courseId}")
    public boolean isEnrolledInCourse(@PathVariable String studentId, @PathVariable String courseId) {
        return courseservice.isEnrolledInCourse(studentId, courseId);
    }

    @PutMapping("attend/{Student_id}/{Course_id}")
    public void attend(@PathVariable String Student_id, @PathVariable String Course_id) {
        s_attendservice.attend(Student_id, Course_id);
    }

    @GetMapping("/print_rank")
    public List<S_rankdto> print_result() {
        return s_rankservice.print_result();
    }

    @GetMapping("/getUser")
    public List<UserDto> getUser() {
        return userService.getAllUsers();
    }


    @PutMapping("/updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PutMapping("/updateAdminProfile")
    public AdminProfileDto updateAdminProfile(@RequestBody AdminProfileDto adminProfileDto) {
        return adminProfileService.updateAdminProfile(adminProfileDto);
    }

    @DeleteMapping("/deleteUser/{userID}")
    public String deleteUser(@PathVariable String userID) {
        return userService.deleteUser(userID);
    }

    @DeleteMapping("/deleteAdmin/{adminID}")
    public String deleteAdmin(@PathVariable String adminID) {
        return adminProfileService.deleteAdmin(adminID);
    }

    @GetMapping("/getUserByUserId/{userID}")
    public UserDto getUserByUserID(@PathVariable String userID) {
        return userService.getUserByUserID(userID);
    }

    @GetMapping("/getAdminByUserId/{adminID}")
    public AdminProfileDto getAdminByUserID(@PathVariable String adminID) {
        return adminProfileService.getAdminByUserID(adminID);
    }

    @GetMapping("/course/{AdminId}/{CourseId}")
    public List<Coursedto> getCoursesAdmin(@PathVariable String AdminId, @PathVariable String CourseId) {
        return courseservice.findCoursesByAdminId(AdminId, CourseId);
    }

    @GetMapping("/getquiz/{AdminId}/{CourseId}")
    public List<Quizdto> getQuizsAdmin(@PathVariable String AdminId, @PathVariable String CourseId) {
        return quizservice.findQuizByAdminId(AdminId, CourseId);
    }

    // Get the file

    private final Path rootLocation = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new");

    @GetMapping("/getfile/{adminId}/{courseId}")
    public ResponseEntity<Resource> serveFile(@PathVariable String adminId, @PathVariable String courseId, @RequestParam String fileName) {
        try {
            // Construct the file path based on Assignmentdir, courseId, and fileName
            Path targetDir = rootLocation.resolve("Assignment Folder");  // Adjust Assignmentdir as needed
            Path file = targetDir.resolve(courseId).resolve(fileName);  // Resolve courseId and fileName

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getStdfile/{studentId}/{courseId}")
    public ResponseEntity<Resource> serveStdFile(@PathVariable String studentId, @PathVariable String courseId, @RequestParam String fileName) {
        try {
            // Construct the file path based on Assignmentdir, courseId, and fileName
            Path targetDir = rootLocation.resolve("Assignment Student Folder");  // Adjust Assignmentdir as needed
            Path file = targetDir.resolve(courseId).resolve(fileName);  // Resolve courseId and fileName

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Get file details
    @GetMapping("/allAssignment/{adminId}/{courseId}")
    public List<Assignmentdto> allAssignment(@PathVariable String adminId, @PathVariable String courseId) {
        return assignmentservice.allAssignment(adminId, courseId);
    }

    // Get file details
    @GetMapping("/allStdAssignment/{studentId}/{courseId}/{assignmentId}")
    public List<StudentAssignmentDto> allStdAssignment(@PathVariable String studentId, @PathVariable String courseId, @PathVariable Long assignmentId) {
        return studentAssignmentService.allStdAssignment(studentId, courseId, assignmentId);
    }

    @GetMapping("assignments/{courseId}")
    public ResponseEntity<List<Assignmentdto>> getAssignmentsByCourseId(@PathVariable String courseId) {
        List<Assignmentdto> assignments = assignmentservice.allAssignmentByCourseID(courseId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/offeredById/{courseId}")
    public List<Coursedto> offeredById(@PathVariable String courseId) {
        return courseservice.offeredById(courseId);
    }

    public controller(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailMessage emailMessage) {
        // Input validation (simple example, add more complex checks if needed)
        if (emailMessage.getName() == null || emailMessage.getFrom() == null ||
                emailMessage.getPhone() == null || emailMessage.getMessage() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        try {
            // Assuming sendEmail method throws exception in case of failure
            emailSenderService.sendEmail(emailMessage.getName(), emailMessage.getFrom(),
                    emailMessage.getPhone(), emailMessage.getMessage());
            return ResponseEntity.ok("Message sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send: " + e.getMessage());
        }
    }

    @GetMapping("/findQuiz/{adminId}/{courseId}/{quizId}")
    public Quizdto findQuiz(@PathVariable String adminId, @PathVariable String courseId, @PathVariable String quizId) {
        return quizservice.findQuiz(adminId, courseId, quizId);
    }

    @PutMapping("/updateQuiz")
    public void updateQuiz(@RequestParam String adminId,
                           @RequestParam String courseId,
                           @RequestParam String quizName,
                           @RequestParam String value1,
                           @RequestParam String value2,
                           @RequestParam String value3,
                           @RequestParam int value4
    ) {
        quizservice.updateQuiz(adminId, courseId, quizName, value1, value2, value3, value4);
    }

    @DeleteMapping("/deleteQuiz/{adminId}/{courseId}/{quizName}")
    public void deleteQuiz(@PathVariable String adminId, @PathVariable String courseId, @PathVariable String quizName) {
        quizservice.deleteQuiz(adminId, courseId, quizName);
    }

    @GetMapping("/unread/{student_id}")
    public List<StudentNotification> getUnreadNotifications(@PathVariable String student_id) {
        return studentnotificationService.getUnreadNotificationsStudentByAdminId(student_id);
    }

    @PutMapping("/read/{student_id}/{notificationId}")
    public void markNotificationAsRead(@PathVariable String student_id, @PathVariable Long notificationId) {
        studentnotificationService.markNotificationAsRead(student_id, notificationId);
    }

    @GetMapping("/getquiz/{courseId}")
    public List<Quizdto> findQuizById(@PathVariable String courseId) {
        return quizservice.findQuizById(courseId);
    }

    @GetMapping("/getData")
    public Map<Object, Object> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        return googleApiService.readDataFromGoogleSheet();
    }

    @PostMapping("/createSheet")
    public ResponseEntity<String> createGoogleSheet(@RequestBody GoogleSheetDTO request)
            throws GeneralSecurityException, IOException {
        System.out.println("Received request to create sheet: " + request.getSheetName() + " and type is " + request.gettype() + " " + request.getadminId());  // Log received sheet name
        String sheetUrl = googleApiService.createSheet(request);
        System.out.println("Sheet URL: " + sheetUrl);  // Log the created sheet URL
        return ResponseEntity.ok(sheetUrl);
    }

    @GetMapping("/getsheet/{adminId}/{courseId}/{type}")
    public List<Examsheet> findsheet(@PathVariable String adminId, @PathVariable String courseId, @PathVariable String type) {
        return examsheetservice.findsheet(adminId, courseId, type);
    }

    @PostMapping("/examMarkTotal")
    public ResponseEntity<String> saveExamMarkTotal(@RequestBody ExamMarkTotalDTO examMarkTotalDTO) {
        examMarkTotalService.saveOrUpdateExamMarkTotal(examMarkTotalDTO.getStudentId(), examMarkTotalDTO.getPercentage(), examMarkTotalDTO.getCourseId());
        return ResponseEntity.ok("Exam mark total saved successfully");
    }

    @PostMapping("/assignmentMarkTotal")
    public ResponseEntity<String> saveAssignmentMarkTotal(@RequestBody AssignmentMarkTotalDTO assignmentMarkTotalDTO) {
        assignmentMarkTotalService.saveOrUpdateAssignmentMarkTotal(assignmentMarkTotalDTO.getStudentId(), assignmentMarkTotalDTO.getPercentage(), assignmentMarkTotalDTO.getCourseId());
        return ResponseEntity.ok("Assignment mark total saved successfully");
    }

    @PostMapping("/quizMarkTotal")
    public ResponseEntity<String> saveQuizMarkTotal(@RequestBody QuizMarkTotalDTO quizMarkTotalDTO) {
        quizMarkTotalService.saveOrUpdateQuizMarkTotal(quizMarkTotalDTO.getStudentId(), quizMarkTotalDTO.getPercentage(), quizMarkTotalDTO.getCourseId());
        return ResponseEntity.ok("Assignment mark total saved successfully");
    }

    @GetMapping("/combinedMarks")
    public ResponseEntity<List<CombinedMarksDTO>> getCombinedMarks(
            @RequestParam String studentId,
            @RequestParam String courseId) {

        List<CombinedMarksDTO> combinedMarks = combinedMarksService.getCombinedMarks(studentId, courseId);

        // Return the response with proper HTTP status
        if (combinedMarks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(combinedMarks);
    }

    @GetMapping("courseWeights/{courseId}")
    public ResponseEntity<CourseWeight> getWeightByCourseId(@PathVariable String courseId) {
        CourseWeight courseWeight = courseWeightService.getWeightByCourseId(courseId);
        return courseWeight != null ? ResponseEntity.ok(courseWeight) : ResponseEntity.notFound().build();
    }

    @PostMapping("courseWeights")
    public CourseWeight createWeight(@RequestBody CourseWeight courseWeight) {
        return courseWeightService.saveWeight(courseWeight);
    }

    @GetMapping("/getAttendance/{Student_id}/{Course_id}")
    public S_attenddto getAttendance(@PathVariable String Student_id, @PathVariable String Course_id) {
        return s_attendservice.getAttendance(Course_id, Student_id);
    }

    @PostMapping("saveFinalScore")
    public ResponseEntity<S_resultrdto> saveResult(@RequestBody S_resultrdto s_resultrdto) {
        try {
            S_resultrdto savedResult = s_resultservice.saveuser(s_resultrdto);

            return new ResponseEntity<>(savedResult, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error saving result: " + e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}



