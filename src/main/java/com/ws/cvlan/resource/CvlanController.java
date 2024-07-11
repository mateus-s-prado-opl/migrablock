package com.ws.cvlan.resource;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import com.ws.cvlan.pojo.response.AddCvlanBlockResponse;
import com.ws.cvlan.pojo.response.CvlanBlockListResponse;
import com.ws.cvlan.pojo.response.RemoveCvlanBlockResponse;
import com.ws.cvlan.repository.CvlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cvlan")
public class CvlanController {

    private static final Logger logger = LoggerFactory.getLogger(CvlanController.class);

    @Autowired
    private CvlanRepository cvlanRepository;

    @PostMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddCvlanBlockResponse> handleCvlanBlockRequest(@RequestBody @Valid AddCvlanBlockFilter input) {
        logger.info("\n\n\n[API-MIGRABLOCK-LOG] Received request to add CVLAN block: {}", input);
        long startTime = System.currentTimeMillis();

        AddCvlanBlockResponse addCvlanBlockResponse = cvlanRepository.addCvlanBlock(input);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("[API-MIGRABLOCK-LOG] Request completed in {} ms", duration);

        if (addCvlanBlockResponse.hasError()) {
            logger.error("[API-MIGRABLOCK-LOG] Error adding CVLAN block: {}", addCvlanBlockResponse.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCvlanBlockResponse);
        }

        logger.info("[API-MIGRABLOCK-LOG] Successfully added CVLAN block: {}", addCvlanBlockResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCvlanBlockResponse);
    }

    @DeleteMapping(path = "/bloqueio", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RemoveCvlanBlockResponse> handleCvlanBlockRemovalRequest(@RequestBody @Valid RemoveCvlanBlockFilter input) {
        logger.info("\n\n\n[API-MIGRABLOCK-LOG] Received request to remove CVLAN block: {}", input);
        long startTime = System.currentTimeMillis();

        RemoveCvlanBlockResponse removedCvlanBlock = cvlanRepository.executeCvlanBlockRemove(input);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("[API-MIGRABLOCK-LOG] Request completed in {} ms", duration);

        if (removedCvlanBlock.hasError()) {
            logger.error("[API-MIGRABLOCK-LOG] Error removing CVLAN block: {}", removedCvlanBlock.getStackTrace());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(removedCvlanBlock);
        }

        logger.info("[API-MIGRABLOCK-LOG] Successfully removed CVLAN block: {}", removedCvlanBlock);
        return ResponseEntity.status(HttpStatus.OK).body(removedCvlanBlock);
    }

    @GetMapping("/listaBloqueios")
    public ResponseEntity<CvlanBlockListResponse> getCvlanBlockList(@RequestBody @Valid ListCvlanBlockFilter input) {
        logger.info("\n\n\n[API-MIGRABLOCK-LOG] Received request to list CVLAN blocks with filter: {}", input);
        long startTime = System.currentTimeMillis();

        CvlanBlockListResponse cvlanBlocks = cvlanRepository.getCvlanBlockList(input);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("[API-MIGRABLOCK-LOG] Request completed in {} ms", duration);

        if (cvlanBlocks.getCvlanBlockList().isEmpty()) {
            logger.info("[API-MIGRABLOCK-LOG] No CVLAN blocks found for the given filter: {}", input);
            return ResponseEntity.noContent().build();
        }

        logger.info("[API-MIGRABLOCK-LOG] Successfully retrieved CVLAN block list: {}", cvlanBlocks);
        return ResponseEntity.ok(cvlanBlocks);
    }
}