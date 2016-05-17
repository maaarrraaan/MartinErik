package webapp;

import static spark.Spark.*;

import java.util.Map;

import spark.Spark;

public class Main {
    public static void main(String[] args) {
    	Spark.staticFileLocation("res/");
        get("/hello", (req, res) -> 
        "<!DOCTYPE html>"+
        "<html>"+
        "<head>"+
        	"<title>Knowledge base</title>"+
        	"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>"+
        	"<script type='text/javascript' src='jquery-1.12.3.min.js'></script>"+
        	"<script type='text/javascript' src='jquery.js'></script>"+
        	 
		"</script>"+  
        "</head>"+
        "<body>"+
        	"<nav class='navbar navbar-default'>"+
          "<div class='container-fluid'>"+
            "<div class='navbar-header'>"+
              "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1' aria-expanded='false'>"+
                "<span class='sr-only'>Toggle navigation</span>"+
                "<span class='icon-bar'></span>"+
                "<span class='icon-bar'></span>"+
                "<span class='icon-bar'></span>"+
              "</button>"+
              "<a class='navbar-brand' href='/hello'>Knowledge base</a>"+
            "</div>"+
            "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"+
              "<ul class='nav navbar-nav'>"+
               "<li class='active'><a href='/hello'>Search <span class='sr-onl'></span></a></li>"+
                "<li><a href='/taggning'>Texttagging</a></li>"+ 
              "</ul>"+
              "<ul class='nav navbar-nav navbar-right'>"+
                "<li><a href=''#'>Settings</a></li>"+
              "</ul>"+
            "</div>"+
          "</div>"+
        	"</nav>"+
        	"<div class='container'>"+
        		"<br>"+
        		"<br>"+
        		"<br>"+
        		"<div class='text-center' id='result'>"+
        
        		"</div>"+
        		"<br>"+
        		"<br>"+
        		"<form class='form-inline'>"+
        			"<div class='form-group'>"+
        				"<label class='sr-only' for='exampleInputEmail3'>Enter search term</label>"+
        				"<input type='email' class='form-control' id='search' placeholder='Enter search term'>"+
        			"</div>"+
        			"<div class='form-group'>"+
        				"<label class='sr-only' for='exampleInputEmail3'>Enter search term</label>"+
        				"<input type='email' class='form-control' id='context' placeholder='Enter context'>"+
        			"</div>"+
        			"<div class='btn-group'>"+
        				  "<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"+
        				    "Action<span class='caret'></span>"+
        				  "</button>"+
        				  "<ul class='dropdown-menu'>"+
        				    "<li><a href='#'>Action</a></li>"+
        				    "<li><a href='#'>Another action</a></li>"+
        				    "<li><a href='#'>Something else here</a></li>"+
        				    "<li role='separator' class='divider'></li>"+
        				    "<li><a href='#'>Separated link</a></li>"+
        				  "</ul>"+
        				"</div>"+
        			"<button type='button' class='btn btn-primary' id='searchbutton'>Search</button>"+
        		"</form>"+
        	    "<br>"+
        		"<br>"+
        		"<br>"+
        		"<br>"+
        		"<br>"+
        		"<br>"+
          	"</div>"+
        	    
		
			      
        
        "</body>"+
        "</html>"
        
        );
        
        get("/taggning", (req, res) -> 
        "<!DOCTYPE html>"+
        "<html>"+
        "<head>"+
        	"<title>Knowledge base</title>"+
        	"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>"+
        	"<script type='text/javascript' src='jquery-1.12.3.min.js'></script>"+
        	"<script type='text/javascript' src='jquery.js'></script>"+
        	 
		"</script>"+  
        "</head>"+
        "<body>"+
        "<nav class='navbar navbar-default'>"+
        "<div class='container-fluid'>"+
          "<div class='navbar-header'>"+
            "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1' aria-expanded='false'>"+
              "<span class='sr-only'>Toggle navigation</span>"+
              "<span class='icon-bar'></span>"+
              "<span class='icon-bar'></span>"+
              "<span class='icon-bar'></span>"+
            "</button>"+
            "<a class='navbar-brand' href='/hello'>Knowledge base</a>"+
          "</div>"+
          "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"+
            "<ul class='nav navbar-nav'>"+
             "<li><a href='/hello'>Search </a></li>"+
              "<li class='active'><a href='/taggning'>Texttagging<span class='sr-onl'></span></a></li>"+ 
            "</ul>"+
            "<ul class='nav navbar-nav navbar-right'>"+
              "<li><a href=''#'>Settings</a></li>"+
            "</ul>"+
          "</div>"+
        "</div>"+
      	"</nav>"+
			      

   "<br><br><br>"+
	  
 	"<div class='panel panel-default' style='float:left;width:35%; margin-left:30%;' >"+
     "<div class='panel-body' >"+
       "<h2><span class='Donald' style='color:#ff9933;cursor: pointer;'>Donald Trump</span> has won presidential primaries in all five <span class='US' style='color:#ff9933;cursor: pointer;'>US</span> states that voted on Tuesday, while <span class='Hillary' style='color:#ff9933;cursor: pointer;'>Hillary Clinton</span> triumphed in four out of five.</h2><br>"+
       "<h4><span class='Donald' style='color:#ff9933;cursor: pointer;'>Mr Trump</span> called himself <span class='Republican'>the Republican</span> 'presumptive nominee' after victories in <span class='Connecticut' style='color:#ff9933;cursor: pointer;'>Connecticut</span>, <span class='Delaware'>Delaware</span>, <span class='Maryland' style='color:#ff9933;cursor: pointer;'>Maryland</span>, <span class='Pennsylvania' style='color:#ff9933;cursor: pointer;'>Pennsylvania</span> and <span class='Rhode_Island' style='color:#ff9933;cursor: pointer;'>Rhode Island</span>."+
       "The results bring him closer to the number of delegates he needs before the party's national convention in July.For <span class='Democrats'>the Democrats</span>, <span class='Hillary' style='color:#ff9933;;cursor: pointer;'>Mrs Clinton</span> was denied a clean sweep by <span class='Bernie' style='color:#ff9933;cursor: pointer;'>Bernie Sanders</span>.<br><br>The <span class='Vermont' style='color:#ff9933;cursor: pointer;'>Vermont</span> senator won in <span class='Rhode_Island' style='color:#ff9933;cursor: pointer;'>Rhode Island</span> and vowed to fight to the end of the primaries process.Speaking at the <span class='Philadelphia' style='color:#ff9933;cursor: pointer;'>Philadelphia</span> Convention Center after securing the four other states, <span class='Hillary' style='color:#ff9933;cursor: pointer;'>Mrs Clinton</span> said her campaign was setting 'bold, progressive goals' to improve lives in the <span class='US' style='color:#ff9933;cursor: pointer;'>US</span>.'We believe in the goodness of our people and the greatness of our nation,' she said.</h4>"+
     "</div>"+
   "</div>"+
	
   "<div id='res' class='panel panel-default' style='float:right; width:20%; margin-right:14%;' >"+
     "<div class='panel-body' id='result'>"+
     "</div>"+
		
		"<span class='more' id='more' style='visibility: hidden;'>show more</span>"+
		"<span class='less' id='less' style='visibility: hidden;'>show less</span>"+
   "</div>"+
        "</body>"+
        "</html>"
        
        );
        post("/search", (request, response) -> {
            String search = "";
            String context1 = "";
            Carrier carrier = null;
            try {
            	
                search = request.queryParams("indata");
                context1 = request.queryParams("context");
                
                Querying q = new Querying();
        		String[] context = {""};
        		carrier = q.querying(search, context,"Person");
        		
            } catch (Exception e) {}
            return carrier;
        });
    }
}