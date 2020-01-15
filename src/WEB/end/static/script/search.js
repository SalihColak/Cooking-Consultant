function search() {
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  kacheln = document.getElementsByClassName("kachel");

  for (i = 0; i < kacheln.length; i++) {
    a = kacheln[i].getElementsByTagName("a")[0];
    txtValue = a.textContent || a.innerText;

    if (txtValue.toUpperCase().startsWith(filter)) {
      kacheln[i].style.display = "";
    } else {
      kacheln[i].style.display = "none";
    }
  }
}
