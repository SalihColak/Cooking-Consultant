var val = document.getElementById("artDB").value;
document.querySelector(
  '#sel [value="' + val.toUpperCase() + '"]'
).selected = true;

var val1 = document.getElementById("anlassDB").value;
document.querySelector(
  '#sel1 [value="' + val1.toUpperCase() + '"]'
).selected = true;

var val2 = document.getElementById("praeferenzDB").value;
document.querySelector(
  '#sel2 [value="' + val2.toUpperCase() + '"]'
).selected = true;
