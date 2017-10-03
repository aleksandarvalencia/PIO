  function closeNavig() 
    {
        if (document.getElementById("menu_id").value==="1")
        {
             $("#menu_click").click(function(){
                  document.getElementById("menu_items").style.width = "0";
                  document.getElementById("menu_items").style.visibility = "hidden";
                  document.getElementById("menu_id").value="0";
              }); 
       }
       if (document.getElementById("menu_id").value==="0")
       {

           openNavig();
       }
    };

function openNavig() {
    $("#menu_click").click(function(){ // when #showhidecomment is clicked
          document.getElementById("menu_items").style.visibility = "visible";
          document.getElementById("menu_items").style.width = "250px";
          document.getElementById("menu_id").value="1";
        //$("#newtree_b").load( "/New_Menu/newtree_new.jsp");
     }); 

}  ;

function onBodyClick()
{
     
    document.getElementById("menu_items").style.width = "0";
    document.getElementById("menu_items").style.visibility = "hidden";
    document.getElementById("menu_id").value="0";

}
/*
function LogOut()
    {

       //localStorage.clear();
       var form = document.createElement("form");
       var method ="post"; 
       var aa="/PIO/logOut";
       form.setAttribute("method", method);
       form.setAttribute("action", aa);
       document.body.appendChild(form);
       form.submit();

    }


function PIOBeograd()
{
  var form = document.createElement("form");
  var fil=document.getElementById("filijale").value;

  if (document.getElementById("piobeograd").checked)
  {
      tip=1;
  }
  if (document.getElementById("pionovisad").checked)
  {
      tip=2;
  }
  var method ="post"; 
  var duz=fil.length;
  if (duz===1)
  {
     fil="0" + fil;
  }
 
  var aa="/New_Menu/pioKnjizenje/" + fil + "/" + tip;
  form.setAttribute("method", method);
  form.setAttribute("action", aa);
  document.body.appendChild(form);
  form.submit();

}
function PIONoviSad()
{
  var form = document.createElement("form");
  var fil=document.getElementById("filijale").value;

  var method ="post"; 
  var duz=fil.length;
  if (duz===1)
  {
     fil="0" + fil;
  }
  var aa="/PIO/pioKnjizenje/" + fil + "/2";
  form.setAttribute("method", method);
  form.setAttribute("action", aa);
  document.body.appendChild(form);
  form.submit();

}
function CekirajRacune()
{

  var form = document.createElement("form");
  var method ="post"; 
  var aa="/PIO/accountCheck";
  form.setAttribute("method", method);
  form.setAttribute("action", aa);
  document.body.appendChild(form);
  form.submit();
  ShowWaitingProcess();
}
 function GetHeaderInformation()
{
  var form = document.createElement("form");
  var method ="post"; 
  var iznos=document.getElementById("iznospriliva").value;
  var datum=document.getElementById("fileDate").value;
  var aa="/New_Menu/headerInformation/" + datum + "/" + iznos;
  form.setAttribute("method", method);
  form.setAttribute("action", aa);
  document.body.appendChild(form);
  form.submit();
  ShowWaitingProcess();
};

function GetFilijalaDetails()
{

  var form = document.createElement("form");
  var fil=document.getElementById("filijale").value;

  var method ="post"; 
  var duz=fil.length;
  var tip=0;
  if (duz===1)
  {
     fil="0" + fil;
  }

  if (document.getElementById("piobeograd").checked)
  {
      tip=1;
  }
  if (document.getElementById("pionovisad").checked)
  {
      tip=2;
  }


  var aa="/PIO/filijalaDetails/" + fil + "/" + tip;
  form.setAttribute("method", method);
  form.setAttribute("action", aa);
  document.body.appendChild(form);
  form.submit();
  ShowWaitingProcess();
}
function KlikBeograd()
{

     $('#piobeograd').prop('checked', 'checked');
     $('#pionovisad').prop('checked', false);
}
function KlikVojvodina()
{

     $('#pionovisad').prop('checked', 'checked');
     $('#piobeograd').prop('checked', false);

}
function SakriDiv()
{
   document.getElementById('Poruka').style.visibility='hidden';

 }

  function HideWaitingProcess()
{
   document.getElementById('WaitingProcess').style.visibility='hidden';
   document.getElementById('WaitingProcess').style.display='none';
   document.getElementById('loading').style.visibility='hidden';
   document.getElementById('loading').style.display='none';
 }
function ShowWaitingProcess()
{
   document.getElementById('WaitingProcess').style.visibility='visible';
   document.getElementById('loading').style.visibility='visible';

 };
 function SendToBooking()
    {

              var form = document.createElement("form");
              var iznospriliva=document.getElementById("iznospriliva").value;
              var id_k=document.getElementById("idk").value;
              var id_tms=document.getElementById("p_id").value;
              var tip=0;
              if (document.getElementById("piobeograd").checked)
                {
                    tip=1;
                }
                if (document.getElementById("pionovisad").checked)
                {
                    tip=2;
                }
              var method ="post"; 

              var aa="/PIO/sendBooking/" + id_k + "/" + id_tms + "/" + iznospriliva + "/" + tip;
              form.setAttribute("method", method);
              form.setAttribute("action", aa);
              document.body.appendChild(form);
              form.submit();
              ShowWaitingProcess();

    }
    function IzmeniSve()
    {

              var form = document.createElement("form");
              var method ="post"; 

              var aa="/PIO/changeAll/";
              form.setAttribute("method", method);
              form.setAttribute("action", aa);
              document.body.appendChild(form);
              form.submit();
              ShowWaitingProcess();

    }*/