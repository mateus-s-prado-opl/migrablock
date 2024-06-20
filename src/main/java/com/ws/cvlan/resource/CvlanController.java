package com.ws.cvlan.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.pojo.CvlanBlock;
import com.ws.cvlan.repository.CvlanRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cvlan")
public class CvlanController {

    @Autowired
    private CvlanRepository cvlanRepository;

    @PostMapping("/bloqueio")
    public ResponseEntity<CvlanBlock> addCvlanBlock(@RequestBody @Valid AddCvlanBlockFilter input ){
        CvlanBlock addCvlanBlockResponse = cvlanRepository.addCvlanBlock(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCvlanBlockResponse);
    }
}
