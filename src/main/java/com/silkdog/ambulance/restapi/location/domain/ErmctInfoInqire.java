package com.silkdog.ambulance.restapi.location.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Date: 2021-09-10
 * Author: Margarette
 * Description: 응급실 실시간 가용병상정보 조회 도메인
 * (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15000563)
 * */
@Getter
@Setter
@Builder
public class ErmctInfoInqire {

    private Integer resultCode;  // 결과코드
    private String resultMsg;  // 결과메시지
    private Integer rnum;  // 일련번호
    private String hpid;  // 기관코드
    private String phpid;  // 구기관코드
    private String hvidate;  // 입력일시
    private String hvec;  // 응급실
    private String hvoc;  // 수술실
    private String hvcc;  // 신경중환자
    private String hvncc;  // 신생중환자
    private String hvccc;  // 흉부중환자
    private String hvicc;  // 일반중환자
    private String hvgc;  // 입원실
    private String hvdnm;  // 당직의
    private String hvctayn;  // CT가용(가/부)
    private String hvmriayn;  // MRI가용(가/부)
    private String hvangioayn;  // 조영촬영기가용(가/부)
    private String hvventiayn;  // 인공호흡기가용(가/부)
    private String hvamyn;  // 구급차가용여부
    private String hv1;  // 응급실 당직의 직통연락처
    private String hv2;  // 내과중환자실
    private String hv3;  // 외과중환자실
    private String hv4;  // 외과입원실(정형외과)
    private String hv5;  // 신경과입원실
    private String hv6;  // 신경외과중환자실
    private String hv7;  // 약물중환자
    private String hv8;  // 화상중환자
    private String hv9;  // 외상중환자
    private String hv10;  // VENTI(소아)
    private String hv11;  // 인큐베이터(보육기)
    private String hv12;  // 소아당직의 직통연락처
    private String dutyname; // 기관명
    private String dutytel3; // 응급실전화

}
