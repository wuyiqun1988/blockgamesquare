
	/*基础地址*/
	var baseUrl = 'http://192.168.0.115';
	//获取url中的参数
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var reg_rewrite = new RegExp("(^|/)" + name + "/([^/]*)(/|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		var q = window.location.pathname.substr(1).match(reg_rewrite);
		if(r != null) {
			return unescape(r[2]);
		} else if(q != null) {
			return unescape(q[2]);
		} else {
			return '';
		}
	}
	/*判断浏览器类型*/
	var browser = {
		versions: function() {
			var u = navigator.userAgent,
				app = navigator.appVersion;
			return { //移动终端浏览器版本信息
				trident: u.indexOf('Trident') > -1, //IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
				iPad: u.indexOf('iPad') > -1, //是否iPad
				webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
			};
		}(),
		language: (navigator.browserLanguage || navigator.language).toLowerCase()
	};
	alert(000);
	$('.back').click(function() {
		alert(JSON.stringify(browser.versions))
		if(browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
			window.webkit.messageHandlers.goPopBack.postMessage();
		} else if(browser.versions.android) {
			window.android.destroyPage();
		}
	})
