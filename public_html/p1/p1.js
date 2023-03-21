
const form = document.getElementById('registration_form');
const inputs = document.querySelectorAll('#registration_form input');
console.log(form);
console.log(inputs);

var clogin = document.getElementById('clogin');
const clogin_format = /^[a-z0-9]{4,8}$/; /*entre 4 y 8  ascii lowercase letter and numbers*/

var cpasswd = document.getElementById('cpasswd');/*entre 6 y 12  ascii letter and numbers (at least one uppercase letter, one lowercase letter (in both cases ASCII letters only, no accents), one number, and one symbol from set “+-*/
var cpasswd_format = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[+,-,*,/]){6,12}/;

var cdni = document.getElementById('cdni');/*8 digits starting with a number between 0 and 7 and additional non-accented ASCII letter (e.g., 12345678A)*/
var cdni_format = /^[0-7]{1}[0-9]{7}[A-Za-z]{1}$/;

/*Asigno valores a los hidden inputs*/
var browser_info = navigator.appCodeName;
document.getElementById('browser').value=browser_info;

var date = new Date();
var current_date = date.getDate()+"-"+(date.getMonth()+1)+"-"+ date.getFullYear();
var current_time = date.getHours()+":"+date.getMinutes()+":"+ date.getSeconds();
var date_time = current_date+" "+current_time;
document.getElementById('date').value=date_time;

/*Inicializadas a true para que las pueda enviar vacias, una vez escriba algo ya empieza a hacer la comprobacion*/
var validation_result_login = true;
var validation_result_passwd = true;
var validation_result_dni = true;

/*Valida lo que escribamos en los inputs login, passwd y dni cada vez que escribamos pulsemos una tecla  */
const validation = (event) => {
    switch (event.target.name) {
		case "clogin":
            if(clogin_format.test(event.target.value)){
                document.getElementById('in_login').classList.remove('incorrect_in_boxes');
                document.getElementById('in_login').classList.add('correct_in_boxes');
                validation_result_login= true;
            }else{  /*incorrecto*/
                document.getElementById('in_login').classList.remove('correct_in_boxes');
                document.getElementById('in_login').classList.add('incorrect_in_boxes');
                validation_result_login= false;
                if(document.getElementById('in_login').value.length==""){
                    validation_result_login=true;
                }
            }
		break;

		case "cpasswd":
            console.log('entra passwd');
            if(cpasswd_format.test(event.target.value)){
                console.log('entra if passwd');
                document.getElementById('in_passwd').classList.remove('incorrect_in_boxes');
                document.getElementById('in_passwd').classList.add('correct_in_boxes');
                validation_result_passwd= true;
                console.log(validation_result_passwd);
            }else{  /*incorrecto*/
                document.getElementById('in_passwd').classList.remove('correct_in_boxes');
                document.getElementById('in_passwd').classList.add('incorrect_in_boxes');
                validation_result_passwd= false;
                if(document.getElementById('in_passwd').value.length==""){
                    validation_result_passwd=true;
                }
            }
		break;

		case "cdni":
			console.log('entra dni');
            if(cdni_format.test(event.target.value)){
                console.log('entra if cdni');
                document.getElementById('in_dni').classList.remove('incorrect_in_boxes');
                document.getElementById('in_dni').classList.add('correct_in_boxes');
                validation_result_dni=true;
                console.log(validation_result_dni);
            }else{  /*incorrecto*/
                document.getElementById('in_dni').classList.remove('correct_in_boxes');
                document.getElementById('in_dni').classList.add('incorrect_in_boxes');
                validation_result_dni=false;
                if(document.getElementById('in_dni').value.length==""){
                    validation_result_dni=true;
                }
            }
		break;
	}
}

inputs.forEach((input) => {
    input.addEventListener('keyup',validation); 
        console.log('tecla levantada');
    input.addEventListener('blur',validation); 
        console.log('tecla ');
});


/*form.addEventListener('submit', (event) => {
    console.log('entra addEventListener');
})*/


/*FUNCIONES PARA LAS CHECKBOX*/
function check() { /*para que se quite el checkall o el uncheck si los habia pulsado antes*/
    document.getElementById('uncheck_box').checked = false;
    document.getElementById('checkall_box').checked = false;
  }
function check_all() { /*para checkear todas a la vez*/
    checkboxes = document.getElementsByName('cgenre[]');
    document.getElementById('uncheck_box').checked = false;
    for(var i=0, n=checkboxes.length;i<n;i++) {
      checkboxes[i].checked= true;
    }
  }
function uncheck() {   /*para quitar todas las selecciones*/
    checkboxes = document.getElementsByName('cgenre[]');
    for(var i=0, n=checkboxes.length;i<n;i++) {
      checkboxes[i].checked= false;
    }
  }



function get_function(){
    document.getElementById('multipart').disabled = true; /*Si uso get no puedo usar multipart*/
    document.getElementById('registration_form').method="get";
    document.getElementById('registration_form').enctype="application/x-www-form-urlencoded";
}
function post_function(){
    document.getElementById('multipart').disabled = false;
    document.getElementById('registration_form').method="post";
    document.getElementById('registration_form').enctype="multipart/form-data";
}



/*FUNCION PARA ENVIAR LOS 3 INPUTS QUE ANALIZO CON JAVASCRIPT VACIOS O CORRECTOS */
function revision(){
    console.log(validation_result_login);
    console.log(validation_result_passwd);
    console.log(validation_result_dni);

    if (validation_result_login){
        if (validation_result_passwd){
            if(validation_result_dni){
                return true;
            }else{
                console.log("false dni");
                alert("Incorrect DNI format. Please write 8 digits starting with a number between 0 and 7 and an additional non-accented ASCII letter.");
                return false;
            }
        }else{

            console.log("false passwd");
            alert("Incorrect password format. Please write a password with 6-12 characters that includes, at least, one uppercase letter, one lowercase letter, one bumber and one symbol from the set +-/*.");
            return false;
        }
    }else{

        console.log("false login");
        alert("Incorrect login format. Please write a login with 4-8 characters, you can only use lowercase letters and numbers.");
        return false;
    }    
}