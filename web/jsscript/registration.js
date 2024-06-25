/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let username,password,cpassword,city,address,adhar,email,mobile;
/**
 * add
 */
function addUser() {
    username=$("#username").val();
    password=$("#password").val();
    cpassword=$("#cpassword").val();
    city=$("#city").val();
    address=$("#address").val();
    adhar=$("#adhar").val();
    email=$("#email").val();
    mobile=$("#mobile").val();
    if(validateUser()){
        if(password!==cpassword)
        {
            swal("Error!","Password do not Match!","error");
            return ;
        }
        else{
        if(checkEmail()===false)
           return;
        if(checkMobile()===false)
            return;
//        object hai data jo bhejenge dusre usme
        let data ={
                   username:username,
                   password:password,
                   city:city,
                   address:address,
                   userid:adhar,
                   email:email,
                   mobile:mobile
        };
        let xhr=$.post("RegistrationControllerServlet",data,processResponse);       
//        xhr.error(handleError);
          xhr.fail(handleError);
        }
        

        
    }
}
/**
 * ye code shayad ajax ya js ka hoga
 */
function processResponse(responseText,textStatus,xhr)
{
    let str=responseText.trim();
    console.log(str);
    if(str==="success"){
        swal("Success!","Registration done successfully! you can now login","success");
//          window.location="login.html"; we cannot write this ye sath me chal jayega apna chate ha wo pehle padh le or 3 second tak user click kar lega 
        setTimeout(redirectUser,3000);
    }
    else if(str==="uap")
            swal("Error!","Sorry! the userid is already present!","error");
    else
        swal("Error!","Registration failed! Try again","error");
    

}
/**
 * Comment
 */
function handleError(xhr) {
    swal("Error","Problem in server communication",+xhr.statusText,"error");
}

/**
 *validateUser check all field are not empty
 */
function validateUser() 
{
    if(username==="" || password==="" || cpassword==="" || city==="" || address==="" || adhar==="" ||  email==="" || mobile===""){
        swal("Error!","All fields are mandatory!","error");
        return false;
    }
    return true;
}

/**
 * ye check karega email correct format ho
 */
function checkEmail() 
{
 let attheratepos=email.indexOf("@");
 let dotpos=email.indexOf(".");
// @gmail.com-ye galat ha || @ ke turant dot bhi ana galat ha (@.)|| dot position last me nahi(@gmail.com,.in ye sahi ha @gmali.a ye galat ha)
 if(attheratepos<1 || dotpos<attheratepos+2 || dotpos+2>=email.length)
 {
        swal("Error!","Please enter a valid email!","error");
        return false;
 }
 return true;
}

//ye check karega mobile correct format me ho
function checkMobile()
{
    if(Number.isInteger(mobile)===true || mobile.toString().length!==10){
         swal("Error!","Please enter a valid Mobile Number!","error");
        return false;
    }
    return true;
}

/**
 * ye func kuch 3 second baad chalega, is function ke andar dusra window(login.html page)open karne ka code ha
 */
function redirectUser() {
    window.location="login.html";
}