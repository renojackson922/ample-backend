package com.silkdog.ambulance.restapi.location.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Date: 2021-09-10
 * Author: Margarette
 * Description: 병원 정보 도메인
 * (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15000563)
 */
@Getter
@Setter
@Builder
public class EgytListInfoInqire {

    private String resultCode;  // 결과코드
    private String resultMsg;  // 결과메시지
    private String dutyAddr;
    private String dutyEmcls;
    private String dutyEmclsName;
    private String dutyName;
    private String dutyTel1;
    private String dutyTel3;
    private String hpid;
    private String phpid;
    private Integer rnum;
    private Double wgs84Lat;
    private Double wgs84Lon;

}
