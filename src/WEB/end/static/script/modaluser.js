var modal = document.getElementById("delete-modal");
var close = document.getElementsByClassName("modal-close")[0];
var behalten = document.getElementById("behalten");
var löschen = document.getElementById("löschen");

close.onclick = function() {
  modal.style.display = "none";
};

behalten.onclick = function() {
  modal.style.display = "none";
};

function modalanzeigen(elem) {
  var id = elem.id;
  //console.log(id);

  modal.style.display = "block";
  var parent = elem.parentNode.parentNode.parentNode.parentNode;
  löschen.onclick = function() {
    /*Später löschen DB*/
    window.location.href = "/deleteUser?id=" + id;
  };
}
