//var label = document.getElementById(".inputfile");
var input = document.getElementById("fileid");
var label = input.nextElementSibling;
var labelVal = label.innerHTML;

console.log(label);
console.log(input);
console.log(labelVal);

input.addEventListener("change", function(e) {
  var fileName = "";

  fileName = e.target.value.split("\\").pop();
  console.log(fileName);

  if (fileName)
    label.innerHTML =
      '<i class="fa fa-cloud-upload" aria-hidden="true"></i> ' + fileName;
  else label.innerHTML = labelVal;
});
