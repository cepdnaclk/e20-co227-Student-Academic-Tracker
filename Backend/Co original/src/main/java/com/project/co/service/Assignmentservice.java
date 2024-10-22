package com.project.co.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.project.co.dto.NotificationDTO;
import com.project.co.dto.StudentNotificationDTO;
import com.project.co.entity.*;
import com.project.co.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.co.dto.Assignmentdto;

import java.io.File;

@Service
@Transactional
public class Assignmentservice {

    @Autowired
    public Assignmentrepo assignmentrepo;

    @Autowired
    public NotificationRepository notificationrepo;

    @Autowired
     public ModelMapper modelMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StudentNotificationRepository stdNotRepo;

    @Autowired
    private Studentrepo studentRepository;

    @Autowired
    private Courserepo courseRepository;


    // Save file
    public void saveAssignment(String Assignmentdir, String assignmentName, String deadline, String message, String adminID, String courseId, MultipartFile multipartFile) throws IOException {

        // Create the path where the file will be saved
        Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Assignment Folder");


        Path uploadPath = targetDir.resolve(Assignmentdir).resolve(courseId);
        // Create the directory if it does not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    
        // Get the original file name and extension
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
    
        // Construct the new file path with the same extension
        Path filePath = uploadPath.resolve(assignmentName + fileExtension);



    
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    
            // Save data in database
            Assignmentdto assignmentdto = new Assignmentdto();
            assignmentdto.setFilePath(filePath.toString());
            assignmentdto.setAssignmentName(assignmentName);
            assignmentdto.setDeadLine(deadline);
            assignmentdto.setMessage(message);
            assignmentdto.setAdminId(adminID);
            assignmentdto.setCourseId(courseId);
            assignmentdto.setFileType(fileExtension);
            Assignment assignment = modelMapper.map(assignmentdto, Assignment.class);
            assignmentrepo.save(assignment);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setCourse_id(assignmentdto.getCourseId());
            notificationDTO.setMessage("New assignment added: " + assignmentdto.getAssignmentName());
            notificationDTO.setCreatedAt(LocalDateTime.now());
            notificationDTO.setRead(false);
            notificationDTO.setType(NotificationType.ASSIGNMENT);
            notificationrepo.save(modelMapper.map(notificationDTO, Notification.class));


            List<Student> students = studentRepository.findStudentsByCourseId(courseId);

            for (Student student : students) {
                StudentNotification enrollment = new StudentNotification();
                enrollment.setAssignment(assignment);
                enrollment.setStudent(student);
                enrollment.setMessage(assignmentdto.getMessage());
                enrollment.setCourse_id(assignmentdto.getCourseId());
                stdNotRepo.save(enrollment);
            }



        } catch (IOException iOException) {
            // Handle the exception (e.g., log it)
            throw new IOException("Failed to save the file", iOException);
        }

    }
    

    // Get the file
    public List<Assignmentdto> allAssignment(String adminId, String courseId) {
        List<Assignment> assignmentList = assignmentrepo.allAssignment(adminId, courseId);

        // Use stream to map each assignment to Assignmentdto
        return assignmentList.stream()
        .map(assignment -> {
            // Map to DTO
            Assignmentdto dto = modelMapper.map(assignment, Assignmentdto.class);

            // Load file content
            try {
                Path filePath = Paths.get(dto.getFilePath());
                if (Files.exists(filePath)) {
                    dto.setFileContent(Files.readAllBytes(filePath));
                }
            } catch (IOException e) {
            }

            return dto;
        })
                .collect(Collectors.toList());
    }

    public List<Assignmentdto> allAssignmentByCourseID(String courseId) {
        List<Assignment> assignmentList = assignmentrepo.findByCourseId(courseId);

        // Use stream to map each assignment to Assignmentdto
        return assignmentList.stream()
                .map(assignment -> {
                    // Map to DTO
                    Assignmentdto dto = modelMapper.map(assignment, Assignmentdto.class);

                    // Load file content
                    try {
                        Path filePath = Paths.get(dto.getFilePath());
                        if (Files.exists(filePath)) {
                            dto.setFileContent(Files.readAllBytes(filePath));
                        }
                    } catch (IOException e) {
                        // Log error (optional)
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deleteAssignment(String assignmentName, String adminId, String courseId) {
        // Fetch the assignment based on the assignmentName and courseId
        Assignment assignment = assignmentrepo.findByAssignmentNameAndCourseIdAndAdminId(assignmentName, courseId, adminId);

        if (assignment != null) {
            String[] possibleExtensions = {".png", ".jpg", ".jpeg", ".pdf", ".docx"};
            boolean fileFound = false;

            for (String extension : possibleExtensions) {
                Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Assignment Folder");


                Path filePath1 = targetDir.resolve(courseId);
                Object extensionWithName = filePath1 +"\\"+assignmentName ;
                Path filePath = Path.of(extensionWithName + extension);
                System.out.println(filePath);
                File file = new File(String.valueOf(filePath));
                if (file.exists()) {
                    // File found, proceed with deletion
                    file.delete();
                    fileFound = true;
                    break;
                }

            } if(!fileFound) {
                System.out.println("File not found");
            }

            // Delete the assignment from the database
            assignmentrepo.delete(assignment);


        } else {
            throw new RuntimeException("Assignment not found.");
        }
    }

    public Assignmentdto getAssignmentDetails(String assignmentName, String adminId, String courseId, String deadLine, String message, String filePath) {
        // Find the assignment based on the provided parameters
        Assignment assignment = assignmentrepo.findByAssignmentNameAndCourseIdAndAdminIdAndDeadLineAndMessage(
                assignmentName, courseId, adminId
        );

        // Check if the assignment exists
        if (assignment != null) {
            // Return the assignment details
            // Create a DTO to return only the necessary fields
            return new Assignmentdto(assignment.getAssignmentName(), assignment.getDeadLine(),assignment.getMessage(),assignment.getFilePath());
        } else {
            // Handle the case where the assignment is not found
            throw new RuntimeException("Assignment not found with name: " + assignmentName);
        }
    }

    public void updateAssignment(String assignmentName, String deadLine, String adminId, String courseId , String message) {
        // Find the assignment based on the provided parameters
        Assignment assignment = assignmentrepo.findByAssignmentNameAndCourseIdAndAdminId(assignmentName, courseId, adminId);

        // Check if the assignment exists
        if (assignment != null) {
            assignment.setDeadLine(deadLine);
            assignment.setMessage(message);
            assignment.setAssignmentName(assignmentName);
            assignmentrepo.save(assignment);
        } else {
            throw new RuntimeException("Assignment not found with name: " + assignmentName);
        }
    }

}
