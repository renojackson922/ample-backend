package com.silkdog.ambulance.restapi.location.controller;

import com.silkdog.ambulance.restapi.location.domain.EgytListInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.EmrrmRltmUsefulSckbdInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.Inquiry;
import com.silkdog.ambulance.restapi.location.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<String> getHospitalProcessed(Inquiry inquiry) throws IOException, NullPointerException {

        String s1 = StringUtils.isNoneEmpty(inquiry.getStage1()) ? inquiry.getStage1() : "";
        String s2 = StringUtils.isNoneEmpty(inquiry.getStage2()) ? inquiry.getStage2() : "";

        // TODO: 비동기 실행할 것
        List<EmrrmRltmUsefulSckbdInfoInqire> avail = this.hospitalService.getHospitalAvailable(s1, s2, 1, 30);
        List<EgytListInfoInqire> addr = this.hospitalService.getHospitalAddr(s1, s2, 1, 30);

        return ResponseEntity.ok("");
    }


}
