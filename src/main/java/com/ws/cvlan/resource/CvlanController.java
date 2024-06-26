package com.ws.cvlan.resource;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.pojo.AddCvlanBlock;
import com.ws.cvlan.repository.CvlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cvlan")
public class CvlanController {

    @Autowired
    private CvlanRepository cvlanRepository;

    @PostMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddCvlanBlock> handleCvlanBlockRequest(@RequestBody @Valid AddCvlanBlockFilter input) {
        AddCvlanBlock addCvlanBlockResponse = cvlanRepository.addCvlanBlock(input);

        if (addCvlanBlockResponse.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCvlanBlockResponse);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(addCvlanBlockResponse);
    }


    @GetMapping("/listaBloqueios")
    public ResponseEntity<String> a(@RequestBody @Valid ListCvlanBlockFilter input){
        return (ResponseEntity<String>) ResponseEntity.noContent();
    }


}
