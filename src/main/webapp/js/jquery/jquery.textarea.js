/*!
	Autosize v1.17.4 - 2013-08-22
	Automatically adjust textarea height based on user input.
	(c) 2013 Jack Moore - http://www.jacklmoore.com/autosize
	license: http://www.opensource.org/licenses/mit-license.php
*/
(function(e){"function"==typeof define&&define.amd?define(["jquery"],e):e(window.jQuery||window.$)})(function(e){var t,o={className:"autosizejs",append:"",callback:!1,resizeDelay:10},i='<textarea tabindex="-1" style="position:absolute; top:-999px; left:0; right:auto; bottom:auto; border:0; -moz-box-sizing:content-box; -webkit-box-sizing:content-box; box-sizing:content-box; word-wrap:break-word; height:0 !important; min-height:0 !important; overflow:hidden; transition:none; -webkit-transition:none; -moz-transition:none;"/>',n=["fontFamily","fontSize","fontWeight","fontStyle","letterSpacing","textTransform","wordSpacing","textIndent"],s=e(i).data("autosize",!0)[0];s.style.lineHeight="99px","99px"===e(s).css("lineHeight")&&n.push("lineHeight"),s.style.lineHeight="",e.fn.autosize=function(i){return i=e.extend({},o,i||{}),s.parentNode!==document.body&&e(document.body).append(s),this.each(function(){function o(){var t,o;"getComputedStyle"in window?(t=window.getComputedStyle(h),o=h.getBoundingClientRect().width,e.each(["paddingLeft","paddingRight","borderLeftWidth","borderRightWidth"],function(e,i){o-=parseInt(t[i],10)}),s.style.width=o+"px"):s.style.width=Math.max(p.width(),0)+"px"}function a(){var a={};if(t=h,s.className=i.className,c=parseInt(p.css("maxHeight"),10),e.each(n,function(e,t){a[t]=p.css(t)}),e(s).css(a),o(),"oninput"in h&&"setSelectionRange"in h){var r=h.selectionStart;h.value+=" ",h.value=h.value.slice(0,-1),h.setSelectionRange(r,r)}}function r(){var e,n;t!==h?a():o(),s.value=h.value+i.append,s.style.overflowY=h.style.overflowY,n=parseInt(h.style.height,10),s.scrollTop=0,s.scrollTop=9e4,e=s.scrollTop,c&&e>c?(h.style.overflowY="scroll",e=c):(h.style.overflowY="hidden",d>e&&(e=d)),e+=f,n!==e&&(h.style.height=e+"px",w&&i.callback.call(h,h))}function l(){clearTimeout(u),u=setTimeout(function(){var e=p.width();e!==g&&(g=e,r())},parseInt(i.resizeDelay,10))}var c,d,u,h=this,p=e(h),f=0,w=e.isFunction(i.callback),z={height:h.style.height,overflow:h.style.overflow,overflowY:h.style.overflowY,wordWrap:h.style.wordWrap,resize:h.style.resize},g=p.width();p.data("autosize")||(p.data("autosize",!0),("border-box"===p.css("box-sizing")||"border-box"===p.css("-moz-box-sizing")||"border-box"===p.css("-webkit-box-sizing"))&&(f=p.outerHeight()-p.height()),d=Math.max(parseInt(p.css("minHeight"),10)-f||0,p.height()),p.css({overflow:"hidden",overflowY:"hidden",wordWrap:"break-word",resize:"none"===p.css("resize")||"vertical"===p.css("resize")?"none":"horizontal"}),"onpropertychange"in h?"oninput"in h?p.on("input.autosize keyup.autosize",r):p.on("propertychange.autosize",function(){"value"===event.propertyName&&r()}):p.on("input.autosize",r),i.resizeDelay!==!1&&e(window).on("resize.autosize",l),p.on("autosize.resize",r),p.on("autosize.resizeIncludeStyle",function(){t=null,r()}),p.on("autosize.destroy",function(){t=null,clearTimeout(u),e(window).off("resize",l),p.off("autosize").off(".autosize").css(z).removeData("autosize")}),r())})}});

$(document).ready(function(){
	/* textarea height */
	$('textarea').each(function(){
		$(this).autosize();
	});
});