package com.project.co.service;

import com.project.co.dto.LectureNotedto;
import com.project.co.entity.LectureNote;
import com.project.co.repo.LectureNoteRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class LectureNoteServices {

    @Autowired
    public LectureNoteRepo lectureNoteRepo;

    @Autowired
    public ModelMapper modelMapper;


    // Save file
    public void saveLectureNote(String LectureNotedir, String lectureNoteName, String publishDate, String message, String adminID, String courseId, MultipartFile multipartFile) throws IOException {

        // Create the path where the file will be saved
        Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Lecture Notes");


        Path uploadPath = targetDir.resolve(LectureNotedir).resolve(courseId);
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
        Path filePath = uploadPath.resolve(lectureNoteName + fileExtension);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save data in database
            LectureNotedto lectureNotedto = new LectureNotedto();
            lectureNotedto.setFilePath(filePath.toString());
            lectureNotedto.setLectureNoteName(lectureNoteName);
            lectureNotedto.setPublishDate(publishDate);
            lectureNotedto.setMessage(message);
            lectureNotedto.setAdminId(adminID);
            lectureNotedto.setCourseId(courseId);
            lectureNotedto.setFileType(fileExtension);
            lectureNoteRepo.save(modelMapper.map(lectureNotedto, LectureNote.class));

        } catch (IOException iOException) {
            // Handle the exception (e.g., log it)
            throw new IOException("Failed to save the file", iOException);
        }
    }


    // Get the file
    public List<LectureNotedto> allLectureNote(String adminId, String courseId) {
        List<LectureNote> lectureNoteList = lectureNoteRepo.allLectureNote(adminId, courseId);

        return lectureNoteList.stream()
                .map(lectureNote -> {
                    // Map to DTO
                    LectureNotedto dto = modelMapper.map(lectureNote, LectureNotedto.class);

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

    public List<LectureNotedto> allLectureNoteByCourseID(String courseId) {
        List<LectureNote> lectureNoteList = lectureNoteRepo.findByCourseId(courseId);

        return lectureNoteList.stream()
                .map(lectureNote -> {
                    // Map to DTO
                    LectureNotedto dto = modelMapper.map(lectureNote, LectureNotedto.class);

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

    public LectureNotedto getlectureNoteDetails(String lectureNoteName, String adminId, String courseId, String publishDate, String message, String filePath) {
        LectureNote lectureNote = lectureNoteRepo.findByLectureNoteNameAndCourseIdAndAdminIdAndDeadLineAndMessage(
                lectureNoteName, courseId, adminId
        );

        if (lectureNote != null) {

            return new LectureNotedto(lectureNote.getLectureNoteName(), lectureNote.getPublishDate(),lectureNote.getMessage(),lectureNote.getFilePath());
        } else {
            // Handle the case where the assignment is not found
            throw new RuntimeException("Lecture note not found with name: " + lectureNoteName);
        }
    }

    public void updateLectureNote(String lectureNoteName, String publishDate, String adminId, String courseId , String message) {

        LectureNote lectureNote = lectureNoteRepo.findByLectureNoteNameAndCourseIdAndAdminId(lectureNoteName, courseId, adminId);

        if (lectureNote != null) {
            lectureNote.setPublishDate(publishDate);
            lectureNote.setMessage(message);
            lectureNote.setLectureNoteName(lectureNoteName);
            lectureNoteRepo.save(lectureNote);
        } else {
            throw new RuntimeException("Lecture notes not found with name: " + lectureNoteName);
        }
    }

    public void deleteLectureNote(String lectureNoteName, String adminId, String courseId) {

        LectureNote lectureNote = lectureNoteRepo.findByLectureNoteNameAndCourseIdAndAdminId(lectureNoteName, courseId, adminId);

        if (lectureNote != null) {
            String[] possibleExtensions = {".png", ".jpg", ".jpeg", ".pdf", ".docx"};
            boolean fileFound = false;

            for (String extension : possibleExtensions) {
                Path targetDir = Paths.get("C:\\Users\\acer\\Desktop\\SEM 4\\CO200\\project\\Co new\\Lecture Notes\\Lecture Notes Folder");


                Path filePath1 = targetDir.resolve(courseId);
                Object extensionWithName = filePath1 +"\\"+lectureNoteName ;
                Path filePath = Path.of(extensionWithName + extension);
                System.out.println(filePath);
                File file = new File(String.valueOf(filePath));
                if (file.exists()) {
                    file.delete();
                    fileFound = true;
                    break;
                }

            } if(!fileFound) {
                System.out.println("File not found");
            }
            lectureNoteRepo.delete(lectureNote);


        } else {
            throw new RuntimeException("Lecture note not found.");
        }
    }

}
