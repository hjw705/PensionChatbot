//check date is valid
/*
function isValidDate(dateStr) {
     var year = Number(dateStr.substr(0,4));
     var month = Number(dateStr.substr(4,2));
     var day = Number(dateStr.substr(6,2));
     var today = new Date(); // 날자 변수 선언
     var yearNow = today.getFullYear();

     if (month < 1 || month > 12) {
          alert("달은 1월부터 12월까지 입력 가능합니다.");
          return false;
     }
    if (day < 1 || day > 31) {
          alert("일은 1일부터 31일까지 입력가능합니다.");
          return false;
     }
     if ((month==4 || month==6 || month==9 || month==11) && day==31) {
          alert(month+"월은 31일이 존재하지 않습니다.");
          return false;
     }
     if (month == 2) {
          var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
          if (day>29 || (day==29 && !isleap)) {
               alert(year + "년 2월은  " + day + "일이 없습니다.");
               return false;
          }
     }
     return true;
}
*/

function getDate() {
  var date = new Date();
  var year = date.getFullYear();
  var month = new String(date.getMonth()+1);
  var day = new String(date.getDate());

  // 한자리수일 경우 0을 채워준다.
  if(month.length == 1){
    month = "0" + month;
  }
  if(day.length == 1){
    day = "0" + day;
  }

  return year + "" + month + "" + day;
}

function getTime() {
  let today = new Date();

  let hours = today.getHours(); // 시
  let minutes = today.getMinutes();  // 분
  let seconds = today.getSeconds();  // 초

  if(hours.length == 1){
    hours = "0" + hours;
  }
  if(minutes.length == 1){
    minutes = "0" + minutes;
  }
  if(seconds.length == 1){
    seconds = "0" + seconds;
  }

  return hours + "" + minutes + "" + seconds;
}


//submit by enter key
function press() {
  console.log("press!");
  var birthday = document.getElementById("birthday").value;
  var accountNum = document.getElementById("accountNum").value;
  var date = getDate();
  var time = getTime();
  console.log(birthday);
  console.log(accountNum);
  console.log(date);
  console.log(time);
  /*
  if (isValidDate(birthday) == false) {
    return;
  }
  */

  fetch("https://developers.nonghyup.com/OpenFinAccountDirect.nh", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      "Header": {
        "ApiNm": "OpenFinAccountDirect",
        "Tsymd": date,
        "Trtm": time,
        "Iscd": "000491",
        "FintechApsno": "001",
        "ApiSvcCd": "DrawingTransferA",
        "IsTuno": "0015",
        "AccessToken": "2ca42131cbd5f74f542dfca18bf2136414b05dd8c6014323df54356c5e3464b6"
      },
      "DrtrRgyn": "Y",
      "BrdtBrno": birthday,
      "Bncd": "011",
      "Acno": accountNum,
    }),
  })
  .then((response) => response.json())
  .then(function(Rgno) {
    if (Rgno["Rgno"] != null) {
      document.querySelector('article').innerHTML = "<br>계좌 조회 성공!<br>" + Rgno["Rgno"];
    }
    else {
      document.querySelector('article').innerHTML = "<br>ERROR";
    }
  })

  //document.querySelector('article').innerHTML = response;
}

/*
"Header": {
  "ApiNm": "OpenFinAccountDirect",
  "Tsymd": "오늘날짜를입력하세요",
  "Trtm": "112428",
  "Iscd": 000491,
  "FintechApsno": "001",
  "ApiSvcCd": "DrawingTransferA",
  "IsTuno": "0000",
  "AccessToken": "2ca42131cbd5f74f542dfca18bf2136414b05dd8c6014323df54356c5e3464b6"
},
"DrtrRgyn": "Y",
"BrdtBrno": "생년월일을입력하세요",
"Bncd": "은행코드를입력하세요",
"Acno": "계좌번호를입력하세요"
*/

function CloseWindow() {
  window.open('', '_self', '');
  window.close();
  return false;
}
