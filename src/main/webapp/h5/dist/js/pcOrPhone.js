var userAgentInfo = navigator.userAgent;
var Agents = ["Android", "iPhone",
	"SymbianOS", "Windows Phone",
	"iPad", "iPod"
];
var flag = true;
for(var v = 0; v < 6; v++) {
	if(userAgentInfo.indexOf(Agents[v]) > 0) {
		flag = false;
		break;
	};
};
if(flag) {
	window.location.href='../pc/index.html'
};