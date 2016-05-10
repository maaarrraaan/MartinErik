$(document).ready(function() {
    $("#search").click(function(){
    		$img = "<img src='ajax-loader.gif' /><br><br>"
    		$("#result").html($img);
            $indata = document.getElementById("indata").value;
            $.post("search", {indata:$indata}, function(data) {
            	var r = data.split("|||")
            	var t = r[1]
            	var image = "<img src='"+t+"' /><br>"
                $("#result").html(image+r[0]);
            });
        });
});