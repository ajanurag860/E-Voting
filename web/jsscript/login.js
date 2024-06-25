let userid,password;

function connectUser() {
    userid=$("#username").val();
    password=$("#password").val();
    if(validate()===false){
         swal("Access Denied","please enter userid/password","error");
         return;
    }
    let data ={ userid:userid,password:password};          
        let xhr=$.post("LoginControllerServlet",data,processResponse);       
//        xhr.error(handleError);
          xhr.fail(handleError);
       
}
function processResponse(responseText,textStatus,xhr)
{

    if(responseText.trim()==='error'){
        swal("Access Denied","Invalid userid/password","error");
    }
    else if(responseText.trim().indexOf("jsessionid")!==-1){
// if the credentials are correct,then there will be jsession id for sure.        
//        pr is an built in object of promises
            let pr=swal("Success","Login successful","success");     
// fun1 jab chalega jab upar wala successful chal jayega nahi to fun2
//        pr.then(fun1,fun2);   
        pr.then((value)=>{
            window.location=responseText.trim();
        });
    }
    else{
        swal("Access Denied","Some problem occurred:"+responseText,"error");
    }

}

function handleError(xhr)
{
    swal("Error","Problem in server communication"+xhr.statusText,"error");
}
function validate() 
{
    if(userid==="" || password===""){
        swal("Error!","All fields are mandatory","error");
        return false;
    }
    return true;
}
