$(function() {
	
	/*头部导航*/
	$('.header-nav-btn-box').click(function() {
		$('.nav-box').slideToggle('fast', 'linear');
	})
	$('.navtoIntroduce').click(function(){
		console.log(0);
		$('.container').load('introduce.html');
	})
	
})