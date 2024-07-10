package com.ws.cvlan.resource;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import com.ws.cvlan.pojo.response.AddCvlanBlockResponse;
import com.ws.cvlan.pojo.response.RemoveCvlanBlockResponse;
import com.ws.cvlan.pojo.response.CvlanBlockListResponse;
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
    public ResponseEntity<AddCvlanBlockResponse> handleCvlanBlockRequest(@RequestBody @Valid AddCvlanBlockFilter input) {
        AddCvlanBlockResponse addCvlanBlockResponse = cvlanRepository.addCvlanBlock(input);

        if (addCvlanBlockResponse.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCvlanBlockResponse);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(addCvlanBlockResponse);
    }


    @DeleteMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RemoveCvlanBlockResponse> handleCvlanBlockRemovalResquest(@RequestBody @Valid RemoveCvlanBlockFilter input) {
        RemoveCvlanBlockResponse removedCvlanBlock = cvlanRepository.executeCvlanBlockRemove(input);

        if (removedCvlanBlock.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(removedCvlanBlock);
        }

        return ResponseEntity.status(HttpStatus.OK).body(removedCvlanBlock);
    }



    @GetMapping("/listaBloqueios")
    public ResponseEntity<CvlanBlockListResponse> getCvlanBlockList(@RequestBody @Valid ListCvlanBlockFilter input) {
        CvlanBlockListResponse cvlanBlocks = cvlanRepository.getCvlanBlockList(input);
        if (cvlanBlocks.getCvlanBlockList().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cvlanBlocks);
    }

}
