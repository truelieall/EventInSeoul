/*
csrf_token 때문에 사용하는  function

파라미터
	1) url => url 주소
	2) data => 파라미터(json 형식)
	3) csrf_token:  =>  {{ csrf_token }}
	4) callback => 데이터를 리턴받을 function
참조:
- How to use $.post with django?
https://stackoverflow.com/questions/5407463/how-to-use-post-with-django
- Cross Site Request Forgery protection¶
https://docs.djangoproject.com/en/2.0/ref/csrf/
*/
function postAjax(url, data, csrf_token, callback){
	data['csrfmiddlewaretoken'] = csrf_token

    displayLoading("Y"); // 로딩 on
	$.post(url, data, function(res){
        displayLoading("N"); // 로딩 off

		callback(res);
	});
}

/*
	스크롤을 특정위치로 이동
*/
function moveScroll(top){
	$('html, body').animate({scrollTop: top }, 500);
}

/*
    Ajax 화면에서 뒤로가기 눌렀을 시, 스크롤 위치로 이동
    @param
        funcMore => 페이지가 넘어갈 경우 목록을 더 불러오기위해 실행해야 할 메서드.
        search_id => 위치한 행사 태그 ID 를 저장.
 */
// (뒤로가기 눌렀을 시) 스크롤 초기화.
function setMoveScroll(funcMore, search_id, isLast){
    if(search_id != "") {
        setTimeout(function(){
            //var hash = document.location.hash.replace("#", "")
            //var target = $("#"+hash)
            var target=$("#"+search_id)
            if(target.length > 0){
                // 해당 위치로 이동
                var offset = target.offset();
                moveScroll(offset.top - 10)
                // 찾았다면 클리어.
                displayLoading("N");
                return;
            }
            //likeListApp.getLikeListMore()
            if(isLast > 0 ){
            	displayLoading("N");
            	return;
            }
            funcMore();
        }, 300)
    }
}



/**
 * 객체 안에 특정 key 값과 value 를 비교하여 해당값 리턴
 *  # value 안에 배열이 또 있거나 하면 안됨.
 */
function compareKeyObj(list, key, value){
    var result = {}
    for (idx in list){
        var item = list[idx]
        if(list[idx][key] == value){
            result = item
            break;
        }
    }
    return result;
}


/**
 * 배열의 마지막 특정 키값 가져오기.
 *
 *  @param list=> 배열
 *      key => 찾고자하는 키값
 */
function lastKeyValue(list, key){
    if(list.length == 0){
        return "";
    }
    var cnt = list.length
    var last_item = list[cnt - 1];
    var value = last_item[key];
    return value;
}

/**
 * 글 내용중에 해시태그 내용을 a 링크로 감싸기.
 */
function replaceHashTagelink(txt){
    if(txt){
        var tags = [];
        txt = txt.replace(/#[^#\s,;]+/gm, function(tag) {
            tags.push(tag);
            var tag_value = tag.replace(/#/g, '');
            tag_value = tag_value.replace(/<br>/g, '');
            //return '<a href="/tag/get?value=' + tag_value + '">' + tag + '</a>'
            return '<a onclick="clickhashTag(\'' + tag_value + '\')">' + tag + '</a>'
        });
    }
    return txt
}

/**
 * 해시태그 클릭
 */
function clickhashTag(value){
    var url = "/tag/get?value=" + value
    $.get(url, function(res){
        if(res.code =='0000'){
        }else{
            alert(res.message);
            return;
        }
    })
}


/**
 * 로그인 우선 체크
 */
function chkLogin(login_yn){
    if( login_yn != "Y" ){
        alert("로그인이 필요한 서비스 입니다.\n첫화면으로 이동합니다");
        location.href = "/";
        return false;
    }
}



/***************************************************************
 * 공통으로 쓰이는 Function
 ***************************************************************/
/*
    로딩화면 ON/OFF
    @param status: "Y":켜짐 / "N"꺼짐
 */
function displayLoading(status){
    if ( status == "Y" ){
        $(".loading_form").show()
    }else{
        $(".loading_form").hide()
    }
}


/**
 * 이벤트 상세페이지 이동
 */
function moveEventDetail(cultCode){
    var url = "./event/" + cultCode;
    location.href=url;
}

/***************************************************************
	Vue 공통.
***************************************************************/
// 날짜 포멧
Vue.filter('formatDate', function(value){
     if(value){
         return moment(String(value)).format('YYYY.MM.DD')
     }
 })

// 숫자 포멧(자릿수 콤마)
Vue.filter('formatNumberComma', function(value){
    if(value) {
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
})

// textarea 줄바꿈 기능 수정
Vue.filter("replaceTextBr", function(value){
    if(value){
        return value.replace(/<br>/g, "\r\n");
    }
})

// review 테이블의 review_status 값을 한글로 변경
Vue.filter("valueReviewStatus", function(value){
    if(value){
        if(value == "like")
            return "찜하기";
        else if(value == "reading")
            return "읽는중";
        else if(value == "read")
            return "읽음";
    }
})

/************** 템플렛 */
// 	footer
var templateFooter = "<div>";
// templateFooter += '	<div class="info">'
// templateFooter += ' 		<p>문의 : varkiry05@naver.com</p>';
// templateFooter += '	</div>';
templateFooter += '	<div class="footer_link_url">';
templateFooter += '		<ul>';
templateFooter += '			<li>	What are you doing today?</li>';
templateFooter += '		</ul>';
templateFooter += '	</div>';
templateFooter += '	</div>';

Vue.component('footer-component', { template: templateFooter })


// 	로그인 버튼
tempLoginBtn = '';
tempLoginBtn += '<div class="container">'
tempLoginBtn += '	<div class="ext_login_area">'
tempLoginBtn += '		<div class="login_btn_style">'
tempLoginBtn += '			<div class="login_btn_bg2">'
tempLoginBtn += '				<div id="naverIdLogin"></div>'
tempLoginBtn += '			</div>'
tempLoginBtn += '		</div>'
tempLoginBtn += '		<div class="login_btn_style">'
tempLoginBtn += '			<div class="login_btn_bg2">'
tempLoginBtn += '				<a id="kakao-login-btn"></a>'
tempLoginBtn += '			</div>'
tempLoginBtn += '		</div>'
tempLoginBtn += '		<div class="login_btn_style">'
tempLoginBtn += '			<div class="login_btn_bg2">'
tempLoginBtn += '				<div class="fb-login-button" data-max-rows="1" '
tempLoginBtn += '				data-size="large" data-button-type="login_with" '
tempLoginBtn += '				data-show-faces="false" data-auto-logout-link="false" '
tempLoginBtn += '				data-use-continue-as="false"'
tempLoginBtn += '				scope="public_profile,email"'
tempLoginBtn += '				onlogin="checkLoginState()"></div>'
tempLoginBtn += '			</div>'
tempLoginBtn += '		</div>'
tempLoginBtn += '		<div class="login_btn_style">'
tempLoginBtn += '			<div class="login_btn_bg color_google" id="google_join_btn"'
tempLoginBtn += '			onclick="loginGoogle()"'
tempLoginBtn += '			style="width:224px;">'
tempLoginBtn += '				<div>'
tempLoginBtn += '					<img src="/assets/img/logo/logo_google.png" />'
tempLoginBtn += '				</div>'
tempLoginBtn += '				<div class="title colorWhite">Google 로 로그인</div>'
tempLoginBtn += '			</div>'
tempLoginBtn += '		</div>'
tempLoginBtn += '	</div>'
tempLoginBtn += '</div>'

Vue.component('login-btn-component', { template: tempLoginBtn })

function setCookie(cookie_name, value, days) {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + days);
  // 설정 일수만큼 현재시간에 만료값으로 지정
  var cookie_value = escape(value) + ((days == null) ? '' : '; path=/ ; expires=' + exdate.toUTCString());
  document.cookie = cookie_name + '=' + cookie_value;
}

function getCookie(name) {
    var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return v ? v[2] : null;
}

postJSON = function(url,data){
    return $.ajax({url:url,data:JSON.stringify(data),type:'POST',async: false, contentType:'application/json'});
};


function showalert(message, alerttype, listNo) {
	
	$("#alertdiv"+ listNo).remove();
	
    $('#alert_placeholder').append('<div id="alertdiv' + listNo + '" class="alert ' +  alerttype + '" style="display: none;"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>');
    
    $("#alertdiv" + listNo).fadeIn();
    
    setTimeout(function() { 
      $("#alertdiv"+ listNo).fadeOut();
      setTimeout(function(){$("#alertdiv" + listNo).remove()},500);
    }, 3000);
}

function getToday() {
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd<10) {
	    dd = '0'+dd
	} 

	if(mm<10) {
	    mm = '0'+mm
	} 

	today = yyyy + mm + dd ;
	return today;
}

function httpStrTrans(inUrl) {
	inUrl = inUrl + '';
	if(inUrl.trim().substring(0, 4).toUpperCase() != 'HTTP'){
		return 'http://' + inUrl.trim();
	}
	return inUrl;
}
