package com.silkdog.ambulance.restapi.location.controller;

import com.silkdog.ambulance.restapi.location.domain.EgytListInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.EmrrmRltmUsefulSckbdInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.Inquiry;
import com.silkdog.ambulance.restapi.location.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("data-api")
@RequiredArgsConstructor
public class LocationController {

    private final HospitalService hospitalService;

    @GetMapping("get-hospital-available")
    public List<EmrrmRltmUsefulSckbdInfoInqire> getHospitalAvailable(Inquiry inquiry) throws IOException, NullPointerException {

        String s1 = StringUtils.isNoneEmpty(inquiry.getStage1()) ? inquiry.getStage1() : "";
        String s2 = StringUtils.isNoneEmpty(inquiry.getStage2()) ? inquiry.getStage2() : "";

        return this.hospitalService.getHospitalAvailable(s1, s2, 1, 30);
    }

    @GetMapping("get-hospital-addr")
    public List<EgytListInfoInqire> getHospitalAddr(Inquiry inquiry) throws IOException, NullPointerException {

        String s1 = StringUtils.isNoneEmpty(inquiry.getStage1()) ? inquiry.getStage1() : "";
        String s2 = StringUtils.isNoneEmpty(inquiry.getStage2()) ? inquiry.getStage2() : "";

        return this.hospitalService.getHospitalAddr(s1, s2, 1, 30);
    }

    @GetMapping("get-hospital-processed")
    public ResponseEntity<String> getHospitalProcessed(Inquiry inquiry) throws IOException, NullPointerException, ExecutionException, InterruptedException {

        String s1 = StringUtils.isNoneEmpty(inquiry.getStage1()) ? inquiry.getStage1() : "";
        String s2 = StringUtils.isNoneEmpty(inquiry.getStage2()) ? inquiry.getStage2() : "";

        // TODO: 비동기 실행할 것
        List<EmrrmRltmUsefulSckbdInfoInqire> avail = asyncEmrrmRltmUsefulSckbdInfoInqire(s1, s2).get();
        List<EgytListInfoInqire> addr = asyncEgytListInfoInqire(s1, s2).get();

        return ResponseEntity.ok("");
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<EmrrmRltmUsefulSckbdInfoInqire>> asyncEmrrmRltmUsefulSckbdInfoInqire(String s1, String s2) throws IOException {
        return CompletableFuture.completedFuture(this.hospitalService.getHospitalAvailable(s1, s2, 1, 30));
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<EgytListInfoInqire>> asyncEgytListInfoInqire(String s1, String s2) throws IOException {
        return CompletableFuture.completedFuture(this.hospitalService.getHospitalAddr(s1, s2, 1, 30));
    }

}
