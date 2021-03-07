'use strict';

const APP_KEY = "622a26bbe65339602e2bea28321bb212";

const myModalEl = document.getElementById('exampleModal')
myModalEl.addEventListener('shown.bs.modal', (e) => { // show는 modal 사이즈가 계산되지 않은 상태라 카카오맵 타일들이 깨짐
    renderMap(e);
});

GetHospitalList();

/*  API 호출부분 */
async function getDataApi(stage1, stage2) {
    const response = await axios.all([getHospitalAvailable(stage1, stage2), getHospitalAddr(stage1, stage2)])
    console.log(response)
    return response;
}
function getHospitalAvailable(stage1, stage2){
    return axios.get(`/api/getHospitalAvailable?stage1=${stage1}&stage2=${stage2}`);
}
function getHospitalAddr(stage1, stage2){
    return axios.get(`/api/getHospitalAddr?stage1=${stage1}&stage2=${stage2}`);
}

function GetHospitalList(){

    let lng = 0.00;
    let lat = 0.00;

    $("#renderPlace").html(
        `<div class="text-center">
                    로딩 중...
                </div>`
    );

    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(function(position) {
            lng = (position.coords.longitude)
            lat = (position.coords.latitude)
            console.log(lat, lng)
        });
    }

    const searchString = $("#searchString");
    let _stage1 = searchString.val() === "" ? "" : searchString.val().split(' ')[0];
    let _stage2 = searchString.val() === "" ? "" : searchString.val().split(' ')[1];

    //console.log(_stage1, _stage2);

    getDataApi(_stage1, _stage2).then((e)=>{
        //console.log(_stage1, _stage2);
        let hospitalsAvailTmp = e[0].data.response.body.items.item;
        const hospitalsAddr = e[1].data.response.body.items.item;

        // 두 개의 JSON 데이터를 조회하여 HPID 공통키가 있는 것들을 조회해서 주소와 위/경도 정보 밀어넣기
        for (let [indexAvail, valAvail] of hospitalsAvailTmp.entries()) {
            hospitalsAvailTmp[indexAvail].dutyAddr = "주소정보가 없습니다"
            hospitalsAvailTmp[indexAvail].distCalc = 0
            for (let [indexAddr, valAddr] of hospitalsAddr.entries()) {
                if(hospitalsAvailTmp[indexAvail].hpid === hospitalsAddr[indexAddr].hpid){
                    hospitalsAvailTmp[indexAvail].dutyAddr = hospitalsAddr[indexAddr].dutyAddr;
                    let targetLat = hospitalsAddr[indexAddr].wgs84Lat ?? 0;
                    let targetLng = hospitalsAddr[indexAddr].wgs84Lon ?? 0;
                    hospitalsAvailTmp[indexAvail].targetLat = targetLat;
                    hospitalsAvailTmp[indexAvail].targetLng = targetLng;
                    hospitalsAvailTmp[indexAvail].distCalc = Distance(lat, lng, targetLat, targetLng, 'K').toFixed(2);
                }
            }
        }

        hospitalsAvailTmp.sort((a,b) => {
            return a.distCalc - b.distCalc;
        });

        // TODO: 나중에 base64 로 인코딩하기
        const result = (hospitalsAvailTmp).map(hospital => hospital.distCalc == 0 ? "" :
            `<a class="list-group-item list-group-item-action flex-column align-items-start" data-bs-toggle="modal" data-bs-target="#exampleModal" data-enc="${b64EncodeUnicode(JSON.stringify(hospital))}" style="min-height:70px;">
                      <div class="d-flex ">
                        <div style="width:30px; padding:10px 0;">
                          <span style="color:${GetAvailableNumberColor(hospital.hvec)}">●</span>
                        </div>
                        <div style="min-width:160px;">
                          <span style="display:block; font-weight:500">${hospital.dutyName}</span>
                          <span style="display:block; font-size:.75rem">${hospital.dutyAddr}</span>
                        </div>
                        <div class="ms-auto">
                          <span style="display:block; text-align:right; font-size:.75rem; color: #fd8f46;">${ hospital.distCalc } km 이내</span>
                          <span style="display:block; text-align:right; font-size:.75rem;" >가용병상: ${hospital.hvec <= 0 ? 0 : hospital.hvec }석</span>
                          <span style="display:block; text-align:right; font-size:.75rem;" >마지막 업데이트:&nbsp;
                          ${ hospital.hvidate.toString().substring(8).substring(0,2) }시 ${hospital.hvidate.toString().substring(8).substring(3,4)}분 </span>
                        </div>
                      </div>
                    </a>`);

        $("#renderPlace").html(result);

    });
}

function Distance(lat1, lon1, lat2, lon2, unit) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
        return 0;
    }
    else {
        var radlat1 = Math.PI * lat1/180;
        var radlat2 = Math.PI * lat2/180;
        var theta = lon1-lon2;
        var radtheta = Math.PI * theta/180;
        var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        if (dist > 1) {
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit=="K") { dist = dist * 1.609344 }
        if (unit=="N") { dist = dist * 0.8684 }
        return dist;
    }
}

function GetAvailableNumberColor(t){

    const roomAvailable = t || 0;
    if (roomAvailable >= 8)
        return "green";
    else if (roomAvailable >= 4 && roomAvailable < 8)
        return"orange";
    else if (roomAvailable >= 1 && roomAvailable < 4)
        return "red";
    else if (roomAvailable <= 0)
        return "#888";
    else
        return "#000";

}


function renderMap(e){

    const filter = $(e.relatedTarget).attr('data-enc');
    const parsedData = JSON.parse(b64DecodeUnicode(filter));
    console.log(parsedData);

    //$("#modalNaviInfo").data("enc", filter);
    $("a#modalNaviInfo").attr("href", `/popupNavi?enc=${filter}`);
    $("#modalHospitalName").text(parsedData.dutyName);
    $("#modalDutyAddr").text(parsedData.dutyAddr);
    $("#modalDutyTel").text(parsedData.dutyTel3);
    let mapContainer = document.getElementById('mapContainer');
    let options = {
        center: new daum.maps.LatLng(33.450701, 126.570667),
        level: 4
    };

    let map = new daum.maps.Map(mapContainer, options); //지도 생성 및 객체 리턴
    let geocoder = new daum.maps.services.Geocoder(); //주소-좌표 변환 객제 생성
    let roadAddrFull = parsedData.dutyAddr;
    geocoder.addressSearch(roadAddrFull, function (result, status) { //주소로 좌표 검색.
        if (status === daum.maps.services.Status.OK) { //검색 완료이면
            var coords = new daum.maps.LatLng(parsedData.targetLat, parsedData.targetLng);
            var marker = new daum.maps.Marker({
                map: map,
                position: coords
            });
            map.setCenter(coords);
        }
    });

    var mapTypeControl = new daum.maps.MapTypeControl();
    var zoomControl = new daum.maps.ZoomControl();
    map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);
    map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

}

function b64EncodeUnicode(str) {
    return btoa(
        encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function toSolidBytes(
            match,
            p1
        ) {
            return String.fromCharCode("0x" + p1);
        })
    );
}

function b64DecodeUnicode(str) {
    return decodeURIComponent(
        atob(str)
            .split("")
            .map(function (c) {
                return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join("")
    );
}

// var geocoder = new kakao.maps.services.Geocoder();
// geocoder.coord2RegionCode(parsedData.targetLng, parsedData.targetLat, AddressInfo);
//
// function AddressInfo(result, status) {
//     if (status === kakao.maps.services.Status.OK) {
//         for (var i = 0; i < result.length; i++) {
//             if (result[i].region_type === 'H') {
//                 console.log(result[i]);
//             }
//         }
//     }
// }
