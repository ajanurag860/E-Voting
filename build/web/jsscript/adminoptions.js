 

function redirectadministratorpage(){
    swal("Admin!","Rediecting to admin action page!","success").then(value=>{
        window.location="adminactions.jsp";
    });
}
function redirectvotingpage(){
    swal("Admin!","Rediecting to Voting controller page!","success").then(value=>{
        window.location="VotingControllerServlet";
    });
}
//Adminactions.jsp call this method
function manageuser(){
    swal("Admin!","Rediecting to user Management  page!","success").then(value=>{
        window.location="manageuser.jsp";
    });
}
function managecandidate(){
    swal("Admin!","Rediecting to Candidate management page!","success").then(value=>{
        window.location="managecandidate.jsp";
    });
}


function showaddcandidateform()
{   
removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px red");
newdiv.innerHTML="<h3>Add new Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
<table><tr><th>Candidate Id:</th><td><input type='text' id='cid'></td></tr>\n\
\n\<tr><th>User Id:</th><td><input type='text' id='uid' onKeyPress='return getdetails(event)'></td></tr>\n\
\n\ <tr><th>Candidate Name:</th><td><input type='text' id='cname'></td></tr>\n\
\n\<tr><th>City:</th><td><select id='city'></select></td></tr>\n\
 <tr><th>Party:</th><td><input type='text' id='party'></td></tr>\n\
 <tr><td colspan='2'><input type='file' name='files' value='Select Image'></td></tr>\n\
 <tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th></tr>\n\
 <tr><th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
var addcand=$("#result")[0];
addcand.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
data={id:"getid"};
  $.post("AddCandidateControllerServlet",data,function(responseText){
       $("#cid").val(responseText);
       // to disable editing of candidate number by the user
       $("#cid").prop("disabled",true);
       
      });
}
function showupdatecandidateform(){ 
    removecandidateForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Update Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<div style='color:white;font-weight:bold'>Candidate Id:</div><div><select id='cid'></select></div>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";

    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = {data: "cid"};
    $.post("UpdateCandidateControllerServlet", data, function (responseText) {
        var candidlist = JSON.parse(responseText);
        $("#cid").append(candidlist.cid);
    });
    $("#cid").change(function () {
        var cid = $("#cid").val();
        if (cid === ''){
            swal("No selection!", "Please select an id", "error");
            return;
        }
//         is bar cid ke value jayegei
        data = {data: cid};
        $.post("UpdateCandidateControllerServlet", data, function (responseText) {
            clearText();
            var details = JSON.parse(responseText);
            $("#addresp").append(details.subdetails);
            
        });
    });

}
function updatecandidate(){
 var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    var cid=$("#cid").val();
    var cname=$("#name").val();
    var city=$("#city").val();
    var party=$("#party").val();
    data.append("cid",cid);
    data.append("cname",cname);
    data.append("party",party);
    data.append("city",city);
    $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "UpdateCandidateControllerServlet",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                str=data.trim();
                console.log("responseText in added "+str);
                if(str=="success"){
                swal("Admin!","candidate Updated sucessfully", "success").then((value)=>{
               showupdatecandidateform();
    })}
else{
    swal("Admin!","Candidate cannot elect from selected party in selected city", "error")
}
    },
error: function (e) {
swal("Admin!", e, "success");
}
           
    });
    }
/**
 * Comment
 */
function clearText() {
    $("#addresp").html("");
}

function getdetails(e){
    if(e.keyCode===13){
        data={uid:$("#uid").val()};
        $.post("AddCandidateControllerServlet",data,function(responseText)
        {
            let details=JSON.parse(responseText);
            let city=details.city;
            let uname=details.username;
            if(uname==="wrong")
                swal("wrong Adhar No!","Adhaar no is invalid!","error");
            else{
                $("#cname").val(uname);
                $("#city").empty();
                $("#city").append(city);
                $("#cname").prop("disabled",true);
            }
              
        });
    }
}
/**
 * addcandidate
 */
function addcandidate()
{
    var form = $('#fileUploadForm')[0];
    // FormData(form name)---This is a class of JS which can retrieve binary data from the name of the form given in the argument (in our case-(form))
    var data = new FormData(form);
    var cid = $("#cid").val();
    var cname = $("#cname").val();
    var city = $("#city").val();
    var party = $("#party").val();
    var uid = $("#uid").val();
    data.append("cid", cid);
    data.append("uid", uid);
    data.append("cname", cname);
    data.append("party", party);
    data.append("city", city);
    $.ajax({
        type: "POST",
        enctype: 'mutlipart/form-data',
        url: "AddNewCandidateControllerServlet",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000, //for bulky data 
        success: function (data) {
            str = data + "....";
            swal("Admin!", str, "success").then((value) => {
                showaddcandidateform();
            });
        }, error: function (e) {
            swal("admin!", e, "error");
        }
    });
}


function removecandidateForm(){
var contdiv = document.getElementById("result");
var formdiv=document.getElementById("candidateform");
if(formdiv!==null){
$("#candidateform").fadeOut("3500");
  contdiv.removeChild(formdiv);
  }
}


/**
 * showcandidate()
 */
function showcandidate()
{
removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px red");
newdiv.innerHTML="<h3>Show Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<div style='color:white;font-weight:bold'>Candidate Id:</div><div><select id='cid'></select></div>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";

var addcand=$("#result")[0];
addcand.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
data={data:"cid"};
  $.post("ShowCandidateControllerServlet",data,function(responseText){
      var candidlist = JSON.parse(responseText); 
      $("#cid").append(candidlist.cid);
      });
      $("#cid").change(function(){
         var cid = $("#cid").val();
         if(cid===''){
             swal("No selection!","Please select an id","error");
             return;
         }
//         is bar cid ke value jayegei
data={data:cid};
  $.post("ShowCandidateControllerServlet",data,function(responseText){
      clearText();
      var details = JSON.parse(responseText); 
      $("#addresp").append(details.subdetails);
      });
      }); 
     
}

function electionresult(){
    $.post("ElectionResultControllerServlet",null,function(responseText){
     swal("Result fetched","success","success");
     $("#result").html(responseText.trim());
        
        
    });
}