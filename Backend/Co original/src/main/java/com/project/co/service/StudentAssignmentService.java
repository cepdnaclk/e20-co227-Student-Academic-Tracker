package com.project.co.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.co.dto.StudentAssignmentDto;
import com.project.co.entity.StudentAssignment;
import com.project.co.repo.StudentAssignmentRepo;

import java.io.File;

@Service
@Transactional

public class StudentAssignmentService {

    @Autowired
    public StudentAssignmentRepo studentAssignmentRepo;

    @Autowired
    public ModelMapper modelMapper;

    // Save file
    public void saveStdAssignment(String AssignmentStddir, String assignmentName, String studentID, String courseId, Long assignmentId, MultipartFile multipartFile) throws IOException {

        // Create the path where the file will be saved
        Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new");


        Path uploadPath = targetDir.resolve(AssignmentStddir).resolve(courseId);
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
            StudentAssignmentDto studentAssignmentDto = new StudentAssignmentDto();
            studentAssignmentDto.setFilePath(filePath.toString());
            studentAssignmentDto.setAssignmentName(assignmentName);
            studentAssignmentDto.setStudentId(studentID);
            studentAssignmentDto.setCourseId(courseId);
            studentAssignmentDto.setFileType(fileExtension);
            studentAssignmentDto.setAssignmentId(assignmentId);
            studentAssignmentRepo.save(modelMapper.map(studentAssignmentDto, StudentAssignment.class));

        } catch (IOException iOException) {
            // Handle the exception (e.g., log it)
            throw new IOException("Failed to save the file", iOException);
        }
    }
    

    // Get the file
    public List<StudentAssignmentDto> allStdAssignment(String studentId, String courseId , Long assignmentId) {
        List<StudentAssignment> assignmentList = studentAssignmentRepo.allStdAssignment(studentId, courseId, assignmentId);

        // Use stream to map each studentAssignment to StudentAssignmentDto
        return assignmentList.stream()
        .map(assignment -> {
            // Map to DTO
            StudentAssignmentDto dto = modelMapper.map(assignment, StudentAssignmentDto.class);

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


    public void deleteStdAssignment(String assignmentName, String studentId, String courseId) {
        // Fetch the assignment based on the assignmentName and courseId
        StudentAssignment assignment = studentAssignmentRepo.findByAssignmentNameAndCourseIdAndStudentId(assignmentName, courseId, studentId);

        if (assignment != null) {
            String[] possibleExtensions = {".png", ".jpg", ".jpeg", ".pdf", ".docx"};
            boolean fileFound = false;

            for (String extension : possibleExtensions) {
                Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Assignment Student Folder");


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
            studentAssignmentRepo.delete(assignment);


        } else {
            throw new RuntimeException("Assignment not found.");
        }
    }

    public StudentAssignmentDto getStdAssignmentDetails(String assignmentName, String studentId, String courseId, String filePath) {
        // Find the assignment based on the provided parameters
        StudentAssignment assignment = studentAssignmentRepo.findByAssignmentNameAndCourseIdAndStudentId(
                assignmentName, courseId, studentId
        );

        // Check if the assignment exists
        if (assignment != null) {
            // Return the assignment details
            // Create a DTO to return only the necessary fields
            return new StudentAssignmentDto(assignment.getAssignmentName(), assignment.getFilePath());
        } else {
            // Handle the case where the assignment is not found
            throw new RuntimeException("Assignment not found with name: " + assignmentName);
        }
    }

    public void updateStdAssignment(String assignmentName, String studentId, String courseId ) {
        // Find the assignment based on the provided parameters
        StudentAssignment assignment = studentAssignmentRepo.findByAssignmentNameAndCourseIdAndStudentId(assignmentName, courseId, studentId);

        // Check if the assignment exists
        if (assignment != null) {
            assignment.setAssignmentName(assignmentName);
            studentAssignmentRepo.save(assignment);
        } else {
            throw new RuntimeException("Assignment not found with name: " + assignmentName);
        }
    }

}
