package com.project.co.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.io.IOException;

import java.security.GeneralSecurityException;

import com.project.co.dto.GoogleSheetDTO;
import com.project.co.dto.Examsheetdto;
import com.project.co.entity.Examsheet;

import org.modelmapper.ModelMapper;
import com.project.co.quickstart.GoogleApiUtil;
import com.project.co.repo.Examsheetrepo;

@Service
public class GoogleAPIService {

    @Autowired
    GoogleApiUtil sheetsQuickstart;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public Examsheetrepo examsheetrepo;

    public Map<Object, Object>  readDataFromGoogleSheet() throws IOException, GeneralSecurityException{
        return sheetsQuickstart.getDataFromSheet();
    }

    public String createSheet(GoogleSheetDTO request) throws GeneralSecurityException, IOException {

        // Create object from S_rank
        Examsheet examsheet = new Examsheet();
        examsheet.setCourseId(request.getSheetName());
        examsheet.setAdminId(request.getadminId());
        examsheet.setType(request.gettype());
        examsheet.setUrl(sheetsQuickstart.createGoogleSheet(request));
        examsheetrepo.save(modelMapper.map(examsheet,Examsheet.class));

		return sheetsQuickstart.createGoogleSheet(request);
	}
}

