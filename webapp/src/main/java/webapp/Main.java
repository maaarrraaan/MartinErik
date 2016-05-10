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
              "<a class='navbar-brand' href='index.html'>Knowledge base</a>"+
            "</div>"+
            "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"+
              "<ul class='nav navbar-nav'>"+
               "<li class='active'><a href='#'>Search <span class='sr-onl'></span></a></li>"+
                "<li><a href='taggning.html'>Texttagging</a></li>"+ 
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
        		"<form class='form-horizontal' class='text-center'>"+
	        	  	 "<div class='form-group form-group-lg'>"+
	        	    	"<div class='col-sm-10'>"+
	        	      	 "<input class='form-control' type='text' id='indata' name='indata' placeholder='Enter search term'>"+
	        	        "</div>"+
	        	     "</div>"+
	        	     "<div class='text-center'>"+
	     	  			"<button type='button' id='search' class='btn btn-primary btn-lg'>Search</button>"+
	     	  		"</div>"+
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
        
        post("/search", (request, response) -> {
            String search = "";
            Carrier carrier = null;
            
            try {
            	
                search = request.queryParams("indata");
               
                Querying q = new Querying();
        		String[] context = {""};
        		carrier = q.querying(search, context,"Person");
        		
            } catch (Exception e) {}
            String result = carrier.toString();
            String img = carrier.getThumbnail();
           
            return result +"|||"+img;
        });
    }
}