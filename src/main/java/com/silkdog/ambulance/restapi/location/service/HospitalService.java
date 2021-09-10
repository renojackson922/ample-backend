package com.silkdog.ambulance.restapi.location.service;

import com.silkdog.ambulance.restapi.location.domain.EgytListInfoInqire;
import com.silkdog.ambulance.restapi.location.domain.EmrrmRltmUsefulSckbdInfoInqire;

import java.io.IOException;
import java.util.List;

public interface HospitalService {

    List<EmrrmRltmUsefulSckbdInfoInqire> getHospitalAvailable(String s1, String s2, int pNo, int rowCount) throws IOException;
    List<EgytListInfoInqire> getHospitalAddr(String s1, String s2, int pNo, int rowCount) throws IOException;
}
