function addFields() {
  var number = document.getElementById("member").value;
  var start = document.getElementById("start");
  var erzeugen = document.getElementById("erzeugen");
  erzeugen.innerHTML = "";
  if (number <= 20 && number > 0) {
    while (start.hasChildNodes()) {
      start.removeChild(start.lastChild);
    }
    for (i = 0; i < number; i++) {
      var divtest = document.createElement("div");
      divtest.setAttribute("class", "columns is-vcentered");
      var columnName = document.createElement("div");
      columnName.setAttribute("class", "column is-3");
      divtest.appendChild(columnName);
      var content = document.createElement("div");
      content.setAttribute("class", "content has-text-right");
      columnName.appendChild(content);
      var title = document.createElement("div");
      title.setAttribute("class", "title is-5");
      title.innerHTML = "Zutat " + (i + 1) + ":";
      content.appendChild(title);

      var columnInput = document.createElement("div");
      columnInput.setAttribute("class", "column is-3");
      divtest.appendChild(columnInput);
      var input = document.createElement("input");
      input.setAttribute("type", "text");
      input.setAttribute("class", "input is-rounded");
      input.setAttribute("placeholder", "Milch");
      input.setAttribute("id", "zutatName" + (i + 1));
      input.setAttribute("name", "zutatName" + (i + 1));
      input.setAttribute("required", "");
      columnInput.appendChild(input);

      var columnInput = document.createElement("div");
      columnInput.setAttribute("class", "column is-2");
      divtest.appendChild(columnInput);
      var input = document.createElement("input");
      input.setAttribute("type", "text");
      input.setAttribute("class", "input is-rounded");
      input.setAttribute("placeholder", "Menge");
      input.setAttribute("id", "zutatMenge" + (i + 1));
      input.setAttribute("name", "zutatMenge" + (i + 1));
      input.setAttribute("required", "");
      columnInput.appendChild(input);

      var columnInput = document.createElement("div");
      columnInput.setAttribute("class", "column is-2");
      divtest.appendChild(columnInput);
      var input = document.createElement("input");
      input.setAttribute("type", "text");
      input.setAttribute("class", "input is-rounded");
      input.setAttribute("placeholder", "Einheit");
      input.setAttribute("id", "zutatEinheit" + (i + 1));
      input.setAttribute("name", "zutatEinheit" + (i + 1));
      input.setAttribute("required", "");
      columnInput.appendChild(input);

      start.appendChild(divtest);
    }
  } else {
    var notification = document.createElement("p");
    notification.setAttribute("class", "has-text-danger");
    notification.innerHTML = "Ungültig!";
    erzeugen.appendChild(notification);
  }
}

function addTextarea() {
  var number = document.getElementById("memberText").value;
  var start = document.getElementById("startText");
  var erzeugen = document.getElementById("erzeugenText");
  erzeugen.innerHTML = "";
  if (number <= 10 && number > 0) {
    while (start.hasChildNodes()) {
      start.removeChild(start.lastChild);
    }

    for (i = 0; i < number; i++) {
      var divtest = document.createElement("div");
      divtest.setAttribute("class", "columns ");
      var columnName = document.createElement("div");
      columnName.setAttribute("class", "column is-3");
      divtest.appendChild(columnName);
      var content = document.createElement("div");
      content.setAttribute("class", "content has-text-right");
      columnName.appendChild(content);
      var title = document.createElement("div");
      title.setAttribute("class", "title is-5");
      title.innerHTML = "Schritt " + (i + 1) + ":";
      content.appendChild(title);

      var columnInput = document.createElement("div");
      columnInput.setAttribute("class", "column is-5");
      divtest.appendChild(columnInput);
      var input = document.createElement("textarea");
      input.setAttribute("class", "textarea");
      input.setAttribute("placeholder", "Schritt");
      input.setAttribute("rows", "5");
      input.setAttribute("id", "schritt" + (i + 1));
      input.setAttribute("name", "schritt" + (i + 1));
      input.setAttribute("required", "");
      columnInput.appendChild(input);

      start.appendChild(divtest);
    }
  } else {
    var notification = document.createElement("p");
    notification.setAttribute("class", "has-text-danger");
    notification.innerHTML = "Ungültig!";
    erzeugen.appendChild(notification);
  }
}
